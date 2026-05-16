package com.atguigu.cocomall.thirdparty;

import com.aliyun.oss.OSSClient;
import com.atguigu.cocomall.thirdparty.component.SmsComponent;
import com.atguigu.cocomall.thirdparty.util.HttpUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CocomallThirdPartyApplicationTests {


    @Autowired
    OSSClient ossClient;

    @Autowired
    SmsComponent smsComponent;

    @Test
    public void testSendCode() {
        smsComponent.sendSmsCode("135xxxxxxxx", "161218");
    }

    @Test
    public void sendSms() {
            String host = "https://dfsns.market.alicloudapi.com";
            String path = "/data/send_sms";
            String method = "POST";
            String appcode = "c4c82c1a95774b15a63b022d0984aae8";
            Map<String, String> headers = new HashMap<String, String>();
            //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
            headers.put("Authorization", "APPCODE " + appcode);
            //根据API的要求，定义相对应的Content-Type
            headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            Map<String, String> querys = new HashMap<String, String>();
            Map<String, String> bodys = new HashMap<String, String>();
            bodys.put("content", "code:161125");
            bodys.put("template_id", "CST_ptdie100");  //注意，CST_ptdie100该模板ID仅为调试使用，调试结果为"status": "OK" ，即表示接口调用成功，然后联系客服报备自己的专属签名模板ID，以保证短信稳定下发
            bodys.put("phone_number", "135xxxxxxxx");


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

    @Test
    public void contextLoads() {
    }

    @Test
    public void testUpload() throws FileNotFoundException {
//        // Endpoint以东京为例，其它Region请按实际情况填写。
//        String endpoint = "oss-ap-northeast-1.aliyuncs.com";
//        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
//        String accessKeyId = "L**********H";
//        String accessKeySecret = "B*********************k";
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = new FileInputStream("C:\\Users\\yufei\\Desktop\\1.jpg");

        ossClient.putObject("cocomall-bucket", "dgf.jpg", inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        System.out.println("上传成功...");
    }

}
