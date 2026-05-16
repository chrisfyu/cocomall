package com.atguigu.cocomall.thirdparty.component;

import com.atguigu.cocomall.thirdparty.util.HttpUtils;
import lombok.Data;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/16 11:35
 */

@ConfigurationProperties(prefix = "spring.cloud.alicloud.sms")
@Data
@Component
public class SmsComponent {

    private String host;
    private String path;
    private String templateId;
    private String appcode;

    public void sendSmsCode(String phone, String code) {
        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:" + code);
        bodys.put("template_id", templateId);  //注意，CST_ptdie100该模板ID仅为调试使用，调试结果为"status": "OK" ，即表示接口调用成功，然后联系客服报备自己的专属签名模板ID，以保证短信稳定下发
        bodys.put("phone_number", phone);

        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity,"UTF-8");
            System.out.println(result);
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
