package com.example.offcommtech;

import java.io.IOException;

import com.example.offcomm.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EnterCode extends Activity implements OnClickListener {
	EditText code;
	Button continue3;
	Sha1Hex obj = new Sha1Hex();
	String hexcode;
	FileRW a;
	SharedPreferences pref;
	SharedPreferences.Editor editor;
	TextView recevAgain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_code);
		code = (EditText) findViewById(R.id.code);
		recevAgain = (TextView) findViewById(R.id.recevAgain);
		continue3 = (Button) findViewById(R.id.enable_continue);
		continue3.setEnabled(false);
		continue3.setOnClickListener(this);
		pref = getSharedPreferences("testapp", MODE_PRIVATE);
		editor = pref.edit();
		

		SharedPreferences preferences2 = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		String next = preferences2.getString("next", "");
		System.out.println("next in enter code :" + next);
		recevAgain.setPaintFlags(recevAgain.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
		recevAgain.setOnClickListener(this);
		a = new FileRW(this, "hexcode.txt");
		try {
			hexcode = a.Read().toString();
			System.out.println("in enter code" + hexcode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Toast.makeText(getApplicationContext(), hexcode, Toast.LENGTH_LONG).show();
		code.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.length() == 6) {
					if (code.getText().toString().equals(hexcode)) {
						continue3.setEnabled(true);

					} else {
						System.out.println("hexcode= " + hexcode
								+ "\nenterredtext= " + s.toString());
						Toast.makeText(getApplicationContext(),
								"Enter Verification code is wrong",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.enter_code, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.enable_continue) {

			talkwithserver a = new talkwithserver(this);
			this.finish();
			a.execute();

		}else if(v.getId()==R.id.recevAgain){
			/*Intent myintent123 = new Intent(this,MainActivity.class);
			startActivity(myintent123);*/
			Toast.makeText(getApplicationContext(), "We cant help you !", Toast.LENGTH_LONG).show();
		}

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
			String phno = preferences.getString("phno", "");
			data = "R," + phno + ",192.168.1.123";
			System.out.println("phone number: " + phno);
			sendDataToServer a = new sendDataToServer(data);
			result = a.receive();
			System.out.println("Result: " + result);
			//remove this in order to continue communication with server
			
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
						"Registration Success!", Toast.LENGTH_LONG).show();
				SharedprefClass b = new SharedprefClass(getApplicationContext());
				b.put("regflag", "1");

				Intent myintent = new Intent(getApplicationContext(),
						mainActivity.class);
				startActivity(myintent);
				
				
			} else {
				Toast.makeText(getApplicationContext(), "Registration Failed!",
						Toast.LENGTH_LONG).show();
			}

		}
	}

}
