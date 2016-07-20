package com.tarena.allrun.activity;

import com.tarena.allrun.R;
import com.tarena.allrun.util.ExceptionUtil;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ChatActivity extends BaseActivity{
	WebView webView;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		try {
			setContentView(R.layout.chat);
			webView=(WebView) findViewById(R.id.chatWebView);
			//º”‘ÿassets/www/index.html
			webView.loadUrl("file:///android_asset/www/index.html");
			
			webView.setWebViewClient(new WebViewClient(){
				@Override
				public boolean shouldOverrideUrlLoading
				(WebView view, String url) {
					return super.shouldOverrideUrlLoading(view, url);
				}
			});
			//…Ë÷√÷¥––javascript
			WebSettings settings=webView.getSettings();
			settings.setJavaScriptEnabled(true);
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		}
	}

}
