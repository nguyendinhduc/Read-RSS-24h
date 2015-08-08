package com.ducnd.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ducnd.Common.Common;
import com.ducnd.exercise17_rss.R;
import com.ducnd.my_adpater.MyAdapter_OrLink;
import com.ducnd.my_interface.OnDismiss;
import com.ducnd.my_item.MyItem_Link;

public class MainActivity extends Activity implements OnItemClickListener{
	private static final String TAG = "MainActivity";
	private ListView listView;
	private MyAdapter_OrLink adapterOrLink;
	private ArrayList<MyItem_Link> arrItemOrLink;
	private Handler mHandler;
	private static final int SLEEP_BG = 9798759;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new ThreadSleepBG().start();
		initMainActivity();
		this.mHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				if ( msg.what == SLEEP_BG ) {
					((ImageView)findViewById(R.id.bg)).setVisibility(View.GONE);
					((LinearLayout)findViewById(R.id.lineaRoot)).setVisibility(View.VISIBLE);
				}
			}
		};
	}
	public void initMainActivity() {
		initArrItemOrLink();
		adapterOrLink = new MyAdapter_OrLink(this, this.arrItemOrLink);
		listView = (ListView)findViewById(R.id.listView);
		listView.setAdapter(adapterOrLink);
		listView.setOnItemClickListener(this);
	}

	public void initArrItemOrLink() {
		arrItemOrLink = new ArrayList<MyItem_Link>();
		arrItemOrLink.add(new MyItem_Link("Tin tức hàng ngày", "http://www.24h.com.vn/upload/rss/tintuctrongngay.rss"));
		arrItemOrLink.add(new MyItem_Link("Bóng đá", "http://www.24h.com.vn/upload/rss/bongda.rss"));
		arrItemOrLink.add(new MyItem_Link("An ninh – Hình sự", "http://www.24h.com.vn/upload/rss/anninhhinhsu.rss"));
		arrItemOrLink.add(new MyItem_Link("Thời trang ", "http://www.24h.com.vn/upload/rss/thoitrang.rss"));
		arrItemOrLink.add(new MyItem_Link("Thời trang Hi-tech", "http://www.24h.com.vn/upload/rss/thoitranghitech.rss"));
		arrItemOrLink.add(new MyItem_Link("Tài chính – bất động sản", "http://www.24h.com.vn/upload/rss/taichinhbatdongsan.rss"));
		arrItemOrLink.add(new MyItem_Link("Ẩm thực", "http://www.24h.com.vn/upload/rss/amthuc.rss"));
		arrItemOrLink.add(new MyItem_Link("Làm đẹp", "http://www.24h.com.vn/upload/rss/lamdep.rss"));
		arrItemOrLink.add(new MyItem_Link("Phim", "http://www.24h.com.vn/upload/rss/phim.rss"));
		arrItemOrLink.add(new MyItem_Link("Giáo dục – du học", "http://www.24h.com.vn/upload/rss/giaoducduhoc.rss"));
		arrItemOrLink.add(new MyItem_Link("Bạn trẻ - cuộc sống", "http://www.24h.com.vn/upload/rss/canhacmtv.rss"));
		arrItemOrLink.add(new MyItem_Link("Ca nhạc – MTV", "http://www.24h.com.vn/upload/rss/lamdep.rss"));
		arrItemOrLink.add(new MyItem_Link("Thể thao", "http://www.24h.com.vn/upload/rss/thethao.rss"));
		arrItemOrLink.add(new MyItem_Link("Phi thường – kì quặc", "http://www.24h.com.vn/upload/rss/phithuongkyquac.rss"));
		arrItemOrLink.add(new MyItem_Link("Công nghệ thông tin", "http://www.24h.com.vn/upload/rss/congnghethongtin.rss"));
		arrItemOrLink.add(new MyItem_Link("Ô tô – xe máy", "http://www.24h.com.vn/upload/rss/otoxemay.rss"));
		arrItemOrLink.add(new MyItem_Link("Thị trường – tiêu dùng", "http://www.24h.com.vn/upload/rss/thitruongtieudung.rss"));
		arrItemOrLink.add(new MyItem_Link("Du lịch", "http://www.24h.com.vn/upload/rss/dulich.rss"));
		arrItemOrLink.add(new MyItem_Link("Sức khỏe – đời sống", "http://www.24h.com.vn/upload/rss/suckhoedoisong.rss"));
		arrItemOrLink.add(new MyItem_Link("Cười 24h", "http://www.24h.com.vn/upload/rss/cuoi24h.rss"));
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		TechCurnchTask tech = new TechCurnchTask("link");
		tech.execute(arrItemOrLink.get(position).getlinkRSS());
		Intent intent = new Intent();
		intent.setClass(this, Activity_Internet.class);
		try {
			intent.putExtra(Common.SEND_POSITION_LINK, tech.get());
			startActivity(intent);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private class TechCurnchTask extends AsyncTask<String, String, String> {

		private static final String TAG = "TechCurnchTask";
		private String type;
		public TechCurnchTask(String type) {
			this.type = type;
		}
		@Override
		protected String doInBackground(String... params) {
			String link = null;
			try {
				URL url = new URL(params[0]);
				
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				InputStream inputStream = connection.getInputStream();
				link = processXML(inputStream);
				Log.i(TAG,"doInBackground_Link: "+ link);
			} catch (MalformedURLException e) {
				Log.i(TAG, "TechCurnchTask_MalformedURLException_Erro url: " + e.toString());
			} catch (IOException e) {
				Log.i(TAG, "TechCurnchTask_IOException_Erro url: " + e.toString());
			} catch (Exception e) {
				Log.i(TAG, "TechCurnchTask_Exception_Erro url: " + e.toString());
			}
			return link;
		}
		
		public String processXML( InputStream inputStream) throws Exception {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document documentXML = builder.parse(inputStream);
			
			Element rootElement = documentXML.getDocumentElement();
			NodeList itemList = rootElement.getElementsByTagName(this.type);
			return itemList.item(0).getTextContent();
		}
		
	}
	private class ThreadSleepBG extends Thread{
		@Override
		public void run() {
			super.run();
			SystemClock.sleep(3000);
			Message msg = new Message();
			msg.what = SLEEP_BG;
			msg.setTarget(mHandler);
			msg.sendToTarget();
		}
	}
}
