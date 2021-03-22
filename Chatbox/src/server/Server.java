package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import server.Threading.ClientThread;
import server.Threading.Packet;
import server.Users.State;

public class Server extends Packet {

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

	public void distributeMessage(String msg) {
		distributeMessage(msg, 0);
	}

	public boolean isOnline(String username) {
		ClientThread current = null;
		try {
			for (ClientThread client : clients) {
				if (client.getUser() != null)
					if (client.getUser().getUsername().equals(username))
						if (client.getUser().getState() == State.CHATTING) {
							current = client;
							DataOutputStream d_out = client.getdOutputStream();
							d_out.writeInt(IDLE_PACKET);// check if this user has an open connection
							return true;
						}
			}
		} catch (IOException e) {//if the connection was closed early we can go ahead and reap this connection and try again.
			if (e instanceof SocketException)
				if (e.getMessage().contains("Connection reset by peer")) {
					clients.remove(current);
					System.out.println(
							"Disconnted Early:" + current.toString() + " Remaining Users: " + clients.size() + "");
					return false;
				}
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Distrubutes message to every client on network
	 * 
	 * @param msg
	 */
	public void distributeMessage(String msg, int index) {
		int lastIndex = index;
		try {
			// send input as output to all active threads including this one with user name
			// BufferedWriter b_out = null;
			DataOutputStream d_out = null;
			for (int i = lastIndex; i <= clients.size() - 1; i++) {
				// get current threads output stream
				// b_out = client.getbWriter();
				ClientThread client = clients.get(i);
				lastIndex = i;// update any changes to the last index we used.
				if (client.getUser() == null || client.getUser().getState() != State.CHATTING)
					continue;
				d_out = client.getdOutputStream();
				if (d_out != null) {
					d_out.writeInt(MESSAGE_PACKET);// String packet
					d_out.writeBytes("SERVER->" + msg + "\n");
				}
			}
		} catch (IOException e) {
			if (e instanceof SocketException) // if this happens its probably because the client closed before the
												// server reaped
												// the thread.
				if (e.getMessage().contains("Connection reset by peer")) {
					ClientThread last = clients.get(lastIndex);
					clients.remove(lastIndex);
					System.out.println(
							"Disconnted Early:" + last.toString() + " Remaining Users: " + clients.size() + "");
					distributeMessage(msg, lastIndex);// picks up where we left off.
					return;
				}
			e.printStackTrace();
		}
	}
}
