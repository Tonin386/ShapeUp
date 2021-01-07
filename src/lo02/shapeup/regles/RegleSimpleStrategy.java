package lo02.shapeup.regles;

import lo02.shapeup.partie.*;

public class RegleSimpleStrategy implements RegleStrategy, Cloneable {

	@Override
	public void debutJeu(Partie partie) {
		for(JoueurStrategy j : partie.getJoueurs()) {
			Carte c = partie.piocher();
			j.piocherVictorieuse(c);
		}
	}

	@Override
	public void jouer(Partie partie) {

		Carte c = partie.piocher();
		int tour = partie.getTour();
		JoueurStrategy j = partie.getJoueurs().get(tour);
		j.piocher(c);
		partie.emitSignal();
	}
}