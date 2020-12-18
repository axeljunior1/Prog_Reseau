package Serveur;

import java.net.ServerSocket;
import java.net.Socket;

import Client.ThreadBataille;

public class Serveur {
	public static void main(String[] args) {

		try {
			ServerSocket ecoute = new ServerSocket(1500);
			System.out.println("Serveur lancé!");
			int partie =1; 	
			while(true) {
				//partie = 1;
				Socket client1 = ecoute.accept();
				Socket client2 = ecoute.accept();
				Parties p = new Parties(client1, client2, partie); // definition d'une partie entre deux joueurs 
				System.out.println(" la partie "+ partie+" est lancée");
				partie++;

			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
