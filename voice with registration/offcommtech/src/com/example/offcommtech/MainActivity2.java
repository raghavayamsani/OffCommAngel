package com.example.offcommtech;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.example.offcomm.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class MainActivity2 extends Activity implements OnClickListener {
	TextView country;
	EditText regnum;
	Button continue2;
	String result, input, hexcode;
	String code = "hexcode.txt";
	FileRW write = new FileRW(this, code);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.register);

		country = (TextView) findViewById(R.id.cou_code);
		country.setOnClickListener(this);
		regnum = (EditText) findViewById(R.id.register_num);
		continue2 = (Button) findViewById(R.id.continue2);
		continue2.setOnClickListener(this);
		continue2.setEnabled(false);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		String next = preferences.getString("next", "");
		System.out.println("next :" + next);
		if (next.equals("1")) {

			Intent myinIntent = new Intent(this, EnterCode.class);
			startActivity(myinIntent);
		}

		regnum.addTextChangedListener(new TextWatcher() {

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
				if (s.length() == 10) {

					continue2.setEnabled(true);

				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {

			if (resultCode == RESULT_OK) {
				result = data.getStringExtra("result");
				System.out.println(result);
				country.setText(result);

			}
			if (resultCode == RESULT_CANCELED) {
				// Write your code if there's no result
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.cou_code) {
			Intent intent = new Intent(this, CountryCode.class);
			startActivityForResult(intent, 1);
		}

		if (v.getId() == R.id.continue2) {
			input = regnum.getText().toString();
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(this);
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString("phno", input);

			editor.commit();

			// ////////////////////

			SharedprefClass aa = new SharedprefClass(getApplicationContext());
			aa.put("next", "1");
			System.out.println("setting next from main activity :"
					+ aa.get("next"));

			// //////////////////
			if (input.matches("^[0-9]{10}$")) {

				System.out.println(input);
				Sha1Hex obj = new Sha1Hex();
				try {
					hexcode = obj.makeSHA1Hash(input);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					write.Write(hexcode);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				

					//SendCodeSms sms = new SendCodeSms(this);
					//sms.sendSMS(input,
							//"Your Code for registration in OffComm is" + " "
								//	+ hexcode);

					Intent in = new Intent(this, EnterCode.class);
					startActivity(in);
					System.out.println("here");
	

		
			} else {
				Toast.makeText(getApplicationContext(),
						"Please Enter 10 digit Mobile Number!",
						Toast.LENGTH_LONG).show();
			}
			this.finish();
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		country.setText(savedInstanceState.getString("country"));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString("country", country.getText().toString());
	}

}
