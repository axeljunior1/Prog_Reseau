package Serveur;

import java.net.ServerSocket;
import java.net.Socket;

import Client.ThreadBataille;

public class Serveur {
	public static void main(String[] args) {

		 try {
				ServerSocket ecoute = new ServerSocket(1500);
				System.out.println("Serveur lancé!");
					
				while(true) {
					Socket client1 = ecoute.accept();
					Socket client2 = ecoute.accept();
					new ThreadBataille(client1,client2).start();
					
				}
				} catch(Exception e) {

				}
	}
	
}
