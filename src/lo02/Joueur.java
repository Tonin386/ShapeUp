package lo02;

public abstract class Joueur {

	protected String nom;
	protected Carte[] jeu;
	protected Carte[] cartesPosees;
	protected Carte victorieuse;

	public Joueur() {
		jeu = new Carte[3];
	}
	
	public Joueur(String nom) {
		this.nom = nom;
		jeu = new Carte[3];
	}
	
	public abstract void jouer();

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public boolean aPerdu() {
		return false; //TODO
	}

	public void piocher(Carte c) {
		int i = 0;
		boolean trouve = false;
		while(i < 3 && !trouve) {
			if(jeu[i].getCouleur() == "") {
				jeu[i] = c;
				trouve = true;
			}
			else {
				i++;
			}
		}

	}

}
