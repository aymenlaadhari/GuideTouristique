package com.example.activities;

import com.example.navigation.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends Activity{

	private WebView webView;
	 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
 
		webView = (WebView) findViewById(R.id.webView1);
		
		//webView.loadUrl("http://www.google.com");
 
	}

	public WebView getWebView() {
		return webView;
	}

	public void setWebView(WebView webView) {
		this.webView = webView;
	}
	
	public static WebViewActivity newInstance(String text, String page) {

		WebViewActivity f = new WebViewActivity();
		f.webView.loadUrl(page);
	
		Bundle b = new Bundle();
		b.putString("msg", text);
		

		return f;
	}
	
}
