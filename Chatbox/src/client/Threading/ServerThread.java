package client.Threading;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;

import client.Client;
import client.GUI;

/**
 * ServerThread is a thread that handles all communications with the server
 */
public class ServerThread extends Packet implements Runnable {

	/**
	 * State variables
	 */
	private Socket socket;
	private String username;
	private LinkedList<String> queuedMessages;
	private DataOutputStream d_out;
	private DataInputStream d_in;
	private BufferedReader b_in;
	private GUI gui;
	private State state;
	private Client client;

	/**
	 * Constructor
	 * 
	 * @param socket   - socket used to connect to server
	 * @param username - display name picked by user
	 * @throws IOException
	 */
	public ServerThread(Socket socket, Client client) throws IOException {
		this.socket = socket;
		this.client = client;
		queuedMessages = new LinkedList<String>();
		d_out = new DataOutputStream(socket.getOutputStream());// write packet ints
		d_in = new DataInputStream(socket.getInputStream());// reader for ints
		b_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));// text input
		this.state = State.LOGIN;
		ServerThread thread = this;
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				gui = new GUI(thread, client);
				gui.setVisible(true);
			}
		});
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
			while (!socket.isClosed()) {
				if (socket.getInputStream().available() > 0) {
					int packetId = d_in.readInt();
					handleIncommingPackets(packetId);
				} 
				if (!queuedMessages.isEmpty()) {
						String msg = "";
						synchronized (queuedMessages) {
							msg = queuedMessages.pop();
						}
						if (state == State.CHATTING) {
						if (!msg.isEmpty()) {
							//d_out.writeInt(IDLE_PACKET);
							d_out.writeInt(MESSAGE_PACKET);// string packet
							d_out.writeBytes(username + ": " + msg + "\n");
						} else
							System.out.println("Error: Blank Message");
						}
					}
				}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void handleIncommingPackets(int packetId) throws IOException {
		if (packetId == MESSAGE_PACKET) {
			System.out.println(b_in.readLine());
		} else if(packetId == IDLE_PACKET) {
			//idle packet, don't do anything
		} else if (packetId == DISCONNECT_PACKET) {// if client recieves this server closed socket
			System.out.println("You have been disconnected for being idle");
			socket.close();
			System.exit(0);
		} else if (packetId == LOGIN_CHECK) {
			int opcode = d_in.readInt();
			gui.handleLoginReq(opcode);	
		}
	}

	public void checkLogin(String username, String pass) {
		try {
			d_out.writeInt(LOGIN_CHECK);
			d_out.writeBytes(username + "\n");
			d_out.writeBytes(pass + "\n");
			this.username = username;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void changeState(int state) {
		try {
			setState(State.values()[state]);
			d_out.writeInt(STATE_CHANGE);
			d_out.writeInt(state);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public State getState() {
		return state;
	}

	private void setState(State state) {
		this.state = state;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
