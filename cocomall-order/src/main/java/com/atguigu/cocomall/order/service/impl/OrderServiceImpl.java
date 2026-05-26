package com.atguigu.cocomall.order.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.atguigu.cocomall.order.feign.CartFeignService;
import com.atguigu.cocomall.order.feign.MemberFeignService;
import com.atguigu.cocomall.order.feign.WareFeignService;
import com.atguigu.cocomall.order.interceptor.LoginUserInterceptor;
import com.atguigu.cocomall.order.vo.MemberAddressVo;
import com.atguigu.cocomall.order.vo.OrderConfirmVo;
import com.atguigu.cocomall.order.vo.OrderItemVo;
import com.atguigu.cocomall.order.vo.SkuStockVo;
import com.atguigu.common.utils.R;
import com.atguigu.common.vo.MemberRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.cocomall.order.dao.OrderDao;
import com.atguigu.cocomall.order.entity.OrderEntity;
import com.atguigu.cocomall.order.service.OrderService;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    MemberFeignService memberFeignService;

    @Autowired
    CartFeignService cartFeignService;

    @Autowired
    WareFeignService wareFeignService;

    @Autowired
    ThreadPoolExecutor executor;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException {
        OrderConfirmVo confirmVo = new OrderConfirmVo();
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        // 获取之前的请求
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        CompletableFuture<Void> getAddressFuture = CompletableFuture.runAsync(() -> {
            // 1、远程查询所有的收货地址列表
            // 共享之前的请求数据
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<MemberAddressVo> address = memberFeignService.getAddress(memberRespVo.getId());
            confirmVo.setAddress(address);
        }, executor);


        CompletableFuture<Void> cartFuture = CompletableFuture.runAsync(() -> {
            // 2、远程查询购物车所有选中的购物项
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<OrderItemVo> items = cartFeignService.getCurrentUserCartItems();
            confirmVo.setItems(items);
        }, executor).thenRunAsync(() -> {
            List<OrderItemVo> items = confirmVo.getItems();
            List<Long> collect = items.stream().map(item -> item.getSkuId()).collect(Collectors.toList());

            R hasStock = wareFeignService.getSkuHasStock(collect);
            List<SkuStockVo> data = hasStock.getData(new TypeReference<List<SkuStockVo>>() {
            });
            if (data != null) {
                Map<Long, Boolean> map = data.stream().collect(Collectors.toMap(SkuStockVo::getSkuId, SkuStockVo::getHasStock));
                confirmVo.setStocks(map);
            }
        }, executor);

        // 3、查询用户积分
        Integer integration = memberRespVo.getIntegration();
        confirmVo.setIntegration(integration);

        // 4、其他数据自动计算

        // TODO 5、防重令牌

        CompletableFuture.allOf(getAddressFuture, cartFuture).get();

        return confirmVo;
    }

}