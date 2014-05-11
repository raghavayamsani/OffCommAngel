package com.finalproject.cse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.dexterlabs.menulist.R;


@SuppressLint({ "SimpleDateFormat", "ValidFragment" })
public class DetailFrag extends Fragment {
	// public Button button_back,button_menu;
	public Button button_back, button_order;
	public ListView lv;
	ArrayList<BaseItem> myitemlist;
	public int n = 0;
	public String strng = "default";
	private SimpleAdapter arrayAdapter;
	List<Map<String, String>> items;
	delListener mCallback;
	numListener numCallback;
	private static final int ID_UP = 1;
	private static final int ID_DOWN = 2;

	/*
	 * String ftpIpAdress; String ftpPort; String ftpUserName; String
	 * ftpPassword;
	 */

	// String ftpIpAdress="192.168.1.5";
	// String ftpIpAdress="192.168.1.2";
	// String ftpIpAdress="restaurant.serveftp.com";
	// String ftpIpAdress="menu.serverftp.com";
	// String ftpPort="21";
	// String ftpUserName="dexter";
	// String ftpPassword="dl@123";
	// String ftpUserName="restaurant";
	// String ftpPassword="rest@123";

	File myfile;
	FTPClient client;

	String FileName;

	String[] arr1, arr2, arr3;

	SaveBill sBill;

	// boolean sendResult = false;
	String data;

	String tab;
	TextView resultText;
	FTPSessionManager ftpSession;

	// ProgressBar progress;
	// Container Activity must implement these interfaces
	// this listener is to inform the parent activity that an item has been
	// deleted from the menu
	public interface delListener {
		public void onDelClick(int i);
	}

	// this listener is to inform the parent activity that an item's number
	public interface numListener {
		public void onNumChange(int i, int p);
	}

