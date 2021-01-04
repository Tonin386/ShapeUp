package lo02.shapeup.partie;

import lo02.shapeup.regles.*;
import lo02.shapeup.affichage.*;

public class Partie  {

	public final static int SIMPLE_2JOUEURS = 0;
	public final static int SIMPLE_3JOUEURS = 1;
	public final static int AVANCE_2JOUEURS = 2;
	public final static int AVANCE_3JOUEURS = 3;

	private int modeDeJeu;
	private int tour;
	private Banque banque;
	private Plateau plateau;
	private Joueur[] joueurs;
	private RegleStrategy regleDuJeu;
	private Affichage affichage;
	private Carte carteCachee;
	
	public Partie() {
		
	}

	public Partie(int modeDeJeu, Affichage affichage) {
		this.modeDeJeu = modeDeJeu;
		this.tour = 0;
		this.banque = new Banque();
		this.plateau = new Plateau();
		this.affichage = affichage;

		switch(modeDeJeu) {
			default:
			case 0:
			case 1:
			this.regleDuJeu = new RegleSimpleStrategy();
			break;
			case 2:
			case 3:
			this.regleDuJeu = new RegleAvanceeStrategy();
			break;
		}
		
		this.joueurs = this.affichage.definirJoueurs(modeDeJeu);
		this.carteCachee = this.banque.piocher();
		this.regleDuJeu.debutJeu(this);
	}

	public boolean estFinie() {

		switch (modeDeJeu) {
			case 0:
			case 2:
			if(this.plateau.getCartesPosees() == 15) return true;
			break;
			case 1:
			case 3:
			if(this.plateau.getCartesPosees() == 14) return true;
			break;
		}

		return false;
	}

	public void debutJeu() {
		this.regleDuJeu.debutJeu(this);
	}

	public void jouer() {
		this.affichage.debutTour(this.joueurs[this.tour]);
		this.regleDuJeu.jouer(this);
		this.tour = (this.tour + 1) % this.joueurs.length;
		this.affichage.afficherPlateau(this.plateau);
	}
	
	public Carte piocher() {
		return this.affichage.afficherCartePiochee(this.banque.piocher());
	}
	
	public int getModeDeJeu() {
		return this.modeDeJeu;
	}
	
	public int getTour() {
		return this.tour;
	}

	public Plateau getPlateau() {
		return this.plateau;
	}
	
	public Joueur[] getJoueurs() {
		return this.joueurs;
	}
	
	public Affichage getAffichage() {
		return this.affichage;
	}
	
	public Carte getCarteCachee() {
		return this.carteCachee;
	}
	
	public Banque getBanque() {
		return this.banque;
	}
	
	public void accept(PartieElementVisitor visitor) {
		visitor.visitPartie(this);
	}
	
	public PartieElement[] getElements() {
		PartieElement[] elements =  new PartieElement[5];

		elements[0] = this.getPlateau();
		elements[1] = this.getCarteCachee();
		int i = 0;
		for(Joueur j : this.getJoueurs())
		{
			elements[2 + i] = j;
			i++;
		}
		
		return elements;
	}
}