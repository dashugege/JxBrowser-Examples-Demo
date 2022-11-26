/*
 *  Copyright 2022, TeamDev. All rights reserved.
 *
 *  Redistribution and use in source and/or binary forms, with or without
 *  modification, must retain the above copyright notice and the following
 *  disclaimer.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 *  A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 *  OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 *  OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.teamdev.jxbrowser.examples.seo.engine;


import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.net.proxy.CustomProxyConfig;
import com.teamdev.jxbrowser.net.proxy.Proxy;
import com.teamdev.jxbrowser.profile.Profile;

public class ProxyUtils {

    public static ProxyUtils INSTANCE = null;

    private ProxyUtils(){}

    public static ProxyUtils getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ProxyUtils();
        }
        return  INSTANCE;
    }


    public void setProxy(Engine engine,String ip,String port){
        Profile profile = engine.proxy().profile();
        Proxy proxy = profile.proxy();
        String proxyRules = "http=" + ip + ":" + port + ";http=" + ip + ":" + port ;
        String exceptions = "<local>";
        proxy.config(CustomProxyConfig.newInstance(proxyRules, exceptions));
    }



}
