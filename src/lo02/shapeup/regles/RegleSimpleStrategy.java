package lo02.shapeup.regles;

import lo02.shapeup.partie.*;

/**
*	La classe RegleSimpleStrategy permet de d�crire le d�roulement du jeu � la partie si celle-ci suit des r�gles simples. La classe impl�mente l'interface RegleStrategy.
* 
*	@author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
*	@version 1.0
*	@see lo02.shapeup.regles.RegleStrategy
*/
public class RegleSimpleStrategy implements RegleStrategy {

	/**
	 * Permet d'effectuer les actions du d�but de jeu.
	 * Dans ce cas, faire piocher la carte victorieuse � chaque joueur.
	 * @param partie la partie � commencer
	 */
	@Override
	public void debutJeu(Partie partie) {
		for(JoueurStrategy j : partie.getJoueurs()) {
			Carte c = partie.piocher();
			j.piocherVictorieuse(c);
		}
	}

	/**
	 * Permet de simuler l'avancement de la partie
	 * @param partie la partie dans laquelle on avance
	 */
	@Override
	public void jouer(Partie partie) {

		Carte c = partie.piocher();
		int tour = partie.getTour();
		JoueurStrategy j = partie.getJoueurs().get(tour);
		j.piocher(c);
		partie.emitSignal();
	}
}