package com.example.offcommtech;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class sendCallDataToserver {
	String data = "";
	DatagramSocket s;
	String result;

	sendCallDataToserver(String data) {
		this.data = data;
	}

	public String receive() {

		DatagramSocket aSocket = null;
		try {
			aSocket = new DatagramSocket();
			byte[] m = data.getBytes();
			int msg_length = data.length();
			InetAddress aHost = InetAddress.getByName("192.168.42.1");
			int serverPort = 9876;
			DatagramPacket request = new DatagramPacket(m, msg_length, aHost,
					serverPort);
			System.out.println("sending" + data);
			aSocket.send(request);
			byte[] buffer = new byte[14];
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			aSocket.receive(reply);
			result = new String(reply.getData());

		} catch (SocketException e) {
			System.out.println("Socket:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO:" + e.getMessage());
		} finally {
			if (aSocket != null)
				aSocket.close();
		}
		return result;
	}
}
