package lo02.shapeup.affichage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import lo02.shapeup.jeu.*;

public class AffichageConsole implements Affichage {

	@Override
	public Joueur[] definirJoueurs(int modeDeJeu) {
		
		Joueur[] joueurs;

		switch(modeDeJeu) {
			case 0:
			case 2:
			joueurs = new Joueur[1];
			break;
			
			case 1:
			case 3:
			joueurs = new Joueur[2];
			break;
			
			default:
			joueurs = new Joueur[1];
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
		System.out.println("La carte piochÃ©e est : " + c);
		return c;
	}

	@Override
	public int choisirAction() {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("Veuillez choisir l'action Ã  effectuer :");
		System.out.println("0 - Poser carte.");
		System.out.println("1 - DÃ©placer carte.");

		int action = scan.nextInt();

		return action;
	}

	@Override
	public int demanderDeplacement() {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("Voulez-vous déplacer une carte avant de finir le tour ?");
		System.out.println("0 - Oui.");
		System.out.println("1 - Non.");

		int action = scan.nextInt();

		return action;
	}

	@Override
	public int[] choisirPositionCarte(List<int[]> positionnementsPossibles) {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("Veuillez choisir une position :");
		int i = 0;
		while(i < positionnementsPossibles.size()) {
			System.out.println(i + " - " + positionnementsPossibles.get(i));
			i++;
		}

		int position = scan.nextInt(); 

		return positionnementsPossibles.get(position);
	}

	@Override
	public int[] choisirDeplacementCarte(List<int[]> deplacementsPossibles) {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("Veuillez choisir un déplacement :");
		int i = 0;
		while(i < deplacementsPossibles.size()) {
			System.out.println(i + " - " + deplacementsPossibles.get(i));
			i++;
		}

		int deplacement = scan.nextInt(); 

		return deplacementsPossibles.get(deplacement);
	}

	public void afficherPlateau(Plateau plateau) {
		String asciiPlateau = "";

		Map<int[], Carte> disposition = plateau.getDisposition();
		List<int[]> coordinates = new ArrayList<int[]>(disposition.keySet());

		Comparator<int[]> comparator = Comparator.comparing( (int[] x) -> x[0]).thenComparing(x -> x[1]);
		coordinates.sort(comparator);

		int[] porteeX = plateau.getPorteeX();
		int[] porteeY = plateau.getPorteeY();

		for(int y = porteeY[0]; y <= porteeY[1]; y++) {
			asciiPlateau += y + " - ";
			for(int x = porteeX[0]; x <= porteeX[1]; x++) {

				int[] coords = new int[] {x, y};
				if(coordinates.contains(coords)) {
					asciiPlateau +=  x + "." + disposition.get(coords) + " ";
				}
				else {
					asciiPlateau += x + ".Aucune carte ";
				}
			}
			asciiPlateau += '\n';
		}

		System.out.println(asciiPlateau);
	}

}
