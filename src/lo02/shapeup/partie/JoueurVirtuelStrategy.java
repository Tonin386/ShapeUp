package lo02.shapeup.partie;

import java.util.List;
import java.lang.Math;

/**
*	La classe JoueurVirtuelStrategy h�rite de la classe abstraite JoueurStrategy, et repr�sente un joueur de la partie contr�l� par un ordinateur.
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
	 * Instancie un nouveau joueur virtuel en sp�cifiant son nom.
	 * @param nom le nom du joueur.
	 */
	public JoueurVirtuelStrategy(String nom) {
		super(nom);
	}

	/**
	 * Permet au joueur de jouer son tour.
	 * Choisit automatiquement les actions � effectuer.
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
	 * Choisit al�atoirement une carte � jouer dans son jeu.
	 * @param partie la partie dans laquelle l'ordinateur joue.
	 * @return la carte jou�e.
	 */
	private Carte choisirCarte(Partie partie) {
		return this.jeu.get((int) (Math.random() * this.jeu.size()));
	}

	/**
	 * Choisit al�atoirement une position o� poser une carte.
	 * @param partie la partie dans laquelle l'ordinateur joue.
	 * @return la position choisie.
	 */
	private List<Integer> choisirPosition(Partie partie) {
		return partie.getPositionnementsPossibles().get((int) (Math.random() * partie.getPositionnementsPossibles().size()));
	}

	/**
	 * Choisit al�atoirement le d�placement � effectuer.
	 * @param partie la partie dans laquelle l'ordinateur joue.
	 * @return le d�placement choisi.
	 */
	private List<Integer> choisirDeplacement(Partie partie) {
		return partie.getDeplacementsPossibles().get((int) (Math.random() * partie.getDeplacementsPossibles().size()));
	}
}