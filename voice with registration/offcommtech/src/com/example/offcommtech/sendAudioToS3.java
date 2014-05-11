package com.example.offcommtech;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

public class sendAudioToS3  implements Runnable {
	Thread t;

	public sendAudioToS3() {
		

		t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		System.out.println("Sending Audio to s3 **");
		DatagramSocket dSocketS = null;
		try {
			dSocketS = new DatagramSocket();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		android.os.Process
				.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);

		Log.d("voice", "Thread starting...");
		int maxBufferSizeS = 4096; // my value. see what's working best
									// for you.
		@SuppressWarnings("deprecation")
		int minBufferSizeS = AudioRecord.getMinBufferSize(11025,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT);
		int actualBufferSizeS = Math.max(minBufferSizeS, maxBufferSizeS);
		actualBufferSizeS = actualBufferSizeS *1;
		@SuppressWarnings("deprecation")
		AudioRecord arecS = new AudioRecord(MediaRecorder.AudioSource.VOICE_COMMUNICATION,
				11025, AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT, actualBufferSizeS);

		byte[] buffer = new byte[actualBufferSizeS];
		Log.d("voice", "Starting to record, buffersize=" + actualBufferSizeS);
		arecS.startRecording();

		while (Thread.currentThread().isAlive()
				&& !Thread.currentThread().isInterrupted()) {

			try {
				Log.d("voice", "Recording..");
				arecS.read(buffer, 0, actualBufferSizeS);
				DatagramPacket dPacketS = new DatagramPacket(buffer,
						actualBufferSizeS);

				dPacketS.setAddress(InetAddress.getByName(UdpStream.destip));
				dPacketS.setPort(2048);
				dSocketS.send(dPacketS);

			} catch (Exception e) {
				dSocketS.close();
				e.printStackTrace();
			}

		}
	}

}
