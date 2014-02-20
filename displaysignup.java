package com.example.bisonrta;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class DisplaySignupActivity extends Activity implements IListener {
	@SuppressLint("NewApi")
	
	 Communication com = null;
	RadioGroup rg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_signup);
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_signup, menu);
		return true;
	}
	
	public void sendUsers(View view)
	{
		com = new Communication(this);
		com.connect();
	}

	@Override
	public void onConnect() throws IOException {
		// TODO Auto-generated method stub
		EditText edittext = (EditText) findViewById(R.id.editText4);
		String password = edittext.getText().toString();
		
		edittext = (EditText) findViewById(R.id.editText5);
		String confirmPassword = edittext.getText().toString();
		
		if(password.equals(confirmPassword))
		{
			
		edittext = (EditText) findViewById(R.id.editText1);
		String type = edittext.getText().toString();
		
		int a= -1;
		if(type.equals("I"))
			a = 0;
		else if(type.equals("S"))
			a = 1;
			
		edittext = (EditText) findViewById(R.id.editText2);
		String userID = edittext.getText().toString();
		
		edittext = (EditText) findViewById(R.id.editText3);
		String username = edittext.getText().toString();
		
		com.updateUser(a,userID,username,password);
		}
		
	}

	@Override
	public void onNewMessage(String a) {
		// TODO Auto-generated method stub
		Message msg = handler.obtainMessage();
		msg.obj = this;
		msg.arg1 = Integer.parseInt(a);
		handler.sendMessage(msg);	
	}
	
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			DisplaySignupActivity text = (DisplaySignupActivity) msg.obj;
			if(msg.arg1==1)
			{
				Intent intent = new Intent(text, MainActivity.class);
				startActivity(intent);
			}
			else
			{				
			}
		}
	};
}
