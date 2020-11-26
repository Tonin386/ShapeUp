package lo02.shapeup.jeu;

import java.util.Scanner;
import lo02.shapeup.affichage.*;

public class MainApp {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		
		System.out.println("Veuillez choisir l'interface de cette partie :");
		System.out.println("0 - Interface lignes de commande");
		System.out.println("1 - Interface graphique (Non-implémentée)");
		
		int modeAffichage = scan.nextInt();
		Affichage affichage;
		
		if(modeAffichage == 0) {
			
			affichage = new AffichageConsole();
			
			System.out.println("Veuillez choisir le mode de jeu.");
			System.out.println("0 - Deux joueurs, mode simple");
			System.out.println("1 - Trois joueurs, mode simple");
			System.out.println("2 - Deux joueurs, mode avancé");
			System.out.println("3 - Trois joueurs, mode avancé");

			int modeDeJeu = scan.nextInt();
			Partie partie = new Partie(modeDeJeu, affichage);

			while(partie.estFinie() == false) {
				partie.jouer();	
			}

			System.out.println("Partie terminée !");
		}
		else {
			scan.close();
			return;
		}
		
		scan.close();
	}
}
