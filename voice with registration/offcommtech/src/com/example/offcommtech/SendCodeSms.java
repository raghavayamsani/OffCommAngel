package com.example.offcommtech;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SendCodeSms extends Activity {
	private final Context context;
	public SendCodeSms(Context context)
	{
		super();
		this.context = context;
	}


	      
		public void sendSMS(String phoneNumber, String message)
	    {        
	        PendingIntent pi = PendingIntent.getActivity(context, 0,
	            new Intent(context, SendCodeSms.class), 0);  
	      
	        	
	      //  try{
	        	
	        
	        SmsManager sms = SmsManager.getDefault();
	        sms.sendTextMessage(phoneNumber, null, message, pi, null); 
	       
	        Toast.makeText(context, "SMS Sent!",
                    Toast.LENGTH_LONG).show();
	        	
	      //  }	catch(Exception e)
	       // {
	        //	Toast.makeText(getApplicationContext(), "SMS failed, please try again later!",
	          //          Toast.LENGTH_LONG).show();
	            //  e.printStackTrace();
	       
	      //  }
	    }
	
	
	
	
	
	
}
