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

package com.teamdev.jxbrowser.examples.seo;


import com.teamdev.jxbrowser.dom.Element;
import com.teamdev.jxbrowser.examples.seo.engine.FrameLoadFinishedListener;
import com.teamdev.jxbrowser.examples.seo.engine.IBrowser;
import com.teamdev.jxbrowser.examples.seo.engine.ProxyUtils;
import com.teamdev.jxbrowser.examples.seo.net.ArrayIpJsonBean.DataDTO.IDataDTO;
import com.teamdev.jxbrowser.examples.seo.net.IP;
import com.teamdev.jxbrowser.examples.seo.net.IpArrayListener;
import com.teamdev.jxbrowser.examples.seo.net.IpListener;
import java.util.List;

public class TestMain {

    public static String search_keyword = "json在线格式";
    public static String url = "https://www.baidu.com";

    public static void main(String[]args){
        // 单ip
//
        newBrowser();


    }


    public static void newBrowser(){
        ImplementSeoProcess seoProcess = new ImplementSeoProcess();
        seoProcess.getSingleIp(new ExecutionCompletedListener() {
            @Override
            public void completed() {
                newBrowser();
            }
        });
    }



}
