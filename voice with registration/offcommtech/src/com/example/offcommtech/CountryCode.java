package com.example.offcommtech;

import java.util.Locale;

import com.example.offcomm.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CountryCode extends Activity implements OnItemClickListener {
ListView listview;
GetCountryName getcountryname = new GetCountryName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.country_code);
		String[] recourseList = this.getResources().getStringArray(
				R.array.CountryCodes);

		listview = (ListView) findViewById(R.id.listView);
		listview.setAdapter(new CountriesListAdapter(this, recourseList));
		listview.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.country_code, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		String itemValue = (String)listview.getItemAtPosition(position);
		String[] g=itemValue.split(",");
		String display = getcountryname.GetCountryZipCode(g[1]).trim()+""+"(+"+getcountryname.GetCountryZipCode(g[0]).trim()+")";
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result", display);
		setResult(RESULT_OK, returnIntent);
		finish();

	}

}
