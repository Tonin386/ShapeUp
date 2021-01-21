package lo02.shapeup.partie;

import java.util.ArrayList;

/**
*	L'interface PartieElementVisitor permet de visiter les éléments de partie afin de calculer les scores de cette partie.
*
*	@author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
*	@version 1.0
*	@see lo02.shapeup.partie.PartieElementCalcVisitor
*/
public interface PartieElementVisitor {
	/**
	 * Permet de visiter un plateau d'une partie.
	 * @param plateau le plateau à visiter
	 */
	void visit(Plateau plateau);

	/**
	 * Permet de visiter le joueur d'une partie.
	 * @param joueur le joueur à visiter
	 */
	void visit(JoueurStrategy joueur);

	/**
	 * Permet le calcul des scores d'une partie en fonction des éléments précedemment visités.
	 * @return une liste de taille égale au nombre de joueurs dans la partie, contenant le score de chaque joueur
	 */
	ArrayList<Integer> obtenirScoresPartie();
}
