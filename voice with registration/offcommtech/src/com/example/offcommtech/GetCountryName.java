package com.example.offcommtech;

import java.util.Locale;

public class GetCountryName {
	String GetCountryZipCode(String ssid){
        Locale loc = new Locale("", ssid);
        
 
       return loc.getDisplayCountry().trim();
}
}
