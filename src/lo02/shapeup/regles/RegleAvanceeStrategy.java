package lo02.shapeup.regles;
import lo02.shapeup.partie.*;

/**
*	La classe RegleAvanceeStrategy permet de d�crire le d�roulement du jeu � la partie si celle-ci suit des r�gles avanc�es. La classe impl�mente l'interface RegleStrategy.
* 
*	@author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
*	@version 1.0
*	@see lo02.shapeup.regles.RegleStrategy
*/
public class RegleAvanceeStrategy implements RegleStrategy {

	/**
	 * Permet d'effectuer les actions du d�but de jeu.
	 * Dans ce cas, faire piocher trois cartes � chaque joueur.
	 * @param partie la partie � commencer
	 */
	@Override
	public void debutJeu(Partie partie) {
		for(JoueurStrategy j : partie.getJoueurs()) {
			for(int i = 0; i < 3; i++) {
				Carte c = partie.piocher();
				j.piocher(c);
			}
		}
	}

	/**
	 * Permet de simuler l'avancement de la partie
	 * @param partie la partie dans laquelle on avance
	 * @deprecated Une mauvaise architecture du projet a rendu cette m�thode inutile dans le cas de la r�gle avanc�e.
	 */
	@Override
	public void jouer(Partie partie) {

	}
}