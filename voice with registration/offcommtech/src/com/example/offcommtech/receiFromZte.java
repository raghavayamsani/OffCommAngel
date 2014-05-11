package com.example.offcommtech;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.util.Log;

public class receiFromZte extends Thread {
	public receiFromZte() {
		// TODO Auto-generated constructor stub

	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		// DatagramSocket dSocket = new DatagramSocket();
		DatagramChannel dChannel = null;
		try {
			dChannel = DatagramChannel.open();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DatagramSocket dSocket = dChannel.socket();

		try {
			dSocket.setReuseAddress(true);

			dSocket.bind(new InetSocketAddress(2048));
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		android.os.Process
				.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
		int maxBufferSize = 4096; // my value. see what's working best
									// for you.
		int minBufferSize = AudioRecord.getMinBufferSize(11025,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT);
		int actualBufferSize = Math.max(minBufferSize, maxBufferSize);
		actualBufferSize = actualBufferSize * 1;

		AudioTrack aTrack;
		aTrack = new AudioTrack(AudioManager.STREAM_VOICE_CALL, 11025,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT, actualBufferSize,
				AudioTrack.MODE_STREAM);

		DatagramPacket dPacket = new DatagramPacket(new byte[actualBufferSize],
				actualBufferSize);
		Log.d("voice", "Packet with buffersize=" + actualBufferSize);
		aTrack.play();
		Log.d("voice", "Playing track..");

		byte[] buffer = new byte[actualBufferSize];
		
		while (!Thread.currentThread().isInterrupted()) {
			try {
				dSocket.receive(dPacket);
				buffer = dPacket.getData();

				aTrack.setPlaybackRate(11025);
				aTrack.write(buffer, 0, buffer.length);
		

			} catch (Exception e) {
				e.printStackTrace();
				dSocket.close();
			}
		}

	}

}
