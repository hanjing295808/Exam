package com.tarena.allrun.activity;

import com.tarena.allrun.R;
import com.tarena.allrun.util.ExceptionUtil;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TaobaoActivity extends BaseActivity{
	WebView webView;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		try {
			setContentView(R.layout.taobao);
			webView=(WebView) findViewById(R.id.taobaoWebView);
			//Õ¯’æ
			webView.loadUrl("https://www.taobao.com/");
			
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
