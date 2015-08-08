package com.ducnd.my_adpater;

import java.util.ArrayList;

import com.ducnd.Common.Common;
import com.ducnd.exercise17_rss.R;
import com.ducnd.my_dialog.Dialog_ListSublink;
import com.ducnd.my_dialog.Dialog_Waitting;
import com.ducnd.my_interface.OnDismiss;
import com.ducnd.my_item.MyItem_Link;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter_OrLink extends BaseAdapter implements OnDismiss{
	protected static final String LINK = "LINK";
	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<MyItem_Link> arrItemOrLink;
	private Dialog_Waitting dialogWaitting;
	public MyAdapter_OrLink( Context mContext, ArrayList<MyItem_Link> arrItemOrLink) {
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(this.mContext);
		this.arrItemOrLink = arrItemOrLink;
		this.dialogWaitting = new Dialog_Waitting(this.mContext);
		Dialog_ListSublink.addDismiss(this);
	}
	@Override
	public int getCount() {
		return arrItemOrLink.size();
	}

	@Override
	public MyItem_Link getItem(int position) {
		return arrItemOrLink.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if ( convertView == null ) {
			convertView = mInflater.inflate(R.layout.item_listview, parent, false);
		}
		final int po = position;
		((TextView)convertView.findViewById(R.id.textName)).setText(arrItemOrLink.get(position).getName());
		((ImageView)convertView.findViewById(R.id.iconMenu)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialogWaitting.show();
				Intent intent  = new Intent();
				intent.setClass(mContext, Dialog_ListSublink.class);
				intent.putExtra(LINK, arrItemOrLink.get(po).getlinkRSS());
				intent.putExtra(Common.TITLE, arrItemOrLink.get(po).getName());
				(mContext).startActivity(intent);
			}
		});
		return convertView;
	}
	@Override
	public void setDismissDialog() {
		this.dialogWaitting.dismiss();
	}
	

}
