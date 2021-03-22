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

	/**
	 * Constructor
	 * 
	 * @param socket = open socket
	 */
	public ClientThread(Socket socket) {
		this.socket = socket;
		try {
			d_out = new DataOutputStream(socket.getOutputStream());// for packet identification
			d_in = new DataInputStream(socket.getInputStream());// reader for ints
			b_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));// text input
			timeout = MILI_DELAY;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handles packets that come from the client
	 * @param packetId
	 * @throws IOException
	 */
	private void handleIncommingPackets(int packetId) throws IOException {
		if (packetId == IDLE_PACKET) {// ping
			timeout = MILI_DELAY;
		} else if (packetId == USERNAME_REQ) {
			String username = b_in.readLine();
			this.user = new User(this, username, "password");//creates user object
			d_out.writeInt(MESSAGE_PACKET);
			d_out.writeBytes("Welcome " + username + " to the chatbox!\n");
		} else if (packetId == MESSAGE_PACKET) {// message
			String msg = b_in.readLine();
			distributeMessage(msg);
		} else if(packetId == LOGIN_CHECK) {
			String username = b_in.readLine();
			String password = b_in.readLine();
			User user = new User(this, username, password);
			d_out.writeInt(LOGIN_CHECK);
			if(user.userExists()) {
				if(user.checkPassword(password)) 
					d_out.writeInt(1);//success
				else
					d_out.writeInt(0);//fail
			} else
				d_out.writeInt(0);
				//user.saveUser();//doesn't exist? just create it.

		}
	}

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