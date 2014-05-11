package com.example.offcommtech;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

public class ServiceListen extends Service {

	Socket clientSocket;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO do something useful
		StartServer();
		return Service.START_STICKY;
	}

	private void StartServer() {
		// TODO Auto-generated method stub

		System.out.println("server Started listening to port 8117");
		Connection c = new Connection(this);
		c.execute();

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}

class Connection extends AsyncTask<String, Void, String> {

	ServerSocket listenSocket = null;
	DataInputStream in;
	String data;
	int serverPort = 8117;
	sendAckToZte connection;
	Intent i4;
	ServiceListen c;
	receiveAudioFroms3 x;
	Utils u = new Utils();
	static String ipaddress = "";

	public Connection(ServiceListen serviceListen) {
		// TODO Auto-generated constructor stub
		this.c = serviceListen;

	}

	@Override
	protected String doInBackground(String... port) {
		// TODO Auto-generated method stub
		System.out.println("connection running in background");
		try {
			listenSocket = new ServerSocket(serverPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			Socket clientSocket;
			try {
				clientSocket = listenSocket.accept();
				in = new DataInputStream(clientSocket.getInputStream());
				data = in.readUTF();

				System.out.println("Received into server in service: " + data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// if ack from zte mobile is "A" then start listening to zte and
			// start sending voice to zte including acknowledgement
			if (data.startsWith("A")) {
				String phone[] = data.split(",");
				System.out
						.println("my phone no in service listener" + phone[1]);
				// //////////////////////////////////////////////////////////////////////////////////
				/*
				 * i4 = new Intent();
				 * i4.setAction("com.example.voice.callingarmpro");
				 * System.out.println("Service Started"); i4.putExtra("<Key>",
				 * "text");
				 */

				// c.sendBroadcast(i4);

				// 8888888888888888888888888888888888888888888888888888888888888888888888888888888888
				sendCallDataToserver d = new sendCallDataToserver("C,"
						+ phone[1] + "," + u.getIPAddress(true));

				System.out.println("Reply for phone no:" + d.receive());
				String result = new String(d.receive());
				result = new String(result.trim());
				System.out.println("Reply22 for phone no :" + result);
				ipaddress = result;

				Intent call_incoming = new Intent(c,CallPopupDialogue.class);
				call_incoming.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				c.startActivity(call_incoming);

				// 88888888888888888888888888888888888888888888888888888888888888

				// ///////////////////////////////////////////////////////////////////////////////////

			} else if (data.equals("B")) {

				System.out.println("Started listening to S3");

				sendAckToS3 a = new sendAckToS3("C", UdpStream.destip);
				try {
					a.SendAcknowledgmnet();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (data.equals("C")) {
				new sendAudio2Zte();
				sendAckToZte d = new sendAckToZte("D", ipaddress);
				d.SendAcknowledgmnet();
			} else if (data.equals("D")) {
				x = new receiveAudioFroms3();
				x.start();
			} else if (data.equals("E")) {
				x.stop();
			}
		}

	}

}