package com.ilife.sanguohero.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.ilife.sanguohero.R;

/**
 * 显示网页
 * Created by Administrator on 2015/9/24.
 */
public class BrowserActivity extends AppCompatActivity {
    public static final String URL = "data_url";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getStringExtra(URL);

        setContentView(R.layout.activity_browser);
        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(url);
    }
}
