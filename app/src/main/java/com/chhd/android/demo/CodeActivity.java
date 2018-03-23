package com.chhd.android.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;

public class CodeActivity extends AppCompatActivity {

    private WebView webView;

    String linkCss = "<style>\n" +
            "        body{\n" +
            "            text-align: center;\n" +
            "            color: #303030;\n" +
            "            font-size: 14px;\n" +
            "        }\n" +
            "    </style>";

    private String content = "<p>昆德拉斯柯达</p><p>的卡拉卡掉了</p><p>大爱是</p><p><img src=\"http://test.hulubao.com/uploads/2b/90/2b9040b297cb67de9f772a0fe0c1d7a1.jpg\" data-filename=\"image name\" style=\"width: 257px;\"><br></p>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        webView = findViewById(R.id.web);

        webView.getSettings().setDefaultTextEncodingName("UTF -8");
        String html = "<html><header>" + linkCss+"</header><body>" + content + "</body></html>";
        webView.loadData(html, "text/html; charset=UTF-8", null);//这种写法可以正确解码
    }
}
