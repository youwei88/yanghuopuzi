package com.wmeimob.custom.system.service;

import com.alibaba.fastjson.JSONObject;
import com.wmeimob.custom.config.CustomProp;
import com.wmeimob.custom.system.bean.WechatMps;
import com.wmeimob.custom.system.bean.WechatUser;
import com.wmeimob.custom.system.dao.WechatMpsMapper;
import com.wmeimob.custom.system.dao.WechatUserMapper;
import com.wmeimob.tool.RandomCodeUtil;
import com.wmeimob.tool.SpringHelper;
import com.wmeimob.tool.SpringRedisUtil;
import com.wmeimob.tool.XmlUtil;
import com.wmeimob.tool.web.HttpHelper;
import com.wmeimob.tool.wechat.WechatSignUtil;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.ServletInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Shinez on 2017/2/22.
 */
public class WechatService {

    private static CustomProp customProp = (CustomProp) SpringHelper.getBean("customProp");
    private static WechatUserMapper wechatUserMapper = (WechatUserMapper) SpringHelper.getBean("wechatUserMapper");
    private static WechatMpsMapper wechatMpsMapper = (WechatMpsMapper) SpringHelper.getBean("wechatMpsMapper");
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Logger logger = LoggerFactory.getLogger(WechatService.class);



