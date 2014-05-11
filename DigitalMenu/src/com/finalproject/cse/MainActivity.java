  package com.finalproject.cse;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.dexterlabs.menulist.R;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;

/**
 * This ish the launcher activity page. we extend BaseSampleActivity which
 * extends FragmentActivity. Base Sample Activity has some code for the menu's
 * only and seems redundant.
 * 
 * @author WINDAdmin
 * 
 */
public class MainActivity extends FragmentActivity implements
		TestFragment.fragListener, MainFrag.callListener,
		DetailFrag.delListener, DetailFrag.numListener {
	FragmentManager fm;
	Fragment fragment;
	ArrayList<BaseItem> myitemlist = new ArrayList<BaseItem>();

	TestFragmentAdapter mAdapter;
	ViewPager mPager;
	PageIndicator mIndicator;

	private static final int FTP_HOST_ID = 12;
	private static final int FTP_PORT_ID = 13;
	private static final int FTP_NAMET_ID = 14;
	private static final int FTP_PWD_ID = 15;

	String ftpIpAdress;
	String ftpPort;
	String ftpUserName;
	String ftpPassword;
	String ftpServerError;
	FTPSessionManager ftpSession;

	String table;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(0);
		setContentView(R.layout.main_activity);
		ftpSession = new FTPSessionManager(getApplicationContext());
		selectTable();

		// this gets the swiping menu fragment set up and running
		mAdapter = new TestTitleFragmentAdapter(getSupportFragmentManager());
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		TitlePageIndicator indicator = (TitlePageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(mPager);
		indicator.setFooterIndicatorStyle(IndicatorStyle.Underline);
		mIndicator = indicator;

		// this sets up the right bar fragment and connects MainFrag to it.
		fm = getSupportFragmentManager();
		fragment = fm.findFragmentById(R.id.right_frag_container);

		if (fragment == null) {

			FragmentTransaction ft = fm.beginTransaction();
			ft.add(R.id.right_frag_container, new MainFrag());
			ft.commit();

		}

		// in case a listener is required that activates on every page change.
		// This listener can send the messages to the rightFrag
		mIndicator
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// Toast.makeText(MainActivity.this, "Changed to page "
						// + position, Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onPageScrolled(int position,
							float positionOffset, int positionOffsetPixels) {
					}

					@Override
					public void onPageScrollStateChanged(int state) {
					}
				});
	}

	/***
	 * This click is a receiver for an implementation in the left_PAGER. it
	 * receives a nice XML item that works properly. everything is cool.
	 */
	MainFrag f;

	@Override
	public void onItemClick(XmlResourceParser xmlItem) {
		Log.e("onitem click", "main" + xmlItem);
		FragmentManager fm = getSupportFragmentManager();
		if (fm.getBackStackEntryCount() == 0) {
			Log.v("if main click", "if main click");
			f = (MainFrag) fm.findFragmentById(R.id.right_frag_container);
		} else {
			fm.popBackStack();
			fm.executePendingTransactions();
		}

		if (f != null && xmlItem != null)
			f.update(xmlItem);
	}

	/**
	 * This interface is a fuckhead that switches the right fragment activity
	 * with the details fragment. fucking acts like a dickhead and wont work
	 * nicely with the XMLparser so because im lazy (stfu), halfway through i
	 * created (for lack of a fucking better name) a baseitem class to handle
	 * the fucking menudata that this fucking SDK required me to fucking bob
	 * around like a fucking basketball.. fuck javadoc sucks.
	 */
	@Override
	public void onButtonClick(BaseItem item) {
		// TODO Auto-generated method stub
		if (item != null)
			myitemlist.add(item);
		Log.e("main button click", "itemlist::" + myitemlist);
		// Log.d("XXX",item.title);
		// Bundle data = new Bundle();
		// data.putString("table", table);
		FragmentTransaction ft = fm.beginTransaction();
		// DetailFrag frag=new DetailFrag();
		ft.replace(R.id.right_frag_container, new DetailFrag(myitemlist));
		// frag.setArguments(data);
		ft.addToBackStack(null);
		ft.commit();
	}

	public void onMenuClick(Object object) {
		Log.e("menu click", "menu click");
		showAlertDialog();
		/*
		 * if(ftpSession.isFTPLoggedIn()) {
		 * Log.e("alreday looged in","alreday logged in");
		 * Toast.makeText(getApplicationContext
		 * (),"Already Logged in",Toast.LENGTH_SHORT).show(); } else{
		 * Log.e("not looged in","not logged in"); showAlertDialog(); }
		 */
	}

	/**
	 * This interface is coming from the delete fragment and is supposed to
	 * handle the deletion of the listiew i
	 */
	@Override
	public void onDelClick(int i) {
		Log.v("delete click", "delete click");
		// Log.d("XXX",
		// Integer.toString(i)+""+Integer.toString(myitemlist.size()));
		// there was a pop operation over here.. but apparently it wasnt
		// required.. a douple operaition was happening..
		// god only knows where the actual delete operation is happening and how
		// its surviving fragment swatches
		// if ever theres an issue with the bloody delete operation later. track
		// from this poin onwards.
		// myitemlist.remove(i);
	}

	@Override
	public void onNumChange(int i, int p) {

		BaseItem bit = myitemlist.get(p);
		bit.num = i;
		myitemlist.set(p, bit);
	}

	public void selectTable() {

		AlertDialog.Builder builderSingle = new AlertDialog.Builder(
				MainActivity.this);
		// builderSingle.setIcon(R.drawable.ic_launcher);
		builderSingle.setTitle("Select Table:");

		final String[] fileList = { "Table 1", "Table 2", "Table 3", "Table 4",
				"Table 5" };

		builderSingle.setSingleChoiceItems(fileList, -1,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Log.d(TAG,"E' stato premuto il pulsante: "+fileList[whichButton]);
						String table = fileList[whichButton];
						Log.e("table name", "table name::" + table);
						saveTable(table);
						// sTable.setTable(table);
						// String getname=sTable.getTable();
						// Log.e("table name get","table name get::"+getname);

					}
				});

		builderSingle.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// String strName = arrayAdapter.getItem(which);
						// Log.e("table name","table name::"+strName);
						dialog.dismiss();
					}
				});

		builderSingle.show();

	}

	public void saveTable(String table) {
		Log.e(" sharedpref count value", "count value::" + table);
		// SharedPreferences sharedPref =
		// this.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("table", table);
		editor.commit();

	}

	public void showAlertDialog() {
		Log.e("showAlertDialog", "showAlertDialog");
		// ftpSession=new FTPSessionManager(getApplicationContext());

		AlertDialog.Builder ftpBuilder = new AlertDialog.Builder(
				MainActivity.this);
		ftpBuilder.setTitle("Give FTP Server Credentials");
		Log.e("after showAlertDialog", "after showAlertDialog");
		// ftpBuilder.setMessage("Give FTP Server Credentials");
		LayoutInflater li = LayoutInflater.from(MainActivity.this);
		View ftpPrompt = li.inflate(R.layout.dialog_layout, null);
		// Use an EditText view to get user input.
		final EditText edit_ftpIpaddress = (EditText) ftpPrompt
				.findViewById(R.id.txt_address);
		final EditText edit_ftpPort = (EditText) ftpPrompt
				.findViewById(R.id.txt_port);
		final EditText edit_ftpUserName = (EditText) ftpPrompt
				.findViewById(R.id.txt_username);
		final EditText edit_ftpPassword = (EditText) ftpPrompt
				.findViewById(R.id.txt_password);

		edit_ftpIpaddress.setId(FTP_HOST_ID);
		edit_ftpPort.setId(FTP_PORT_ID);
		edit_ftpUserName.setId(FTP_NAMET_ID);
		edit_ftpPassword.setId(FTP_PWD_ID);
		// ftpIpaddress.setId(TEXT_ID);
		ftpBuilder.setView(ftpPrompt);

		ftpBuilder.setPositiveButton("Enter",
				new DialogInterface.OnClickListener() {

					public void onClick(final DialogInterface dialog,
							int whichButton) {
						// String value = input.getText().toString();
						ftpIpAdress = edit_ftpIpaddress.getText().toString();
						ftpPort = edit_ftpPort.getText().toString();
						ftpUserName = edit_ftpUserName.getText().toString();
						ftpPassword = edit_ftpPassword.getText().toString();

						Log.e("ipasdfsdf21", "FTP Ip Address name: " + ftpIpAdress);
						Log.e("port", "FTP Ip Port name: " + ftpPort);
						Log.e("user", "FTP Ip Username: " + ftpUserName);
						Log.e("pwd", "FTP Ip Password: " + ftpPassword);

						saveFtpDetails(ftpIpAdress, ftpPort, ftpUserName,
								ftpPassword);
						new Thread("FTPServer") {

							public void run() {
								try {
									FTPClient mFTPClient = new FTPClient();
									// connecting to the host
									mFTPClient.connect(ftpIpAdress,
											Integer.parseInt(ftpPort));

									// now check the reply code, if positive
									// mean connection success
									if (FTPReply
											.isPositiveCompletion(mFTPClient
													.getReplyCode())) {
										Toast.makeText(getApplicationContext(),
												"Connection Successful",
												Toast.LENGTH_SHORT).show();
										Log.e("try if", "try if");
										// login using username & password
										boolean status = mFTPClient.login(
												ftpUserName, ftpPassword);
										Log.e("status", "status::" + status);
										if (!status) {
											Log.e("no session", "no sesion");
											ftpServerError = "";
											dialog.cancel();

											showErrorDialog();
											// showDialog(DIALOG_FTP_SERVER_ERROR);
										} else {
											Log.e("create session",
													"create sesion");

											ftpSession.createFTPServerSession(
													ftpIpAdress, ftpPort,
													ftpUserName, ftpPassword);
											dialog.cancel();
										}
										/*
										 * Set File Transfer Mode
										 * 
										 * To avoid corruption issue you must
										 * specified a correct transfer mode,
										 * such as ASCII_FILE_TYPE,
										 * BINARY_FILE_TYPE, EBCDIC_FILE_TYPE
										 * .etc. Here, I use BINARY_FILE_TYPE
										 * for transferring text, image, and
										 * compressed files.
										 */

										// mFTPClient.setFileType(FTP.BINARY_FILE_TYPE);
										// mFTPClient.enterLocalPassiveMode();

										// return status;
									} else {

										Log.d("ftp",
												"iam in else case ftp coonetion error");
										ftpServerError = "Can't connect to Ftp server,please check hostname and port";
										Toast.makeText(getApplicationContext(),
												ftpServerError,
												Toast.LENGTH_SHORT).show();
									}
								} catch (Exception e) {
									// e.printStackTrace();
									// Log.d(TAG,
									// "Error: could not connect to host " +
									// ftpIpAdress );
								}
							}
						}.start();
						// return;
					}

				});

		ftpBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		AlertDialog alertDialog = ftpBuilder.create();

		// show it
		alertDialog.show();
	}

	public void showErrorDialog() {
		AlertDialog.Builder ftpErrorDialog = new AlertDialog.Builder(this);
		ftpErrorDialog.setTitle("Error");
		ftpErrorDialog
				.setMessage("Network error,check your internet for connecting to ftp server");
		ftpErrorDialog.setNeutralButton("OK",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
						// showDialog(DIALOG_FTP_SERVER_CREDENTIALS);
						// AndroidV.this.finish();
					}
				});

		AlertDialog ftpErrorAlert = ftpErrorDialog.create();
		ftpErrorAlert.show();
	}

	private void saveFtpDetails(String ftpIpAdress, String ftpPort,
			String ftpUserName, String ftpPassword) {

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("ftpIpAdress", ftpIpAdress);
		editor.putString("ftpPort", ftpPort);
		editor.putString("ftpUserName", ftpUserName);
		editor.putString("ftpPassword", ftpPassword);
		editor.commit();
		// TODO Auto-generated method stub

	}

}