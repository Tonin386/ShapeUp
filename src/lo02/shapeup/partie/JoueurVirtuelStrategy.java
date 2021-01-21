package lo02.shapeup.partie;

import java.util.List;
import java.lang.Math;

/**
*	La classe JoueurVirtuelStrategy hérite de la classe abstraite JoueurStrategy, et représente un joueur de la partie contrôlé par un ordinateur.
*
*	@author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
*	@version 1.0
*	@see lo02.shapeup.partie.JoueurStrategy
*/
public class JoueurVirtuelStrategy extends JoueurStrategy {

	/**
	 * Instancie un nouveau joueur virtuel.
	 */
	public JoueurVirtuelStrategy() {
		super();
	}

	/**
	 * Instancie un nouveau joueur virtuel en spécifiant son nom.
	 * @param nom le nom du joueur.
	 */
	public JoueurVirtuelStrategy(String nom) {
		super(nom);
	}

	/**
	 * Permet au joueur de jouer son tour.
	 * Choisit automatiquement les actions à effectuer.
	 * @param partie la partie dans laquelle le joueur joue.
	 */
	public void jouer(Partie partie) {

		if(partie.peutDeplacer()) {
			List<Integer> dep = this.choisirDeplacement(partie);
			partie.deplacerCarte(dep.get(0), dep.get(1), dep.get(2), dep.get(3));
		}

		if(partie.peutPoser()) {
			List<Integer> pos = this.choisirPosition(partie);
			Carte c = this.choisirCarte(partie);
			partie.poserCarte(pos.get(0), pos.get(1), c);
			this.poserCarte(c);
		}

		partie.finirTour();
	}

	/**
	 * Choisit aléatoirement une carte à jouer dans son jeu.
	 * @param partie la partie dans laquelle l'ordinateur joue.
	 * @return la carte jouée.
	 */
	private Carte choisirCarte(Partie partie) {
		return this.jeu.get((int) (Math.random() * this.jeu.size()));
	}

	/**
	 * Choisit aléatoirement une position où poser une carte.
	 * @param partie la partie dans laquelle l'ordinateur joue.
	 * @return la position choisie.
	 */
	private List<Integer> choisirPosition(Partie partie) {
		return partie.getPositionnementsPossibles().get((int) (Math.random() * partie.getPositionnementsPossibles().size()));
	}

	/**
	 * Choisit aléatoirement le déplacement à effectuer.
	 * @param partie la partie dans laquelle l'ordinateur joue.
	 * @return le déplacement choisi.
	 */
	private List<Integer> choisirDeplacement(Partie partie) {
		return partie.getDeplacementsPossibles().get((int) (Math.random() * partie.getDeplacementsPossibles().size()));
	}
}