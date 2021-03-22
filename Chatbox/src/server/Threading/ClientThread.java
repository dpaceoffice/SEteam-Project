package server.Threading;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

import server.Server;
import server.Users.State;
import server.Users.User;

public class ClientThread extends Server implements Runnable {

	private Socket socket;
	private DataOutputStream d_out;
	private DataInputStream d_in;
	private BufferedReader b_in;
	private int timeout;
	private User user; // user on thread
	private int MILI_DELAY = 1000 * 60 * 1;// default timeout: mili * sec * min = 1 min long

	@Override
	public void run() {
		try {
			while (!socket.isClosed()) {// listening for input from thread
				if (socket.getInputStream().available() > 0) {
					int packetId = d_in.readInt();
					handleIncommingPackets(packetId);
				} else {
					//state check here
					if (timeout > 0) {
						timeout--;
						Thread.sleep(1);// we need the thread to sleep for atleast a milisecond to properly time the
										// ping
					} else {
						clients.remove(this);
						d_out.writeInt(IDLE_PACKET);// after TIMEOUT we let the client know we are ending things
						System.out.println("Reaped Connection: " + socket.getInetAddress() + " Remaining Users: "
								+ clients.size());
						Thread.sleep(1000);// wait a second before closing the socket to make sure the int gets sent
											// properly
						socket.close();
					}
				}
			}

		} catch (Exception e) {
			if (e instanceof SocketException)
				if (e.getMessage().contains("Connection reset by peer")) {
					System.out.println("Early disconnect:" + toString() + " Remaining Users: " + clients.size());
					clients.remove(this);
					return;
				}
			e.printStackTrace();
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public DataOutputStream getdOutputStream() {
		return d_out;
	}

	@Override
	public String toString() {
		User user = getUser();
		if (user != null)
			return user.getUsername();
		return "" + socket.getInetAddress();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}