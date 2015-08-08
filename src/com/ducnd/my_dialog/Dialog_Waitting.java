package com.ducnd.my_dialog;

import com.ducnd.exercise17_rss.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.LinearLayout;

public class Dialog_Waitting extends Dialog{
	private Context mContex;
private LayoutInflater mInflater;;
	private LinearLayout Linear = null;
	public Dialog_Waitting(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public Dialog_Waitting(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	public Dialog_Waitting(Context context) {
		super(context);
		this.mContex = context;
		this.mInflater = LayoutInflater.from(this.mContex);
		this.Linear = (LinearLayout)this.mInflater.inflate(R.layout.dialog_watting, null);
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_watting);
		setCancelable(false);
	}
	@Override
	public void onBackPressed() {
		dismiss();
		super.onBackPressed();
	}
}
