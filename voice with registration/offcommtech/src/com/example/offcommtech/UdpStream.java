package com.example.offcommtech;

import com.example.offcomm.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/** UdpStream activity sends and recv audio data through udp */
@SuppressLint("NewApi")
public class UdpStream extends Activity implements OnClickListener {
	static final String LOG_TAG = "UdpStream";
	ImageButton btnSend;
	Button btnRecv;
	Button btn1send3;
	Button btnreceive;
	ImageButton clear;

	boolean isBound = false;
	sendAckToS3 connection;
	EditText et;
	sendCallDataToserver data;
	Utils u;
	static String destip = "";


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.udpstream);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
		btnSend = (ImageButton) findViewById(R.id.btnSend);
		et = (EditText) findViewById(R.id.destPhno);
		btnreceive = (Button) findViewById(R.id.button2);
		clear = (ImageButton) findViewById(R.id.clear);

		btnSend.setOnClickListener(this);

		Intent i = getIntent();
		clear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et.setText("");

			}
		});

		// To listen to service

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnSend:
			// sending audio
			System.out.println("button send");
			Utils u = new Utils();
			String destPhone = et.getText().toString();
			String myphoneno = "";
			// ///////////////////////////////////////////////////////////
			SharedprefClass pref = new SharedprefClass(getApplicationContext());
			myphoneno = pref.get("phno");
			if (destPhone.equals(myphoneno)) {
				Toast.makeText(getApplicationContext(),
						"What is the use of calling your own number !",
						Toast.LENGTH_LONG).show();
			} else {
				System.out.println("entering in background");
				// TODO Auto-generated method stub
				data = new sendCallDataToserver("C," + destPhone + ","
						+ u.getIPAddress(true));
				System.out.println("sending server to c with dest phone"+destPhone);
				String result = new String(data.receive());
				System.out.println("start receiving");
				result = new String(result.trim());
				destip = result;

				new sendAudioToS3();

				// sending acknowledgment before sending voice
				connection = new sendAckToS3("A" + "," + myphoneno);
				try {
					connection.SendAcknowledgmnet();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				initiatecall call = new initiatecall(destPhone,myphoneno);
				call.execute();
			}
			break;

		
		default:
			break;
		}

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

	}

	/*
	 * private BroadcastReceiver uiUpdated = new BroadcastReceiver() {
	 * 
	 * @Override public void onReceive(Context context, Intent intent) { // On
	 * call receiving Toast toast = Toast.makeText(context, "Incoming call",
	 * Toast.LENGTH_LONG); toast.show();
	 * 
	 * Intent call_incoming = new Intent();
	 * call_incoming.setClassName("com.example.voice",
	 * "com.example.voice.CallPopupDialogue");
	 * call_incoming.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	 * context.startActivity(call_incoming);
	 * 
	 * } };
	 */

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// unregisterReceiver(uiUpdated);
	}

	public void settext(View v) {
		Button b = (Button) v;
		String text = b.getText().toString();
		et.append(text);
	}

	class initiatecall extends AsyncTask<String, String, String> {
		String destPhone;
		String myphoneno;
		public initiatecall(String destPhone, String myphoneno) {
			// TODO Auto-generated constructor stub
			System.out.println("iniate call");
			this.destPhone= destPhone;
			System.out.println("dest phone number"+destPhone);
			this.myphoneno = myphoneno;
			System.out.println("my phone no"+myphoneno);
		}

		@Override
		protected String doInBackground(String... params) {
			
			return null;
		}

	}
}
