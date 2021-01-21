package lo02.shapeup.partie;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import lo02.shapeup.regles.*;


/**
*	La classe Partie repr�sente la partie en elle-m�me et permet de g�rer toutes les actions et son d�roulement.
*	Cet objet est observable et notifie des observers de tout changement.
*
*	@author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
*	@version 1.0
*/
@SuppressWarnings("deprecation")
public class Partie extends Observable {

	/**
	 * Repr�sente l'�quivalent num�rique correspondant au mode de jeu simple avec deux joueurs.
	 */
	public final static int SIMPLE_2JOUEURS = 0;

	/**
	 * Repr�sente l'�quivalent num�rique correspondant au mode de jeu simple avec trois joueurs.
	 */
	public final static int SIMPLE_3JOUEURS = 1;

	/**
	 * Repr�sente l'�quivalent num�rique correspondant au mode de jeu avanc� avec deux joueurs.
	 */
	public final static int AVANCE_2JOUEURS = 2;

	/**
	 * Repr�sente l'�quivalent num�rique correspondant au mode de jeu avanc� avec trois joueurs.
	 */
	public final static int AVANCE_3JOUEURS = 3;
	
	/**
	 * Sp�cifie le mode de jeu de la partie.
	 */
	private int modeDeJeu;

	/**
	 * Sp�cifie quel joueur doit jouer.
	 */
	private int tour;

	/**
	 * Sp�cifie la banque de carte associ�e � la partie
	 * @see lo02.shapeup.partie.Banque
	 */
	private Banque banque;

	/**
	 * Sp�cifie le plateau associ� � la partie.
	 * @see lo02.shapeup.partie.Plateau
	 */
	private Plateau plateau;

	/**
	 * Sp�cifie la liste des joueurs de la partie.
	 * @see lo02.shapeup.partie.JoueurStrategy
	 */
	private List<JoueurStrategy> joueurs;

	/**
	 * Sp�cifie les r�gles � suivre pour la partie.
	 * @see lo02.shapeup.regles.ReglesStrategy
	 */
	private RegleStrategy regleDuJeu;

	/**
	 * Sp�cifie la carte cach�e de la partie
	 * @see lo02.shapeup.partie.Carte
	 */
	private Carte carteCachee;

	/**
	 * Sp�cifie l'index de la carte que le joueur veut jouer.
	 */
	private int indexChoisi;
	
	/**
	 * Sp�cifie l'avancement de la d�finition des r�gles de la partie.
	 */
	private boolean modeRegles = false;

	/**
	 * Sp�cifie l'avancement de la d�finition des joueurs de la partie.
	 */
	private boolean modeJoueurs = false;

	/**
	 * Sp�cifie le nombre de joueurs � d�finir.
	 */
	private int joueursADefinir = 0;

	/**
	 * Sp�cifie le prochain joueur � d�finir.
	 */
	private int joueurADefinir = 0;

	/**
	 * Sp�cifie � quel �tat d'avancement est le joueur dans son tour.
	 */
	private int phaseJoueur = 0;

	/**
	 * Sp�cifie si les r�gles de la partie ont �t� d�finies.
	 */
	private boolean reglesDefinies = false;

	/**
	 * Sp�cifie si les joueurs de la partie ont �t� d�finis.
	 */
	private boolean joueursDefinis = false; //D�fition du nom et du type des joueurs

	/**
	 * Sp�cifie si la partie a commenc�.
	 */
	private boolean partieCommencee = false;

	/**
	 * Sp�cifie si le joueur peut d�placer une carte pendant ce tour.
	 */
	private boolean peutDeplacer = false;

	/**
	 * Sp�cifie si le joueur peut poser une carte pendant ce tour.
	 */
	private boolean peutPoser = true;

	/**
	 * Sp�cifie si le joueur doit poser une carte pendant ce tour.
	 */
	private boolean doitPoser = true;
	
	/**
	 * Instancie une nouvelle partie
	 */
	public Partie() {
		this.modeDeJeu = 0;
		this.tour = 0;
		this.banque = new Banque();
		this.plateau = new Plateau();
		this.joueurs = new ArrayList<JoueurStrategy>();
		
		this.carteCachee = this.banque.piocher();
	}
	
	/**
	 * D�finit le mode de jeu de la partie.
	 * @param modeDeJeu le mode de jeu
	 */
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

	/**
	 * Ajoute un joueur � la partie.
	 * @param j le nouveau joueur
	 */
	public void ajouterJoueur(JoueurStrategy j) {
		joueurs.add(j);
	}

