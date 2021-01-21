package lo02.shapeup.partie;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import lo02.shapeup.regles.*;


/**
*	La classe Partie représente la partie en elle-même et permet de gérer toutes les actions et son déroulement.
*	Cet objet est observable et notifie des observers de tout changement.
*
*	@author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
*	@version 1.0
*/
@SuppressWarnings("deprecation")
public class Partie extends Observable {

	/**
	 * Représente l'équivalent numérique correspondant au mode de jeu simple avec deux joueurs.
	 */
	public final static int SIMPLE_2JOUEURS = 0;

	/**
	 * Représente l'équivalent numérique correspondant au mode de jeu simple avec trois joueurs.
	 */
	public final static int SIMPLE_3JOUEURS = 1;

	/**
	 * Représente l'équivalent numérique correspondant au mode de jeu avancé avec deux joueurs.
	 */
	public final static int AVANCE_2JOUEURS = 2;

	/**
	 * Représente l'équivalent numérique correspondant au mode de jeu avancé avec trois joueurs.
	 */
	public final static int AVANCE_3JOUEURS = 3;
	
	/**
	 * Spécifie le mode de jeu de la partie.
	 */
	private int modeDeJeu;

	/**
	 * Spécifie quel joueur doit jouer.
	 */
	private int tour;

	/**
	 * Spécifie la banque de carte associée à la partie
	 * @see lo02.shapeup.partie.Banque
	 */
	private Banque banque;

	/**
	 * Spécifie le plateau associé à la partie.
	 * @see lo02.shapeup.partie.Plateau
	 */
	private Plateau plateau;

	/**
	 * Spécifie la liste des joueurs de la partie.
	 * @see lo02.shapeup.partie.JoueurStrategy
	 */
	private List<JoueurStrategy> joueurs;

	/**
	 * Spécifie les règles à suivre pour la partie.
	 * @see lo02.shapeup.regles.ReglesStrategy
	 */
	private RegleStrategy regleDuJeu;

	/**
	 * Spécifie la carte cachée de la partie
	 * @see lo02.shapeup.partie.Carte
	 */
	private Carte carteCachee;

	/**
	 * Spécifie l'index de la carte que le joueur veut jouer.
	 */
	private int indexChoisi;
	
	/**
	 * Spécifie l'avancement de la définition des règles de la partie.
	 */
	private boolean modeRegles = false;

	/**
	 * Spécifie l'avancement de la définition des joueurs de la partie.
	 */
	private boolean modeJoueurs = false;

	/**
	 * Spécifie le nombre de joueurs à définir.
	 */
	private int joueursADefinir = 0;

	/**
	 * Spécifie le prochain joueur à définir.
	 */
	private int joueurADefinir = 0;

	/**
	 * Spécifie à quel état d'avancement est le joueur dans son tour.
	 */
	private int phaseJoueur = 0;

	/**
	 * Spécifie si les règles de la partie ont été définies.
	 */
	private boolean reglesDefinies = false;

	/**
	 * Spécifie si les joueurs de la partie ont été définis.
	 */
	private boolean joueursDefinis = false; //Défition du nom et du type des joueurs

	/**
	 * Spécifie si la partie a commencé.
	 */
	private boolean partieCommencee = false;

	/**
	 * Spécifie si le joueur peut déplacer une carte pendant ce tour.
	 */
	private boolean peutDeplacer = false;

	/**
	 * Spécifie si le joueur peut poser une carte pendant ce tour.
	 */
	private boolean peutPoser = true;

	/**
	 * Spécifie si le joueur doit poser une carte pendant ce tour.
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
	 * Définit le mode de jeu de la partie.
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
	 * Ajoute un joueur à la partie.
	 * @param j le nouveau joueur
	 */
	public void ajouterJoueur(JoueurStrategy j) {
		joueurs.add(j);
	}

	/**
	 * Permet de savoir si la partie est finie selon les règles choisies.
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

	/**
	 * Permet de savoir si le joueur qui joue peut poser une carte.
	 * @return true si le joueur peut poser, false sinon.
	 */
	public boolean peutPoser() {
		return this.peutPoser;
	}

	/**
	 * Permet de savoir si le joueur qui joue peut déplacer une carte.
	 * @return true si le joueur peut déplacer, false sinon
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
	 * Permet de savoir si la partie est commencée.
	 * @return true si la partie a commencé, false sinon.
	 */
	public boolean estCommencee() {
		return this.partieCommencee;
	}

	/**
	 * Effectue les actions nécessaires au commencement de la partie.
	 * Définit la partie comme commencée.
	 */
	public void debutJeu() {
		this.partieCommencee = true;
		this.regleDuJeu.debutJeu(this);
	}

	/**
	 * Effectue les actions nécessaires pour continuer la partie.
	 */
	public void jouer() {
		this.regleDuJeu.jouer(this);
		this.joueurs.get(this.tour).jouer(this);
		this.emitSignal();
	}
	
