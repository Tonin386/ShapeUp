package lo02.shapeup.jeu;
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
	private Regle regleDuJeu;
	private Affichage affichage;
	
	public Partie() {
		
	}

	public Partie(int modeDeJeu, Affichage affichage) {
		this.modeDeJeu = modeDeJeu;
		this.tour = 0;
		this.banque = new Banque();
		this.plateau = new Plateau();
		
		this.joueurs = this.affichage.definirJoueurs(modeDeJeu);
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
	}
	
	public Carte piocher() {
		return this.affichage.afficherCartePiochee(this.joueurs[tour].piocher(this.banque.piocher()));
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
}