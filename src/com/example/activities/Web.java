package com.example.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;

import com.example.navigation.R;

public class Web extends ActionBarActivity {
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		webView = (WebView) findViewById(R.id.webView1);
		webView.loadUrl("http://www.google.com");

	}







}
