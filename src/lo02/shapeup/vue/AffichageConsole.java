package lo02.shapeup.vue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import lo02.shapeup.partie.*;

public class AffichageConsole implements Runnable {

	public static String COMMENCER_PARTIE = "start";
	public static String CHOISIR_REGLES = "R";
	public static String DEFINIR_JOUEURS = "J";
	public static String DEPLACER = "D";
	public static String CHOISIR_CARTE = "C";
	public static String POSER = "P";
	public static String FINIR_TOUR = "F";
	public static String QUITTER = "quit";
	public static String PROMPT = ">";
	
	private Partie partie;
	private boolean choisirDeplacement = false;
	private boolean choisirPosition = false;
	private boolean choisirIndex = false;

	public AffichageConsole(Partie partie) {
		this.partie = partie;
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {

		String saisie = null;
		boolean quitter = false;

		System.out.println("Options possibles :");
		System.out.println(AffichageConsole.CHOISIR_REGLES + " : choisir les règles.");
		System.out.println(AffichageConsole.DEFINIR_JOUEURS + " : choisir le nom des joueurs.");
		System.out.println(AffichageConsole.COMMENCER_PARTIE + " : commencer la partie. (Vous devez avoir choisi les règles et le nom des joueurs au préalable.)");
		System.out.println(AffichageConsole.CHOISIR_CARTE + " : Choisir la carte à jouer.");
		System.out.println(AffichageConsole.POSER + " : poser une carte");
		System.out.println(AffichageConsole.DEPLACER + " : déplacer une carte.");
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
						System.out.println("Vous pouvez désormais choisir le nom de ce joueur.");
					}
					else if(saisie.equals("1")) {
						j = new JoueurVirtuelStrategy();
						this.partie.ajouterJoueur(j);
						this.partie.signalPhaseTerminee();
						System.out.println("Vous pouvez désormais choisir le nom de ce joueur.");
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
						System.out.println("Vous ne pouvez plus déplacer de carte ce tour.");
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
						System.out.println("La partie va désormais commencer.");
						this.partie.debutJeu();
					}
					else {
						System.out.println("La partie ne peut pas encore commencer. Assurez-vous que les règles et les joueurs soient bien définis.");
					}

				}
				else if(saisie.equals(AffichageConsole.CHOISIR_REGLES)) {
					if(this.partie.getReglesDefinies()) {
						System.out.println("Les règles ont déjà été définies.");
					}
					else {
						this.partie.tickModeRegles();
						System.out.println("Veuillez choisir le mode de jeu.");
						System.out.println("0 - Deux joueurs, mode simple");
						System.out.println("1 - Trois joueurs, mode simple");
						System.out.println("2 - Deux joueurs, mode avancé");
						System.out.println("3 - Trois joueurs, mode avancé");
					}
				}
				else if(saisie.equals(DEFINIR_JOUEURS)) {
					if(this.partie.getJoueursDefinis()) {
						System.out.println("Vous avez déjà défini les joueurs pour cette partie.");
					}
					else if(this.partie.getReglesDefinies() == false) {
						System.out.println("Vous devez d'abord définir les règles de la partie.");
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