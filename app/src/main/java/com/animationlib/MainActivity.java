package com.animationlib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

	private static final String TAG = "NotificationAlertActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.btn_notification_activity).setOnClickListener(this);
		findViewById(R.id.btn_search_activity).setOnClickListener(this);
		findViewById(R.id.btn_pulltorefresh_activity).setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_notification_activity :
				startActivity(new Intent(MainActivity.this, NotificationAlertActivity.class));
				break;
			case R.id.btn_search_activity :
				startActivity(new Intent(MainActivity.this, SearchAnimActivity.class));
				break;
			case R.id.btn_pulltorefresh_activity :
				startActivity(new Intent(MainActivity.this, PTRSampleActivity.class));
				break;

		}
	}
}
