package com.atguigu.cocomall.auth.controller;

import com.alibaba.fastjson.TypeReference;
import com.atguigu.cocomall.auth.feign.MemberFeignService;
import com.atguigu.cocomall.auth.feign.ThirdPartyFeignService;
import com.atguigu.cocomall.auth.vo.UserResgisterVo;
import com.atguigu.common.constant.AuthServerConstant;
import com.atguigu.common.exception.BizCodeEnume;
import com.atguigu.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/12 22:22
 */

@Controller
public class LoginController {

    @Autowired
    ThirdPartyFeignService thirdPartyFeignService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    MemberFeignService memberFeignService;

    @ResponseBody
    @PostMapping("/sms/sendcode")
    public R sendCode(@RequestParam("phone") String phone) {

        String redisCode = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
        if (!StringUtils.isEmpty(redisCode)) {
            long l = Long.parseLong(redisCode.split("_")[1]);
            if (System.currentTimeMillis() - l < 60000) {
                return R.error(BizCodeEnume.SMS_CODE_EXCEPTION.getCode(), BizCodeEnume.SMS_CODE_EXCEPTION.getMsg());
            }
        }

////        String code = UUID.randomUUID().toString().substring(0, 5);
        // TODO 修复短信验证码吗feign报错
        String code = "123456";
//        String code = String.valueOf((int)((Math.random() * 9 + 1) * Math.pow(10,5)));

        String substring = code + "_" + System.currentTimeMillis();

        redisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone, substring, 10, TimeUnit.MINUTES);

        thirdPartyFeignService.sendCode(phone, code);

        return R.ok();
    }

    @PostMapping("/register")
    public String register(@Valid UserResgisterVo vo, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {

            Map<String, String> errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
//            model.addAttribute("errors", errors);
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:http://auth.cocomall.com/reg.html";
        }

        // 1、校验验证码
        String code = vo.getCode();

        String s = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
        if (!StringUtils.isEmpty(s)) {
            if (code.equals(s.split("_")[0])) {
                // 删除验证码
                redisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());

                R r = memberFeignService.register(vo);
                if (r.getCode() == 0) {
                    // 注册成功回到登录页
                    return "redirect:http://auth.cocomall.com/login.html";
                } else {
                    Map<String, String> errors = new HashMap<>();
                    errors.put("msg", r.getData(new TypeReference<String>(){}));
                    redirectAttributes.addFlashAttribute("errors", errors);
                    return "redirect:http://auth.cocomall.com/reg.html";
                }

            } else {
                Map<String, String> errors = new HashMap<>();
                errors.put("code", "验证码错误");
                redirectAttributes.addFlashAttribute("errors", errors);
                return "redirect:http://auth.cocomall.com/reg.html";
            }

        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("code", "验证码错误");
            redirectAttributes.addFlashAttribute("errors", errors);
            // 校验出错，转发到注册页
            return "redirect:http://auth.cocomall.com/reg.html";
        }
    }
}
