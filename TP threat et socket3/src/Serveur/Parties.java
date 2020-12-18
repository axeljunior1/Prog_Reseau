package Serveur;

import java.net.Socket;

import Client.ThreadBataille;

public class Parties {
	
	public Parties(Socket client1, Socket client2, int partie) {
		new ThreadBataille(client1, client2, partie).start();
	}
	
}