	/**
	 * Permet de savoir si la partie est finie selon les r�gles choisies.
	 * @return true si la partie est finie, false sinon.
	 */
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

	/**
	 * Permet de finaliser un tour, et d'en commencer un nouveau.
	 */
	public void finirTour() {
		this.peutPoser = true;
		if(this.plateau.getCartesPosees() > 1) this.peutDeplacer = true;
		this.doitPoser = true;
		if(this.regleDuJeu instanceof RegleAvanceeStrategy) {
			Carte c;
			JoueurStrategy j = this.joueurs.get(this.tour);

			if(this.banque.getIndex() < 1) {
			//Ne rien faire car il n'y a plus de cartes � piocher.
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

	/**
	 * Permet de savoir si le joueur qui joue peut poser une carte.
	 * @return true si le joueur peut poser, false sinon.
	 */
	public boolean peutPoser() {
		return this.peutPoser;
	}

	/**
	 * Permet de savoir si le joueur qui joue peut d�placer une carte.
	 * @return true si le joueur peut d�placer, false sinon
	 */
	public boolean peutDeplacer() {
		return this.peutDeplacer;
	}

	/**
	 * Permet de savoir si le joueur qui joue doit poser une carte avant de finir son tour.
	 * @return true si le joueur doit poser, false sinon.
	 */
	public boolean doitPoser() {
		return this.doitPoser;
	}

	/**
	 * Permet de savoir si la partie est commenc�e.
	 * @return true si la partie a commenc�, false sinon.
	 */
	public boolean estCommencee() {
		return this.partieCommencee;
	}

	/**
	 * Effectue les actions n�cessaires au commencement de la partie.
	 * D�finit la partie comme commenc�e.
	 */
	public void debutJeu() {
		this.partieCommencee = true;
		this.regleDuJeu.debutJeu(this);
	}

	/**
	 * Effectue les actions n�cessaires pour continuer la partie.
	 */
	public void jouer() {
		this.regleDuJeu.jouer(this);
		this.joueurs.get(this.tour).jouer(this);
		this.emitSignal();
	}
	
	/**
	 * Permet de poser une carte sur le plateau.
	 * @param x la position en abscisses de la carte.
	 * @param y la position en ordonn�es de la carte.
	 * @param c la carte � poser
	 */
	public void poserCarte(int x, int y, Carte c) {
		this.plateau.poserCarte(x, y, c);
		this.peutPoser = false;
		this.doitPoser = false;
		this.emitSignal();
	}

	/**
	 * Permet de d�placer une carte sur le plateau.
	 * @param x1 l'abscisse d'origine de la carte � d�placer
	 * @param y1 l'ordonn�e d'origine de la carte � deplacer
	 * @param x2 la nouvelle abscisse de la carte � deplacer
	 * @param y2 la nouvelle ordonn�e de la carte � deplacer
	 */
	public void deplacerCarte(int x1, int y1, int x2, int y2) {
		this.plateau.deplacerCarte(x1, y1, x2, y2);
		this.peutDeplacer = false;
		this.emitSignal();
	}

	/**
	 * Permet d'obtenir tous les d�placements possibles � ce stade de la partie.
	 * @return une liste des d�placements possibles 
	 */
	public List<List<Integer>> getDeplacementsPossibles() {
		return this.plateau.getDeplacementsPossibles();
	}

	/**
	 * Permet d'obtenir tous les positionnements possibles � ce stade de la partie.
	 * @return une liste des positionnements possibles
	 */
	public List<List<Integer>> getPositionnementsPossibles() {
		return this.plateau.getPositionnementsPossibles();
	}

	/**
	 * Permet d'obtenir la carte pos�e � une certaine position sur le plateau.
	 * @param x l'abscisse de la carte � trouver
	 * @param y l'ordonn�e de la carte � trouver
	 * @return la carte trouv�e
	 */
	public Carte getCartePosee(int x, int y) {
		return this.plateau.getCartePosee(x, y);
	}

	/**
	 * Permet de savoir combien de cartes sont pos�es sur le plateau.
	 * @return le nombre de cartes pos�es.
	 */
	public int getCartesPosees() {
		return this.plateau.getCartesPosees();
	}

	/**
	 * Effectue l'action de piocher une carte.
	 * @return la carte pioch�e
	 */
	public Carte piocher() {
		Carte c = this.banque.piocher();
		this.emitSignal();
		return c;
	}
	
	/**
	 * Permet d'obtenir le mode de jeu de la partie.
	 * @return le mode de jeu
	 */
	public int getModeDeJeu() {
		return this.modeDeJeu;
	}
	
	/**
	 * Permet d'obtenir le num�ro du joueur qui doit jouer.
	 * @return le tour de la partie.
	 */
	public int getTour() {
		return this.tour;
	}

	/**
	 * Permet d'obtenir le plateau de la partie.
	 * @return le plateau de la partie
	 */
	public Plateau getPlateau() {
		return this.plateau;
	}
	
	/**
	 * Permet d'obtenir la liste des joueurs dans la partie.
	 * @return la liste des joueurs de la partie
	 */
	public List<JoueurStrategy> getJoueurs() {
		return this.joueurs;
	}
	
	/**
	 * Permet d'obtenir la carte cach�e de la partie.
	 * @return la carte cach�e
	 */
	public Carte getCarteCachee() {
		return this.carteCachee;
	}
	
	/**
	 * Permet d'obtenir la banque de la partie
	 * @return la banque de la partie.
	 */
	public Banque getBanque() {
		return this.banque;
	}

	/**
	 * Permet de savoir si les r�gles de la partie ont �t� d�finies.
	 * @return true si les r�gles sont d�finies, false sinon
	 */
	public boolean getReglesDefinies() {
		return this.reglesDefinies;
	}

	/**
	 * Permet de savoir si les joueurs de la partie sont d�finis.
	 * @return true si les joueurs sont d�finis, false sinon
	 */
	public boolean getJoueursDefinis() {
		return this.joueursDefinis;
	}
	
	/**
	 * Permet d'incr�menter le num�ro du prochain joueur � d�finir.
	 */
	public void signalJoueurDefini() {
		this.joueurADefinir++;
	}

	/**
	 * Permet de signaler que tous les joueurs de la partie sont d�finis.
	 */
	public void signalJoueursDefinis() {
		this.joueursDefinis = true;
	}

	/**
	 * Permet de signaler que le joueur peut passer � la phase suivante.
	 */
	public void signalPhaseTerminee() {
		this.phaseJoueur++;
	}
	
	/**
	 * Permet de d�finir le plateau de la partie.
	 * @param plateau le nouveau plateau
	 */
	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
		this.emitSignal();
	}

	/**
	 * Permet de switcher l'avancement de la d�finition des r�gles.
	 */
	public void tickModeRegles() {
		this.modeRegles = !this.modeRegles;
		this.emitSignal();
	}

	/**
	 * Permet de switcher l'avancement de la d�finition des joueurs.
	 */
	public void tickModeJoueurs() {
		this.modeJoueurs = !this.modeJoueurs;
		this.emitSignal();
	}

	/**
	 * Permet de conna�tre l'avancement de la d�finition des r�gles de la partie.
	 * @return l'avancement de la d�finition des r�gles
	 */
	public boolean getModeRegles() {
		return this.modeRegles;
	}

	/**
	 * Permet de conna�tre l'avancement de la d�finition des joueurs de la partie.
	 * @return l'avancement de la d�finition des joueurs.
	 */
	public boolean getModeJoueurs() {
		return this.modeJoueurs;
	}

	/**
	 * Permet d'obtenir le nombre de joueurs � d�finir dans la partie.
	 * @return le nombre de joueurs � d�finir.
	 */
	public int getJoueursADefinir() {
		return this.joueursADefinir;
	}

	/**
	 * Permet d'obtenir le num�ro du prochain joueur � d�finir.
	 * @return l'index du joueur � d�finir.
	 */
	public int getJoueurADefinir() {
		return this.joueurADefinir;
	}

	/**
	 * Permet d'obtenir l'avancement de la phase de s�lection du joueur.
	 * @return la phase de s�lection
	 */
	public int getPhaseJoueur() {
		return this.phaseJoueur;
	}

	/**
	 * D�finit l'index que le joueur a choisi dans son jeu	 
	 * @param i l'index choisi
	 */
	public void setIndexChoisi(int i) {
		this.indexChoisi = i;
		this.emitSignal();
	}

	/**
	 * Permet d'obtenir l'index choisi par le joueur dans son jeu
	 * @return l'index choisi
	 */
	public int getIndexChoisi() {
		return this.indexChoisi;
	}

	/**
	 * Permet de notifier les observers d'un changement.
	 */
	public void emitSignal() {
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Permet d'obtenir tous les �l�ments n�cessaires au calcul des scores de la partie
	 * @return les PartieElements de la partie.
	 */
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