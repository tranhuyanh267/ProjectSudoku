package com.example.sudokuproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MenuActivity extends Activity implements OnClickListener{

	Button btnStart, btnSetting, btnAbout, btnExit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_layout);
		btnStart = (Button) findViewById(R.id.imageButton1);
		btnSetting = (Button) findViewById(R.id.imageButton2);
		btnAbout = (Button) findViewById(R.id.imageButton3);
		btnExit = (Button) findViewById(R.id.imageButton4);
		btnStart.setOnClickListener(this);
		btnSetting.setOnClickListener(this);
		btnAbout.setOnClickListener(this);
		btnExit.setOnClickListener(this);
		
		
		
		
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageButton1:
			Intent intent = new Intent(getApplicationContext(), choose_difficult.class);
			startActivity(intent);
			break;
		case R.id.imageButton3:
			Toast.makeText(getApplicationContext(), "I cant help you!!", Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
		
	}
}
