package com.wmeimob.custom.exception;

import com.alibaba.fastjson.JSONObject;
import com.wmeimob.tool.message.MessageConst;
import com.wmeimob.tool.message.RestResult;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
@RestController
public class ExceptionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 全局异常通知，包括aop的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public JSONObject exceptionNotify(Exception e) {
        logger.error(e.getMessage(), e);
        return RestResult.fail("服务器繁忙，请稍后再试");
    }


    /**
     * 未授权的访问
     * @param e
     * @return
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public JSONObject accessDeniedException(Exception e) {
        return RestResult.msg(HttpStatus.SC_FORBIDDEN,e.getMessage());
    }
    /**
     * 数字转换异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = NumberFormatException.class)
    public JSONObject numberFormatException(Exception e) {
        return RestResult.msg(HttpStatus.SC_INTERNAL_SERVER_ERROR,e.getMessage());
    }

    /**
     * 请求参数数据类型不匹配
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public JSONObject bindException(Exception e) {
        return RestResult.msg(HttpStatus.SC_BAD_REQUEST,"请求参数数据类型不匹配");
    }
    /**
     * 打印自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = CustomException.class)
    public JSONObject customException(CustomException e) {
        return RestResult.fail(e.getMessage());
    }



    /**
     * 参数错误
     * @param e
     * @return
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public JSONObject illegalArgumenException(IllegalArgumentException e) {
        return RestResult.fail(e.getMessage());
    }
    /**
     * 请求方法错误
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public JSONObject requestMethodErrorException(HttpRequestMethodNotSupportedException e){
        return RestResult.msg(e.getMessage());
    }

    @RequestMapping("/restError")
    public JSONObject restError(HttpServletRequest request) {
        Object msgObj = request.getAttribute("msg");
        if (msgObj==null)return RestResult.fail();
        if (msgObj instanceof MessageConst.Msg)
            return RestResult.msg( (MessageConst.Msg) msgObj);
        return RestResult.fail(msgObj.toString());
    }
}