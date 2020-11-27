package lo02.shapeup.regles;
import lo02.shapeup.jeu.*;

import java.util.List;

public class RegleAvancee implements Regle {

	@Override
	public void debutJeu(Partie partie) {
		for(Joueur j : partie.getJoueurs()) {
			for(int i = 0; i < 3; i++) {
				Carte c = partie.piocher();
				j.piocher(c);
			}
		}
	}

	@Override
	public void jouer(Partie partie) {
		
		Plateau plateau = partie.getPlateau();

		Carte c;
		int tour = partie.getTour();

		if(plateau.getCartesPosees() != 0) {
			int action = partie.getAffichage().choisirAction();

			boolean aPoseCarte = false;
			boolean aDeplaceCarte = false;

			if(action == 0) {
				c = partie.getAffichage().choisirCarteJeu(partie.getJoueurs()[tour].getJeu());
				List<Integer> position = partie.getAffichage().choisirPositionCarte(plateau.getPositionnementsPossibles());
				plateau.poserCarte(position.get(0), position.get(1), c);
				partie.getJoueurs()[tour].poserCarte(c);

				aPoseCarte = true;
			}
			else if(action == 1 && plateau.getCartesPosees() > 1) {
				List<Integer> deplacement = partie.getAffichage().choisirDeplacementCarte(plateau.getDeplacementsPossibles());
				plateau.deplacerCarte(deplacement.get(0), deplacement.get(1), deplacement.get(2), deplacement.get(3));
				aDeplaceCarte = true;
			}

			if(aDeplaceCarte == false && plateau.getCartesPosees() > 1) {
				action = partie.getAffichage().demanderDeplacement();	

				if(action == 0 && plateau.getCartesPosees() + partie.getJoueurs().length != 17) {
					List<Integer> deplacement = partie.getAffichage().choisirDeplacementCarte(plateau.getDeplacementsPossibles());
					plateau.deplacerCarte(deplacement.get(0), deplacement.get(1), deplacement.get(2), deplacement.get(3));
					aDeplaceCarte = true;
				}
			}

			if(aPoseCarte == false) {
				c = partie.getAffichage().choisirCarteJeu(partie.getJoueurs()[tour].getJeu());
				List<Integer> position = partie.getAffichage().choisirPositionCarte(plateau.getPositionnementsPossibles());
				plateau.poserCarte(position.get(0), position.get(1), c);
				partie.getJoueurs()[tour].poserCarte(c);

				aPoseCarte = true;
			}
		}
		else {
			c = partie.getAffichage().choisirCarteJeu(partie.getJoueurs()[tour].getJeu());
			plateau.poserCarte(0, 0, c);
		}

		if(plateau.getCartesPosees() + partie.getJoueurs().length != 17) {
			for(Joueur j : partie.getJoueurs()) {
				j.piocherVictorieuse(j.getJeu().get(0));
			}
		}
		else {
			c = partie.piocher();
			partie.getJoueurs()[tour].piocher(c);
		}

	}
}