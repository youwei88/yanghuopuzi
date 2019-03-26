package com.wmeimob.tool.sms;

import com.wmeimob.tool.RandomCodeUtil;
import com.wmeimob.tool.SpringRedisUtil;

/**
 * Created by Shinez on 2017/3/22.
 */
public class AliyunSmsUtils {

    public static void sendValidCode(String tel, Integer codeLength,long lifeTime,long requestTimeout) {
        String flag = SpringRedisUtil.get("tels:" + tel + ":timeout");
        if (flag == null) {
            String random = RandomCodeUtil.randCodeNum(codeLength);
            AliyunSms.sendValidCode(tel, random);
            SpringRedisUtil.save("tels:" + tel + ":sms_code", random, lifeTime);
            SpringRedisUtil.save("tels:" + tel + ":timeout", "flag", requestTimeout);
        }
    }

    public static void sendValidCode(String tel) {
        String flag = SpringRedisUtil.get("tels:" + tel + ":timeout");
        if (flag == null) {
            String random = RandomCodeUtil.randCodeNum(6);
            AliyunSms.sendValidCode(tel, random);
            SpringRedisUtil.save("tels:" + tel + ":sms_code", random, 15*60*1000L);
            SpringRedisUtil.save("tels:" + tel + ":timeout", "flag", 60*1000L);
        }
    }
}
