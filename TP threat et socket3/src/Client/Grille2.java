package Client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Grille2 {
	
	ArrayList< ArrayList<String>> array = new ArrayList<ArrayList<String>>();
	static List<String> givenListlettres =Arrays.asList("A","B","C","D","E","F","G","H","I","J","");
	static List<String> givenListchiffres = Arrays.asList("1","2","3","4","5","6","7","8","9","10");
	
	public Grille2() {

		for (String s1 : givenListlettres) {
			ArrayList<String> al = new ArrayList<String>();
			for (String s2 : givenListchiffres) {
				if ( s1!="") {
					al.add("OO");   //al.add("s2+s1"); pour la construction des cases 
				}
			}
			array.add(al);
		}
		
	}
	
	public String toUpperCase(String Case) { // majuscule //Ok
		Case=Case.toUpperCase();
		return Case;
	}

	public boolean contains(String Case) { // contient ? // ok 
		Case= Case.replace(" ", "");
		Case=Case.toUpperCase();

		for (ArrayList<String> str : array) {
			if (str.contains(Case))return true;
		}
		return false;
	}
	
	public int indexof(String Case) {
		Case= Case.replace(" ", "");
		Case=Case.toUpperCase();
		
		for (ArrayList<String> str : array) {
			if (str.contains(Case)) {
				return array.indexOf(str);
			}
			else {
				System.out.println("ne contient pas l'element demandé");
			}
		}
		return 0;
	}
	
	public void ajoutCase(String Case) {
		Case=Case.toUpperCase();
		String [] Case_split = new String[3];
		Case_split[0]=Case.charAt(0)+"";
		Case_split[1]=Case.charAt(1)+"";
		if (Case.length()==3) Case_split[1]=Case.charAt(1)+""+Case.charAt(2);	
		
		for (String s1 : givenListlettres) {
			if (s1.equals(Case_split[0])& s1!="") {
				ArrayList<String> al = array.get(givenListlettres.indexOf(s1));
				//System.out.println(givenListlettres.indexOf(s1));
				for (String s2 : givenListchiffres) { 
					if (s2.equals( Case_split[1]) && s2!="") {
						al.set(Integer.parseInt(s2)-1, s1+s2);
						
					}   
				}
			}
			
		}
	}
	
	
	
	public void PlacerLesNavires(String navir) {  // navir est de la forme ("taille,caseDebut,sens") ex: "2,A 2,H"
		String composant[]= navir.split(",");
		int taille = Integer.parseInt(composant[0] ); String debut=composant[1] ; String sens=composant[2] ; 
		
		debut=debut.toUpperCase();
		String [] debut_split = new String[3];
		debut_split[0]=debut.charAt(0)+"";
		debut_split[1]=debut.charAt(1)+"";
		if (debut.length()==3) debut_split[1]=debut.charAt(1)+""+debut.charAt(2);
		System.out.println(taille+" "+debut+" "+sens);
		if (sens.toUpperCase().equals("V") ) { // ajout vertical
			int i = givenListlettres.indexOf(debut_split[0]), j=i;
			System.out.println("test sens");
			while (j <i+taille) {
				ajoutCase(debut);
				j++;
				debut= givenListlettres.get(j)+""+debut_split[1];
			}
		}
		else if (sens.toUpperCase().equals("H")) { // ajout horizontal
			int i =Integer.parseInt(debut_split[1]), j=i;
			while (j <i+taille) {
				ajoutCase(debut);
				j++;
				debut= debut_split[0]+""+Integer.toString(j);
			}
		}
	}

	
	
	public void tostring() {
		for (ArrayList<String> arraylist : array) {
			System.out.println(arraylist);
		}
	}
	
	
	
	

}
