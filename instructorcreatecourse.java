package com.example.bisonrta;

import java.io.IOException;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class InstructorCreateCourse extends Activity implements IListener {
	private Communication com = null;
	public static final String EXTRA_MESSAGE1 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instructor_create_course);
	}

	public void sendCourse(View view) {
		com = new Communication(this);
		com.connect();

		Intent intent = new Intent(this, InstructorDisplayCourselist.class);
		EditText ed = (EditText) findViewById(R.id.editText1);
		String course = ed.getText().toString();
		intent.putExtra(EXTRA_MESSAGE1, course);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.instructor_create_course, menu);
		return true;
	}
	String username;
	@Override
	public void onConnect() throws IOException {
		EditText edittext1 = (EditText) findViewById(R.id.editText1);
		String code = edittext1.getText().toString();

		EditText edittext2 = (EditText) findViewById(R.id.EditText2);
		String courseName = edittext2.getText().toString();
		
		Intent i = getIntent();
		username = i.getStringExtra(InstructorDisplayCourselist.EXTRA_MESSAGE);
		com.updateCourseList(code, courseName, username);

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
			InstructorCreateCourse text = (InstructorCreateCourse) msg.obj;
			if(msg.arg1==1)
			{
				Intent intent = new Intent(text, InstructorDisplayCourselist.class);
				String user = username;
				intent.putExtra(EXTRA_MESSAGE1, user);
				startActivity(intent);
			}
			else
			{				
			}
		}

	};

}
