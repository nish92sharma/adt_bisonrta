package com.example.bisonrta;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Communication implements Runnable {
	int port = 3000;
	String ipaddress = "172.16.0.7";

	IListener listen = null;

	BufferedReader in = null;
	DataOutputStream out = null;
	Socket s = null;

	public Communication(IListener listener) {
		listen = listener;
	}

	public void connect() {
		Thread th = new Thread(this);
		th.start();
	}

	public int validateLogin(String user, String password) throws IOException {
		String msg = "LOGIN(" + user + "," + password + ")" + "\r\n";
		out.writeBytes(msg);
		return 0;
	}

	public int getCourseList(String username) throws IOException {
		String msg = "LISTCOURSES(" + username + ")\r\n";
		out.writeBytes(msg);
		return 0;
	}

	public int getQuizList(String code) throws IOException {
		String msg = "LISTQUIZZES(" + code + ")\r\n";
		out.writeBytes(msg);
		return 0;
	}

	public int getQuestionList(String a) throws IOException {
		String msg = "GETQUIZ(" + a + ")\r\n";
		out.writeBytes(msg);
		return 0;
	}

	public int updateCourseList(String code, String courseName, String username)
			throws IOException {
		String msg = "ADDCOURSE(" + code + "," + courseName + "," + username
				+ ")\r\n";
		out.writeBytes(msg);
		return 0;
	}

	public int updateQuizList(String courseArray, String quizName)
			throws IOException {
		String msg = "ADDQUIZ(" + courseArray + "," + quizName + ")\r\n";
		out.writeBytes(msg);
		return 0;
	}

	public int updateQuestionList(String quizId, String question,
			String[] options, String points) throws IOException {
		String msg = "ADDQUESTION(" + quizId + "," + question + ","
				+ options[0] + "," + options[1] + "," + options[2] + ","
				+ options[3] + "," + points + ")\r\n";
		out.writeBytes(msg);
		out.flush();

		return 0;
	}

	public int getFeedback(String quizId) throws IOException {
		String msg = "GETFEEDBACK(" + quizId + ")\r\n";
		out.writeBytes(msg);
		out.flush();
		return 0;
	}
	
	public int checkAnswers(String answers) throws IOException {
		String msg = "CHECKANSWER(" + answers + ")\r\n";
		out.writeBytes(msg);
		out.flush();
		return 0;
	}
	
	public int getStudentCourseList(String user, String coursecode) throws IOException
	{
		String msg = "ENROLL("+user+","+coursecode+")\r\n";
		out.writeBytes(msg);
		out.flush();
		return 0;
		
	}
	
	public int updateUser(int type, String userId, String username, String password) throws IOException
	{
		String msg="SIGNUP("+type+","+userId+","+username+","+password+")\r\n";
		out.writeBytes(msg);
		out.flush();
		return 0;
	}

	@Override
	public void run() {

		InetAddress ip;

		try {
			ip = InetAddress.getByName(ipaddress);
			s = new Socket(ip, port);

			// in = new InputStreamReader(s.getInputStream());
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			// out = new OutputStreamWriter(s.getOutputStream());
			out = new DataOutputStream(s.getOutputStream());

			listen.onConnect();

			BufferedReader br = in;

			String a;

			while ((a = br.readLine()) != null) {
				listen.onNewMessage(a);
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			s.close();
			in.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
