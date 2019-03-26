package com.wmeimob.custom.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.mzlion.easyokhttp.HttpClient;
import com.wmeimob.custom.annotation.Logging;
import com.wmeimob.custom.config.CustomProp;
import com.wmeimob.custom.system.bean.WechatMps;
import com.wmeimob.custom.system.bean.WechatUser;
import com.wmeimob.custom.system.dao.WechatMpsMapper;
import com.wmeimob.custom.system.dao.WechatUserMapper;
import com.wmeimob.custom.system.security.JwtUtility;
import com.wmeimob.custom.system.service.WechatService;
import com.wmeimob.tool.InputValidator;
import com.wmeimob.tool.SpringRedisUtil;
import com.wmeimob.tool.message.RestResult;
import com.wmeimob.tool.web.CookieUtil;
import com.wmeimob.tool.web.HttpHelper;
import com.wmeimob.tool.web.RequestResponseTools;
import com.wmeimob.tool.wechat.WechatApi;
import com.wmeimob.tool.wechat.WechatSignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by Shinez on 2016/12/29.
 */

@RestController
@RequestMapping("/core")
@Logging
@CrossOrigin
public class WechatController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private WechatMpsMapper wechatMpsMapper;
    @Resource
    private WechatUserMapper wechatUserMapper;

    @Resource
    private JwtUtility jwtUtility;

    @Resource
    private CustomProp customProp;


    /***
     * 发起网页授权
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping("/oauth2")
    public Object oauth2(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String redirect = request.getParameter("redirect");
        if (!InputValidator.isUrl(redirect))
            return RestResult.fail("redirect 不合法");
        String mpid = customProp.getConfig("mpid");
        WechatMps wechatMps = SpringRedisUtil.get("mps:" + mpid + ":object");
        if (wechatMps == null) {
            wechatMps = wechatMpsMapper.selectByPrimaryKey(Integer.valueOf(mpid));
            SpringRedisUtil.save("mps:" + mpid + ":object", wechatMps, 6 * 3600 * 1000L);
        }
        String callBackUrl = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/core/saveOpenid?" +
                "redirect=" + URLEncoder.encode(redirect, "utf-8") + "&mpid=" + mpid;
//        if (StringUtils.isEmpty(wechatMps.getComponentAuthorizeOpenidUrl())) {
        //本地授权
        return new ModelAndView("redirect:" + WechatApi.GET_CODE
                .replace("APPID", wechatMps.getAppid())
                .replace("SCOPE", wechatMps.getAuthScope())
                .replace("REDIRECT_URI", URLEncoder.encode(callBackUrl, "utf-8"))
        );
//        } else {
//        第三方授权
//            String path = wechatMps.getComponentAuthorizeOpenidUrl().replace("${APPID}", wechatMps.getAppid()).replace("${REDIRECT}", URLEncoder.encode(callBackUrl, "utf-8"));
//            return new ModelAndView("redirect:" + path);
//        }
    }

    /**
     * 微信网页授权回调
     *
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/saveOpenid")
    public Object saveUserInfo(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        ModelAndView modelAndView = new ModelAndView();
//        String userData = request.getParameter("data");
        String path = request.getParameter("redirect");
//        if (!StringUtils.isEmpty(userData)) {
//            //第三方平台来源
//            JSONObject jsonObject = JSONObject.parseObject(URLDecoder.decode(userData, "utf-8"));
//            String openid = jsonObject.getString("openid");
//            WechatUser wechatUser = new WechatUser();
//            wechatUser.setOpenid(openid);
//            wechatUserMapper.insertIgnore(wechatUser);
//            SpringRedisUtil.save("login_openids:"+openid,1,7*24*60*60*1000L);//一周
//            modelAndView.setViewName("redirect:" + InputValidator.encodeChinese(path, "utf-8"));
//            return modelAndView;
//        }
        //来源微信
        String code = request.getParameter("code");
        String mpid = request.getParameter("mpid");
        Assert.notNull(code, "code is null");
        Assert.notNull(mpid, "mpid missing");
        WechatMps wechatMps = SpringRedisUtil.get("mps:" + mpid + ":object");
        if (wechatMps == null) {
            wechatMps = wechatMpsMapper.selectByPrimaryKey(Integer.valueOf(mpid));
            SpringRedisUtil.save("mps:" + mpid + ":object", wechatMps, 6 * 3600 * 1000L);
        }
        JSONObject jsonObject = JSONObject.parseObject(HttpHelper.SSLGet(WechatApi.GET_OPENID
                        .replace("APPID", wechatMps.getAppid())
                        .replace("SECRET", wechatMps.getSecret())
                        .replace("CODE", code)
                , null));
        String openid = jsonObject.getString("openid");
        WechatUser wechatUser = new WechatUser();
        wechatUser.setOpenid(openid);
        if (openid != null) {
            if ("snsapi_userinfo".equals(wechatMps.getAuthScope())) {
                //保存用户信息
                JSONObject userInfo = JSONObject.parseObject(
                        HttpClient.get(WechatApi.GET_USER_INFO_BASE
                                .replace("ACCESS_TOKEN", jsonObject.getString("access_token")
                                        .replace("OPENID", openid)
                                )).asString()
                );
                if (userInfo.getString("openid") != null) {
                    userInfo.put("appid", wechatMps.getAppid());
                    wechatUser = WechatService.saveWechatUserInfo(userInfo);
                }
            }

            String jwt = jwtUtility.generateToken(wechatUser);
            path += (path.contains("?") ? "&" : "?") + "token=" + jwt;
            modelAndView.setViewName("redirect:" + InputValidator.encodeChinese(path, "utf-8"));
            return modelAndView;
        }
        return RestResult.fail("授权有误，请稍后再试");
    }

    public static void main(String[] args) {

    }


//    @Logging("接收来自微信的消息")
//    @RequestMapping("/{appid}")
//    public String receiveMsgFromWechat(HttpServletRequest request, @PathVariable("appid") String appid) throws IOException {
//        Map<String, String> map = RequestResponseTools.convertRequestMap(request.getParameterMap());
//        WechatMps wechatMps = SpringRedisUtil.get("mps:" + appid + ":object");
//        if (wechatMps == null) {
//            wechatMps = new WechatMps();
//            wechatMps.setAppid(appid);
//            wechatMps = wechatMpsMapper.selectOne(wechatMps);
//            Assert.notNull(wechatMps, "公众号服务器配置不正确");
//            SpringRedisUtil.save("mps:" + appid + ":object", wechatMps, 6 * 3600 * 1000L);
//        }
//        for (String str : map.keySet()) {
//            logger.info(str + ":" + map.get(str));
//        }
//        boolean result = WechatSignUtil.checkSignature(wechatMps.getToken(), map.get("signature"), map.get("timestamp"), map.get("nonce"));
//        if (result) {
//            String str = WechatService.processMsg(appid, request.getInputStream());
//            if ("success".equals(str))
//                return map.get("echostr");
//            return str;
//        }
//        return "error token";
//    }
}
