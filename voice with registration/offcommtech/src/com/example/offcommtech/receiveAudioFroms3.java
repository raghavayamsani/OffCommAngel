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

public class receiveAudioFroms3 extends Thread {
	public receiveAudioFroms3() {
		// TODO Auto-generated constructor stub
	};

	@SuppressWarnings("deprecation")
	public void run() {
		System.out.println("receiving audio from s3 **");
		// DatagramSocket dSocket = new DatagramSocket();
		DatagramChannel dChannelR = null;
		try {
			dChannelR = DatagramChannel.open();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DatagramSocket dSocketR = dChannelR.socket();

		try {
			dSocketR.setReuseAddress(true);

			dSocketR.bind(new InetSocketAddress(2047));
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Log.d("voice", "DatagramSocket open.");

		android.os.Process
				.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
		int maxBufferSizeR = 4096; // my value. see what's working best
									// for you.
		int minBufferSizeR = AudioRecord.getMinBufferSize(11025,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT);
		int actualBufferSizeR = Math.max(minBufferSizeR, maxBufferSizeR);
		actualBufferSizeR = actualBufferSizeR * 1;

		AudioTrack aTrack;
		aTrack = new AudioTrack(AudioManager.STREAM_VOICE_CALL, 11025,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT, actualBufferSizeR,
				AudioTrack.MODE_STREAM);

		DatagramPacket dPacket = new DatagramPacket(
				new byte[actualBufferSizeR], actualBufferSizeR);
		Log.d("voice", "Packet with buffersize=" + actualBufferSizeR);
		aTrack.play();
		Log.d("voice", "Playing track..");

		byte[] bufferR = new byte[actualBufferSizeR];

		while (!Thread.currentThread().isInterrupted()) {
			try {
				dSocketR.receive(dPacket);
				bufferR = dPacket.getData();

				aTrack.setPlaybackRate(11025);
				aTrack.write(bufferR, 0, bufferR.length);

			} catch (Exception e) {
				e.printStackTrace();
				dSocketR.close();
			}
		}

	};

}
