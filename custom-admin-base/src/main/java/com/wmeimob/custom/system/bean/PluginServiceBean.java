package com.wmeimob.custom.system.bean;

import java.io.Serializable;

/**
 * 客户端 服务组件
 * Created by Shinez on 2017/3/11.
 */
public class PluginServiceBean implements Serializable  {

    private static final long serialVersionUID = 1L;

    private String serviceName;

    private String loginoutUrl;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getLoginoutUrl() {
        return loginoutUrl;
    }

    public void setLoginoutUrl(String loginoutUrl) {
        this.loginoutUrl = loginoutUrl;
    }

    public PluginServiceBean(String serviceName){
        this.serviceName = serviceName;
    }
}
