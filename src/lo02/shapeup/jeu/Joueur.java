package lo02.shapeup.jeu;

import java.util.ArrayList;
import java.util.List;

public abstract class Joueur {

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