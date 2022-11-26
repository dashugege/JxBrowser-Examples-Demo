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

import static com.teamdev.jxbrowser.engine.RenderingMode.OFF_SCREEN;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.browser.callback.CreatePopupCallback;
import com.teamdev.jxbrowser.browser.callback.CreatePopupCallback.Response;
import com.teamdev.jxbrowser.dom.Document;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.examples.seo.ua.UserAgentGenerate;
import com.teamdev.jxbrowser.examples.seo.utisl.EnglishResolution;
import com.teamdev.jxbrowser.examples.seo.utisl.EnglishResolution.WidthHeiht;
import com.teamdev.jxbrowser.navigation.Navigation;
import com.teamdev.jxbrowser.navigation.event.FrameLoadFinished;
import com.teamdev.jxbrowser.view.swing.BrowserView;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class IBrowser {


    public Browser browser = null;
    public Engine engine = null;
    public int flag = 0;


    public synchronized Browser getBrowser( ) {
        String ua = UserAgentGenerate.genetateUa();
        engine = Engine.newInstance(EngineOptions.newBuilder(OFF_SCREEN)
                .userAgent(ua)
                .build());
        browser = engine.newBrowser();
        return browser;
    }


    public synchronized void createJFrame(Browser tBrowser) {
        SwingUtilities.invokeLater(() -> {
            WidthHeiht widthHeiht = EnglishResolution.getEnglishResolution(1);
            BrowserView view = BrowserView.newInstance(tBrowser);
            JFrame frame = new JFrame("seo browser");
            frame.getContentPane().add(view, BorderLayout.CENTER);
            frame.setSize(widthHeiht.getWidth(), widthHeiht.getHeight());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        tBrowser.set(CreatePopupCallback.class, params -> {
            tBrowser.navigation().loadUrl(params.targetUrl());
            return Response.suppress();
        });
    }


    public synchronized void loadUrl(Browser tBrowser, String url) {

        tBrowser.set(CreatePopupCallback.class, params -> {
            tBrowser.navigation().loadUrl(params.targetUrl());
            return Response.suppress();
        });

        Navigation navigation = tBrowser.navigation();
        navigation.loadUrl(url);
    }





    public synchronized void  setFlag(int flag){
        this.flag = flag;
    }
    public synchronized void registerListener(Browser tBrowser ,FrameLoadFinishedListener listener){
        tBrowser.navigation().on(FrameLoadFinished.class, event ->
                event.frame().document().flatMap(Document::documentElement).ifPresent(element -> {
                            listener.listenerFlag(flag,element);
                        }
                )
        );
    }


}
