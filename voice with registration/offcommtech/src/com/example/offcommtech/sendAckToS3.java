package com.example.offcommtech;

import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class sendAckToS3  {
	private String ack;
	Socket s = null;
	String ipv4 = "";

	public sendAckToS3(String ack) {
		// TODO Auto-generated constructor stub
		this.ack = ack;

	}

	public sendAckToS3(String ack, String destip) {
		this.ack = ack;
		this.ipv4 = destip;
		// TODO Auto-generated constructor stub
	}

	public void SendAcknowledgmnet() throws Exception {
		// TODO Auto-generated method stub
		try {

			int serverPort = 8117;
		

			s = new Socket(UdpStream.destip, serverPort);
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			out.writeUTF(ack); // UTF is a string
								// encoding see
								// Sn. 4.4

		} catch (UnknownHostException e1) {
			System.out.println("Socket:" + e1.getMessage());
			s.close();
		} catch (EOFException e11) {
			System.out.println("EOF:" + e11.getMessage());
			s.close();
		} catch (IOException e) {
			System.out.println("readline:" + e.getMessage());
			s.close();
		} finally {
			if (s != null)
				try {
					s.close();
				} catch (IOException e) {
					System.out.println("close:" + e.getMessage());
					s.close();
				}
		}
	}

	
}
