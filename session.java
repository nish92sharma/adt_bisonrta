package com.example.bisonrta;

public class session {
	
	private static session session = null;
	public static String username=null;
	
	private session()
	{}
	
	public static synchronized session getInstance()
	{
		if(session==null)
			session = new session();
		
			return session;
	}

}
