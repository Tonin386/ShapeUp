package lo02.shapeup.partie;

import java.util.ArrayList;
import java.util.List;

//attribut interface Strategy -> joueur virtuel débutant, avancé et joueur réel

public abstract class Joueur implements PartieElement {

	protected String nom;
	protected List<Carte> jeu;
	protected Carte victorieuse;

	public Joueur() {
		this.jeu = new ArrayList<Carte>();
	}
	
	public Joueur(String nom) {
		this.nom = nom;
		this.jeu = new ArrayList<Carte>();
	}
	
	public void accept(PartieElementVisitor visitor) {
		visitor.visit(this);
	}
	
	public void poserCarte(Carte c) {
		this.jeu.remove(c);
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Carte piocherVictorieuse(Carte c) {
		this.victorieuse = c;
		return c;
	}

	public Carte piocher(Carte c) {
		this.jeu.add(c);
		return c;
	}

	public String toString() {
		return this.nom;
	}

	public List<Carte> getJeu() {
		return this.jeu;
	}
	
	public Carte getCarteVictorieuse() {
		return this.victorieuse;
	}
	

}