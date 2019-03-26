//package com.wmeimob.custom.config;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.wmeimob.custom.annotation.DataFilter;
//import org.apache.ibatis.executor.statement.StatementHandler;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.plugin.*;
//import org.apache.ibatis.reflection.MetaObject;
//import org.apache.ibatis.reflection.SystemMetaObject;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.util.StringUtils;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.lang.reflect.Method;
//import java.sql.Connection;
//import java.util.List;
//import java.util.Properties;
//
///**
// * Created by Shinez on 2016/11/23.
// */
//@Intercepts({
//        @Signature(type = StatementHandler.class,
//                method = "prepare", args = {Connection.class, Integer.class})}
//)
//public class MybatisDataInterceptor implements Interceptor {
//
//
//    HttpServletRequest request;
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//
//
//        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
//        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
//        // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过循环可以分离出最原始的的目标类)
//        while (metaStatementHandler.hasGetter("h")) {
//            Object object = metaStatementHandler.getValue("h");
//            metaStatementHandler = SystemMetaObject.forObject(object);
//        }
//
//
//        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
//        String id = mappedStatement.getId();
//        String className = id.substring(0, id.lastIndexOf("."));
//        String methodName = id.substring(id.lastIndexOf(".") + 1, id.length());
//        if (methodName.contains("_COUNT")) {
//            methodName = methodName.replace("_COUNT", "");
//        }
//        Class<?> classObj = Class.forName(className);
//        DataFilter dataFilterClass = classObj.getAnnotation(DataFilter.class);
//        Method[] methods = classObj.getMethods();
//        Method method = null;
//        int i = 0;
//        while (i < methods.length) {
//
//            if (methods[i].getName().equals(methodName)) {
//                method = methods[i];
//                break;
//            }
//            ++i;
//        }
//        DataFilter dataFilterMethod = null;
//        if (method != null) {
//            dataFilterMethod = method.getAnnotation(DataFilter.class);
//        }
//        if ((dataFilterMethod != null && !dataFilterMethod.filter())//false method
//                || (dataFilterClass != null && !dataFilterClass.filter())//false class
//                || (dataFilterMethod == null && dataFilterClass == null)//null all
//                ||dataFilterClass==null) { //null class
//            return invocation.proceed();
//        }
//        try {
//            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        }catch (NullPointerException e){
//            return invocation.proceed();
//        }
//        if (dataRoles == null) {
//            return invocation.proceed();
//        }
//       JSONArray sysUserDataRoles = (JSONArray) dataRoles;
//
//        String fieldName = sysUserDataRoles.getJSONObject(0).getString("columnName");
//        String tableAlias = dataFilterClass.tableAlias();
//        String fieldVal =  sysUserDataRoles.getJSONObject(0).getString("columnValue");
//        //BoundSql对象是处理sql语句的。
//        String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
//
//        if (!"".equals(dataFilterClass.value())) {
//            fieldName = dataFilterClass.value();
//        }
//        if (sql.trim().contains("JOIN")) {
//            if (!tableAlias.equals("")) {
//                fieldName = tableAlias + "." + fieldName;
//            }
//        }
//        if (!sql.trim().contains("SELECT LAST_INSERT_ID()")
//                && (sql.toLowerCase().trim().indexOf("select") == 0
//                || sql.toLowerCase().trim().indexOf("update") == 0
//                || sql.toLowerCase().trim().indexOf("delete") == 0
//        )) {
//            if (sql.toLowerCase().trim().contains("where")) {
//                sql = sql.toLowerCase().trim().replace("where", " where " + fieldName + "='" + fieldVal + "' and ");
//            } else if (sql.toLowerCase().trim().contains("order by")) {
//                sql = sql.toLowerCase().trim().replace("order by", "where " + fieldName + "='" + fieldVal + "' order by");
//            } else if (sql.toLowerCase().trim().contains("limit")) {
//                sql = sql.toLowerCase().trim().replace("limit", "where " + fieldName + "='" + fieldVal + "' limit");
//            } else {
//                sql = sql + " where " + fieldName + "='" + fieldVal + "'";
//            }
//        } else if (sql != null && sql.toLowerCase().trim().indexOf("insert") == 0) {
//            sql = sql.toLowerCase().trim().replace(") values", "," + fieldName + ") values").replace("? )", "?,'" + fieldVal + "')");
//        }
//        metaStatementHandler.setValue("delegate.boundSql.sql", sql.replace("%y", "%Y")); //重写SQL
//        return invocation.proceed();//实际就是调用原来的prepared方法，只是再次之前我们修改了sql
//    }
//
//    public static void main(String[] args) {
//        String sql="select * from user where";
//
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        return Plugin.wrap(target, this);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//
//    }
//}
