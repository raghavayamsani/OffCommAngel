package com.finalproject.cse;

import android.util.Log;

public class SaveBill {
	private String table;
	
	private int bill;	

	public int getBill() {
		return bill;
	}

	

	public String getTable() {
		Log.e("getTable","getTable::"+table);
		return table;
	}


	public SaveBill()
	{
		
	}

	public void setTable(String table) {
		// TODO Auto-generated method stub
		Log.e("settable","settable::"+table);
		this.table = table;
	}

	public void setBill(int bill) {
		// TODO Auto-generated method stub
		Log.e("setbill","setbill::"+bill);
		this.bill = bill;
		
	}

}
