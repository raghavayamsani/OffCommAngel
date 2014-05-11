package com.example.offcommtech;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

public class sendAudio2Zte implements Runnable {
Thread t ;

	public sendAudio2Zte() {
		// TODO Auto-generated constructor stub
		
		t = new Thread(this,"zte");
		t.start();
	}

	@Override
	public void run() {
		System.out.println("sending audio to zte **");
		DatagramSocket dSocket = null;
		try {
			dSocket = new DatagramSocket();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		android.os.Process
				.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);

		Log.d("voice", "Thread starting...");
		int maxBufferSize = 4096; // my value. see what's working best
									// for you.
		@SuppressWarnings("deprecation")
		int minBufferSize = AudioRecord.getMinBufferSize(11025,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT);
		int actualBufferSize = Math.max(minBufferSize, maxBufferSize);
		actualBufferSize = actualBufferSize * 1;
		@SuppressWarnings("deprecation")
		AudioRecord arec = new AudioRecord(MediaRecorder.AudioSource.VOICE_COMMUNICATION,
				11025, AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT, actualBufferSize);

		byte[] buffer = new byte[actualBufferSize];
		arec.startRecording();
		while (Thread.currentThread().isAlive()
				&& !Thread.currentThread().isInterrupted()) {

			try {
				arec.read(buffer, 0, actualBufferSize);
				DatagramPacket dPacket = new DatagramPacket(buffer,
						actualBufferSize);
		

				dPacket.setAddress(InetAddress.getByName(Connection.ipaddress));
				dPacket.setPort(2047);
				dSocket.send(dPacket);

			} catch (Exception e) {
				dSocket.close();
				e.printStackTrace();
			}

		}
	}

	
}
