package com.ducnd.my_adpater;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.security.auth.Destroyable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.ducnd.Common.Common;
import com.ducnd.exercise17_rss.R;
import com.ducnd.my_item.Item_Load_Image;
import com.ducnd.my_item.Item_SubLink;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter_SubLink extends BaseAdapter{

	private static final String TAG = "MyAdapter_SubLink";
	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<Item_SubLink> arrItemSubLink = new ArrayList<Item_SubLink>();
	private ArrayList<Item_Load_Image> arrCheckAction = new ArrayList<Item_Load_Image>();
	public MyAdapter_SubLink(Context mContext, String linkRSS) {
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(this.mContext);

		TechCurnchTask tech =  new TechCurnchTask("item");
		tech.execute(linkRSS);
		try {
			this.arrItemSubLink = tech.get();
			for ( int i = 0; i < arrItemSubLink.size(); i++) {
				arrCheckAction.add(new Item_Load_Image());
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	public ArrayList<Item_SubLink> getArrSubLink() {
		return this.arrItemSubLink;
	}
	@Override
	public int getCount() {
		return arrItemSubLink.size();
	}

	@Override
	public Item_SubLink getItem(int position) {
		return arrItemSubLink.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	private class ViewHolder{
		private TextView textName, textDate, textWaitting;
		private ImageView imageIcon;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
//		if ( convertView == null ) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_sublink, parent, false);
			holder.textName = (TextView)convertView.findViewById( R.id.textName);
			holder.textDate = (TextView)convertView.findViewById(R.id.textDate);
			holder.imageIcon = (ImageView)convertView.findViewById(R.id.imageIcon);
			holder.textWaitting = (TextView)convertView.findViewById(R.id.textWatting);
			convertView.setTag(holder);
//		}
//		else {
//			holder = (ViewHolder)convertView.getTag();
//		}
		
		holder.textName.setText(arrItemSubLink.get(position).getTitle());
		holder.textDate.setText(arrItemSubLink.get(position).getDate());
		
		if ( !arrCheckAction.get(position).isFinish()  && !arrCheckAction.get(position).isLoading() ) {
			arrCheckAction.get(position).setLoading(true);
			ThreadSetImage th = new ThreadSetImage(holder.imageIcon, holder.textWaitting, position);;
			th.start();
		}
		else {
			if ( arrCheckAction.get(position).isFinish() ) holder.imageIcon.setImageBitmap(arrCheckAction.get(position).getBm());
			holder.textWaitting.setVisibility(View.GONE);
		}
//		((TextView)convertView.findViewById(R.id.textName)).setText(arrItemSubLink.get(position).getTitle());
//		((TextView)convertView.findViewById(R.id.textDate)).setText(arrItemSubLink.get(position).getDate());
//
//		if ( arrCheckAction.get(position).isFinish() == false && arrCheckAction.get(position).isLoading() == false ) {
//			arrCheckAction.get(position).setLoading(true);
//			new ThreadSetImage((ImageView)convertView.findViewById(R.id.imageIcon), (TextView)convertView.findViewById(R.id.textWatting), position).start();
//		}
//		else 
//			if ( arrCheckAction.get(position).isFinish() ) {
//				((ImageView)convertView.findViewById(R.id.imageIcon)).setImageBitmap(arrCheckAction.get(position).getBm());
//			}
//



		return convertView;
	}
	private class ThreadSetImage extends Thread {
		private static final int SET_IMAGE_FOR_IMAGE = 9668;
		private ImageView image;
		private int positonThread;
		private Handler mHandler;
		private TextView textWatting;
		private Bitmap bm;
		public ThreadSetImage(ImageView imagee, TextView textWating, int position) {
			this.image = imagee;
			this.positonThread = position;
			this.textWatting = textWating;
			this.mHandler = new Handler(){
				

				public void handleMessage(android.os.Message msg) {
					if ( msg.what == SET_IMAGE_FOR_IMAGE) {
						image.setImageBitmap(bm);
						arrCheckAction.get(positonThread).setBm(bm);
						arrCheckAction.get(positonThread).setFinish(true);
						arrCheckAction.get(positonThread).setLoading(false);
						ThreadSetImage.this.textWatting.setVisibility(View.GONE);
						
					}
				}
			};
		}
		@Override
		public void run() {
			super.run();
			TechBitmapfromInternet techBitmap = new TechBitmapfromInternet();;
			techBitmap.execute(arrItemSubLink.get(this.positonThread).getLinkImage());
			try {
				this.bm = techBitmap.get();
				Message msg = new Message();
				msg.what = SET_IMAGE_FOR_IMAGE;
				msg.setTarget(mHandler);
				msg.sendToTarget();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private class TechBitmapfromInternet extends AsyncTask<String, String, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
			URL url;
			try {
				url = new URL(params[0]);
				Bitmap bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());

				return bm;
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

	}

	private ArrayList<Item_SubLink> getArrItemSublink(String rootLink) {
		ArrayList<Item_SubLink> arr = new ArrayList<Item_SubLink>();

		return arr;
	}
	private class TechCurnchTask extends AsyncTask<String, String, ArrayList<Item_SubLink>> {

		private static final String TAG = "TechCurnchTask";
		private String type;
		public TechCurnchTask(String type) {
			this.type = type;
		}
		@Override
		protected ArrayList<Item_SubLink> doInBackground(String... params) {
			ArrayList<Item_SubLink> arr = new ArrayList<Item_SubLink>();
			try {
				URL url = new URL(params[0]);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				InputStream inputStream = connection.getInputStream();
				arr = processXML(inputStream);
			} catch (MalformedURLException e) {
				Log.i(TAG, "TechCurnchTask_MalformedURLException_Erro url: " + e.toString());
			} catch (IOException e) {
				Log.i(TAG, "TechCurnchTask_IOException_Erro url: " + e.toString());
			} catch (Exception e) {
				Log.i(TAG, "TechCurnchTask_Exception_Erro url: " + e.toString());
			}
			return arr;
		}
		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
		}
		public ArrayList<Item_SubLink> processXML( InputStream inputStream ) throws Exception {
			ArrayList<Item_SubLink> arr = new ArrayList<Item_SubLink>();
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document documentXML = builder.parse(inputStream);

			Element rootElement = documentXML.getDocumentElement();
			NodeList itemList = rootElement.getElementsByTagName(this.type);
			Node currentChild = null;
			NodeList currentChildeList = null;
			Node childOfChild = null;
			String title = "";
			String linkImage = "";
			String link = "";
			String description="";
			String date = "";
			for ( int i = 0; i < itemList.getLength(); i++ ) {
				currentChild = itemList.item(i);
				currentChildeList = currentChild.getChildNodes();
				for ( int j = 0; j < currentChildeList.getLength();  j++ ) {
					childOfChild = currentChildeList.item(j);
					if ( childOfChild.getNodeName().equals("title") ) {
						title = childOfChild.getTextContent();
					}
					if ( childOfChild.getNodeName().equals("description") ) {
						description = childOfChild.getTextContent();
					}
					if ( childOfChild.getNodeName().equals("link") ) {
						link = childOfChild.getTextContent();
					}
					if ( childOfChild.getNodeName().equals("pubDate") ) {
						date = childOfChild.getTextContent();
					}
					if ( childOfChild.getNodeName().equals("summaryImg") ) {
						linkImage = childOfChild.getTextContent();
					}

				}
				arr.add(new Item_SubLink(title, description, link, date, linkImage));
			}

			return arr;
		}

	}


}
