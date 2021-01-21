package lo02.shapeup.partie;

/**
*	La classe JoueurPhysiqueStrategy hérite de la classe abstraite JoueurStrategy, et représente un joueur de la partie contrôlé par un humain.
*
*	@author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
*	@version 1.0
*	@see lo02.shapeup.partie.JoueurStrategy
*/
public class JoueurPhysiqueStrategy extends JoueurStrategy {

	/**
	 * Instancie un nouveau joueur physique.
	 */
	public JoueurPhysiqueStrategy() {
		super();
	}

	/**
	 * Instancie un nouveau joueur physique en spécifiant son nom.
	 * @param nom le nom du joueur.
	 */
	public JoueurPhysiqueStrategy(String nom) {
		super(nom);
	}

	/**
	 * Permet au joueur de jouer son tour.
	 * @param partie la partie dans laquelle le joueur joue.
	 * @deprecated Une mauvaise architecture du projet a rendu cette méthode inutile dans le cas du joueur physique.
	 */
	public void jouer(Partie partie) {
		
	}
}