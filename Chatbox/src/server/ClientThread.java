package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ClientThread extends Server implements Runnable {

	private Socket socket;
	private DataOutputStream d_out;
	private int timeout;
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
			timeout = MILI_DELAY;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {

			DataInputStream d_in = new DataInputStream(socket.getInputStream());// reader for ints
			BufferedReader b_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));// text input

			while (!socket.isClosed()) {// listening for input from thread
				if (socket.getInputStream().available() > 0) {
					int packetId = d_in.readInt();
					if (packetId == 1000) {// ping
						timeout = MILI_DELAY;
					} else if (packetId == 2) {
						String username = b_in.readLine();
						d_out.writeInt(1);// String
						d_out.writeBytes("Welcome " + username + " to the chatbox!\n");
					} else if (packetId == 1) {// message
						String msg = b_in.readLine();
						distributeMessage(msg);
					}
				} else {
					if (timeout > 0) {
						timeout--;
						Thread.sleep(1);// we need the thread to sleep for atleast a milisecond to properly time the
										// ping
					} else {
						clients.remove(this);
						System.out.println("Disconnected Connection: " + socket.getInetAddress() + " Remaining Users: "
								+ clients.size());
						d_out.writeInt(1000);// after a minute of not talking with the client we remove it
						socket.close();
					}
				}
			}

		} catch (Exception e) {
			if (e instanceof SocketException)
				return;
			e.printStackTrace();
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public DataOutputStream getdOutputStream() {
		return d_out;
	}

}