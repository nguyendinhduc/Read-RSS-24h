package com.ducnd.my_dialog;

import java.util.ArrayList;

import com.ducnd.Common.Common;
import com.ducnd.activity.Activity_Internet;
import com.ducnd.exercise17_rss.R;
import com.ducnd.my_adpater.MyAdapter_SubLink;
import com.ducnd.my_interface.OnDismiss;
import com.ducnd.my_item.Item_SubLink;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Dialog_ListSublink extends Activity implements OnItemClickListener{
	private Context mContext;
	private LayoutInflater mInflater;
	private LinearLayout linList;
	private ListView listVieSubLink;
	private ArrayList<Item_SubLink> arrItemSubLink = new ArrayList<Item_SubLink>();
	private MyAdapter_SubLink adapter;
	private String subLink = "";
	private Intent intent;
	private static OnDismiss onDimiss;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_sublink);
		this.intent = getIntent();
		((TextView)findViewById(R.id.title)).setText(intent.getStringExtra(Common.TITLE));
		initDialog();
		listVieSubLink.setOnItemClickListener(this);
		onDimiss.setDismissDialog();
	}
	public void initDialog() {
		listVieSubLink = (ListView)findViewById(R.id.listSubLink);
		if ( listVieSubLink == null )  Toast.makeText(this, "null List", Toast.LENGTH_SHORT).show();
		adapter = new MyAdapter_SubLink(this, intent.getStringExtra("LINK"));
		arrItemSubLink = adapter.getArrSubLink();
		if ( adapter == null ) Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
		listVieSubLink.setAdapter(adapter);
		
		
	}
	
	public static void addDismiss( OnDismiss dismiss ) {
		onDimiss = dismiss;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent i = new Intent();
		i.setClass(this, Activity_Internet.class);
		i.putExtra(Common.SEND_POSITION_LINK, arrItemSubLink.get(position).getLink());
		startActivity(i);
	}
	
}
