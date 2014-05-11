package com.example.offcommtech;

import com.example.offcomm.R;
import com.example.offcommtech.EnterCode.talkwithserver;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class mainActivity extends Activity implements OnClickListener {
	Utils u = new Utils();
	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);
		Button login = (Button) findViewById(R.id.login);

		login.setOnClickListener(this);

		talkwithserver a = new talkwithserver(this);
		a.execute();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.login) {

			// TODO Auto-generated method stub

			System.out.println("Clicked dail number");
			String myphone = "";
			SharedprefClass pref = new SharedprefClass(getApplicationContext());
			myphone = pref.get("phno");
			System.out.println("MY phone:"+myphone);
			Intent i = new Intent(getApplicationContext(), UdpStream.class);

			startActivity(i);

		}

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// Registration of receiver
		// registerReceiver(uiUpdated, new IntentFilter("LOCATION_UPDATED1"));
		// To start a service
		Intent intent = new Intent(this, ServiceListen.class);
		this.startService(intent);
	}

	class talkwithserver extends AsyncTask<String, String, String> {
		String data;
		String result = "";
		Context context;

		public talkwithserver(Context context) {
			// TODO Auto-generated constructor stub
			this.context = context;
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());
			System.out.println("here in login");
			String phno = preferences.getString("phno", "");
			data = "L," + phno + ","+u.getIPAddress(true);
			System.out.println("here in login 1");
			System.out.println("phone number: " + phno);
			sendDataToServer a = new sendDataToServer(data);
			System.out.println("here in login after data");
			result = a.receive();
			System.out.println("here in login after data receive");
			System.out.println("Result: " + result);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				Toast.makeText(getApplicationContext(),
						"You are not connected to OffComm !", Toast.LENGTH_LONG).show();
				
			} else if (result.equals("1")) {
				Toast.makeText(getApplicationContext(),
						"Login Success!", Toast.LENGTH_LONG).show();
				
				
			} else {
				Toast.makeText(getApplicationContext(), "Login Failed!",
						Toast.LENGTH_LONG).show();
			}

		}
	}

}
