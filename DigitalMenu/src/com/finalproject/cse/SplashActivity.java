package com.finalproject.cse;


import com.dexterlabs.menulist.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {
	
	
		
		protected int _splashTime = 4000; 
		
		private Thread splashTread;
		
		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.splash);
		    
		    
	   final SplashActivity sPlashScreen = this; 
		    
		    // thread for displaying the SplashScreen
		    splashTread = new Thread() {
		        @Override
		        public void run() {
		            try {	            	
		            	synchronized(this){
		            		wait(_splashTime);
		            	}
		            	
		            } catch(InterruptedException e) {} 
		            finally {
		                finish();
		                
		               /* final Intent intent = new Intent(SplashActivity.this,
		                		MainActivity.class);
		                // disable default animation for new intent
		                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		                ActivitySwitcher.animationOut(findViewById(R.id.container),
		                        getWindowManager(),
		                        new ActivitySwitcher.AnimationFinishedListener() {
		                            @Override
		                            public void onAnimationFinished() {
		                                startActivity(intent);
		                            }
		                        });*/
		                
		                Intent i = new Intent();
		                i.setClass(sPlashScreen, MainActivity.class);
		        		startActivity(i);
		                
		                //stop();
		            }
		        }
		    };
		    
		    splashTread.start();
		}
		

}
