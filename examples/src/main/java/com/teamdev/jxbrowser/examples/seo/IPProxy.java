package com.teamdev.jxbrowser.examples.seo;

import com.teamdev.jxbrowser.engine.Engine;

//https://github.com/jiangxianli/ProxyIpLib#免费代理ip库
public class IPProxy {
    public static IPProxy INSTANCE = null;
    private IPProxy(){}
    public static IPProxy getInstance(){
        if(INSTANCE == null){
            INSTANCE = new IPProxy();
        }
        return INSTANCE;
    }

    public void setProxyIp(String ip,String port){
        System.getProperties().setProperty("http.proxyHost",ip);
        System.getProperties().setProperty("http.proxyPort",port);
    }



}
