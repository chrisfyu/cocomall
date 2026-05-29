package com.atguigu.cocomall.ware.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.atguigu.cocomall.ware.entity.WareOrderTaskDetailEntity;
import com.atguigu.cocomall.ware.entity.WareOrderTaskEntity;
import com.atguigu.cocomall.ware.feign.OrderFeignService;
import com.atguigu.cocomall.ware.service.WareOrderTaskDetailService;
import com.atguigu.cocomall.ware.service.WareOrderTaskService;
import com.atguigu.cocomall.ware.vo.OrderVo;
import com.atguigu.common.exception.NoStockException;
import com.atguigu.cocomall.ware.feign.ProductFeignService;
import com.atguigu.cocomall.ware.vo.OrderItemVo;
import com.atguigu.cocomall.ware.vo.SkuHasStockVo;
import com.atguigu.cocomall.ware.vo.WareSkuLockVo;
import com.atguigu.common.to.mq.StockDetailTo;
import com.atguigu.common.to.mq.StockLockedTo;
import com.atguigu.common.utils.R;
import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.cocomall.ware.dao.WareSkuDao;
import com.atguigu.cocomall.ware.entity.WareSkuEntity;
import com.atguigu.cocomall.ware.service.WareSkuService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    WareSkuDao wareSkuDao;

    @Autowired
    ProductFeignService productFeignService;

    @Autowired
    WareOrderTaskService orderTaskService;

    @Autowired
    WareOrderTaskDetailService orderTaskDetailService;

    @Autowired
    OrderFeignService orderFeignService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<WareSkuEntity> queryWrapper = new QueryWrapper<>();
        String skuId = (String) params.get("skuId");
        if(!StringUtils.isEmpty(skuId)){
            queryWrapper.eq("sku_id",skuId);
        }

        String wareId = (String) params.get("wareId");
        if(!StringUtils.isEmpty(wareId)){
            queryWrapper.eq("ware_id",wareId);
        }


        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
        //1、判断如果还没有这个库存记录新增
        List<WareSkuEntity> entities = wareSkuDao.selectList(new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId));
        if(entities == null || entities.size() == 0){
            WareSkuEntity skuEntity = new WareSkuEntity();
            skuEntity.setSkuId(skuId);
            skuEntity.setStock(skuNum);
            skuEntity.setWareId(wareId);
            skuEntity.setStockLocked(0);
            //TODO 远程查询sku的名字，如果失败，整个事务无需回滚
            //1、自己catch异常
            //TODO 还可以用什么办法让异常出现以后不回滚？高级
            try {
                R info = productFeignService.info(skuId);
                Map<String,Object> data = (Map<String, Object>) info.get("skuInfo");

                if(info.getCode() == 0){
                    skuEntity.setSkuName((String) data.get("skuName"));
                }
            } catch (Exception e){

            }

            wareSkuDao.insert(skuEntity);
        }else{
            wareSkuDao.addStock(skuId,wareId,skuNum);
        }
    }

    @Override
    public List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds) {

        List<SkuHasStockVo> collect = skuIds.stream().map(skuId -> {
            SkuHasStockVo vo = new SkuHasStockVo();

            Long count = baseMapper.getSkuStock(skuId);

            vo.setSkuId(skuId);
            vo.setHasStock(count == null? false : count > 0);
            return vo;
        }).collect(Collectors.toList());

        return collect;
    }

    /**
     * 为某个订单锁定库存
     * rollbackFor = NoStockException.class
     *
     * 库存解锁场景
     * 1、下单成功，过期没支付被系统取消，被用户手动取消。
     * 2、下单成功，库存锁定成功，后续业务调用失败，导致订单回滚。需要解锁库存。
     *
     * @param vo
     * @return
     */
    @Transactional
    @Override
    public Boolean orderLockStock(WareSkuLockVo vo) {

        // 保存工作单详情，追溯。
        WareOrderTaskEntity taskEntity = new WareOrderTaskEntity();
        taskEntity.setOrderSn(vo.getOrderSn());
        orderTaskService.save(taskEntity);

        // 1、找到每个商品在哪个仓库都有库存
        List<OrderItemVo> locks = vo.getLocks();

        List<SkuWareHasStock> collect = locks.stream().map(item -> {
            SkuWareHasStock stock = new SkuWareHasStock();
            Long skuId = item.getSkuId();
            stock.setSkuId(skuId);
            stock.setNum(item.getCount());
            // 查询这个商品在哪里有库存
            List<Long> wareIds = wareSkuDao.listWareIdHasSkuStock(skuId);
            stock.setWareId(wareIds);

            return stock;
        }).collect(Collectors.toList());

        // 2、锁定库存
        for (SkuWareHasStock hasStock : collect) {
            Boolean skuStocked = false;
            Long skuId = hasStock.getSkuId();
            List<Long> wareIds = hasStock.getWareId();
            if (wareIds == null || wareIds.size() == 0) {
                // 没有任何仓库有这个商品的库存
                throw new NoStockException(skuId);
            }

            //1、如果每一个商品都锁定成功,将当前商品锁定了几件的工作单记录发给MQ
            //2、锁定失败。前面保存的工作单信息都回滚了。发送出去的消息，即使要解锁库存，由于在数据库查不到指定的id，所有就不用解锁
            for (Long wareId : wareIds) {
                // 看几行受影响，成功1行受影响，否则0行受影响
                Long count = wareSkuDao.lockSkuStock(skuId, wareId, hasStock.getNum());

                if (count == 1) {
                    skuStocked = true;
                    // TODO 告诉MQ库存锁定成功
                    WareOrderTaskDetailEntity entity = new WareOrderTaskDetailEntity(null, skuId, "", hasStock.getNum(), taskEntity.getId(), wareId, 1);
                    orderTaskDetailService.save(entity);
                    StockLockedTo lockedTo = new StockLockedTo();
                    lockedTo.setId(taskEntity.getId());

                    // 只发id不行，防止回滚以后找不到数据
                    StockDetailTo stockDetailTo = new StockDetailTo();
                    BeanUtils.copyProperties(entity, stockDetailTo);
                    lockedTo.setDetail(stockDetailTo);

                    rabbitTemplate.convertAndSend("stock-event-exchange", "stock.locked", lockedTo);

                    break;
                } else {

                }
            }

            // 当前商品所有仓库都没有锁住
            if (!skuStocked) {
                throw new NoStockException(skuId);
            }
        }

        return true;
    }

    @Override
    public void unlockStock(StockLockedTo to) {

            /**
             * 解锁
             * 1、查询数据库关于这个订单锁定库存信息
             *   有：证明库存锁定成功了
             *      解锁：订单状况
             *          1、没有这个订单，必须解锁库存
             *          2、有这个订单，不一定解锁库存
             *              订单状态：已取消：解锁库存
             *                      没取消：不能解锁库存
             *   没有工作单：库存锁定失败，库存回滚了，无需解锁
             */
            StockDetailTo detail = to.getDetail();
            Long detailId = detail.getId();

            WareOrderTaskDetailEntity byId = orderTaskDetailService.getById(detailId);
            if (byId != null) {
                // 解锁
                Long id = to.getId();
                WareOrderTaskEntity taskEntity = orderTaskService.getById(id);
                String orderSn = taskEntity.getOrderSn();
                R r = orderFeignService.getOrderStatus(orderSn);
                if (r.getCode() == 0) {
                    // 订单数据返回成功
                    OrderVo data = r.getData(new TypeReference<OrderVo>() {
                    });
                    if (data == null || data.getStatus() == 4) {
                        // 订单不存在或者订单已取消
                        // 当前库存工作单详情状态是1（已锁定）才可以解锁
                        if (byId.getLockStatus() == 1) {
                            unLockStock(detail.getSkuId(), detail.getWareId(), detail.getSkuNum(), detailId);
                        }
                    }
                } else {
                    // 消息拒绝以后重新放到队列里面，让别人继续消费解锁。
                    throw new RuntimeException("远程服务失败");
                }
            } else {
                // 无需解锁
            }
    }

    private void unLockStock(Long skuId, Long wareId, Integer num, Long taskDetailId) {
        // 库存解锁
        wareSkuDao.unlockStock(skuId, wareId, num);
        // 更新库存工作单的状态
        WareOrderTaskDetailEntity entity = new WareOrderTaskDetailEntity();
        entity.setId(taskDetailId);
        entity.setLockStatus(2);
        orderTaskDetailService.updateById(entity);
    }

    @Data
    class SkuWareHasStock {
        private Long skuId;
        private Integer num;
        private List<Long> wareId;
    }

}