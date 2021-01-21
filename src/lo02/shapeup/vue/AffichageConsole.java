package lo02.shapeup.vue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import lo02.shapeup.partie.*;

/**
 *	La classe AffichageConsole permet de contr�ler la partie � travers une interface textuelle, en parall�le de l'interface graphique. Elle impl�mente l'interface Runnable.
 *	Cette classe est une vue dans l'architecture MVC.
 * 	
 * @author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
 * @version 1.0
 */
public class AffichageConsole implements Runnable {

	/**
	 * D�finit la commande correspondant au commencement de la partie.
	 */
	public static String COMMENCER_PARTIE = "start";

	/**
	 * D�finit la commande correspondant au choix des r�gles de la partie.
	 */
	public static String CHOISIR_REGLES = "R";

	/**
	 * D�finit la commande correspondant au choix des joueurs de la partie.
	 */
	public static String DEFINIR_JOUEURS = "J";

	/**
	 * D�finit la commande correspondant au d�placement d'une carte.
	 */
	public static String DEPLACER = "D";

	/**
	 * D�finit la commande correspondant au choix d'une carte.
	 */
	public static String CHOISIR_CARTE = "C";

	/**
	 * D�finit la commande correspondant � la pose d'une carte.
	 */
	public static String POSER = "P";

	/**
	 * D�finit la commande correspondant � la fin d'un tour.
	 */
	public static String FINIR_TOUR = "F";

	/**
	 * D�finit la commande correspondant � l'arr�t de l'ex�cution du programme.
	 */
	public static String QUITTER = "quit";

	/**
	 * D�finit un caract�re pour montrer que l'on attend une entr�e utilisateur.
	 */
	public static String PROMPT = ">";
	
	/**
	 * Sp�cifie la partie contr�l�e � travers l'interface console.
	 */
	private Partie partie;

	/**
	 * Sp�cifie si le joueur doit choisir son d�placement dans la console.
	 */
	private boolean choisirDeplacement = false;

	/**
	 * Sp�cifie si le joueur doit choisir sa position dans la console.
	 */
	private boolean choisirPosition = false;

	/**
	 * Sp�cifie si le joueur doit choisir l'index de la carte de son jeu choisie dans la console.
	 */
	private boolean choisirIndex = false;

	/**
	 * Instancie un nouvel AffichageConsole pour une partie.
	 * @param partie la partie jouable � travers la console.
	 */
	public AffichageConsole(Partie partie) {
		this.partie = partie;
		Thread t = new Thread(this);
		t.start();
	}

