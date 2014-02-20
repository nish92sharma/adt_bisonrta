package com.example.bisonrta;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity implements IListener {

	public static final String MESSAGE = null;
	Communication com = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void sendMessage(View view) {
		Intent intent = new Intent(this, DisplaySignupActivity.class);
		startActivity(intent);
	}

	public void requestCourseList(View view) {
		com = new Communication(this);
		com.connect();
	}

	@Override
	public void onConnect() throws IOException {
		Log.d("app", "connected");

		EditText edittext1 = (EditText) findViewById(R.id.editText1);
		String user = edittext1.getText().toString();

		EditText edittext2 = (EditText) findViewById(R.id.editText2);
		String password = edittext2.getText().toString();

		com.validateLogin(user, password);

	}

	@Override
	public void onNewMessage(String a) {

		Message msg = handler.obtainMessage();
		msg.obj = this;
		msg.arg1 = Integer.parseInt(a);
		handler.sendMessage(msg);

	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			MainActivity text = (MainActivity) msg.obj;

			if (msg.arg1 == 1)// Student
			{

				Intent Studentintent = new Intent(text,
						StudentDisplayCourseList.class);
				EditText edittext = (EditText) findViewById(R.id.editText1);
				String username = edittext.getText().toString();
				Studentintent.putExtra(MESSAGE, username);
				startActivity(Studentintent);
			} else if (msg.arg1 == 0)// Instructor
			{
				Intent InstructorIntent = new Intent(text,
						InstructorDisplayCourselist.class);
				EditText edittext = (EditText) findViewById(R.id.editText1);
				String username = edittext.getText().toString();
				InstructorIntent.putExtra(MESSAGE, username);
				startActivity(InstructorIntent);

			} else {
				AlertDialog.Builder adb = new AlertDialog.Builder(text);
				adb.setTitle("Error");
				adb.setMessage("Incorrect Information");
				adb.setPositiveButton("Okay",null);
				adb.setCancelable(true);
				adb.create().show();
				

			}

		}

	};
}
