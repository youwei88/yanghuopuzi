package com.wmeimob.tool.message;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by xiangzhao on 2016/3/15.
 */
public class RestResult {

    public static void print(Object object) {
        System.out.print(object);
    }

    public static void println(Object object) {
        System.out.println(object);
    }

    public static void println() {
        System.out.println();
    }

    private static Map<String, Object> dataMap;

    public static Map<String, Object> getDataMap() {
        if (dataMap == null)
            dataMap = new HashMap<>();
        else
            dataMap.clear();
        return dataMap;
    }

    /**
     * 构建返回json
     *
     * @param msgCode 响应代码
     * @param msgInfo 响应说明
     * @return json对象
     */
    public static JSONObject msg(int msgCode, String msgInfo) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", msgCode);
        if (MessageConst.showInfo)
            jsonObject.put("info", msgInfo);
        return jsonObject;
    }

    /**
     * 构建返回json
     *
     * @param msgCode 响应代码
     * @param msgInfo 响应说明
     * @param dataMap 数据（Map集合）
     * @return
     */
    private static JSONObject msg(int msgCode, String msgInfo, Map dataMap) {
        JSONObject jsonObject = msg(msgCode, msgInfo);
        jsonObject.put("data", dataMap);
        return jsonObject;
    }


    /**
     * 构建返回json
     *
     * @param map
     * @return
     */
    public static JSONObject success(Map map) {
        return msg(MessageConst.Msg.SUCCESS, map);
    }
    /**
     * 构建返回json
     *
     * @param map
     * @return
     */
    public static JSONObject success(Object obj) {
        Map<String,Object> map = getDataMap();
        String className = obj.getClass().getSimpleName();
        className = Character.toLowerCase(className.charAt(0)) + className.substring(1);
        map.put(className,obj);
        return success(map);
    }

//    /**
//     * 构建返回json
//     *
//     * @param map
//     * @return
//     */
//    public static <T> JSONObject success(List<T> list) {
//        Map map = getDataMap();
//        map.put("list",list);
//        return success(map);
//    }

    /**
     * 构建返回json
     *
     * @param map
     * @return
     */
    public static <T> JSONObject success(PageInfo<T> pageInfo) {
        Map map = getDataMap();
        map.put("page",pageInfo);
        return success(map);
    }

    /**
     * 构建返回json
     *
     * @return
     */
    public static JSONObject success() {
        return msg(MessageConst.Msg.SUCCESS);
    }


    /**
     * 返回失败 不说明具体原因
     * @return
     */
    public static JSONObject fail() {
        return msg(MessageConst.Msg.HANDLE_FAIL);
    }

    /**
     * 返回失败，自定义原因
     * @param reason
     * @return
     */
    public static JSONObject fail(String reason) {
        return msg(-1,reason);
    }



    /**
     * 构建返回json
     *
     * @param msg
     * @return
     */
    public static JSONObject msg(MessageConst.Msg msg) {
        return msg(msg.id, msg.info);
    }

    /**
     * 构建返回json
     *
     * @param msg
     * @return
     */
    public static JSONObject msg(MessageConst.Msg msg, Map dataMap) {
        return msg(msg.id, msg.info, dataMap);
    }


    /**
     * 构建返回json（不一定是出错了 返回状态码为当前时间戳 原因自定）
     *
     * @param info
     * @return
     */
    public static JSONObject msg(String info) {
        return msg((int) System.currentTimeMillis(), info);
    }

    public static JSONObject msg(HttpStatus httpStatus) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", httpStatus.value());
        if (MessageConst.showInfo)
            jsonObject.put("info", httpStatus.getReasonPhrase());
        return jsonObject;
    }

    /**
     * 向data里追加key-value
     * @param jsonObject
     * @param key
     * @param value
     * @return
     */
    public static JSONObject appendData(JSONObject jsonObject, String key, Object value) {
        Map map;
        try {
            map = (Map) jsonObject.get("data");
        } catch (Exception e) {
            map = getDataMap();
        }
        if (map == null) {
            map = getDataMap();
        }
        map.put(key, value);
        jsonObject.put("data",map);
        return jsonObject;
    }

    /**
     * 向data里追加map
     * @param jsonObject
     * @param addMap
     * @return
     */
    public static JSONObject appendData(JSONObject jsonObject,Map<String,Object> addMap) {
        Map map;
        try {
            map = (Map) jsonObject.get("data");
        } catch (Exception e) {
            map = getDataMap();
        }
        if (map == null) {
            map = getDataMap();
        }
        map.putAll(addMap);
        jsonObject.put("data",map);
        return jsonObject;
    }


    public static JSONObject jsonResultWithValidResult(BindingResult result){
        StringBuffer sb = new StringBuffer("");
        Map map = getDataMap();
        if (result.hasErrors()){
            List<ObjectError> list = result.getAllErrors();
            Iterator<ObjectError> iterator = list.iterator();
            while (iterator.hasNext())
            {
                ObjectError objectError = iterator.next();
                String field = objectError.getCodes()[1].split("\\.")[1];
                map.put(field,objectError.getDefaultMessage());
            }
        }
        return msg(MessageConst.Msg.VALID_FILED,map);
    }

    public static int getTimeDifft(Date minDate,Date maxDate){
        long diff = maxDate.getTime()-minDate.getTime();
        return (int) (diff/1000);
    }

    /**
     * 获取今天剩余秒
     * @return
     */
    public static long getTodayResidualTime(){

        Calendar min = Calendar.getInstance();

        Calendar max = Calendar.getInstance();
        max.set(Calendar.HOUR_OF_DAY, 23);
        max.set(Calendar.MINUTE, 59);
        max.set(Calendar.SECOND, 59);

        min.get(Calendar.HOUR_OF_DAY);
        min.get(Calendar.MINUTE);
        min.get(Calendar.SECOND);

        return max.getTimeInMillis()-min.getTimeInMillis()+1000;
    }

    public static void main(String[] args) {
        System.out.println(getTodayResidualTime());
    }

    public static void renderJsonResultFail(HttpServletResponse response, String message) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("utf-8");
            out = response.getWriter();
            out.print(fail(message));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out!=null)
                out.close();
        }
    }


    public static void renderJsonResultSuccess(HttpServletResponse response,Map<String,Object> map) {
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            out.write(success(map).toJSONString().getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out!=null)
                try {
                    out.close();
                } catch (IOException ignored) {

                }
        }
    }
}