	@SuppressLint("ValidFragment")
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (delListener) activity;
			numCallback = (numListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement Listeners!!");
		}
	}

	public DetailFrag() {

	}

	public DetailFrag(ArrayList<BaseItem> s) {
		myitemlist = s;
		// Log.e("detailfragment","list size::"+myitemlist.size());
		// Log.e("detailfragment","list::"+myitemlist.get(0).title);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// first prepare the list view to accept the items
		final View view = inflater.inflate(R.layout.detail, container, false);
		button_back = (Button) view.findViewById(R.id.button1);
		button_order = (Button) view.findViewById(R.id.btn_order);
		resultText = (TextView) view.findViewById(R.id.resultText);
		// progress=(ProgressBar)view.findViewById(R.id.marker_progress);
		// progress.setIndeterminate(false);
		sBill = new SaveBill();
		// table_name=sTable.getTable();
		String timeStamp1 = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
				.format(new Date());
		Log.e("date", "date::" + timeStamp1);
		FileName = "Order" + "_" + timeStamp1 + ".txt";
		
		lv = (ListView) view.findViewById(R.id.listview1);

		// String[] arr1 = new String[myitemlist.size()];
		// String[] arr2 = new String[myitemlist.size()];
		// String[] arr3 = new String[myitemlist.size()];
		Log.e("itemslist", "itemslist::" + myitemlist);
		arr1 = new String[myitemlist.size()];
		arr2 = new String[myitemlist.size()];
		arr3 = new String[myitemlist.size()];
		for (int i = 0; i < myitemlist.size(); i++) {
			arr1[i] = myitemlist.get(i).title;
			arr2[i] = myitemlist.get(i).price;
			arr3[i] = "Qty: " + Integer.toString(myitemlist.get(i).num);

		}

		String[] from = new String[] { "str", "price", "numbs" };
		int[] to = new int[] { R.id.textp1, R.id.textp2, R.id.textp3 };
		items = new ArrayList<Map<String, String>>();

		for (int i = 0; i < arr1.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("str", String.format("%s", arr1[i]));
			map.put("price", String.format("%s", arr2[i]));
			map.put("numbs", String.format("%s", arr3[i]));
			items.add(map);
		}

		arrayAdapter = new SimpleAdapter(getActivity(), items,
				R.layout.menulist, from, to);

		lv.setAdapter(arrayAdapter);

		// this is the single click listener.. :D inside it lies
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View mview,
					final int position, long id) {
				Context mContext = getActivity();
				LayoutInflater inflater = (LayoutInflater) mContext
						.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
				View layout = inflater.inflate(R.layout.dialog,
						(ViewGroup) mview.findViewById(R.id.layout_root));
				final NumberPicker np = (NumberPicker) layout
						.findViewById(R.id.numberPicker1);
				np.setMaxValue(10);
				np.setMinValue(1);
				np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setView(layout);
				builder.setCancelable(true);
				// builder.setIcon(R.drawable.dialog_question);
				builder.setTitle("Change Number of items.");
				builder.setInverseBackgroundForced(true);
				// this is the dialog element that implements the changing
				// number shz
				builder.setPositiveButton("Update",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Log.e("update click", "value::" + np.getValue());
								numCallback.onNumChange(np.getValue(), position);
								Map<String, String> mss = items.get(position);
								Log.e("num picker", "picker::" + mss);
								mss.put("numbs", String.format("Qty: %s",
										Integer.toString(np.getValue())));
								items.set(position, mss);
								arrayAdapter.notifyDataSetChanged();
								setbill(view);
							}
						});

				AlertDialog alert = builder.create();
				alert.show();

			}
		});

		// long click to delete code
		lv.setLongClickable(true);
		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> av, View v, int pos,
					long id) {
				// Toast.makeText(getActivity(), Integer.toString(pos),
				// Toast.LENGTH_SHORT).show();
				items.remove(pos);
				myitemlist.remove(pos);
				arrayAdapter.notifyDataSetChanged();
				setbill(view);
				mCallback.onDelClick(pos);
				return true;
			}
		});

		// GTF back
		button_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				fm.popBackStack();
			}

		});

		button_order.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.e("order click", "order click");

				new UploadFile().execute("");

			}
		});

		setbill(view);

		return view;
	}

	public void setbill(View view) {
		TextView child = (TextView) view.findViewById(R.id.bill_amount);
		int sum = 0;
		for (int i = 0; i < myitemlist.size(); i++) {
			int p = Integer.parseInt(myitemlist.get(i).price);
			sum += myitemlist.get(i).num * p;
			sBill.setBill(sum);
			Log.e("setbill", "setbill sum::" + sum);
		}
		if (child != null) {
			child.setText(Integer.toString(sum));
			Log.d("XXX", "fdfdffdfdfdf");
		}

	}

	public void uploadData() {
		int price = sBill.getBill();
		Log.e("price", "getbill::" + price);

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity()
						.getApplicationContext());
		tab = prefs.getString("table", null);
		Log.e("getTable name", "get table name::" + tab);

		try {
			File newFolder = new File(
					Environment.getExternalStorageDirectory(), "MenuList");
			if (!newFolder.exists()) {
				newFolder.mkdir();
			}
			try {
				// File file = new File(newFolder, "MyTest" +".txt");
				File file = new File(newFolder, FileName);
				file.createNewFile();
				// FileWriter out = new FileWriter(file,false);
				FileWriter out = new FileWriter(file);
				BufferedWriter writeFile = new BufferedWriter(out);
				Log.e("before write", "before write");
				writeFile.write("\r\n");
				writeFile.write(tab);
				// writeFile.newLine();
				writeFile.write("\r\n");
				writeFile.write("======");
				// writeFile.newLine();
				writeFile.write("\r\n");
				writeFile.write("\r\n");
				writeFile.write("Item");
				writeFile.write("\r\t\t\t\t\t\t");
				writeFile.write("Quantity");
				writeFile.write("\r\t\t\t\t\t\t");
				writeFile.write("Price");
				// writeFile.newLine();
				writeFile.write("\r\n");
				writeFile.write("-----");
				writeFile.write("\r\t\t\t\t\t\t");
				writeFile.write("---------");
				writeFile.write("\r\t\t\t\t\t\t");
				writeFile.write("------");
				// writeFile.newLine();
				writeFile.write("\r\n");
				for (int i = 0; i < arr1.length; i++) {
					// arr1[i]=myitemlist.get(i).title;
					// arr2[i]=myitemlist.get(i).price;
					// arr3[i]="Qty: "+Integer.toString(myitemlist.get(i).num);
					Log.v("for loop", "for loop::" + arr1[i].toString());

					writeFile.write("\r\n");
					writeFile.write("\r\n");
					writeFile.write(arr1[i].toString());
					writeFile.write("\r\t\t\t\t\t\t");
					writeFile.write(arr3[i].toString());
					writeFile.write("\r\t\t\t\t\t\t");
					writeFile.write(arr2[i].toString());
					writeFile.write("\r\n");

				}
				writeFile.write("\r\n");
				writeFile.write("\r\n");
				writeFile.write("\r\n");
				writeFile.write("\r\n");
				writeFile.write("\r\n");
				writeFile
						.write("\r\t\t\t\t\t\t\t" + "Total Price ::  " + price);

				writeFile.close();
				out.close();
				Log.v("Data written to file", "data written to file");

			} catch (Exception ex) {
				System.out.println("ex: " + ex);
			}
		} catch (Exception e) {
			System.out.println("e: " + e);
		}

	}

	@SuppressLint("SdCardPath")
	private class UploadFile extends AsyncTask<String, Void, Boolean> {
		String ftpIpAdress, ftpPort, ftpUserName, ftpPassword;

		

		@Override
		protected void onPreExecute() {
		

		}

		@Override
		protected Boolean doInBackground(String... params) {
			Log.e("doInBackground", "doInBackground::");
			boolean sendResult = false;

			uploadData();

			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(getActivity()
							.getApplicationContext());
			ftpIpAdress = prefs.getString("ftpIpAdress", null);
			ftpPort = prefs.getString("ftpPort", null);
			ftpUserName = prefs.getString("ftpUserName", null);
			ftpPassword = prefs.getString("ftpPassword", null);
			Log.v("shared ip", "shared ip::" + ftpIpAdress);
			Log.v("shared user", "shared user::" + ftpUserName);
			Log.v("shared pwd", "shared pwd::" + ftpPassword);

		
			try {
				

				File file = new File("/sdcard/MenuList/" + FileName);
				
				
				HttpClient httpclient = new DefaultHttpClient();
				httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

				HttpPost httppost = new HttpPost("http://192.168.42.1:8080/angelhack/test.php");
				

				MultipartEntity mpEntity = new MultipartEntity();
				ContentBody cbFile = new FileBody(file);
				mpEntity.addPart("userfile", cbFile);

				httppost.setEntity(mpEntity);
				System.out.println("executing request " + httppost.getRequestLine());
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity resEntity = response.getEntity();

				System.out.println(response.getStatusLine());
				if (resEntity != null) {
				System.out.println(EntityUtils.toString(resEntity));
				}
				if (resEntity != null) {
				resEntity.consumeContent();
				}

				httpclient.getConnectionManager().shutdown();
				

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
	
		}

	}

	public static boolean deleteDirectory(File path) {
		Log.e("delete directory", "dleete");
		if (path.exists()) {
			File[] files = path.listFiles();
			if (files == null) {
				return true;
			}
			for (int i = 0; i < files.length; i++) {
				Log.e("files not null", "for loop");
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}

}
