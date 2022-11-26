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
import com.teamdev.jxbrowser.examples.seo.net.IP;
import com.teamdev.jxbrowser.examples.seo.net.IpListener;
import com.teamdev.jxbrowser.examples.seo.utisl.PrintUtils;
import com.teamdev.jxbrowser.examples.seo.utisl.TimeUtils;
import java.util.Random;

public class ImplementSeoProcess {

    /**
     * 0  初始化
     * 1 点击搜索按钮
     */
    int state = 0;
    boolean searchClick = false;

    IBrowser iBrowser = null;



    public void createBrowser(){
        iBrowser = new IBrowser();
        iBrowser.getBrowser();
    }

    public void getIp(){
        IP ip = new IP();
        ip.getIp(new IpListener() {
            @Override
            public void success(String ip, String port) {
                System.out.println("ip " + ip + " port " + port);
                ProxyUtils.getInstance().setProxy(iBrowser.engine,ip,port);
                setListener(state);

                iBrowser.createJFrame(iBrowser.browser);


                iBrowser.loadUrl(iBrowser.browser,TestMain.url);
            }

            @Override
            public void error() {

            }
        });
    }



    public void setListener(int flag){
        iBrowser.setFlag(flag);
        iBrowser.registerListener(iBrowser.getBrowser(),
                new FrameLoadFinishedListener() {
                    @Override
                    public void listenerFlag(int flag, Element element) {
                        PrintUtils.print("FrameLoadFinishedListener flag : " + flag);
                            if(flag == 0){
                                firstStep(element,flag);
                            }
                            if(flag == 1 ){
                                secondStep(element);
                            }



                    }
                });
    }



    public void firstStep(Element element,int flag){
        // 输入搜索关键字
        PrintUtils.print(" 输入搜索关键词 ");
        element.findElementById("kw").ifPresent(firstName ->
                firstName.putAttribute("value", TestMain.search_keyword));
        // 模拟点击搜索按钮
        try {
            Thread.sleep(TimeUtils.getRandomTime(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintUtils.print(" 点击搜索按钮 ");
        element.findElementById("su").ifPresent(item -> {
            if (searchClick) {
                return;
            }
            searchClick = true;
            try {
                Thread.sleep(TimeUtils.getRandomTime(true));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                state = 1;
                iBrowser.setFlag(1);
                item.click();
            }
        });
    }

    public  void secondStep(Element element) {
        // 搜索包含seo网址的元素
        try {
            element.findElementsByClassName("siteLink_9TPP3").forEach(item->{
                if(item.textContent().contains("2dyt")){
                    item.attributeNodes().forEach(attribute -> {
                        if("href".equals(attribute.nodeName())){
                            PrintUtils.print("点击搜索链接"+attribute.nodeValue());
                            item.click();
//                            iBrowser.loadUrl(iBrowser.getBrowser(), attribute.nodeValue());
                        }
                    });
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
