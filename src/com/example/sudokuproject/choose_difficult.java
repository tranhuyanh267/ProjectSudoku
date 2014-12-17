package com.example.sudokuproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class choose_difficult extends Activity implements OnClickListener {
	Button btnEasy, btnMedium, btnHard, btnSuperhard, btnResume;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SharedPreferences pre = getSharedPreferences("data", MODE_PRIVATE);
		Log.d("ANDROID", "" + pre.getInt("isNull", 0));
		if (pre.getInt("isNull", 0) == 1) {
			setContentView(R.layout.choosedifficult_resume);
			btnResume = (Button) findViewById(R.id.btnResume);
			btnEasy = (Button) findViewById(R.id.btnEasy);
			btnMedium = (Button) findViewById(R.id.btnNormal);
			btnHard = (Button) findViewById(R.id.btnHard);
			btnSuperhard = (Button) findViewById(R.id.btnSuperHard);

			btnResume.setOnClickListener(this);
			btnEasy.setOnClickListener(this);
			btnMedium.setOnClickListener(this);
			btnHard.setOnClickListener(this);
			btnSuperhard.setOnClickListener(this);

		} else {
			setContentView(R.layout.choosedifficult);
			btnEasy = (Button) findViewById(R.id.btnEasy);
			btnMedium = (Button) findViewById(R.id.btnNormal);
			btnHard = (Button) findViewById(R.id.btnHard);
			btnSuperhard = (Button) findViewById(R.id.btnSuperHard);

			btnEasy.setOnClickListener(this);
			btnMedium.setOnClickListener(this);
			btnHard.setOnClickListener(this);
			btnSuperhard.setOnClickListener(this);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnResume:
			Intent intent = new Intent(getApplicationContext(),
					MainActivity.class);
			startActivity(intent);
			break;
		case R.id.btnEasy:
			sendIntent("Easy", 40);
			break;
		case R.id.btnNormal:
			sendIntent("Normal", 55);
			break;
		case R.id.btnHard:
			sendIntent("Hard", 70);
			break;
		case R.id.btnSuperHard:
			sendIntent("Super Hard", 85);
			break;

		default:
			break;
		}

	}

	public void sendIntent(final String difficult, final int totalscore) {
		SharedPreferences pre = getSharedPreferences("data", MODE_PRIVATE);
		if (pre.getInt("isNull", 0) == 0) {
			Intent intent = new Intent(getApplicationContext(),
					MainActivity.class);
			intent.putExtra("difficult", difficult);
			intent.putExtra("totalscore", totalscore);
			startActivity(intent);
		} else {
			Builder ad = new AlertDialog.Builder(this);
			ad.setMessage("Bạn có muốn chơi lại không??");
			ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					SharedPreferences pre = getSharedPreferences("data",
							MODE_PRIVATE);
					SharedPreferences.Editor editor = pre.edit();
					editor.clear();
					editor.commit();

					SharedPreferences preTime = getSharedPreferences("time",
							MODE_PRIVATE);
					SharedPreferences.Editor editTime = preTime.edit();
					editTime.clear();
					editTime.commit();

					Intent intent = new Intent(getApplicationContext(),
							MainActivity.class);
					intent.putExtra("difficult", difficult);
					intent.putExtra("totalscore", totalscore);
					startActivity(intent);
				}
			});

			ad.setNegativeButton("No", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "No",
							Toast.LENGTH_SHORT).show();
				}
			});
			ad.create();
			ad.show();
		}
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		setContentView(R.layout.choosedifficult_resume);
		btnResume = (Button) findViewById(R.id.btnResume);
		btnEasy = (Button) findViewById(R.id.btnEasy);
		btnMedium = (Button) findViewById(R.id.btnNormal);
		btnHard = (Button) findViewById(R.id.btnHard);
		btnSuperhard = (Button) findViewById(R.id.btnSuperHard);

		btnResume.setOnClickListener(this);
		btnEasy.setOnClickListener(this);
		btnMedium.setOnClickListener(this);
		btnHard.setOnClickListener(this);
		btnSuperhard.setOnClickListener(this);
	}
}
