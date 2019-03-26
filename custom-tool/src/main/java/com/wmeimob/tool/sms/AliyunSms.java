package com.wmeimob.tool.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.PropertyResourceBundle;

/**
 * Created by Shinez on 2017/4/5.
 */
public class AliyunSms {

    private final static Logger logger = LoggerFactory.getLogger(AliyunSms.class);
    private static PropertyResourceBundle property = (PropertyResourceBundle) PropertyResourceBundle.getBundle("common");
    static void sendValidCode(String tel, String code) {
        try {
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", property.getString("ALIYUN_SMS_ACCESS_KEY"), property.getString("ALIYUN_SMS_ACCESS_SECRET"));
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms",  "sms.aliyuncs.com");
            IAcsClient client = new DefaultAcsClient(profile);
            SingleSendSmsRequest request = new SingleSendSmsRequest();
            request.setSignName(new String(property.getString("ALIYUN_SMS_SIGN").getBytes("ISO8859-1"),"utf-8"));//控制台创建的签名名称
            request.setTemplateCode(property.getString("ALIYUN_SMS_VALID_CODE_TEMPLATE"));//控制台创建的模板CODE
            request.setParamString("{\"name\":\"用户\",\"code\":\"" + code + "\"}");//短信模板中的变量；数字需要转换为字符串；个人用户每个变量长度必须小于15个字符。"
            request.setRecNum(tel);//接收号码
            request.setAcceptFormat(FormatType.JSON);
            SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
        } catch (ClientException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
//        sendValidCode("13997558051","123456");
    }
}
