package com.exzalt.MeterDown;

import com.exzalt.MeterDown.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends Activity{
	private WebView webView;
	 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
 
		webView = (WebView) findViewById(R.id.webview);
		webView.loadUrl("http://gpo.iitb.ac.in");
	}
};