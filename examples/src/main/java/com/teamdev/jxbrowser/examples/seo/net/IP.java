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

package com.teamdev.jxbrowser.examples.seo.net;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IP {

    public void getIp(IpListener listener) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://ip.jiangxianli.com/api/proxy_ip")
                .build();
        try (Response response = client.newCall(request).execute()) {
            String result = response.body().string();
            Gson gson = new Gson();
            SingleJsonBean jsonBean = gson.fromJson(result, SingleJsonBean.class);
            if(jsonBean != null&& jsonBean.getCode() == 0 && jsonBean.getData().getIp().length()>0 && jsonBean.getData().getPort().length()>0){
                listener.success(jsonBean.getData().getIp(),jsonBean.getData().getPort());
            }else {
                listener.error();
            }
        } catch (Exception e) {
            listener.error();
            e.printStackTrace();
        }
    }



    public void getArrayIp(IpArrayListener listener){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://ip.jiangxianli.com/api/proxy_ips")
                .build();
        try (Response response = client.newCall(request).execute()) {
            String result = response.body().string();
            Gson gson = new Gson();
            ArrayIpJsonBean jsonBean = gson.fromJson(result, ArrayIpJsonBean.class);
            if(jsonBean != null&& jsonBean.getCode() == 0 ){
                listener.success(jsonBean.getData().getData());
            }else {
                listener.error();
            }
        } catch (Exception e) {
            listener.error();
            e.printStackTrace();
        }
    }

}
