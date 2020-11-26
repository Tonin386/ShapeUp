package lo02.shapeup.regles;

import java.util.List;

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
		
		Plateau plateau = partie.getPlateau();

		Carte c = partie.piocher();

		if(plateau.getCartesPosees() != 0) {
			int action = partie.getAffichage().choisirAction();

			boolean aPoseCarte = false;
			boolean aDeplaceCarte = false;

			if(action == 0) {
				List<Integer> position = partie.getAffichage().choisirPositionCarte(plateau.getPositionnementsPossibles());
				plateau.poserCarte(position.get(0), position.get(1), c);
				aPoseCarte = true;
			}
			else if(action == 1 && plateau.getCartesPosees() > 1) {
				List<Integer> deplacement = partie.getAffichage().choisirDeplacementCarte(plateau.getDeplacementsPossibles());
				plateau.deplacerCarte(deplacement.get(0), deplacement.get(1), deplacement.get(2), deplacement.get(3));
				aDeplaceCarte = true;
			}

			if(aDeplaceCarte == false && plateau.getCartesPosees() > 1) {
				action = partie.getAffichage().demanderDeplacement();	

				if(action == 0) {
					List<Integer> deplacement = partie.getAffichage().choisirDeplacementCarte(plateau.getDeplacementsPossibles());
					plateau.deplacerCarte(deplacement.get(0), deplacement.get(1), deplacement.get(2), deplacement.get(3));
					aDeplaceCarte = true;
				}
			}

			if(aPoseCarte == false) {
				List<Integer> position = partie.getAffichage().choisirPositionCarte(plateau.getPositionnementsPossibles());
				plateau.poserCarte(position.get(0), position.get(1), c);
				aPoseCarte = true;
			}
		}
		else {
			plateau.poserCarte(0, 0, c);
		}
	}
}