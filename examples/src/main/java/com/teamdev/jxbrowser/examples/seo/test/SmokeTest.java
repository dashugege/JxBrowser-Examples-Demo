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

package com.teamdev.jxbrowser.examples.seo.test;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.dom.Document;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.view.swing.BrowserView;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public final class SmokeTest {

    public static void main(String[] args) {
        // Creating and running Chromium engine
        Engine engine = Engine.newInstance(HARDWARE_ACCELERATED);
        Browser browser = engine.newBrowser();

        browser.mainFrame().ifPresent(mainFrame -> {
            mainFrame.loadHtml("<html>\n"
                    + " <head>\n"
                    + " <!-- head definitions go here -->\n"
                    + " </head>\n"
                    + " <body>\n"
                    + " <a class=\"\" href=\"https://www.baidu.com/link?url=OWC6OANRBw8VSepw0y82t2DrLhYhXUI7zfWB4n6ZH3dJ6AwshNWcoinvNQ9rQyli&amp;wd=&amp;eqid=a4f8d3730003a7ff000000026386f3fc\" \n"
                    + " data-showurl-highlight=\"true\" target=\"_blank\" tabindex=\"0\" \n"
                    + " data-click=\"{&quot;F&quot;:&quot;778317EA&quot;,&quot;F1&quot;:&quot;9D73F1E4&quot;,&quot;F2&quot;:&quot;4CA6DE6A&quot;,&quot;F3&quot;:&quot;54E5243F&quot;,&quot;T&quot;:1669788668,&quot;y&quot;:&quot;7F3D7FEF&quot;}\" aria-label=\"\">在线<em>ASCII</em>编码<em>汉字</em>互转(2dyt <em>ascii</em>) | 在线工具\n"
                    + " </a>\n"
                    + " </body>\n"
                    + "</html>");
        });

        SwingUtilities.invokeLater(() -> {
            // Creating Swing component for rendering web content
// loaded in the given Browser instance.
            final BrowserView view = BrowserView.newInstance(browser);

// Creating and displaying Swing app frame.
            JFrame frame = new JFrame("Hello World");
// Close Engine and onClose app window
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    engine.close();
                }
            });
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            JTextField addressBar = new JTextField("https://google.com");
            addressBar.addActionListener(e -> browser.navigation()
                    .loadUrl(addressBar.getText()));
            frame.add(addressBar, BorderLayout.NORTH);
            frame.add(view, BorderLayout.CENTER);
            JButton print = new JButton("Search");
            print.addActionListener(e -> browser.mainFrame().ifPresent(frame1 -> {
                Document document = frame1.document().get();
                document.findElementsByTagName("a").forEach(hrefItem -> {
                    if (hrefItem.textContent().contains("2dyt")) {
                        hrefItem.attributeNodes().forEach(attribute -> {
                            ;
                            System.out.println(attribute.nodeName() + " " + attribute.nodeValue());
                        });
                    }
                });
            }));
            frame.add(print, BorderLayout.SOUTH);
            frame.setSize(1280, 900);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}