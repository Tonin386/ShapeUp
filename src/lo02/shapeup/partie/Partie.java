package lo02.shapeup.partie;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import lo02.shapeup.regles.*;

@SuppressWarnings("deprecation")
public class Partie extends Observable {

	public final static int SIMPLE_2JOUEURS = 0;
	public final static int SIMPLE_3JOUEURS = 1;
	public final static int AVANCE_2JOUEURS = 2;
	public final static int AVANCE_3JOUEURS = 3;
	
	private int modeDeJeu;
	private int tour;
	private Banque banque;
	private Plateau plateau;
	private List<JoueurStrategy> joueurs;
	private RegleStrategy regleDuJeu;
	private Carte carteCachee;
	private int indexChoisi;
	
	private boolean modeRegles = false;
	private boolean modeJoueurs = false;

	private int joueursADefinir = 0;
	private int joueurADefinir = 0;
	private int phaseJoueur = 0;

	private boolean reglesDefinies = false; //Definition du nombre de joueurs et des règles à suivre
	private boolean joueursDefinis = false; //Défition du nom et du type des joueurs
	private boolean partieCommencee = false;

	private boolean peutDeplacer = false;
	private boolean peutPoser = true;
	private boolean doitPoser = true;
	
	
	public Partie() {
		this.modeDeJeu = 0;
		this.tour = 0;
		this.banque = new Banque();
		this.plateau = new Plateau();
		this.joueurs = new ArrayList<JoueurStrategy>();
		
		this.carteCachee = this.banque.piocher();
	}
	
	public void setModeDeJeu(int modeDeJeu) {
		this.modeDeJeu = modeDeJeu;
		this.reglesDefinies = true;

		switch(this.modeDeJeu) {
			default:
			case 0:
			this.regleDuJeu = new RegleSimpleStrategy();
			this.joueursADefinir = 2;
			break;
			case 1:
			this.regleDuJeu = new RegleSimpleStrategy();
			this.joueursADefinir = 3;
			break;
			case 2:
			this.regleDuJeu = new RegleAvanceeStrategy();
			this.joueursADefinir = 2;
			break;
			case 3:
			this.regleDuJeu = new RegleAvanceeStrategy();
			this.joueursADefinir = 3;
			break;
		}
		this.emitSignal();
	}

	public void ajouterJoueur(JoueurStrategy j) {
		joueurs.add(j);
	}

	public boolean estFinie() {

		switch (modeDeJeu) {
			case 0:
			if(this.plateau.getCartesPosees() == 15) {
				return true;
			}
			break;
			case 2:
			if(this.plateau.getCartesPosees() == 15) {
				for(JoueurStrategy j : this.joueurs) {
					j.piocherVictorieuse(j.getJeu().get(0));
				}
				return true;
			}
			break;
			case 1:
			if(this.plateau.getCartesPosees() == 14) {
				return true;
			}
			break;
			case 3:
			if(this.plateau.getCartesPosees() == 14) {
				for(JoueurStrategy j : this.joueurs) {
					j.piocherVictorieuse(j.getJeu().get(0));
				}
				return true;
			}
			break;
		}

		return false;
	}

	public void finirTour() {
		this.peutPoser = true;
		if(this.plateau.getCartesPosees() > 1) this.peutDeplacer = true;
		this.doitPoser = true;
		if(this.regleDuJeu instanceof RegleAvanceeStrategy) {
			Carte c;
			JoueurStrategy j = this.joueurs.get(this.tour);

			if(this.banque.getIndex() < 1) {
			//Ne rien faire car il n'y a plus de cartes à piocher.
			}
			else {
				c = this.piocher();
				j.piocher(c);
			}
		}
		this.indexChoisi = 0;
		this.tour = (this.tour + 1) % this.joueurs.size();
		this.emitSignal();
		if(!this.estFinie()) this.jouer();
	}

	public boolean peutPoser() {
		return this.peutPoser;
	}

	public boolean peutDeplacer() {
		return this.peutDeplacer;
	}

	public boolean doitPoser() {
		return this.doitPoser;
	}

	public boolean estCommencee() {
		return this.partieCommencee;
	}

	public void debutJeu() {
		this.partieCommencee = true;
		this.regleDuJeu.debutJeu(this);
	}

	public void jouer() {
		this.regleDuJeu.jouer(this);
		this.joueurs.get(this.tour).jouer(this);
		this.emitSignal();
	}
	
	public void poserCarte(int x, int y, Carte c) {
		this.plateau.poserCarte(x, y, c);
		this.peutPoser = false;
		this.doitPoser = false;
		this.emitSignal();
	}

	public void deplacerCarte(int x1, int y1, int x2, int y2) {
		this.plateau.deplacerCarte(x1, y1, x2, y2);
		this.peutDeplacer = false;
		this.emitSignal();
	}

	public List<List<Integer>> getDeplacementsPossibles() {
		return this.plateau.getDeplacementsPossibles();
	}

	public List<List<Integer>> getPositionnementsPossibles() {
		return this.plateau.getPositionnementsPossibles();
	}

	public Carte getCartePosee(int x, int y) {
		return this.plateau.getCartePosee(x, y);
	}

	public int getCartesPosees() {
		return this.plateau.getCartesPosees();
	}

	public Carte piocher() {
		Carte c = this.banque.piocher();
		this.emitSignal();
		return c;
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
	
	public List<JoueurStrategy> getJoueurs() {
		return this.joueurs;
	}
	
	public Carte getCarteCachee() {
		return this.carteCachee;
	}
	
	public Banque getBanque() {
		return this.banque;
	}

	public boolean getReglesDefinies() {
		return this.reglesDefinies;
	}

	public boolean getJoueursDefinis() {
		return this.joueursDefinis;
	}
	
	public void signalJoueurDefini() {
		this.joueurADefinir++;
	}

	public void signalJoueursDefinis() {
		this.joueursDefinis = true;
	}

	public void signalPhaseTerminee() {
		this.phaseJoueur++;
	}
	
	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
		this.emitSignal();
	}

	public void tickModeRegles() {
		this.modeRegles = !this.modeRegles;
		this.emitSignal();
	}

	public void tickModeJoueurs() {
		this.modeJoueurs = !this.modeJoueurs;
		this.emitSignal();
	}

	public boolean getModeRegles() {
		return this.modeRegles;
	}

	public boolean getModeJoueurs() {
		return this.modeJoueurs;
	}

	public int getJoueursADefinir() {
		return this.joueursADefinir;
	}

	public int getJoueurADefinir() {
		return this.joueurADefinir;
	}

	public int getPhaseJoueur() {
		return this.phaseJoueur;
	}

	public void setIndexChoisi(int i) {
		this.indexChoisi = i;
		this.emitSignal();
	}

	public int getIndexChoisi() {
		return this.indexChoisi;
	}

	public void emitSignal() {
		this.setChanged();
		this.notifyObservers();
	}

	public void accept(PartieElementVisitor visitor) {
		visitor.visitPartie(this);
	}
	
	public PartieElement[] getElements() {
		PartieElement[] elements =  new PartieElement[5];

		elements[0] = this.getPlateau();
		int i = 0;
		for(JoueurStrategy j : this.getJoueurs()) {
			elements[1 + i] = j;
			i++;
		}
		
		return elements;
	}
}