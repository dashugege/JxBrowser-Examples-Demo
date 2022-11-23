package com.teamdev.jxbrowser.examples.seo;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.dom.Document;
import com.teamdev.jxbrowser.dom.Element;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.examples.seo.ua.UA;
import com.teamdev.jxbrowser.examples.seo.utisl.EnglishResolution;
import com.teamdev.jxbrowser.examples.seo.utisl.EnglishResolution.WidthHeiht;
import com.teamdev.jxbrowser.navigation.Navigation;
import com.teamdev.jxbrowser.navigation.event.FrameLoadFinished;
import com.teamdev.jxbrowser.net.proxy.CustomProxyConfig;
import com.teamdev.jxbrowser.net.proxy.Proxy;
import com.teamdev.jxbrowser.profile.Profile;
import com.teamdev.jxbrowser.view.swing.BrowserView;
import java.awt.BorderLayout;
import java.util.Optional;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import static com.teamdev.jxbrowser.engine.RenderingMode.OFF_SCREEN;

public class Main {

    //    https://www.teamdev.com/jxbrowser
//    http://xuanyimao.com/doc/jxbrowser/helpdoc/jxbrowser/jxbrowser.support.teamdev.com/support/solutions.html
//-Djxbrowser.license.key=1BNDHFSC1G4LIPLNPLSPU2YBP8A9F3QVD5V96HLMALKE5GOYRQAKYPP5REF09PM3LJJK58
    static boolean searchClick = false;

    static int flag = 1;

    static String ip = "89.38.98.236";
    static String port = "80";
    static String url = "https://www.baidu.com/";
    static String search_keyword = "汉子转ascii";

    public static void main(String[] args) {
//        IPProxy.getInstance().setProxyIp("89.38.98.236","80");

        //init
        searchClick = false;
        flag = 1;

        Engine engine = Engine.newInstance(EngineOptions.newBuilder(OFF_SCREEN)
                .userAgent(UA.getUa(1))
                .build());

        Browser browser = engine.newBrowser();

        //代理 暂时不用
        Profile profile = engine.proxy().profile();
        Proxy proxy = profile.proxy();
        String proxyRules = "http=" + ip + ":" + port + ";https=foo:80;ftp=foo:80;socks=foo:80";
        String exceptions = "<local>";  // bypass proxy server for local web pages
//        proxy.config(CustomProxyConfig.newInstance(proxyRules, exceptions));

        WidthHeiht widthHeiht = EnglishResolution.getEnglishResolution(1);

        SwingUtilities.invokeLater(() -> {
            BrowserView view = BrowserView.newInstance(browser);
            JFrame frame = new JFrame("Evaluate XPath");
            frame.getContentPane().add(view, BorderLayout.CENTER);
            frame.setSize(widthHeiht.getWidth(), widthHeiht.getHeight());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        });

        // 输入款输入搜索关键字
        browser.navigation().on(FrameLoadFinished.class, event ->
                event.frame().document().flatMap(Document::documentElement).ifPresent(element -> {
                            try {
                                Thread.sleep(new Random().nextInt(5000)+1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            System.out.println("FrameLoadFinished  "+flag);
                            if (flag == 1) {
                                firstStep(element);
                            }
                            if (flag == 2) {
                                secondStep(element);
                            }


                        }
                )

        );

        Navigation navigation = browser.navigation();
        navigation.loadUrl(url);


    }


    public static void firstStep(Element element) {
        // 输入搜索关键字
        element.findElementById("kw").ifPresent(firstName ->
                firstName.putAttribute("value", search_keyword));
        // 模拟点击搜索按钮
        try {
            Thread.sleep(new Random().nextInt(1000)+1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        element.findElementById("su").ifPresent(item -> {
            if (searchClick) {
                return;
            }
            searchClick = true;
            item.click();
            try {
                Thread.sleep(new Random().nextInt(3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                flag = 2;
            }
        });
    }


    public static void secondStep(Element element) {
        // 搜索包含seo网址的元素
        try {
            element.findElementsByClassName("result c-container xpath-log new-pmd").stream().forEach( item ->{
                if(item.textContent().contains("2dyt")){
                    System.out.println("========click========");
                    item.click();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
