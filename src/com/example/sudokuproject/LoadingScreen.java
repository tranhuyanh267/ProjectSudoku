package com.example.sudokuproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class LoadingScreen extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loadingscreen);
		
		FrameLayout layout = (FrameLayout) findViewById(R.id.layout);
		ImageView iv = (ImageView) findViewById(R.id.imageView1);
		iv.setBackgroundResource(R.anim.animation);
		layout.setBackgroundColor(Color.WHITE);
		AnimationDrawable animationDrawable = (AnimationDrawable) iv.getBackground();
		animationDrawable.start();
		
		final Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(2000);
					Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
					startActivity(intent);
					finish();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		thread.start();
		
	}

}
