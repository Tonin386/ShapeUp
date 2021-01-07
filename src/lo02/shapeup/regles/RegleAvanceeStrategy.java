package lo02.shapeup.regles;
import lo02.shapeup.partie.*;

public class RegleAvanceeStrategy implements RegleStrategy, Cloneable {

	@Override
	public void debutJeu(Partie partie) {
		for(JoueurStrategy j : partie.getJoueurs()) {
			for(int i = 0; i < 3; i++) {
				Carte c = partie.piocher();
				j.piocher(c);
			}
		}
	}

	@Override
	public void jouer(Partie partie) {

	}
}