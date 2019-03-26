//package com.wmeimob.custom.system.config;
//
//import com.wmeimob.custom.config.CustomProp;
//import com.wmeimob.custom.system.bean.WechatMps;
//import com.wmeimob.custom.system.dao.WechatMpsMapper;
//import com.wmeimob.tool.SpringHelper;
//import com.wmeimob.tool.SpringRedisUtil;
//import com.wmeimob.tool.message.MessageConst;
//import com.wmeimob.tool.message.RestResult;
//import com.wmeimob.tool.web.CookieUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.StringUtils;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//
///**
// * Created by Shinez on 2016/10/10.
// */
//
//public class WechatInterceptor extends HandlerInterceptorAdapter {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    private WechatMpsMapper wechatMpsMapper = (WechatMpsMapper) SpringHelper.getBean("wechatMpsMapper");
//    private CustomProp customProp = (CustomProp) SpringHelper.getBean("customProp");
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        logger.info("::wechat module::");
//        logger.debug("请求地址：" + request.getMethod() + ':' + request.getServletPath());
//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//
//
//        String openid = null;
//        Integer openidValidResult = null;
//        Cookie openidCookie = CookieUtil.getCookie("openid", request);
//        if (openidCookie != null) {
//            openid = openidCookie.getValue();
//            openidValidResult = SpringRedisUtil.get("login_openids:" + openid);
//        }
//        //验证openid有效性
//        if (openid == null || openidValidResult == null) {
//            CookieUtil.removeCookieByKey("openid", request, response);
//            request.setAttribute("msg", RestResult.msg(420, "未授权"));
//            request.getRequestDispatcher("/restError").forward(request, response);
//            return false;
//        }
//        return true;
//    }
//}
//
