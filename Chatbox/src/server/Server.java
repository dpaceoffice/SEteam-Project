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

