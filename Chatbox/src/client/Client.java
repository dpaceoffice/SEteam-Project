package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
/**
 * Main client class
 */
public class Client {
	/**
	 * Host ip address
	 */
	private static final String HOST = "127.0.0.1";
	
	/**
	 * Open port to connect to
	 */
	private static final int PORT = 43594;

	/**
	 * State variables
	 */
	private String name;
	private String ipAddress;
	private int port;
	private Scanner scan;
