package Client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import Serveur.ListeningThread;


public class Client {
	public static void main(String[] args) {
		Scanner sc = new Scanner( System.in ) ;
		try {
			Socket s = new Socket("127.0.0.1", 1500);
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			new ListeningThread(s).start();
			System.out.println("*********************Connexion réussie!******************************\n---------------------------------------------------------------------------");
			String message="";
			while (message!="quit") {
				message=sc.nextLine();
				out.println(message);
			}
			sc.close();
			s.close();
		} catch(Exception e) {
			// Traitement d'erreur
		}
	}
}