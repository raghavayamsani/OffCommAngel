package com.example.offcommtech;

import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class sendAckToZte  {
	private String ack;
	Socket s = null;
	String ipaddress = "";

	public sendAckToZte(String ack) {
		this.ack = ack;

	}

	public sendAckToZte(String ack, String ipaddress) {
		this.ack = ack;
		this.ipaddress = ipaddress;
		// TODO Auto-generated constructor stub
	}

	public void SendAcknowledgmnet() {
		// TODO Auto-generated method stub
		try {

			int serverPort = 8117;

			s = new Socket(ipaddress, serverPort);
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			System.out.println(ack);
			out.writeUTF(ack); // UTF is a string
								// encoding see
								// Sn. 4.4

		} catch (UnknownHostException e1) {
			System.out.println("Socket:" + e1.getMessage());
		} catch (EOFException e11) {
			System.out.println("EOF:" + e11.getMessage());
		} catch (IOException e) {
			System.out.println("readline:" + e.getMessage());
		} finally {
			if (s != null)
				try {
					s.close();
				} catch (IOException e) {
					System.out.println("close:" + e.getMessage());
				}
		}
	}

}
