package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import server.Threading.ClientThread;
import server.Threading.Packet;

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

	/**
	 * Distrubutes message to every client on network
	 * @param msg
	 */
	public void distributeMessage(String msg, int index) {
		int lastIndex = index;
		try {
			// send input as output to all active threads including this one with user name
			// BufferedWriter b_out = null;
			DataOutputStream d_out = null;
			for (int i = lastIndex; i <= clients.size()-1; i++) {
				// get current threads output stream
				// b_out = client.getbWriter();
				lastIndex = i;//update any changes to the last index we used.
				d_out = clients.get(lastIndex).getdOutputStream();
				if (d_out != null) {
					d_out.writeInt(1);// String packet
					d_out.writeBytes(msg + "\n");
				}
			}
		} catch (IOException e) {
			if (e instanceof SocketException) // if this happens its probably because the client closed before the server reaped
												// the thread.
				if (e.getMessage().contains("Connection reset by peer")) {
					ClientThread last = clients.get(lastIndex);
					clients.remove(lastIndex);
					System.out.println(""+last.toString()+": Disconnected early \n Remaining Users: "+clients.size()+"");
					distributeMessage(msg, lastIndex);//picks up where we left off.
					return;
				}
			e.printStackTrace();
		}
	}
}
