package com.ducnd.activity;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.ducnd.Common.Common;
import com.ducnd.exercise17_rss.R;

public class Activity_Internet extends Activity{
	private Intent intentRoot;
	private String link;
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_view);
		initActyvity_Internet();
		startWebView();
	}
	public void initActyvity_Internet() {
		intentRoot = getIntent();
		link = intentRoot.getStringExtra(Common.SEND_POSITION_LINK);
	}
	
	public void startWebView() {
		webView = (WebView) this.findViewById(R.id.webView);
		webView.getSettings().setLoadsImagesAutomatically(true);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		webView.loadUrl(link);
	}

}
