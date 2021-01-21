package lo02.shapeup.partie;

import java.util.ArrayList;
import java.util.List;

/**
*	La classe abstraite JoueurStrategy repr�sente un joueur de la partie. Il faudra ensuite sp�cifier si le joueur est physique ou virtuel.
*	Sa conception est bas�e sur l'interface Strategy.
*
*	@author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
*	@version 1.0
*	@see lo02.shapeup.partie.JoueurPhysiqueStrategy
*	@see lo02.shapeup.partie.JoueurVirtuelStrategy
*/

public abstract class JoueurStrategy implements PartieElement {

	/**
	 * Sp�cifie le nom du joueur.
	 */
	protected String nom;

	/**
	 * Sp�cifie la liste des cartes du joueur.
	 * @see lo02.shapeup.partie.Carte
	 */
	protected List<Carte> jeu;

	/**
	 * Sp�cifie la carte victorieuse du joueur.
	 */
	protected Carte victorieuse;

	/**
	 * Instancie un nouveau joueur.
	 */
	public JoueurStrategy() {
		this.jeu = new ArrayList<Carte>();
	}
	
	/**
	 * Instancie un nouveau joueur en sp�cifiant son nom.
	 * @param nom le nom du joueur.
	 */
	public JoueurStrategy(String nom) {
		this.nom = nom;
		this.jeu = new ArrayList<Carte>();
	}
	
	/**
	 * Permet d'accepter un PartieElementVisitor pour ce joueur.
	 * @param visitor le visiteur � accepter
	 * @see lo02.shapeup.partie.PartieElementVisitor
	 */
	public void accept(PartieElementVisitor visitor) {
		visitor.visit(this);
	}
	
	/**
	 * Effectuer l'action de jouer pour le joueur dans la partie. Cette m�thode a un comportement diff�rent en fonction du cas JoueurPhysique ou JoueurVirtuel.
	 * @param partie la partie dans laquelle le joueur joue.
	 */
	public abstract void jouer(Partie partie);
	
	/**
	 * Simule l'action de poser une carte pour le joueur, et retire la carte pos�e de son jeu.
	 * @param c la carte � poser.
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
	 * D�finit le nom du joueur.
	 * @param nom le nom du joueur.
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * D�finit sa carte victorieuse
	 * @param c la carte pioch�e
	 * @return la carte victorieuse.
	 */
	public Carte piocherVictorieuse(Carte c) {
		this.victorieuse = c;
		return c;
	}

	/**
	 * Fait piocher une carte au joueur en l'ajoutant � son jeu.
	 * @param c la carte pioch�e
	 * @return la carte pioch�e
	 */
	public Carte piocher(Carte c) {
		this.jeu.add(c);
		return c;
	}

	/**
	 * Permet l'affichage de ce joueur sous forme de texte.
	 * @return la repr�sentation de ce joueur en une cha�ne de caract�res.
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