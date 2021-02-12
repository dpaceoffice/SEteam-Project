package server;

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
				System.out.println("Accepted Connection: " + clientSocket.getInetAddress() + "");
				ClientThread client = new ClientThread(clientSocket);// initates new thread for accepted connection
				new Thread(client).start();// starts the thread
				clients.add(client);// adds thread to arraylist
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
