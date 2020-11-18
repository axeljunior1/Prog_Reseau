package Client;
import java.util.ArrayList;
import java.util.Scanner;

public class Main_test {

	public static void main(String[] args) {
		
	
		
//String s= new Scanner(System.in).nextLine();
		
	//System.out.println(s+" ");
		Grille g = new Grille();
		g.ajoutCase("a1");
		g.PlacerLesNavires("3,a10,v");
		g.setCase(g.indexof("B10"), "B10", "sdf");
		for (ArrayList<String> string : g.array) {
			System.out.println(string);
		}

	}

}
