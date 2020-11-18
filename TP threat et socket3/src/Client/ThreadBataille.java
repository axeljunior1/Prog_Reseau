package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ThreadBataille extends Thread{
	Scanner sc = new Scanner(System.in);
	BufferedReader in,in2;
	PrintWriter out,out2;
	boolean j1;
	
	Grille NaviresJ1 = new  Grille();
	Grille grilJ1 = new  Grille();      // cette grille renseigne sur la position des navires adverse
	Grille NaviresJ2 = new  Grille();
	Grille grilJ2 = new  Grille();
	
	public void PlacerNavires() {
		
		if (j1) {
			out.println(" il est temps de placer les navires");
			out.println(" ils sont de la forme (\"taille,caseDebut,sens\") ex: \"2,A 2,H\" ");
			for (int i = 0; i < 1; i++) {
				String case_navir=new Scanner(in).nextLine();
				NaviresJ1.PlacerLesNavires(case_navir);

			}
			NaviresJ1.tostring(out);
		}
		else {
			out2.println(" il est temps de placer les navires");
			out2.println(" ils sont placés les uns apres les autres sous la forme (A1-A2-Ai) ou (A1-B1-C1)");
			for (int i = 0; i < 1; i++) {

				String case_navir=new Scanner(in2).nextLine();
				NaviresJ2.PlacerLesNavires(case_navir);
				
			}
			NaviresJ2.tostring(out2);
		}
		
	}

	public void tir() {
		if (j1) {
			out.println("J1 à toi de tirer");
			
			String case_navir= new Scanner(in).nextLine();
			case_navir = case_navir.toUpperCase();
			if (NaviresJ2.contains(case_navir)) {
				grilJ1.ajoutCase(case_navir);
				grilJ1.tostring(out);
				grilJ2.setNbCaseTotal();
				out.println(grilJ1.getNbCaseTotal());
				out.println("beau tir: touché");
			}else {

				grilJ1.ajoutCase(case_navir);
				grilJ1.setCase(grilJ1.indexof(case_navir),case_navir, "XX");
				grilJ1.tostring(out);

				out.println("mauvais  tir: raté");
			}
		}else {
			out2.println("J2 à toi de tirer");
			
			String case_navir= new Scanner(in2).nextLine();
			case_navir = case_navir.toUpperCase();
			if (NaviresJ1.contains(case_navir)) {
				grilJ2.ajoutCase(case_navir);
				grilJ2.tostring(out2);
				grilJ1.setNbCaseTotal();
				out2.println(grilJ2.getNbCaseTotal());

				out2.println("beau tir: touché");
			}else {

				grilJ2.ajoutCase(case_navir);
				grilJ2.setCase(grilJ2.indexof(case_navir), case_navir, "XX");
				grilJ2.tostring(out2);

				out2.println("mauvais  tir: raté");
			}
		}
	}
	
	public ThreadBataille(Socket client1,Socket client2) {
		try {

			in = new BufferedReader(new InputStreamReader(client1.getInputStream()));
			out = new PrintWriter(client1.getOutputStream(), true);

			in2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
			out2 = new PrintWriter(client2.getOutputStream(), true);

		}catch (Exception e) {}
	}

	
	public void run() {
		try {	
			
			out.println("le jeux commence");
			NaviresJ1.tostring(out);
			out2.println("le jeux commence");
			NaviresJ2.tostring(out2);
			j1=true;
			if (j1) {
				out.println("J1 à toi");
				PlacerNavires();
				j1=!j1;
			}
			if (!j1) {
				out2.println("J2 à toi");
				PlacerNavires();
				j1=!j1;
			}

			while (grilJ1.getNbCaseTotal()!=0 && grilJ2.getNbCaseTotal()!=0) {
				
				 j1=true;
				if (j1) {
					tir();
					j1=!j1;
				}
				if (!j1) {
					tir();
					j1=!j1;
				}

			}
			if (grilJ1.getNbCaseTotal()==0) {
				out.println(" le joueur 2 a gagné");

				out2.println(" le joueur 2 a gagné");
			}
			if (grilJ2.getNbCaseTotal()==0) {
				out.println(" le joueur 1 a gagné");

				out2.println(" le joueur 1 a gagné");
			}
		}catch (Exception e) {}
	}
	
}

