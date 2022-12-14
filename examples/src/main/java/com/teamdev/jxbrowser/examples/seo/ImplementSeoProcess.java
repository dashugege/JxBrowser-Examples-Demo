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

import static com.google.common.util.concurrent.Uninterruptibles.awaitUninterruptibly;

import com.teamdev.jxbrowser.dom.Element;
import com.teamdev.jxbrowser.dom.Node;
import com.teamdev.jxbrowser.examples.seo.engine.FrameLoadFinishedListener;
import com.teamdev.jxbrowser.examples.seo.engine.IBrowser;
import com.teamdev.jxbrowser.examples.seo.engine.ProxyUtils;
import com.teamdev.jxbrowser.examples.seo.net.ArrayIpJsonBean.DataDTO.IDataDTO;
import com.teamdev.jxbrowser.examples.seo.net.IP;
import com.teamdev.jxbrowser.examples.seo.net.IpArrayListener;
import com.teamdev.jxbrowser.examples.seo.net.IpListener;
import com.teamdev.jxbrowser.examples.seo.utisl.DelayUtils;
import com.teamdev.jxbrowser.examples.seo.utisl.DelayUtils.ICallBack;
import com.teamdev.jxbrowser.examples.seo.utisl.PrintUtils;
import com.teamdev.jxbrowser.examples.seo.utisl.TimeUtils;
import com.teamdev.jxbrowser.frame.Frame;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ImplementSeoProcess {



    IBrowser iBrowser = null;

    ExecutionCompletedListener completedListener = null;

    public void createBrowser(){
        iBrowser = new IBrowser();
        iBrowser.getBrowser();
    }



    public void getSingleIp(String keyword,ExecutionCompletedListener completedListener){
        this.completedListener = completedListener;
        IP ip = new IP();
        ip.getIp(new IpListener() {
            @Override
            public void success(String ip, String port) {
                createBrowser();
                iBrowser.ip = ip;
                iBrowser.port = port;
                iBrowser.searchKeyWords = keyword;

                ProxyUtils.getInstance().setProxy(iBrowser.engine,ip,port);

                iBrowser.browser.mainFrame().ifPresent(frame->{
                    if(frame.isMain()){
                    }
                });
                setListener(0);

                iBrowser.createJFrame(iBrowser.browser);
                iBrowser.loadUrl(iBrowser.browser,IBrowser.targetUrl);

            }

            @Override
            public void error() {

            }
        });
    }



    public void getLocalIp(String ip,String port,String keyword,ExecutionCompletedListener completedListener){
        this.completedListener = completedListener;
        createBrowser();
        iBrowser.ip = ip;
        iBrowser.port = port;
        iBrowser.searchKeyWords = keyword;

        ProxyUtils.getInstance().setProxy(iBrowser.engine,ip,port);

        iBrowser.browser.mainFrame().ifPresent(frame->{
            if(frame.isMain()){
            }
        });
        setListener(0);

        iBrowser.createJFrame(iBrowser.browser);
        iBrowser.loadUrl(iBrowser.browser,IBrowser.targetUrl);

    }



    public void setListener(int flag){
        iBrowser.setFlag(flag);
        iBrowser.registerListener(iBrowser.browser,
                new FrameLoadFinishedListener() {
                    @Override
                    public void listenerFlag(int flag, Element element) {
                        PrintUtils.print("FrameLoadFinishedListener flag : " + flag);
                            if(flag == 0){
                                firstStep(element,flag);
                            }
                            if(flag == 1 ){
                                test(element);
                            }
                    }
                });
    }


    public void firstStep(Element element,int flag){
        // ?????????????????????
        PrintUtils.print(" ????????????????????? ");
        element.findElementById("kw").ifPresent(firstName ->
                firstName.putAttribute("value", iBrowser.searchKeyWords));
        // ????????????????????????
        try {
            DelayUtils.delay(TimeUtils.getRandomTime(true), new ICallBack() {
                @Override
                public void call() {
                    PrintUtils.print(" ?????????????????? ");
                    element.findElementById("su").ifPresent(item -> {
                        if (iBrowser.firstClick) {
                            return;
                        }
                        iBrowser.firstClick = true;
                        DelayUtils.delay(TimeUtils.getRandomTime(true), new ICallBack() {
                            @Override
                            public void call() {
                                iBrowser.setFlag(1);
                                item.click();
                            }
                        });
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void test(Element element){
        iBrowser.browser.devTools().show();
        List<Element> elementList =  element.findElementsByClassName( "c-title t t tts-title");
        element.findElementsByTagName("a").forEach(hrefItem ->{
            if(hrefItem.textContent().contains("??????JSON??????")){
                hrefItem.attributeNodes().forEach(attribute -> {
                    PrintUtils.print(attribute.nodeName() + " " + attribute.nodeValue());
                });
            }
        });
    }

    public  void secondStep(Element element) {
        // ????????????seo???????????????
        try {
            List<Element> elementList =  element.findElementsByClassName("siteLink_9TPP3");
            elementList.forEach(item->{
                int index = elementList.indexOf(item);
                if(item.textContent().contains("2dyt")){
                    if(iBrowser.secondClick){
                        return;
                    }
                    iBrowser.secondClick = true;
                    item.attributeNodes().forEach(attribute -> {
                        if("href".equals(attribute.nodeName())){
                            iBrowser.isFind = true;
                            PrintUtils.print("??????????????????"+attribute.nodeName());
                            PrintUtils.print("??????????????????"+attribute.nodeValue());
                            item.click();

                            try {
                                Thread.sleep(TimeUtils.getRandomTime(true));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if(completedListener != null){
                                completedListener.completed(iBrowser.ip,iBrowser.port,iBrowser.searchKeyWords);
                            }
//                            iBrowser.engine.close();
//                            iBrowser.browser.close();
                        }
                    });
                }else {
                    if(index == elementList.size()-1){
                        if(!iBrowser.isFind){
                            PrintUtils.print(" ==== ????????????========= ");
                            findNext(element);
                        }
                    }
                }

            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void findNext(Element element){
        try {
            Thread.sleep(TimeUtils.getRandomTime(false));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            iBrowser.browser.mainFrame().get().executeJavaScript("window.scrollTo(0,document.body.scrollHeight);");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(TimeUtils.getRandomTime(false));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            List<Element> elementList =  element.findElementsByClassName("page-item_M4MDr pc");
            outer:
            for(int i=0;i<elementList.size();i++){
                List<Node> pageListNode =  elementList.get(i).children();
                for(int j=0;j<pageListNode.size();j++){
                    try {
                        if(Integer.valueOf(pageListNode.get(j).nodeValue()) - 1 == iBrowser.page){
                            PrintUtils.print(" ========??????========= " + iBrowser.page);
                            pageListNode.get(j).click();
                            iBrowser.page++;
                            findNext(element);
                            break outer;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
