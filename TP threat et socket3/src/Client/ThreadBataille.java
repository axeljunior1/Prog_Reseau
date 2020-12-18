package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ThreadBataille extends Thread{
	Scanner sc = new Scanner(System.in);
	private BufferedReader in,in2;
	private PrintWriter out,out2;
	private boolean j1;
	private int partie;
	
	private Grille NaviresJ1 = new  Grille();   // la grille du joueur
	private Grille grilJ1 = new  Grille();      // cette grille renseigne sur la position des navires adverse
	private Grille NaviresJ2 = new  Grille();
	private Grille grilJ2 = new  Grille();
	
	public ThreadBataille(Socket client1,Socket client2, int partie ) {
		try {
			this.partie = partie;
			
			in = new BufferedReader(new InputStreamReader(client1.getInputStream()));
			out = new PrintWriter(client1.getOutputStream(), true);

			in2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
			out2 = new PrintWriter(client2.getOutputStream(), true);

		}catch (Exception e) {}
	}
	
	public void PlacerNavires() { // recupere les données entrées par le client et place le navir 
		int size =1 ;
		if (j1) {
			out.println(" il est temps de placer les navires");
			out.println(" ils sont de la forme (\"taille,caseDebut,sens\") ex: \"2,A2,H\" ");
			while(size>0) {
				String case_navir=new Scanner(in).nextLine(); 
				size = NaviresJ1.PlacerUnNavires_Manuel(out, case_navir); //placer le navire entré
			}
			NaviresJ1.tostring(out); // affichage de la grille 
		}else {
			out2.println(" il est temps de placer les navires");
			out2.println(" ils sont de la forme (\"taille,caseDebut,sens\") ex: \"2,A2,H\" ");
			while(size>0) {
				String case_navir=new Scanner(in2).nextLine();
				size = NaviresJ2.PlacerUnNavires_Manuel(out2, case_navir);
			}
			NaviresJ2.tostring(out2);
		}
	}
	
	public void PlacerNavires_aleatoirement() {
		
		if(j1) {
			NaviresJ1.Placer_tous_les_navires(out);
			NaviresJ1.tostring(out);
		}else {
			NaviresJ2.Placer_tous_les_navires(out2);
			NaviresJ2.tostring(out2);
		}
		
		
	}

	public void Brodcast_messages_Serveur(PrintWriter out1,PrintWriter out2, String message) { // envoyer un msg a tous les clients 
	out1.println(message);
	out2.println(message);	
}

	public void tir() { // les joueurs effectuent des tirs 
	if (j1) {
		out.println("\n_____"+NaviresJ1.getName()+" à toi de tirer");
		String case_navir= new Scanner(in).nextLine();
		while (case_navir.length()<2||case_navir.length()>3) {
			out.println("\n_____Case invalide tire a nouveau");
			case_navir = (new Scanner(in).nextLine()).toUpperCase();
		}
		case_navir = case_navir.toUpperCase();
			if (NaviresJ2.contains(case_navir)) {
				grilJ1.ajoutCase(case_navir);
				grilJ1.tostring(out);
				NaviresJ2.setNbCaseTotal();
				out.println("Nombre de case restante a toucher: "+NaviresJ2.getNbCaseTotal());
				out.println("______Beau tir: touché");
			}else {
				grilJ1.ajoutCase(case_navir);
				grilJ1.setCase(grilJ1.indexof(case_navir),case_navir, "XX");
				grilJ1.tostring(out);
				out.println("Nombre de case restante a toucher: "+NaviresJ2.getNbCaseTotal());
				out.println("______Mauvais  tir: raté");
			
		}

	}else {
		out2.println("\n_____"+NaviresJ2.getName()+" à toi de tirer");

		String case_navir= (new Scanner(in2).nextLine()).toUpperCase();
		while (case_navir.length()<2||case_navir.length()>3) {
			out2.println("\n_____Case invalide tire a nouveau");
			case_navir = (new Scanner(in2).nextLine()).toUpperCase();
		}
		if (NaviresJ1.contains(case_navir)) {
			grilJ2.ajoutCase(case_navir);
			grilJ2.tostring(out2);
			NaviresJ1.setNbCaseTotal(); // si le navire a ete touché on reduit le nbre de case restantes 
			out2.println("Nombre de case restante a toucher: "+NaviresJ1.getNbCaseTotal());
			out2.println("______Beau tir: touché");

		}else {

			grilJ2.ajoutCase(case_navir);
			grilJ2.setCase(grilJ2.indexof(case_navir), case_navir, "XX");
			grilJ2.tostring(out2);
			out2.println("Nombre de case restante a toucher: "+NaviresJ1.getNbCaseTotal());
			out2.println("______Mauvais  tir: raté");
		}
		
	}
}

	public void run() {
		try {	
			String message = "*************Bienvenue dans bataille navale. Partie N°: "+partie+"****************"
					+ "\n_________________________________________________________________________________________\n";

			Brodcast_messages_Serveur(out, out2, message);
			Brodcast_messages_Serveur(out, out2, "___Entre ton pseudo__");
			NaviresJ1.setName(in.readLine());
			NaviresJ2.setName(in2.readLine());

			String relance_partie ="O";
			while (relance_partie.equals("O")) {
				j1=true;
				if (j1) {
					String methode_remplicage="";
					while (!methode_remplicage.equals("A") || !methode_remplicage.equals("M")) {
						out.println("\n*Pour placer les navires de facon Manuel tappez'M', tapez 'A' pour un remplicage aleatoire");
						methode_remplicage =  (in.readLine()).toUpperCase();
						if (methode_remplicage.equals("A") ) {
							PlacerNavires_aleatoirement();
							break;
						}
						if (methode_remplicage.equals("M") ) {
							out.println(NaviresJ1.getName()+" à toi");
							PlacerNavires();
							break;
						}
					}

					j1=!j1;
				}
				if (!j1) {
					String methode_remplicage="";
					while (!methode_remplicage.equals("A") || !methode_remplicage.equals("M")) {
						out2.println("\n Pour placer les navires de facon Manuel tappez'M', tapez 'A' pour un remplicage aleatoire");
						methode_remplicage = (in2.readLine()).toUpperCase();
						if (methode_remplicage.equals("A") ) {
							PlacerNavires_aleatoirement();
							break;
						}
						if (methode_remplicage.equals("M") ) {
							out2.println(NaviresJ2.getName()+" à toi");
							PlacerNavires();
							break;
						}
					}
					j1=!j1;
				}
				while (NaviresJ1.getNbCaseTotal()!=0 && NaviresJ2.getNbCaseTotal()!=0) {

					j1=true;
					if (j1) {
						tir();
						j1=!j1;
					}
					if (!j1 && NaviresJ2.getNbCaseTotal()!=0) {
						tir();
						j1=!j1;
					}

				}
				if (NaviresJ1.getNbCaseTotal()==0) {
					message = "\n***********"+ NaviresJ2.getName()+" à gagné************";
					Brodcast_messages_Serveur(out, out2, message);
				}
				if (NaviresJ2.getNbCaseTotal()==0) {
					message = "\n***********"+NaviresJ1.getName()+" à gagné****";
					Brodcast_messages_Serveur(out, out2, message);
				}
				Brodcast_messages_Serveur(out, out2, "Tapez O pour Relancer la partie et N pour Sortir");
				String relance_j1 = in.readLine().toUpperCase(), relance_j2=in2.readLine().toUpperCase();
				if (relance_j1.toUpperCase().equals("O")&& relance_j2.toUpperCase().equals("O")) {
				}else {
					relance_partie ="N";
					Brodcast_messages_Serveur(out, out2, "**********Au Revoir************");
					
				}
			}
		}catch (Exception e) {}
	}
	
}

