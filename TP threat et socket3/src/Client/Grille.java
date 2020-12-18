package Client;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Grille {
	
	private int nbCaseTotal = 0 ;  
	private String name ; //nom du joueur
	private ArrayList<Integer> List_taille_navires = new ArrayList<Integer>(); // liste de la taille des navires a placer 
	private ArrayList< ArrayList<String>> array = new ArrayList<ArrayList<String>>(); // liste de ArrayList
	private static List<String> givenListlettres =Arrays.asList("A","B","C","D","E","F","G","H","I","J","");
	private static List<String> givenListchiffres = Arrays.asList("1","2","3","4","5","6","7","8","9","10");
	
	public Grille() { // constructeur
		for (int i = 2; i < 6; i++) {   // remplir List_taille_navires des differrentes tailles de navires  
			//List_taille_navires.add(i);
		}
		List_taille_navires.add(3); // ajoute le 3 car il ya deux navire de talle 3 
		
		for (String s1 : givenListlettres) {  // initialisation de la liste de ArrayList
			ArrayList<String> al = new ArrayList<String>();
			for (String s2 : givenListchiffres) {
				if ( s1!="") al.add("OO");   //al.add("s2+s1"); pour la construction des cases 
			}
			array.add(al);
		}
		
	}
	
	
	//accesseurs de nbCaseTotal
	public int getNbCaseTotal() {	return nbCaseTotal;	}
	public void setNbCaseTotal() {		this.nbCaseTotal--;	}

	public String getName() {		return name;	}
	public void setName(String name) {		this.name = name;	}
	
	public boolean contains(String Case) { // la liste de données contient une case particuliere ? // ok 
		Case= Case.replace(" ", "");
		Case=Case.toUpperCase();

		for (ArrayList<String> str : array) {
			if (str.contains(Case))return true;
		}
		return false;
	}
	
	public int indexof(String Case) {  // a quelle ArrayList de la liste de donnée appartient la case  
		Case= Case.replace(" ", "");
		Case=Case.toUpperCase();
		
		for (ArrayList<String> str : array) {
			if (str.contains(Case))	return array.indexOf(str);
		}
		return 0;
	}
	
	public boolean ajoutCase(String Case) { //ajout d'une case 
		Case=Case.toUpperCase();
		String [] Case_split = new String[3];
		Case_split[0]=Case.charAt(0)+"";
		Case_split[1]=Case.charAt(1)+"";
		if (Case.length()==3) Case_split[1]=Case.charAt(1)+""+Case.charAt(2);	
		for (String s1 : givenListlettres) {
			if (s1.equals(Case_split[0])& s1!="") {
				ArrayList<String> al = array.get(givenListlettres.indexOf(s1));
				for (String s2 : givenListchiffres) { 
					if (s2.equals( Case_split[1]) && s2!="" && (al.get(Integer.parseInt(s2)-1).equals("OO"))) {
						al.set(Integer.parseInt(s2)-1, s1+s2);
						return true;
					}
				}
			}
		}
		return false; 
	}
	
	public void setCase( int index , String Case , String newCase) { //modification d'une case 
		Case = Case.toUpperCase();
		newCase = newCase.toUpperCase();
		ArrayList<String> al = array.get(index);
		for (int i=0; i<al.size(); i++) { 
			if (al.get(i).equals( Case) ) {
				al.set(i, newCase);
			}   
		}

	}
	
	public int PlacerUnNavires_Manuel(PrintWriter out , String navir) {  // navir est de la forme ("taille,caseDebut,sens") ex: "2,A 2,H"

		String composant[]= navir.split(",");
		int taille ;
		try {

			taille = Integer.parseInt(composant[0] ); //valeur de a taille entré n'est pas in entier ?
		} catch (Exception e) {
			out.println("navir non pris en compte"); 
			return List_taille_navires.size();
		}
		if (composant.length!=3) {
			out.println("navir non pris en compte"); 
			return List_taille_navires.size(); 
		}
		String debut=composant[1] ; 
		String sens=composant[2] ; 
		if (PlacerUnNavirs_isPossible(taille, debut, sens)) {

			debut=debut.toUpperCase();
			String [] debut_split = new String[3];
			debut_split[0]=debut.charAt(0)+""; // position en lettre 
			debut_split[1]=debut.charAt(1)+"";  // position en chiffres 
			if (debut.length()==3) debut_split[1]=debut.charAt(1)+""+debut.charAt(2);
			if (debut.length()==1 || debut.length()>3) return List_taille_navires.size(); //si le case de debut n'est pas correct on revoie le tableau de valeurs possible 
			try {
				if (List_taille_navires.contains(taille)  ) {
					if (sens.toUpperCase().equals("V") ) { // ajout vertical
						int i = givenListlettres.indexOf(debut_split[0]), j=i;
						if (11-i-taille>0) { //test sur les bordure de de la grille 

							while (j <i+taille) {
								ajoutCase(debut);
								j++;
								debut= givenListlettres.get(j)+""+debut_split[1];
							}
							List_taille_navires.remove(List_taille_navires.indexOf(taille));	
						}else {
							out.println("Impossible de placer le navir");
							return List_taille_navires.size();
						}
					}
					else if (sens.toUpperCase().equals("H")) { // ajout horizontal
						int i =Integer.parseInt(debut_split[1]), j=i;
						if (11-i-taille>0) { // etudie la possibilité de placer ou pas le navire 

							while (j <i+taille) {
								ajoutCase(debut);
								j++;
								debut= debut_split[0]+""+Integer.toString(j);
							}
							List_taille_navires.remove(List_taille_navires.indexOf(taille));
						}else {
							out.println("Impossible de placer le navir");
							return List_taille_navires.size();
						}
					}

					nbCaseTotal =nbCaseTotal+taille; 
					out.println("Navir placé: place le suivant");
					out.println(getNbCaseTotal() + " "+ taille);
				}else {
					out.println("Un navire de cette taille a déja été ajouté ");
					out.println("les tailles restantes sont les suivantes: "+List_taille_navires.toString());
				}
			} catch (Exception e) {
				out.println(" Navir non ajouté: Réessaye ");
			}	
		}else {
			out.println("\nImpossible de placer le navir a ce niveau: Coincidence de case\n Réessaye");
		}
		
		return List_taille_navires.size();
	}

	public boolean Placer_un_Navire( int taille , String debut, String sens) {  // placer les navirs automatiquement  

		if (PlacerUnNavirs_isPossible(taille, debut, sens)) { // est-il possble de placer le navire? 
			
			debut=debut.toUpperCase();
			String [] debut_split = new String[3];
			debut_split[0]=debut.charAt(0)+""; // position en lettre 
			debut_split[1]=debut.charAt(1)+"";  // position en chiffres 
			if (debut.length()==3) debut_split[1]=debut.charAt(1)+""+debut.charAt(2);
			try {
				if (List_taille_navires.contains(taille)  ) { // la liste des talles contient bien la taille a ajouter 
					if (sens.toUpperCase().equals("V") ) { // ajout vertical
						int i = givenListlettres.indexOf(debut_split[0]), j=i;
						if (11-i-taille>0) { //test sur les bordure de de la grille 
							while (j <i+taille) {
								ajoutCase(debut); // ajout de la case 
								j++;
								debut= givenListlettres.get(j)+""+debut_split[1]; // deplacement du cuisseur vers le bas 
							}
							List_taille_navires.remove(List_taille_navires.indexOf(taille));	// le navire est placé, on retire cette taille du tableau 
						}else {
							return false;					}
					}
					else if (sens.toUpperCase().equals("H")) { // ajout horizontal
						int i =Integer.parseInt(debut_split[1]), j=i;
						if (11-i-taille>0) { //test sur les bordure de de la grille  

							while (j <i+taille) {
								ajoutCase(debut);
								j++;
								debut= debut_split[0]+""+Integer.toString(j);
							}
							List_taille_navires.remove(List_taille_navires.indexOf(taille));
						}else {
							return false;
						}
					}

					this.nbCaseTotal =this.nbCaseTotal+taille;  //  nbCaseTotal augmente de la taille du navire placé 
					
					return true;
				}
			} catch (Exception e) {
				return false;
			}

		}
		return false;
	}

	public boolean PlacerUnNavirs_isPossible( int taille , String debut, String sens) {  // est-il possible de placer un navire? 
		ArrayList<String> liste_case_a_ajouter = new ArrayList<String>(); //stocke les cases a ajouter 
		debut=debut.toUpperCase();
		String [] debut_split = new String[3];
		debut_split[0]=debut.charAt(0)+""; // position en lettre 
		debut_split[1]=debut.charAt(1)+"";  // position en chiffres 
		if (debut.length()==3) debut_split[1]=debut.charAt(1)+""+debut.charAt(2);
		try {
			if (List_taille_navires.contains(taille)  ) {
				if (sens.toUpperCase().equals("V") ) { // ajout vertical
					int i = givenListlettres.indexOf(debut_split[0]), j=i;
					if (11-i-taille>0) { //test sur les bordure de de la grille 
						
						while (j <i+taille) {
							liste_case_a_ajouter.add(debut);
							j++;
							debut= givenListlettres.get(j)+""+debut_split[1];
						}
					}else {
						return false;					}
				}
				else if (sens.toUpperCase().equals("H")) { // ajout horizontal
					int i =Integer.parseInt(debut_split[1]), j=i;
					if (11-i-taille>0) { // etudie la possibilité de placer ou pas le navire par rapport aux bordures 

						while (j <i+taille) {
							liste_case_a_ajouter.add(debut);
							j++;
							debut= debut_split[0]+""+Integer.toString(j);
						}
					}else {
						return false;
					}
				}

				for (String string : liste_case_a_ajouter) {
					if (contains(string)) {
						return false;
					}
				}

				return true;
			}
		} catch (Exception e) {
			return false;
		}
		
		
		return false;
	}

	public void Placer_tous_les_navires(PrintWriter out) { // place tous les navirs  grace a la methode PlacerLesNaviresall
		String [] Sens = {"v","h"};
		while (List_taille_navires.size()>0) {
			int taille = List_taille_navires.get(List_taille_navires.size()-1);
			String debut = givenListlettres.get((int) (Math.random()*10)) +""+givenListchiffres.get((int) (Math.random()*10));
			String sens  = Sens[(int)(Math.random()*2)];
			Placer_un_Navire( taille , debut , sens);			
		}
	}

	public void tostring(PrintWriter out) { // afficher la liste de arraylist
		for (ArrayList<String> arraylist : array) {
			out.println(arraylist);
		}
	}
	

}
