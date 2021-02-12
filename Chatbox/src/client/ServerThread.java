package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * ServerThread is a thread that handles all communications with the server
 */
public class ServerThread implements Runnable {

	/**
	 * State variables
	 */
	private Socket socket;
	private String userName;
	private final LinkedList<String> queuedMessages;

	/**
	 * Constructor
	 * 
	 * @param socket   - socket used to connect to server
	 * @param userName - display name picked by user
	 */
	public ServerThread(Socket socket, String userName) {
		this.socket = socket;
		this.userName = userName;
		queuedMessages = new LinkedList<String>();
	}

	/**
	 * Queues a string message to be sent to server
	 * 
	 * @param message
	 */
	public void appendMessage(String message) {
		synchronized (queuedMessages) {
			queuedMessages.push(message);
		}
	}

	/**
	 * Thread handling for incomming server messages and to send client input back to
	 * server
	 */
	@Override
	public void run() {
		System.out.println("Welcome, " + userName + ", you have connected successfully!");

		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			InputStream in = socket.getInputStream();
			Scanner inputScanner = new Scanner(in);
			DataInputStream numInput = new DataInputStream(in);
			
			while (!socket.isClosed()) {
				if(numInput.available() > 0) {
					int packet = numInput.readInt();
					System.out.println("Packet Recieved: "+packet);
					if(packet == 100)
						System.out.println("Ping");
					else if(packet == 1) {
						//if(inputScanner.hasNextLine())
							System.out.println(inputScanner.nextLine());
					}
				} 

				if (!queuedMessages.isEmpty()) {//from us
					String msg = "";
					synchronized (queuedMessages) {
						msg = queuedMessages.pop();
					}
					out.println(userName + ": " + msg);
				}
			}
			inputScanner.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
}
