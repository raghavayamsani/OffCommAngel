package com.example.offcommtech;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.util.Log;

public class FileRW {
	String text = " ";
	String filename = " ";
	Context context;

	FileRW(Context context,String filename) {
		this.context = context;
		this.filename = filename;

	}


	public void Write(String text) throws IOException {
		this.text = text;
		BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter(new File(context.getFilesDir() + File.separator
						+ filename)));
		bufferedWriter.write(text);
		bufferedWriter.close();
	}
	public void Writeapp(String text) throws IOException {
		this.text = text;
		BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter(new File(context.getFilesDir() + File.separator
						+ filename),true));
		bufferedWriter.write(text);
		bufferedWriter.close();
	}
	

	public StringBuilder Read() throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new FileReader(new File(context.getFilesDir() + File.separator
						+ filename)));
		String read;
		StringBuilder builder = new StringBuilder("");

		while ((read = bufferedReader.readLine()) != null) {
			builder.append(read);
		}
		Log.d("Output", builder.toString());
		bufferedReader.close();
		return builder;
	}
}