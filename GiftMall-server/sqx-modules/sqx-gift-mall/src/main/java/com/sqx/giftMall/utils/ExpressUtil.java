package com.sqx.giftMall.utils;

import com.alibaba.fastjson2.JSONObject;
import com.sqx.common.core.exception.BizException;
import com.sqx.domain.repository.service.sys.CommonInfoService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * @author chengming
 * @since 2025/10/13
 * <p>
 * 第三方快递工具类
 */
@Component
public class ExpressUtil {

    @Resource
    private CommonInfoService commonInfoService;

    public JSONObject find(String expressNumber) {
        String appCode = commonInfoService.getCommonValue(325);
        String host = "https://wuliu.market.alicloudapi.com";// 【1】请求地址 支持http 和 https 及 WEBSOCKET
        String path = "/kdi";  // 【2】后缀
        String appcode = appCode; // 【3】开通服务后 买家中心-查看AppCode
        String no = expressNumber.trim();// 【4】请求参数，详见文档描述
        String type = ""; //  【4】请求参数，不知道可不填 95%能自动识别
        String urlSend = host + path + "?no=" + no + "&type=" + type;  // 【5】拼接请求链接

        try {
            URL url = new URL(urlSend);
            HttpURLConnection httpURLCon = (HttpURLConnection) url.openConnection();
            httpURLCon.setRequestProperty("Authorization", "APPCODE " + appcode);// 格式Authorization:APPCODE (中间是英文空格)
            int httpCode = httpURLCon.getResponseCode();
            if (httpCode == 200) {
                String json = read(httpURLCon.getInputStream());
                JSONObject jsonObject = JSONObject.parseObject(json);
                JSONObject phone = jsonObject.getJSONObject("result");
                String phone1 = (String) phone.get("number");
                if (phone1 != null) {
                    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^(.*?):");
                    java.util.regex.Matcher matcher = pattern.matcher(phone1);
                    if (matcher.find()) {
                        phone1 = matcher.group(1); // 获取第一个捕获组，即冒号前面的部分
                    }
                    phone.put("number", phone1);
                }

                String statusCode = (String) jsonObject.get("status");
                if (statusCode != null && !statusCode.equals("0")) {
                    throw new BizException("物流单号有误请联系发货人");
                }

                return jsonObject;
            } else {
                Map<String, List<String>> map = httpURLCon.getHeaderFields();
                String error = map.get("X-Ca-Error-Message").get(0);
                if (httpCode == 400 && error.equals("Invalid AppCode `not exists`")) {
                    System.out.println("AppCode错误 ");
                } else if (httpCode == 400 && error.equals("Invalid Url")) {
                    System.out.println("请求的 Method、Path 或者环境错误");
                } else if (httpCode == 400 && error.equals("Invalid Param Location")) {
                    System.out.println("参数错误");
                } else if (httpCode == 403 && error.equals("Unauthorized")) {
                    System.out.println("服务未被授权（或URL和Path不正确）");
                } else if (httpCode == 403 && error.equals("Quota Exhausted")) {
                    System.out.println("套餐包次数用完 ");
                } else {
                    System.out.println("参数名错误 或 其他错误");
                    System.out.println(error);
                }
            }

        } catch (MalformedURLException e) {
            System.out.println("URL格式错误");
            throw new BizException("URL地址错误");
        } catch (UnknownHostException e) {
            System.out.println("URL地址错误");
            throw new BizException("URL地址错误");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败");
        }

        throw new BizException("查询失败");
    }

    /*
     * 读取返回结果
     */
    private static String read(InputStream is) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null) {
            line = new String(line.getBytes(), "utf-8");
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}
