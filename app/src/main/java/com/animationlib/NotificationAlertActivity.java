package com.animationlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.animationlib.notification.NotificationAnimView;


public class NotificationAlertActivity extends AppCompatActivity {

	private static final String TAG = "NotificationAlertActivity";
	private Button btnNotify;
	private int mBadgeCount = 0;
	private NotificationAnimView mNotificationAnimView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notify);

		btnNotify = (Button) findViewById(R.id.btn_notify);
		// Set a toolbar to replace the action bar.
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		mNotificationAnimView = (NotificationAnimView) findViewById(R.id.custom_animview);

		btnNotify.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mNotificationAnimView.notifyUser(++mBadgeCount);
			}
		});

	}

}
