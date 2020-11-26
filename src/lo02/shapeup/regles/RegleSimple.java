package lo02.shapeup.regles;
import lo02.shapeup.jeu.*;

public class RegleSimple implements Regle {

	@Override
	public void debutJeu(Partie partie) {
		for(Joueur j : partie.getJoueurs()) {
			Carte c = partie.piocher();
			j.piocherVictorieuse(c);
		}
	}

	@Override
	public void jouer(Partie partie) {
		
		int tour = partie.getTour();
		Plateau plateau = partie.getPlateau();

		Carte c = partie.piocher();

		if(tour != 0) {
			int action = partie.getAffichage().choisirAction();

			boolean aPoseCarte = false;
			boolean aDeplaceCarte = false;

			if(action == 0) {
				int position[] = partie.getAffichage().choisirPositionCarte(plateau.getPositionnementsPossibles());
				plateau.poserCarte(position[0], position[1], c);
				aPoseCarte = true;
			}
			else if(action == 1) {
				int deplacement[] = partie.getAffichage().choisirDeplacementCarte(plateau.getDeplacementsPossibles());
				plateau.deplacerCarte(deplacement[0], deplacement[1], deplacement[2], deplacement[3]);
				aDeplaceCarte = true;
			}

			if(aDeplaceCarte == false) {
				action = partie.getAffichage().demanderDeplacement();	

				if(action == 0) {
					int deplacement[] = partie.getAffichage().choisirDeplacementCarte(plateau.getDeplacementsPossibles());
					plateau.deplacerCarte(deplacement[0], deplacement[1], deplacement[2], deplacement[3]);
					aDeplaceCarte = true;
				}
			}

			if(aPoseCarte == false) {
				int position[] = partie.getAffichage().choisirPositionCarte(plateau.getPositionnementsPossibles());
				plateau.poserCarte(position[0], position[1], c);
				aPoseCarte = true;
			}
		}
		else {
			plateau.poserCarte(0, 0, c);
		}
	}
}