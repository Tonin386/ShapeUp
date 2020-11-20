package lo02;
import lo02.shapeup.regles.*;

public abstract class Partie  {

	public final static int SIMPLE_2JOUEURS = 0;
	public final static int SIMPLE_3JOUEURS = 1;
	public final static int AVANCE_2JOUEURS = 2;
	public final static int AVANCE_3JOUEURS = 3;

	protected int modeDeJeu;
	protected Banque banque;
	protected Plateau plateau;
	protected Joueur[] joueurs;
	protected Regle regleDuJeu;
	
	public Partie() {
		
	}

	public Partie(int modeDeJeu) {
		this.modeDeJeu = modeDeJeu;
		banque = new Banque();
		plateau = new Plateau();

		switch(modeDeJeu) {
			case 0:
			case 2:
			this.joueurs = new Joueur[1];
			case 1:
			case 3:
			this.joueurs = new Joueur[2];
		}
		
		definirJoueurs();
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

	public abstract void jouer();
	
	public abstract void piocher();
	
	public abstract void poserCarte();
	
	public abstract void deplacerCarte();

	protected abstract void definirJoueurs();
}
