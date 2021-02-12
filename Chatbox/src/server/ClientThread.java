package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread extends Server implements Runnable {
	private Socket socket;
	private PrintWriter outPrintWrapper;
	private DataOutputStream outDataWrapper;

	/**
	 * Constructor
	 * 
	 * @param socket = open socket
	 */
	public ClientThread(Socket socket) {
		this.socket = socket;
	}

	/**
	 * Text output stream
	 * 
	 * @return PrintWriter output
	 */
	private PrintWriter getWriter() {
		return outPrintWrapper;
	}
