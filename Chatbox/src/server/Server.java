package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	/**
	 * Arraylist to keep track of active threads
	 */
	public static ArrayList<ClientThread> clients;

	@SuppressWarnings("resource")
	public static void main(String args[]) {
		try {
			// initilize arraylist maintaing active threads
			clients = new ArrayList<ClientThread>();

			// initilize the server on port 43594
			ServerSocket serverSocket = new ServerSocket(43594);
			System.out.println("Listening for connections on port 43594");

			// Loop indefinitly till program is exited
			while (true) {
				Socket clientSocket = serverSocket.accept();// accepts any incomming connections
				ClientThread client = new ClientThread(clientSocket);// initates new thread for accepted connection
				new Thread(client).start();// starts the thread
				clients.add(client);// adds thread to arraylist
				System.out.println(
						"Accepted Connection: " + clientSocket.getInetAddress() + " Current Users: " + clients.size());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void distributeMessage(String msg) throws IOException {
		// send input as output to all active threads including this one with user name
		//BufferedWriter b_out = null;
		DataOutputStream d_out = null;
		for (ClientThread client : clients) {
			// get current threads output stream
			//b_out = client.getbWriter();
			d_out = client.getdOutputStream();
			if (d_out != null) {
				d_out.writeInt(1);// String
				d_out.writeBytes(msg + "\r\n");
			}
		}
	}

}
