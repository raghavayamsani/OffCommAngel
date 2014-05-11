/**
 * Developed By Dexter Labs
 *
 */

package com.finalproject.cse;

import java.util.HashMap;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;



public class FTPSessionManager {
    // Shared Preferences
    SharedPreferences ftp_pref;
 
    // Editor for Shared preferences
    Editor ftp_editor;
 
    // Context
    Context _context;
 
    // Shared pref mode
    int PRIVATE_MODE = 0;
 
    // Sharedpref file name
    private static final String PREF_NAME = "FTPServerPref";
 
    // All Shared Preferences Keys
    private static final String IS_FTP_LOGIN = "IsFtpLoggedIn";
 
    // User name (make variable public to access from outside)
    public static final String KEY_HOST = "host";
 
    // Email address (make variable public to access from outside)
    public static final String KEY_PORT = "port";
    public static final String KEY_NAME = "name";
    
    // Email address (make variable public to access from outside)
    public static final String KEY_PWD = "password";
 //Activity activity;
    // Constructor
    public FTPSessionManager(Context context){
        this._context = context;
        ftp_pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        ftp_editor = ftp_pref.edit();
      //  this.activity=activity;
    }
 
    /**
     * Create login session
     * */
    public void createFTPServerSession(String host, String port,String name,String password){
    	
    	Log.d("sesionManager","iam ftp session manager create session");
        // Storing login value as TRUE
        ftp_editor.putBoolean(IS_FTP_LOGIN, true);
 
        // Storing name in pref
        ftp_editor.putString(KEY_HOST, host);
 
        // Storing email in pref
        ftp_editor.putString(KEY_PORT, port);
        // Storing name in pref
        ftp_editor.putString(KEY_NAME, name);
 
        // Storing email in pref
        ftp_editor.putString(KEY_PWD, password);
 
        // commit changes
        ftp_editor.commit();
    }  
 
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
   /* public void checkFTPConnection(){
        // Check login status
        if(!this.isFTPLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
 
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
 
            // Staring Login Activity
            _context.startActivity(i);
            //activity.finish();
        }
 
    }*/
 
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getFTPServerrDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        
        // user name
        user.put(KEY_HOST, ftp_pref.getString(KEY_HOST, null));
 
        // user email id
        user.put(KEY_PORT, ftp_pref.getString(KEY_PORT, null));
        // user name
        user.put(KEY_NAME, ftp_pref.getString(KEY_NAME, null));
 
        // user email id
        user.put(KEY_PWD, ftp_pref.getString(KEY_PWD, null));
 
        // return user
        return user;
    }
 
    /**
     * Clear session details
     * */
    public void logoutFTPServer(){
        // Clearing all data from Shared Preferences
    	ftp_editor.clear();
    	ftp_editor.commit();
 
    }
 
    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isFTPLoggedIn(){
        return ftp_pref.getBoolean(IS_FTP_LOGIN, false);
    }
}
