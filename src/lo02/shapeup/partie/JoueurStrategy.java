package lo02.shapeup.partie;

import java.util.ArrayList;
import java.util.List;

/**
*	La classe abstraite JoueurStrategy représente un joueur de la partie. Il faudra ensuite spécifier si le joueur est physique ou virtuel.
*	Sa conception est basée sur l'interface Strategy.
*
*	@author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
*	@version 1.0
*	@see lo02.shapeup.partie.JoueurPhysiqueStrategy
*	@see lo02.shapeup.partie.JoueurVirtuelStrategy
*/

public abstract class JoueurStrategy implements PartieElement {

	/**
	 * Spécifie le nom du joueur.
	 */
	protected String nom;

	/**
	 * Spécifie la liste des cartes du joueur.
	 * @see lo02.shapeup.partie.Carte
	 */
	protected List<Carte> jeu;

	/**
	 * Spécifie la carte victorieuse du joueur.
	 */
	protected Carte victorieuse;

	/**
	 * Instancie un nouveau joueur.
	 */
	public JoueurStrategy() {
		this.jeu = new ArrayList<Carte>();
	}
	
	/**
	 * Instancie un nouveau joueur en spécifiant son nom.
	 * @param nom le nom du joueur.
	 */
	public JoueurStrategy(String nom) {
		this.nom = nom;
		this.jeu = new ArrayList<Carte>();
	}
	
	/**
	 * Permet d'accepter un PartieElementVisitor pour ce joueur.
	 * @param visitor le visiteur à accepter
	 * @see lo02.shapeup.partie.PartieElementVisitor
	 */
	public void accept(PartieElementVisitor visitor) {
		visitor.visit(this);
	}
	
	/**
	 * Effectuer l'action de jouer pour le joueur dans la partie. Cette méthode a un comportement différent en fonction du cas JoueurPhysique ou JoueurVirtuel.
	 * @param partie la partie dans laquelle le joueur joue.
	 */
	public abstract void jouer(Partie partie);
	
	/**
	 * Simule l'action de poser une carte pour le joueur, et retire la carte posée de son jeu.
	 * @param c la carte à poser.
	 */
	public void poserCarte(Carte c) {
		this.jeu.remove(c);
	}

	/**
	 * Permet d'obtenir le nom du joueur
	 * @return le nom du joueur.
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Définit le nom du joueur.
	 * @param nom le nom du joueur.
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Définit sa carte victorieuse
	 * @param c la carte piochée
	 * @return la carte victorieuse.
	 */
	public Carte piocherVictorieuse(Carte c) {
		this.victorieuse = c;
		return c;
	}

	/**
	 * Fait piocher une carte au joueur en l'ajoutant à son jeu.
	 * @param c la carte piochée
	 * @return la carte piochée
	 */
	public Carte piocher(Carte c) {
		this.jeu.add(c);
		return c;
	}

	/**
	 * Permet l'affichage de ce joueur sous forme de texte.
	 * @return la représentation de ce joueur en une chaîne de caractères.
	 */
	public String toString() {
		return this.nom;
	}

	/**
	 * Permet d'obtenir le jeu du joueur.
	 * @return le jeu du joueur
	 */
	public List<Carte> getJeu() {
		return this.jeu;
	}
	
	/**
	 * Permet d'obtenir la carte victorieuse du joueur.
	 * @return la carte victorieuse du joueur.
	 */
	public Carte getCarteVictorieuse() {
		return this.victorieuse;
	}
}