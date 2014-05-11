package com.finalproject.cse;

import java.util.Random;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.dexterlabs.menulist.R;

import com.viewpagerindicator.PageIndicator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This basesampleactivity was a base fragment class for the left side slider
 * module.
 * 
 * @author WINDAdmin
 * 
 */
public abstract class BaseSampleActivity extends FragmentActivity {
	private static final Random RANDOM = new Random();

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.random:
			final int page = RANDOM.nextInt(mAdapter.getCount());
			Toast.makeText(this, "Changing to page " + page, Toast.LENGTH_SHORT)
					.show();
			mPager.setCurrentItem(page);
			return true;

		case R.id.add_ftp:
			ftpSession = new FTPSessionManager(getApplicationContext());
			if (ftpSession.isFTPLoggedIn()) {
				Log.e("alreday looged in", "alreday logged in");
				Toast.makeText(getApplicationContext(), "Already Logged in",
						Toast.LENGTH_SHORT).show();
			} else {
				Log.e("not looged in base", "not logged in base");
				showAlert();
			}
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void showAlert() {
		Log.e("show alert", "show alert");
	
		AlertDialog.Builder ftpBuilder = new AlertDialog.Builder(
				BaseSampleActivity.this);
		ftpBuilder.setTitle("Give FTP Server Credentials");
		Log.e("after dialog", "after dialog");
	
		LayoutInflater li = LayoutInflater.from(BaseSampleActivity.this);
		View ftpPrompt = li.inflate(R.layout.dialog_layout, null);
	
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
		
		ftpBuilder.setView(ftpPrompt);

		ftpBuilder.setPositiveButton("Enter",
				new DialogInterface.OnClickListener() {

					public void onClick(final DialogInterface dialog,
							int whichButton) {
						System.out.println("here2");
					
						ftpIpAdress = edit_ftpIpaddress.getText().toString();
						ftpPort = edit_ftpPort.getText().toString();
						ftpUserName = edit_ftpUserName.getText().toString();
						ftpPassword = edit_ftpPassword.getText().toString();
						System.out.println("here2");
						Log.e("ip4566", "FTP Ip Address name: " + ftpIpAdress);
						Log.e("port", "FTP Ip Port name: " + ftpPort);
						Log.e("user", "FTP Ip Username: " + ftpUserName);
						Log.e("pwd", "FTP Ip Password: " + ftpPassword);
						System.out.println("here2");
						
						try {
							FTPClient mFTPClient = new FTPClient();
							// connecting to the host
							System.out.println("here");
							mFTPClient.connect(ftpIpAdress,
									Integer.parseInt(ftpPort));
							System.out.println("here1");
							// now check the reply code, if positive
							// mean connection success
							if (FTPReply.isPositiveCompletion(mFTPClient
									.getReplyCode())) {
								// login using username & password
								System.out
										.println("connetion established !!!--------");
								boolean status = mFTPClient.login(ftpUserName,
										ftpPassword);
								if (!status) {
									ftpServerError = "";
									dialog.cancel();
									showErrorDialog();
									// showDialog(DIALOG_FTP_SERVER_ERROR);
								} else {
									ftpSession.createFTPServerSession(
											ftpIpAdress, ftpPort, ftpUserName,
											ftpPassword);
									dialog.cancel();
								}
								/*
								 * Set File Transfer Mode
								 * 
								 * To avoid corruption issue you must specified
								 * a correct transfer mode, such as
								 * ASCII_FILE_TYPE, BINARY_FILE_TYPE,
								 * EBCDIC_FILE_TYPE .etc. Here, I use
								 * BINARY_FILE_TYPE for transferring text,
								 * image, and compressed files.
								 */

								// mFTPClient.setFileType(FTP.BINARY_FILE_TYPE);
								// mFTPClient.enterLocalPassiveMode();

								// return status;
							} else {

								Log.d("ftp",
										"iam in else case ftp coonetion error");
								ftpServerError = "Can't connect to Ftp server,please check hostname and port";
								Toast.makeText(getApplicationContext(),
										ftpServerError, Toast.LENGTH_SHORT)
										.show();
							}
						} catch (Exception e) {
							// e.printStackTrace();
							// Log.d(TAG,
							// "Error: could not connect to host " +
							// ftpIpAdress );
						}
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
}