	/**
	 * Permet de poser une carte sur le plateau.
	 * @param x la position en abscisses de la carte.
	 * @param y la position en ordonnées de la carte.
	 * @param c la carte à poser
	 */
	public void poserCarte(int x, int y, Carte c) {
		this.plateau.poserCarte(x, y, c);
		this.peutPoser = false;
		this.doitPoser = false;
		this.emitSignal();
	}

	/**
	 * Permet de déplacer une carte sur le plateau.
	 * @param x1 l'abscisse d'origine de la carte à déplacer
	 * @param y1 l'ordonnée d'origine de la carte à deplacer
	 * @param x2 la nouvelle abscisse de la carte à deplacer
	 * @param y2 la nouvelle ordonnée de la carte à deplacer
	 */
	public void deplacerCarte(int x1, int y1, int x2, int y2) {
		this.plateau.deplacerCarte(x1, y1, x2, y2);
		this.peutDeplacer = false;
		this.emitSignal();
	}

	/**
	 * Permet d'obtenir tous les déplacements possibles à ce stade de la partie.
	 * @return une liste des déplacements possibles 
	 */
	public List<List<Integer>> getDeplacementsPossibles() {
		return this.plateau.getDeplacementsPossibles();
	}

	/**
	 * Permet d'obtenir tous les positionnements possibles à ce stade de la partie.
	 * @return une liste des positionnements possibles
	 */
	public List<List<Integer>> getPositionnementsPossibles() {
		return this.plateau.getPositionnementsPossibles();
	}

	/**
	 * Permet d'obtenir la carte posée à une certaine position sur le plateau.
	 * @param x l'abscisse de la carte à trouver
	 * @param y l'ordonnée de la carte à trouver
	 * @return la carte trouvée
	 */
	public Carte getCartePosee(int x, int y) {
		return this.plateau.getCartePosee(x, y);
	}

	/**
	 * Permet de savoir combien de cartes sont posées sur le plateau.
	 * @return le nombre de cartes posées.
	 */
	public int getCartesPosees() {
		return this.plateau.getCartesPosees();
	}

	/**
	 * Effectue l'action de piocher une carte.
	 * @return la carte piochée
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
	 * Permet d'obtenir le numéro du joueur qui doit jouer.
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
	 * Permet d'obtenir la carte cachée de la partie.
	 * @return la carte cachée
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
	 * Permet de savoir si les règles de la partie ont été définies.
	 * @return true si les règles sont définies, false sinon
	 */
	public boolean getReglesDefinies() {
		return this.reglesDefinies;
	}

	/**
	 * Permet de savoir si les joueurs de la partie sont définis.
	 * @return true si les joueurs sont définis, false sinon
	 */
	public boolean getJoueursDefinis() {
		return this.joueursDefinis;
	}
	
	/**
	 * Permet d'incrémenter le numéro du prochain joueur à définir.
	 */
	public void signalJoueurDefini() {
		this.joueurADefinir++;
	}

	/**
	 * Permet de signaler que tous les joueurs de la partie sont définis.
	 */
	public void signalJoueursDefinis() {
		this.joueursDefinis = true;
	}

	/**
	 * Permet de signaler que le joueur peut passer à la phase suivante.
	 */
	public void signalPhaseTerminee() {
		this.phaseJoueur++;
	}
	
	/**
	 * Permet de définir le plateau de la partie.
	 * @param plateau le nouveau plateau
	 */
	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
		this.emitSignal();
	}

	/**
	 * Permet de switcher l'avancement de la définition des règles.
	 */
	public void tickModeRegles() {
		this.modeRegles = !this.modeRegles;
		this.emitSignal();
	}

	/**
	 * Permet de switcher l'avancement de la définition des joueurs.
	 */
	public void tickModeJoueurs() {
		this.modeJoueurs = !this.modeJoueurs;
		this.emitSignal();
	}

	/**
	 * Permet de connaître l'avancement de la définition des règles de la partie.
	 * @return l'avancement de la définition des règles
	 */
	public boolean getModeRegles() {
		return this.modeRegles;
	}

	/**
	 * Permet de connaître l'avancement de la définition des joueurs de la partie.
	 * @return l'avancement de la définition des joueurs.
	 */
	public boolean getModeJoueurs() {
		return this.modeJoueurs;
	}

	/**
	 * Permet d'obtenir le nombre de joueurs à définir dans la partie.
	 * @return le nombre de joueurs à définir.
	 */
	public int getJoueursADefinir() {
		return this.joueursADefinir;
	}

	/**
	 * Permet d'obtenir le numéro du prochain joueur à définir.
	 * @return l'index du joueur à définir.
	 */
	public int getJoueurADefinir() {
		return this.joueurADefinir;
	}

	/**
	 * Permet d'obtenir l'avancement de la phase de sélection du joueur.
	 * @return la phase de sélection
	 */
	public int getPhaseJoueur() {
		return this.phaseJoueur;
	}

	/**
	 * Définit l'index que le joueur a choisi dans son jeu	 
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
	 * Permet d'obtenir tous les éléments nécessaires au calcul des scores de la partie
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