	/**
	 * Permet le contr�le entier de la partie dans la console en parall�le de la vue graphique.
	 */
	@Override
	public void run() {

		String saisie = null;
		boolean quitter = false;

		System.out.println("Options possibles :");
		System.out.println(AffichageConsole.CHOISIR_REGLES + " : choisir les r�gles.");
		System.out.println(AffichageConsole.DEFINIR_JOUEURS + " : choisir le nom des joueurs.");
		System.out.println(AffichageConsole.COMMENCER_PARTIE + " : commencer la partie. (Vous devez avoir choisi les r�gles et le nom des joueurs au pr�alable.)");
		System.out.println(AffichageConsole.CHOISIR_CARTE + " : Choisir la carte � jouer.");
		System.out.println(AffichageConsole.POSER + " : poser une carte");
		System.out.println(AffichageConsole.DEPLACER + " : d�placer une carte.");
		System.out.println(AffichageConsole.FINIR_TOUR + " : finir son tour.");
		System.out.println(AffichageConsole.QUITTER + " pour quitter.");

		do {
			saisie = this.lireChaine();

			if(this.partie.getModeRegles() && saisie != null && this.partie.getReglesDefinies() == false) {
				if(saisie.equals("0")) {
					this.partie.setModeDeJeu(0);
					this.partie.tickModeRegles();
				}
				else if(saisie.equals("1")) {
					this.partie.setModeDeJeu(1);
					this.partie.tickModeRegles();
				}
				else if(saisie.equals("2")) {
					this.partie.setModeDeJeu(2);
					this.partie.tickModeRegles();
				}
				else if(saisie.equals("3")) {
					this.partie.setModeDeJeu(3);
					this.partie.tickModeRegles();
				}
				else {
					System.out.println("Veuillez choisir un mode de jeu valide.");
				}
			}
			else if(this.partie.getModeJoueurs() && saisie != null && this.partie.getJoueursDefinis() == false) {
				if(this.partie.getPhaseJoueur() % 2 == 0) {
					JoueurStrategy j;
					if(saisie.equals("0")) {
						j = new JoueurPhysiqueStrategy();
						this.partie.ajouterJoueur(j);
						this.partie.signalPhaseTerminee();
						System.out.println("Vous pouvez d�sormais choisir le nom de ce joueur.");
					}
					else if(saisie.equals("1")) {
						j = new JoueurVirtuelStrategy();
						this.partie.ajouterJoueur(j);
						this.partie.signalPhaseTerminee();
						System.out.println("Vous pouvez d�sormais choisir le nom de ce joueur.");
					}
					else {
						System.out.println("Veuillez entrer une option correcte.");
					}
				}
				else {
					JoueurStrategy j = this.partie.getJoueurs().get(this.partie.getJoueurADefinir());
					j.setNom(saisie);
					this.partie.signalJoueurDefini();
					this.partie.signalPhaseTerminee();
					if(this.partie.getJoueurADefinir() >= this.partie.getJoueursADefinir()) {
						this.partie.tickModeJoueurs();
						this.partie.signalJoueursDefinis();
					}
					else {
						System.out.println("Entrer le type du joueur : ");
						System.out.println("0 - Joueur physique.");
						System.out.println("1 - Joueur virtuel.");
					}
				}
			}
			else if(this.choisirIndex && saisie != null) {
				if(Integer.parseInt(saisie) < this.partie.getJoueurs().get(this.partie.getTour()).getJeu().size()) {
					this.partie.setIndexChoisi(Integer.parseInt(saisie));
				}
				this.choisirIndex = false;
			}
			else if(this.choisirPosition && saisie != null) {
				if(Integer.parseInt(saisie) < this.partie.getPositionnementsPossibles().size()) {
					List<Integer> position = this.partie.getPositionnementsPossibles().get(Integer.parseInt(saisie));
					this.partie.poserCarte(position.get(0), position.get(1), this.partie.getJoueurs().get(this.partie.getTour()).getJeu().get(this.partie.getIndexChoisi()));
				}
				this.choisirPosition = false;
			}
			else if(this.choisirDeplacement && saisie != null) {
				if(Integer.parseInt(saisie) < this.partie.getDeplacementsPossibles().size()) {
					List<Integer> dep = this.partie.getDeplacementsPossibles().get(Integer.parseInt(saisie));
					this.partie.deplacerCarte(dep.get(0), dep.get(1), dep.get(2), dep.get(3));
				}
				this.choisirDeplacement = false;
			}
			else if(this.partie.estCommencee() && saisie != null) {
				if(saisie.equals(AffichageConsole.QUITTER) == true) {
					quitter = true;		    
				}
				else if(saisie.equals(AffichageConsole.CHOISIR_CARTE) == true) {
					for(int i = 0; i < this.partie.getJoueurs().get(this.partie.getTour()).getJeu().size(); i++) {
						List<Carte> jeu = this.partie.getJoueurs().get(this.partie.getTour()).getJeu();
						System.out.println(i + " - " + jeu.get(i).toString());
					}
					this.choisirIndex = true;
				}
				else if(saisie.equals(AffichageConsole.POSER) == true) {
					if(this.partie.peutPoser()) {
						int i = 0;
						for(List<Integer> pos : this.partie.getPositionnementsPossibles()) {
							System.out.println(i + " - " + pos.get(0) + ", " + pos.get(1));
							i++;
						}
						this.choisirPosition = true;
					}
					else {
						System.out.println("Vous ne pouvez plus poser de carte ce tour.");
					}
				}
				else if(saisie.equals(AffichageConsole.DEPLACER) == true) {
					if(this.partie.peutDeplacer()) {
						int i = 0;
						for(List<Integer> dep : this.partie.getDeplacementsPossibles()) {
							System.out.println(i + " - " + dep.get(0) + ", " + dep.get(1) + " -> " + dep.get(2) + ", " + dep.get(3));
							i++;
						}
						this.choisirDeplacement = true;
					}
					else {
						System.out.println("Vous ne pouvez plus d�placer de carte ce tour.");
					}

				}
				else if(saisie.equals(AffichageConsole.FINIR_TOUR) == true) {
					if(this.partie.doitPoser() == false) {
						this.partie.finirTour();
					}
					else {
						System.out.println("Vous devez d'abord poser une carte.");
					}
				}
			}
			else if (saisie != null) {
				if(saisie.equals(AffichageConsole.QUITTER) == true) {
					quitter = true;		    
				}
				else if(saisie.equals(AffichageConsole.COMMENCER_PARTIE)) {
					if(this.partie.getJoueursDefinis() && this.partie.getReglesDefinies()) {
						System.out.println("La partie va d�sormais commencer.");
						this.partie.debutJeu();
					}
					else {
						System.out.println("La partie ne peut pas encore commencer. Assurez-vous que les r�gles et les joueurs soient bien d�finis.");
					}

				}
				else if(saisie.equals(AffichageConsole.CHOISIR_REGLES)) {
					if(this.partie.getReglesDefinies()) {
						System.out.println("Les r�gles ont d�j� �t� d�finies.");
					}
					else {
						this.partie.tickModeRegles();
						System.out.println("Veuillez choisir le mode de jeu.");
						System.out.println("0 - Deux joueurs, mode simple");
						System.out.println("1 - Trois joueurs, mode simple");
						System.out.println("2 - Deux joueurs, mode avanc�");
						System.out.println("3 - Trois joueurs, mode avanc�");
					}
				}
				else if(saisie.equals(DEFINIR_JOUEURS)) {
					if(this.partie.getJoueursDefinis()) {
						System.out.println("Vous avez d�j� d�fini les joueurs pour cette partie.");
					}
					else if(this.partie.getReglesDefinies() == false) {
						System.out.println("Vous devez d'abord d�finir les r�gles de la partie.");
					}
					else {
						this.partie.tickModeJoueurs();
						System.out.println("Entrer le type du joueur : ");
						System.out.println("0 - Joueur physique.");
						System.out.println("1 - Joueur virtuel.");
					}
				}
				else {
					System.out.println("Commande non reconnue...");
				}		
			}
		} while (quitter == false);
		System.exit(0);
	}
	
	/**
	 * Permet de lire une entr�e utilisateur dans la console.
	 * @return la cha�ne lue
	 */
	private String lireChaine() {
		BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
		String resultat = null;
		try {
			System.out.print(AffichageConsole.PROMPT);
			resultat = br.readLine();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return resultat;	
	}
}