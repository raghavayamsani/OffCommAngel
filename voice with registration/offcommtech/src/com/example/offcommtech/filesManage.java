package com.example.offcommtech;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.util.Log;

public class filesManage {
	private static final String TAG = "call";
	private static final String FILENAME = "myFile.txt";
	Context c;

	public filesManage(Context t) {
		// TODO Auto-generated constructor stub
		this.c = t;
	}

	public void writeToFile(String data) {
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					c.openFileOutput(FILENAME, Context.MODE_PRIVATE));
			outputStreamWriter.write(data);
			outputStreamWriter.close();
		} catch (IOException e) {
			Log.e(TAG, "File write failed: " + e.toString());
		}

	}

	public String readFromFile() {

		String ret = "";

		try {
			InputStream inputStream = c.openFileInput(FILENAME);

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";
				StringBuilder stringBuilder = new StringBuilder();

				while ((receiveString = bufferedReader.readLine()) != null) {
					stringBuilder.append(receiveString);
				}

				inputStream.close();
				ret = stringBuilder.toString();
			}
		} catch (FileNotFoundException e) {
			Log.e(TAG, "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e(TAG, "Can not read file: " + e.toString());
		}

		return ret;
	}

}
