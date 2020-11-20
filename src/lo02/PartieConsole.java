package lo02;

import java.util.Scanner;

public class PartieConsole extends Partie {

	public PartieConsole(int modeDeJeu) {
		super(modeDeJeu);
	}

	protected void definirJoueurs() {
		Scanner scan = new Scanner(System.in);

		int nombreJoueurs = this.joueurs.length;
		for(int i = 0; i < nombreJoueurs; i++) {
			System.out.println("Entrez le nom du Joueur "+ (int)(i+1) +" : ");
			String nom = scan.next();

			System.out.println("Ce joueur est :");
			System.out.println("0 - Un humain");
			System.out.println("1 - Un Ordinateur");
			int classe = scan.nextInt();

			if(classe == 0) {
				this.joueurs[i] = new JoueurPhysique(nom);
			}
			else {
				this.joueurs[i] = new JoueurVirtuel(nom);
			}
		}
	}
	
	public void jouer() {
		
	}
}
