package com.ducnd.my_item;

import android.graphics.Bitmap;

public class Item_Load_Image {
	private boolean isLoading = false;
	private boolean finish = false;
	private Bitmap bm = null;
	public boolean isLoading() {
		return isLoading;
	}
	public void setLoading(boolean isLoading) {
		this.isLoading = isLoading;
	}
	public boolean isFinish() {
		return finish;
	}
	public void setFinish(boolean finish) {
		this.finish = finish;
	}
	public Bitmap getBm() {
		return bm;
	}
	public void setBm(Bitmap bm) {
		this.bm = bm;
	}
	
}
