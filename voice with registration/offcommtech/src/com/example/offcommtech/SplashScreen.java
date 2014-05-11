package com.example.offcommtech;

import com.example.offcomm.R;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;

public class SplashScreen extends Activity {
	SharedPreferences pref;
	Intent mIntent;
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		
		SharedprefClass a = new SharedprefClass(getApplicationContext());
		String regflag = a.get("regflag");
		String nextflag = a.get("next");
		System.out.println("registartion flag :" + regflag);
		System.out.println("next flag :"+ nextflag);
		if (regflag.equals("1")) {
			
			Intent myinIntent = new Intent(this, mainActivity.class);
		System.out.println("hi");
			startActivity(myinIntent);
			System.out.println("hi");
			this.finish();
			
		
		} else {
			if(nextflag.equals("1")){
				
				Intent myinIntent3 = new Intent(this, EnterCode.class);
				startActivity(myinIntent3);
				this.finish();
			}else{
			Intent myinIntent2 = new Intent(this, MainActivity2.class);
			startActivity(myinIntent2);
			this.finish();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

}
