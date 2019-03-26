//package com.wmeimob.custom.interceptor;
//
//import com.wmeimob.custom.annotation.Token;
//import com.wmeimob.custom.enums.TokenType;
//import com.wmeimob.tool.SpringRedisUtil;
//import com.wmeimob.tool.message.MessageConst;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.*;
//
///**
// * 表单提交token验证拦截器
// * Created by xiangzhao on 2016/8/4.
// */
//
//public class TokenInterceptor extends HandlerInterceptorAdapter {
//
//    private Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);
//
//    private Map<String, Object> lockMap = new HashMap<>();
//
//
//    private boolean validRequestTooManyTimes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String requestType = request.getHeader("X-Requested-With");
//        if (requestType != null) {
//            Object lastTimestamp = SpringRedisUtil.deleteAndGet("users:" + request.getSession().getId() + ":last_request_timestamp");
//            long thisTimeStamp = System.currentTimeMillis();
//            SpringRedisUtil.save("users:" + request.getSession().getId() + ":last_request_timestamp", thisTimeStamp, 20 * 60 * 1000l);
//            if (lastTimestamp != null) {
//                if (thisTimeStamp - Long.parseLong(lastTimestamp.toString()) < 200) {
//                    request.setAttribute("msg", MessageConst.Msg.HANDLE_TOO_MUCH);
//                    request.getRequestDispatcher("/restError").forward(request, response);
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        lockMap.putIfAbsent(request.getSession().getId(), new byte[0]);
//        synchronized (lockMap.get(request.getSession().getId())) {
//            if (validRequestTooManyTimes(request, response)) {
//                String requestType = request.getHeader("X-Requested-With");
//                String servletPath = request.getServletPath();
//                logger.debug("请求地址：" + servletPath);
//                request.setCharacterEncoding("UTF-8");
//                response.setCharacterEncoding("UTF-8");
//                response.setHeader("Server", "Microsoft-IIS/7.5");
//                if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
//                    Token methodToken = ((HandlerMethod) handler).getMethodAnnotation(Token.class);
//                    Token classToken = ((HandlerMethod) handler).getBean().getClass().getAnnotation(Token.class);
//                    Token finalToken = null;
//                    if (classToken == null && methodToken == null) {
//                        return true;
//                    }
//
//                    if (classToken != null && methodToken == null) {
//                        finalToken = classToken;
//                        String[] excludes;
//                        String requestMethod = request.getMethod();
//                        if (requestMethod.equals("POST")) {
//                            excludes = finalToken.postExcludes();
//                        } else {
//                            excludes = finalToken.getExcludes();
//                        }
//                        if (Arrays.asList(excludes).contains(servletPath.replace(finalToken.requestRoot(), "")) || servletPath.equals("/index.html")) {
//                            return true;
//                        }
//                    } else {
//                        finalToken = methodToken;
//                    }
//
//                    String serverToken = SpringRedisUtil.deleteAndGet("users:" + request.getSession().getId() + ":token");
//                    if (finalToken.type().equals(TokenType.VALIDATE)) {
//                        String token = request.getHeader("token");
//                        if (token == null || token.equals("-1") || !token.equals(serverToken)) {
//                            logger.info("token valid fail");
//                            request.setAttribute("msg", MessageConst.Msg.TOKEN_ERROR);
//                            request.getRequestDispatcher("/restError").forward(request, response);
//                            return false;
//                        } else {
//                            //成功验证, ajax 请求token清空刷新
//                            String newToken = UUID.randomUUID().toString().replace("-", "");
//                            logger.debug("flush new token->" + newToken);
//                            response.setHeader("token", newToken);
//                            request.getSession().setAttribute("token", newToken);
//                            SpringRedisUtil.save("users:" + request.getSession().getId() + ":token", newToken, 20 * 60 * 1000l);
//                            return true;
//                        }
//                    } else {
//                        //不是验证类型(创建) 刷新token
//                        String newToken = UUID.randomUUID().toString().replace("-", "");
//                        logger.debug("create new token->" + newToken);
//                        response.setHeader("token", newToken);
//                        request.getSession().setAttribute("token", newToken);
//                        SpringRedisUtil.save("users:" + request.getSession().getId() + ":token", newToken, 20 * 60 * 1000l);
//                        logger.debug("none valid flag,flush token TokenInterceptor<<<");
//                        return true;
//                    }
//
//                }
//            }
//            return false;
//        }
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
////        System.out.println("视图渲染之前");
//
//    }
//}
