package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;

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
	 * Thread handling for incomming server messages and to send client input back
	 * to server
	 */
	@Override
	public void run() {
		try {

			DataOutputStream d_out = new DataOutputStream(socket.getOutputStream());// write packet ints

			DataInputStream d_in = new DataInputStream(socket.getInputStream());// reader for ints
			BufferedReader b_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));// text input

			d_out.writeInt(2);// tells the server client information
			d_out.writeBytes(userName + "\n");

			while (!socket.isClosed()) {
				if (socket.getInputStream().available() > 0) {
					int packet = d_in.readInt();
					// System.out.println("Packet Recieved: " + packet);

					if (packet == 1) {
						System.out.println(b_in.readLine());
					} else if (packet == 1000) {// if client recieves this server closed socket
						System.out.println("You have been disconnected for being idle");
						socket.close();
						System.exit(0);
					}
				}

				if (!queuedMessages.isEmpty()) {
					String msg = "";
					synchronized (queuedMessages) {
						msg = queuedMessages.pop();
					}
					if (!msg.isEmpty()) {
						d_out.writeInt(1000);// refresh idle
						d_out.writeInt(1);// string packet
						d_out.writeBytes(userName + ": " + msg + "\n");
					} else
						System.out.println("Error: Blank Message");
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
