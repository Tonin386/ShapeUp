package lo02;

import java.util.Scanner;

public class MainApp {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		
		System.out.println("Veuillez choisir l'interface de cette partie :");
		System.out.println("0 - Interface lignes de commande");
		System.out.println("1 - Interface graphique (Non-implémentée)");
		
		int modeAffichage = scan.nextInt();
		
		if(modeAffichage == 0) {
			
			System.out.println("Veuillez choisir le mode de jeu.");
			System.out.println("0 - Deux joueurs, mode simple");
			System.out.println("1 - Trois joueurs, mode simple");
			System.out.println("2 - Deux joueurs, mode avancé");
			System.out.println("3 - Trois joueurs, mode avancé");

			int modeDeJeu = scan.nextInt();
			
			PartieConsole partie = new PartieConsole(modeDeJeu);
			partie.jouer();
		}
		else {
			return;
		}
	}
}
