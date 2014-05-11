package com.example.offcommtech;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Sha1Hex
{
	String hexcode;
	int hexcode1;

    public String makeSHA1Hash(String input)
            throws NoSuchAlgorithmException
        {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.reset();
            byte[] buffer = input.getBytes();
            md.update(buffer);
            byte[] digest = md.digest();

            String hexStr = "";
            for (int i = 0; i < digest.length; i++) {

            	hexStr +=   (( digest[i] & 0x11 ) + 0x10);
            }
            return hexStr.substring(1, 7);
        }
}