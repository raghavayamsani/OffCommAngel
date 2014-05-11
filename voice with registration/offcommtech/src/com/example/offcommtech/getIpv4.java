package com.example.offcommtech;

import java.net.InetAddress;

public class getIpv4 {
	 InetAddress ownIP;
	public getIpv4() {
		// TODO Auto-generated constructor stub	
	}
	public String getIp(){  
		return ownIP.getHostAddress();
	}
}
