package lo02.shapeup.affichage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import lo02.shapeup.partie.*;

public class AffichageConsole implements Affichage {

	@Override
	public Joueur[] definirJoueurs(int modeDeJeu) {
		
		Joueur[] joueurs;

		switch(modeDeJeu) {
			case 0:
			case 2:
			joueurs = new Joueur[2];
			break;
			
			case 1:
			case 3:
			joueurs = new Joueur[3];
			break;
			
			default:
			joueurs = new Joueur[2];
			break;
		}
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		for(int i = 0; i < joueurs.length; i++)
		{
			System.out.println("Entrer le nom du joueur #" + i + " :");
			String nom = scan.next();
			System.out.println("Entrer le type du joueur : ");
			System.out.println("0 - Joueur physique.");
			System.out.println("1 - Joueur virtuel.");
			int type = scan.nextInt();

			Joueur joueur;
			if(type == 0) {
				joueur = new JoueurPhysique(nom);
			}
			else {
				joueur = new JoueurVirtuel(nom);
			}
			
			joueurs[i] = joueur;
		}
		
		return joueurs;
	}

	@Override
	public void debutTour(Joueur j) {
		System.out.println("C'est au tour de " + j + " de jouer.");
	}

	@Override
	public Carte afficherCartePiochee(Carte c) {
		System.out.println("La carte piochée est : " + c);
		return c;
	}

	@Override
	public Carte choisirCarteJeu(List<Carte> jeu) {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("Veuillez choisir une carte à jouer :");
		this.afficherJeu(jeu);
		int carte = 0; //scan.nextInt();

		return jeu.get(carte);
	}

	@Override
	public void afficherJeu(List<Carte> jeu) {
		int i = 0;
		while(i < jeu.size()) {
			System.out.println(i + " - " + jeu.get(i));
			i++;
		}
	}

	@Override
	public int choisirAction() {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("Veuillez choisir l'action à effectuer :");
		System.out.println("0 - Poser carte.");
		System.out.println("1 - Déplacer carte.");

		int action = 0; //scan.nextInt();

		return action;
	}

	@Override
	public int demanderDeplacement() {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("Voulez-vous déplacer une carte avant de finir le tour ?");
		System.out.println("0 - Oui.");
		System.out.println("1 - Non.");

		int action = 1;//scan.nextInt();

		return action;
	}

	@Override
	public List<Integer> choisirPositionCarte(List<List<Integer>> positionnementsPossibles) {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("Veuillez choisir une position :");
		int i = 0;
		while(i < positionnementsPossibles.size()) {
			System.out.println(i + " - " + positionnementsPossibles.get(i).get(0) + ";" + positionnementsPossibles.get(i).get(1));
			i++;
		}

		int position = 0;//scan.nextInt(); 

		return positionnementsPossibles.get(position);
	}

	@Override
	public List<Integer> choisirDeplacementCarte(List<List<Integer>> deplacementsPossibles) {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("Veuillez choisir un déplacement :");
		int i = 0;
		while(i < deplacementsPossibles.size()) {
			System.out.println(i + " - " + deplacementsPossibles.get(i).get(0) + ";" + deplacementsPossibles.get(i).get(1) + " -> " + deplacementsPossibles.get(i).get(2) + ";" + deplacementsPossibles.get(i).get(3));
			i++;
		}

		int deplacement = scan.nextInt(); 

		return deplacementsPossibles.get(deplacement);
	}

	public void afficherPlateau(Plateau plateau) {
		String asciiPlateau = "";

		Map<List<Integer>, Carte> disposition = new HashMap<List<Integer>, Carte>(plateau.getDisposition());
		List<Integer> porteeX = plateau.getPorteeX();
		List<Integer> porteeY = plateau.getPorteeY();

		for(int y = porteeY.get(0); y <= porteeY.get(1); y++) {
			for(int x = porteeX.get(0); x <= porteeX.get(1); x++) {

				List<Integer> coords = new ArrayList<Integer>();
				coords.add(x);
				coords.add(y);
				if(disposition.get(coords) != null) {
					asciiPlateau += "\t("+ x + ";" + y + ") " + disposition.get(coords);
				}
				else {
					asciiPlateau += "\t("+ x + ";" + y + ") " +  "     Aucune carte      ";
				}
			}
			asciiPlateau += '\n';
		}

		System.out.println(asciiPlateau);
	}

}