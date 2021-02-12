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

	@Override
	public void run() {
		try {
			// Client output stream, where the server sends information
			outPrintWrapper = new PrintWriter(socket.getOutputStream(), false);
			outDataWrapper = new DataOutputStream(socket.getOutputStream());
			// Clients input sream, where the server recieves information
			Scanner in = new Scanner(socket.getInputStream());
			while (!socket.isClosed()) {// listening for input from thread
				if (in.hasNextLine()) {// if recieved input
					String input = in.nextLine();// initlize input as string

					// send input as output to all active threads including this one with user name
					for (ClientThread client : clients) {
						// get current threads output stream
						PrintWriter outWriter = client.getWriter();
						DataOutputStream numWriter = client.getOutWrapper();
						// null pointer exception check
						if (outWriter != null) {
							numWriter.writeInt(1);// String
							outWriter.write(input + "\r\n");
							outWriter.flush();
							numWriter.flush();
						}
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public DataOutputStream getOutWrapper() {
		return outDataWrapper;
	}

	public Socket getSocket() {
		return socket;
	}

}