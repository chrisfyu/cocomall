package com.atguigu.cocomall.order.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.atguigu.cocomall.order.vo.PayVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    //在支付宝创建的应用的id
    private   String app_id = "2016092200568607";

    // 商户私钥，您的PKCS8格式RSA2私钥
    private  String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCvZQ2XJ0kDOq4WiBoy2LCdgTdb5SMan7sFZtStjFmsqxoZuUd8ntuPikcZKXAAZcNcXDc99Ltkt+i2fMdzvg1Ung0cwKX6wqyBLU10lkMOUMs4hVl0MhW3Ihx8D9+kZIEg1yAbxuUrF8cV81Or+qxpnJ4iwNVwkOPrUOp0n8HTpVX1mjYYjkXhM/LtvVZ+IFc4yq+tVY0tTgsVPYcCtaKoYUzhSmncgkpjdMjthy4eTSje/hDMgnLFUsDs0cfdWLN8pVTxxiipr+Y/+Ax3mCfyBEtFuYn0OXKXXjHEp+WX/iQ6yqTdyhcYkxGWD4jIqP+g7mMrP7vKsbkquQOzxayTAgMBAAECggEACmL4mA/qgfdyocDzlDlC1ED3r0h1eLkm0R4S0Cg0k0YaqJVRR27835Y3uaS7jjp4hDqtxsx8YG2HqW7gPNlvXqhxbFd4PM5Uet3c7V+Mnwdn0XQMJRZmNM8fUrV57/lHsFMtApgXsCKbVpBvTwrsNODieHpk6WKbLK9BAyEG0Gqrv1xCTD55NDyr+mdKXINt5ZDaP8R2DeNrXrTrU7+qGsM7Qfsz8429egEZx/1LnCA7PeMRZvHsSex9KZtpaXYHFGMqgSAH93yKj+rW685+KwmGSK4q0ezfGtQR7V1T6r71inwR5dQzWyTyN1ZJw0ZVWroFgwUAOUvllN/ptkmKUQKBgQD0VpJvyIbRt3J2iyYnswB/tQMk5meqBuxN1oK9tirXBTyyqZaoTtk+Z49vqhSIU0yezW81htiuF5A/gepx2v4/IqtSqCKSyxqBfFwbxONuky9gHdM+I1XyazmtSbJz8vR9U/88O60Wm1vhIe+61tWh1eWIJ7IxhA11AswfhOYMuwKBgQC3xBnOECBsh9OLnhIm9PVVxF+IGQFshsmc6kdZlKO9KdDlEiOh+TMJwhMkBqAfRJTMaz8WxPL4met2ZUJL/cXfHgAmjDnGlN69lc4ELikEd3S5aB4AWVG4ybcaHsrhLqS16WSMU1kD9l5Y3q+pbiiMW8wWpCMxVstj1T3rn6cOCQKBgE0Wx0rXZJnkHAwEqPwbgMvKC3zn6Mr/Niz0wfki8W83qsffs7XUcrw6pkmfyqycQ29S94RW0CRVMOCol5RmeJLo2E7S112jEPDLkK/+NZdcfrT/k/dl5KcAZ4kh2Fi2zaaBCuUxGtIoIBvuvhkf0PUnbCzCAXmX5TsGr+o93usjAoGBAKZtEhW+Iy9HX73tPXFMnbe8LeybAOAhvgu/XTjy1cumSEp9QAocHy3yNtWErpVCziPH6Q4c9hNRip7iG8WoogBsMiS3EEgZYRR/zGGa0Ij8CpkzgyA7xDg/bvVX99MyI/ef1PEFNvPQtydzHdGrM0vSgyXqJvkzKuZSJE71exzJAoGAQcSJ+EtdoZpqe7bjNZ2IpgSCmE0ErYFVMT6IW95VcEXOciRhEYG3TQKYuhm0ZRN+D4DfZdL8vRRQ2YDMrK14UUXzp6TBI+yirTDfbK6LYpuOZqjcvxVFwF4tdI7/K7blNY4fbhApO/Nx5tUd8duErIznnDbFVBlBjl0JWYQdrN0=";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private  String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3/IpQAsmsxozQkhGNY7aSrEtDQHjuNdhX9Z/zrZ+3aSC1gK2LS3EYXzzwFoDXMHRcTZajVqYPqKTnHWzx4lXDqJs8UlfcbSnJHEPVCGXqXl04WE9PXtcxicwNEUuDh6EeQu9GnOoBYQQiueKVK9NUdAr3UJyQF4eA45LCG0ZGNTHfqSJeLPzOcrcZglHY7NYXudxA8K7Xht1oA9laRFPmYgUNg9HuypGt1AwgOcDi9nrf1xrjeOTrPzKwLHoHp3fSTkm3GiAQZzwvkXDsK5Z99SinMyxgRze+ePiWmD01ArZXx+HNM5ZQw5dD+TLWO5qct4Yj16tE4GGCGfscfSJDwIDAQAB";
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    private  String notify_url ="http://hjl.mynatapp.cc/payed/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    private  String return_url ="http://member.cocomall.com/memberOrder.html";

    // 签名方式
    private  String sign_type = "RSA2";

    // 字符编码格式
    private  String charset = "utf-8";

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private  String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public  String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        System.out.println("支付宝的响应："+result);

        return result;

    }
}