    public static WechatUser saveWechatUserInfo(JSONObject jsonObject) {
        try {
            WechatUser wechatUser = JSONObject.parseObject(jsonObject.toJSONString(), WechatUser.class);
            String openid = wechatUser.getOpenid();
            WechatUser queryWechatUser = new WechatUser();
            queryWechatUser.setOpenid(openid);
            synchronized (openid.intern()) {
                queryWechatUser = wechatUserMapper.selectOne(queryWechatUser);
                if (queryWechatUser == null) {
                    wechatUserMapper.insertSelective(wechatUser);
                } else {
                    wechatUser.setId(queryWechatUser.getId());
                    wechatUserMapper.updateByPrimaryKeySelective(wechatUser);
                }
                return wechatUser;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static String processMsg(String appid, ServletInputStream inputStream) {
        StringBuffer sb = new StringBuffer();
        try {
            InputStreamReader e = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader br = new BufferedReader(e);
            String s = "";
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            String str = sb.toString();
            logger.info("Request Body:" + str);
            if (StringUtils.isEmpty(str)) {
                return "success";
            }

            Map map = XmlUtil.readStringXmlOut(str);
            String msgType = (String) map.get("MsgType");
            String gh = (String) map.get("ToUserName");
            String openid = (String) map.get("FromUserName");
            String createTime = (String) map.get("CreateTime");
            if ("event".equals(msgType)) {
                String eventName = (String) map.get("Event");
                String eventKey = (String) map.get("EventKey");
                if ("subscribe".equals(eventName)) {
                    return processSubstribe(appid, openid, createTime);
                }
                if ("unsubscribe".equals(eventName)) {
                    return processUnSubscribe(appid, openid, createTime);
                }
            } else if ("text".equals(msgType)) {
                return processText(appid, openid, createTime, (String) map.get("Content"), (String) map.get("MsgId"));
            }
        } catch (Exception var14) {
            logger.error(var14.getMessage(), var14);
        }

        return "success";
    }

    private static String processText(String appid, String openid, String createTime, String content, String msgId) {
        return "";
    }

    private static String processUnSubscribe(String appid, String openid, String createTime) {
        logger.debug(openid + "取消关注" + appid);
        synchronized (openid.intern()) {
            WechatUser wechatUser = new WechatUser();
            wechatUser.setOpenid(openid);
            wechatUser = wechatUserMapper.selectOne(wechatUser);
            wechatUser.setSubscribe(Boolean.FALSE);
            wechatUser.setAppid(appid);
            wechatUser.setOpenid(openid);
            wechatUser.setSubscribeTime(new Date(Long.valueOf(createTime) * 1000L));
            if (wechatUser.getId()!=null) {
                wechatUserMapper.updateByPrimaryKeySelective(wechatUser);
            } else {
                wechatUserMapper.insertSelective(wechatUser);
            }

            return "";
        }
    }

    private static String processSubstribe(String appid, String openid, String createTime) {
        logger.debug(openid + "关注" + appid);
        synchronized (openid.intern()) {
            WechatUser wechatUser = new WechatUser();
            wechatUser.setOpenid(openid);
            wechatUser = wechatUserMapper.selectOne(wechatUser);
            wechatUser.setSubscribe(Boolean.TRUE);
            wechatUser.setAppid(appid);
            wechatUser.setOpenid(openid);
            wechatUser.setSubscribeTime(new Date(Long.valueOf(createTime) * 1000L));
            if (wechatUser.getId()!=null) {
                wechatUserMapper.updateByPrimaryKeySelective(wechatUser);
            } else {
                wechatUserMapper.insertSelective(wechatUser);
            }

            return "";
        }
    }

    private static String sendText(String gh, String openid, String text) {
        return "<xml><ToUserName><![CDATA[" + openid + "]]></ToUserName><FromUserName><![CDATA[" + gh + "]]></FromUserName><CreateTime>" + System.currentTimeMillis() + "</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[" + text + "]]></Content></xml>";
    }


    private static String getAccessTokenFromOpen(String appid) {
        ArrayList nameValuePairs = new ArrayList();
        nameValuePairs.add(new BasicNameValuePair("appid", appid));
        String accesstoken = JSONObject.parseObject(HttpHelper.SSLPost("http://" + customProp.getConfig("server-root") + "/component/getCustomAccessToken", nameValuePairs)).getJSONObject("data").getString("accessToken");
        return accesstoken;
    }

    private static String getAccessToken(String appid) {
        String accessToken = (String) SpringRedisUtil.get("mps:" + appid + ":access_token");
        if (accessToken == null) {
            WechatMps wechatMps = (WechatMps) SpringRedisUtil.get("mps:" + appid + ":object");
            if (wechatMps == null) {
                wechatMps=new WechatMps();
                wechatMps.setAppid(appid);
                wechatMps = wechatMpsMapper.selectOne(wechatMps);
                SpringRedisUtil.save("mps:" + wechatMps.getAppid() + ":object", wechatMps, 21600000L);
            }

            String result = HttpHelper.SSLGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET".replace("APPID", wechatMps.getAppid()).replace("APPSECRET", wechatMps.getSecret()), (List) null);
            JSONObject jsonObject = JSONObject.parseObject(result);
            accessToken = jsonObject.getString("access_token");
            if (accessToken == null) {
                return "";
            }

            SpringRedisUtil.save("mps:" + appid + ":access_token", accessToken, (long) (jsonObject.getInteger("expires_in") - 600) * 1000L);
        }

        return accessToken;
    }

    public static Map<String, Object> jssdkInit(String appid, String currentUrl) {
        long timestamp = System.currentTimeMillis();
        String nonceStr = RandomCodeUtil.randCode(12);
        TreeMap<String,Object> map = new TreeMap<>();
        map.put("timestamp", timestamp / 1000L);
        map.put("noncestr", nonceStr);
        map.put("url", currentUrl);
        String ticket = (String) SpringRedisUtil.get("mps:" + appid + ":jsapi_ticket");
        if (ticket == null) {
            JSONObject sb = JSONObject.parseObject(HttpHelper.SSLGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi".replace("ACCESS_TOKEN", getAccessToken(appid)), (List) null));
            if (sb != null && sb.getString("ticket") != null) {
                ticket = sb.getString("ticket");
                SpringRedisUtil.save("mps:" + appid + ":jsapi_ticket", ticket, (long) (sb.getInteger("expires_in") - 600) * 1000L);
            }
        }

        map.put("jsapi_ticket", ticket);
        StringBuffer sb1 = new StringBuffer("");
        Iterator result = map.keySet().iterator();

        String sha1;
        while (result.hasNext()) {
            sha1 = (String) result.next();
            sb1.append(sha1 + "=" + map.get(sha1) + "&");
        }

        String result1 = sb1.substring(0, sb1.length() - 1);
        sha1 = "";

        try {
            sha1 = WechatSignUtil.SHA1(result1);
        } catch (NoSuchAlgorithmException var12) {
            logger.error(var12.getMessage(), var12);
        }

        map.put("signature", sha1.toLowerCase());
        map.put("appId", appid);
        return map;
    }

}
