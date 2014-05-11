package com.example.offcommtech;

import android.app.Activity;
import android.app.Dialog;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.offcomm.R;

public class CallPopupDialogue extends Activity {
	Ringtone r;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Toast toast = Toast.makeText(this, "Incoming call",
				Toast.LENGTH_LONG);
		toast.show();
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.calling);
		dialog.setTitle("Incoming Call");
		Button accept = (Button) dialog.findViewById(R.id.accept);
		Uri notification = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		r = RingtoneManager.getRingtone(getBaseContext(), notification);
		// playing sound alarm
		r.play();

		accept.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				r.stop();
				receiFromZte r2 = new receiFromZte();
				r2.start();
				sendAckToZte connection2 = new sendAckToZte("B",Connection.ipaddress);
				connection2.SendAcknowledgmnet();
				
			}
		});
		dialog.show();
	}

}