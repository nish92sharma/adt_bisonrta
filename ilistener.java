package com.example.bisonrta;

import java.io.IOException;

public interface IListener {

	public void onConnect() throws IOException;

	public void onNewMessage(String a);
	
}
