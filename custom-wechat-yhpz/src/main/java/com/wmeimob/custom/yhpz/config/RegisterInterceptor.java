package com.wmeimob.custom.yhpz.config;

import com.wmeimob.custom.system.bean.WechatUser;
import com.wmeimob.custom.yhpz.bean.User;
import com.wmeimob.custom.yhpz.service.WxUserService;
import com.wmeimob.tool.SpringHelper;
import com.wmeimob.tool.message.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Shinez on 2017/8/12.
 */

@Slf4j
public class RegisterInterceptor extends HandlerInterceptorAdapter {

    private WxUserService wxUserService = (WxUserService) SpringHelper.getBean("wxUserService");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        WechatUser wechatUser = (WechatUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = wxUserService.findByWechatUserId(wechatUser.getId());
        if (user == null) {
            request.setAttribute("msg", RestResult.msg(HttpStatus.SC_FORBIDDEN, "用户未注册"));
            request.getRequestDispatcher("/restError").forward(request, response);
            return false;
        }
        return true;
    }
}
