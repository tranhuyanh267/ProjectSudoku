package com.example.sudokuproject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	LinearLayout container;
	int HEIGHT, WIDTH, n1, n2;
	TextView[][] arrTextview = new TextView[9][9];
	int[][] actual = new int[9][9];
	long starttime = 0L;
	String timerStop;
	Handler handler = new Handler();
	TextView time,difficult;
	Runnable runnable;
	long millis,oldtime = 10000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SharedPreferences abc = getSharedPreferences("data", MODE_PRIVATE);
		if (abc.getInt("isNull", 0) == 1) {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					actual[j][i] = Integer.parseInt(abc.getString("cell" + j
							+ i, ""));

				}

			}
		} else {

			SudokuPuzzle puzzle = new SudokuPuzzle();
			Generate g = new Generate();
			g.InitValue();
			g.generate(1);
			puzzle.actual = g.getActual();
			int totalscore = getIntent().getIntExtra("totalscore", 0);
			puzzle.CreateEmplyCells(totalscore);
			actual = puzzle.actual;
		}
		createView();
		setBackgroundcolor();
		 time = (TextView) findViewById(R.id.txtTime);
		 
		 SharedPreferences preTime = getSharedPreferences("time", MODE_PRIVATE);
		 
		 oldtime = preTime.getLong("time", 0);
		 starttime();
		 Log.d("test", "" + preTime.getLong("time", 0));
		 
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String name = getResources().getResourceEntryName(v.getId());

		if (name.contains("num")) {
			int num = Integer.valueOf(name.substring(3, 4));

			arrTextview[n1][n2].setText("" + num);

		}

		else if (name.contains("cell")) {

			n1 = Integer.valueOf(name.substring(5, 6));
			n2 = Integer.valueOf(name.substring(4, 5));
			setBackgroundcolor();
			selected();
			arrTextview[n1][n2].requestFocus();

		}
	}

	public void setBackgroundcolor() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				arrTextview[i][j]
						.setBackgroundResource(R.drawable.button_white);
			}
		}

		int a = 0, b = 3;
		for (int k = a; k <= a + 2; k++) {
			for (int k2 = b; k2 <= b + 2; k2++) {
				arrTextview[k][k2]
						.setBackgroundResource(R.drawable.button_silver);
			}
		}
		int c = 3, d = 0;
		for (int k = c; k <= c + 2; k++) {
			for (int k2 = d; k2 <= d + 2; k2++) {
				arrTextview[k][k2]
						.setBackgroundResource(R.drawable.button_silver);
			}
		}
		int e = 6, f = 3;
		for (int k = e; k <= e + 2; k++) {
			for (int k2 = f; k2 <= f + 2; k2++) {
				arrTextview[k][k2]
						.setBackgroundResource(R.drawable.button_silver);
			}
		}
		int g = 3, h = 6;
		for (int k = g; k <= g + 2; k++) {
			for (int k2 = h; k2 <= h + 2; k2++) {
				arrTextview[k][k2]
						.setBackgroundResource(R.drawable.button_silver);
			}
		}
	}

	public void selected() {

		for (int i = 0; i < 9; i++) {
			arrTextview[n1][i].setBackgroundResource(R.drawable.button_green);
		}
		for (int i = 0; i < 9; i++) {
			arrTextview[i][n2].setBackgroundResource(R.drawable.button_green);
		}
		int startC = n1 - (n1 % 3);
		int startR = n2 - (n2 % 3);
		for (int i = startC; i <= startC + 2; i++) {
			for (int j = startR; j <= startR + 2; j++) {
				arrTextview[i][j]
						.setBackgroundResource(R.drawable.button_green);
			}
		}
	}

	public void createView() {
		Display display = getWindowManager().getDefaultDisplay();
		HEIGHT = display.getHeight();
		WIDTH = display.getWidth();
		container = (LinearLayout) findViewById(R.id.container);

		RelativeLayout topboard = new RelativeLayout(this);
		topboard.setLayoutParams(new LayoutParams(WIDTH, (HEIGHT - WIDTH) / 4));
		// topboard.setOrientation(LinearLayout.VERTICAL);
		topboard.setBackgroundColor(Color.WHITE);
		topboard.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
		container.addView(topboard);

		TextView time = new TextView(this);
		time.setText("abc");
		time.setId(getResources().getIdentifier("txtTime", "id",
				getPackageName()));
		topboard.addView(time);

		LinearLayout board = new LinearLayout(this);
		board.setLayoutParams(new LayoutParams(WIDTH, WIDTH));
		board.setOrientation(LinearLayout.VERTICAL);
		board.setBackgroundColor(Color.WHITE);
		container.addView(board);

		for (int i = 0; i < 9; i++) {
			LinearLayout miniRaw = new LinearLayout(this);
			miniRaw.setLayoutParams(new LayoutParams(WIDTH, WIDTH / 9));
			miniRaw.setOrientation(LinearLayout.HORIZONTAL);
			// miniRaw.setBackgroundResource(R.drawable.layout);
			board.addView(miniRaw);
			for (int j = 0; j < 9; j++) {

				TextView textView = new TextView(this);
				textView.setHeight(WIDTH / 9);
				textView.setWidth(WIDTH / 9);
				textView.setId(getResources().getIdentifier("cell" + i + j,
						"id", getPackageName()));

				if (actual[i][j] != 0) {
					textView.setText("" + actual[i][j]);

				} else {
					textView.setOnClickListener(this);
				}

				textView.setBackgroundResource(R.drawable.button_silver);

				textView.setGravity(Gravity.CENTER);
				arrTextview[j][i] = textView;

				miniRaw.addView(textView);

			}
		}

		LinearLayout layoutNum = new LinearLayout(this);
		layoutNum.setOrientation(LinearLayout.HORIZONTAL);
		layoutNum.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		for (int i = 0; i < 9; i++) {
			Button button = new Button(this);
			button.setText("" + (i + 1));
			button.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT, 1));
			button.setId(getResources().getIdentifier("num" + (i + 1), "id",
					getPackageName()));
			button.setOnClickListener(this);
			layoutNum.addView(button);
		}
		container.addView(layoutNum);
	}

	public void starttime() {
		 runnable = new Runnable() {

			public void run() {

				final long start = starttime;
				 millis = SystemClock.uptimeMillis() - start + oldtime;
				int seconds = (int) (millis / 1000);
				int minutes = seconds / 60;
				seconds = seconds % 60;

				timerStop = minutes + ":" + String.format("%02d", seconds);
				time.setText(timerStop);

				handler.postDelayed(this, 200);

			}
		};
		if (starttime == 0L) {
			starttime = SystemClock.uptimeMillis();
			handler.removeCallbacks(runnable);
			handler.postDelayed(runnable, 100);
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		SharedPreferences pre = getSharedPreferences("data", MODE_PRIVATE);
		SharedPreferences.Editor editor = pre.edit();
		editor.putInt("isNull", 1);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (arrTextview[i][j].getText() == "") {
					editor.putString("cell" + j + i, "0");
				} else {
					editor.putString("cell" + j + i, arrTextview[i][j]
							.getText().toString());
				}
			}
		}
		editor.commit();
		handler.removeCallbacks(runnable);
		SharedPreferences preTime = getSharedPreferences("time", MODE_PRIVATE);
		SharedPreferences.Editor editTime = preTime.edit();
		Log.d("test", "millis = " + millis);
		editTime.putLong("time", millis);
		editTime.commit();
	}
}
