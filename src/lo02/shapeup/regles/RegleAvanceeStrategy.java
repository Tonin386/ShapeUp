package lo02.shapeup.regles;
import lo02.shapeup.partie.*;

/**
*	La classe RegleAvanceeStrategy permet de décrire le déroulement du jeu à la partie si celle-ci suit des règles avancées. La classe implémente l'interface RegleStrategy.
* 
*	@author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
*	@version 1.0
*	@see lo02.shapeup.regles.RegleStrategy
*/
public class RegleAvanceeStrategy implements RegleStrategy {

	/**
	 * Permet d'effectuer les actions du début de jeu.
	 * Dans ce cas, faire piocher trois cartes à chaque joueur.
	 * @param partie la partie à commencer
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
	 * @deprecated Une mauvaise architecture du projet a rendu cette méthode inutile dans le cas de la règle avancée.
	 */
	@Override
	public void jouer(Partie partie) {

	}
}