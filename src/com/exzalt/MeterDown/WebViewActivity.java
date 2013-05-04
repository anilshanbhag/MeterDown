package com.exzalt.MeterDown;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends Activity{
	private WebView webView;
	 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		
		Intent intent=getIntent();
		int cityno=intent.getIntExtra("cityno",0);
		int choice=intent.getIntExtra("choice",0);
		
		String data="";
		switch (choice)
		{
			case 0:
				switch (cityno)
				{
					case 0:
						data="<body>" + "<img src=\"mumbaiAuto.png\"/></body>";
						break;
					case 1:
						data="<body>" + "<img src=\"puneAuto.png\"/></body>";
						break;
					case 2:
						data="<body>" + "<img src=\"bengaluruAuto.png\"/></body>";
						break;
					case 4:
						data="<body>" + "<img src=\"hyderabadAuto.png\"/></body>";
						break;
					case 5:
						data="<body>" + "<img src=\"delhiAuto.png\"/></body>";
						break;
				}
				break;
			case 1:
				switch(cityno)
				{
					case 0:
						data="<body>" + "<img src=\"mumbaiTaxi.png\"/></body>";
						break;
					case 3:
						data="<body>" + "<img src=\"kolkataTaxi.png\"/></body>";
						break;
					case 5:
						data="<body>" + "<img src=\"delhiTaxi.png\"/></body>";
						break;
				}
		
		}
 
		webView = (WebView) findViewById(R.id.webview);
		webView.loadDataWithBaseURL("file:///android_asset/",data , "text/html", "utf-8",null);
	}
};