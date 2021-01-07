package lo02.shapeup.partie;

public class Carte {
	
	public final static int ROUGE = 0;
	public final static int VERT = 1;
	public final static int BLEU = 2;
	
	public final static int CERCLE = 0;
	public final static int TRIANGLE = 1;
	public final static int CARRE = 2;

	public final static int VIDE = 0;
	public final static int PLEINE = 1;

	public final static String[] COULEURS = {"Rouge", "Vert", "Bleu"};
	public final static String[] FORMES = {"Cercle", "Triangle", "Carre"};
	public final static String[] REMPLISSAGES = {"Pleine", "Vide"};
	
	private String couleur;
	private String forme;
	private int remplissage;

	public Carte() {
		this.couleur = "";
		this.forme = "";
		this.remplissage = -1;
	}
	
	@SuppressWarnings("static-access")
	public Carte(int couleur, int forme, int remplissage) {
		this.couleur = this.COULEURS[couleur];
		this.forme = this.FORMES[forme];
		this.remplissage = remplissage;
	}

	public Carte(String couleur, String forme, String remplissage) {

		this.couleur = couleur;
		this.forme = forme;

		if(remplissage == "Pleine") {
			this.remplissage = 1;
		}
		else {
			this.remplissage = 0;
		}
	}

	public String getCouleur() {
		return this.couleur;
	}

	public String getForme() {
		return this.forme;
	}

	public int getRemplissage() {
		return this.remplissage;
	}

	public String getPath() {
		String path = "img/";

		if(this.forme == "Cercle") {
			path += "cercle/";
		}
		else if(this.forme == "Triangle") {
			path += "triangle/";
		}
		else {
			path += "carre/";
		}

		if(this.couleur == "Rouge") {
			path += "r";
		}
		else if(this.couleur == "Vert") {
			path += "v";
		}
		else {
			path += "b";
		}

		switch(this.remplissage) {
			case 0:
			path += "v";
			break;
			case 1:
			path += "p";
			break;
		}

		path += ".png";

		return path;
	}

	@SuppressWarnings("static-access")
	public String toString() {
		return "Carte " + this.forme + " " + this.couleur + " " + this.REMPLISSAGES[this.remplissage];
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (this.getClass() != o.getClass())
			return false;
		Carte carte = (Carte) o;
		return this.forme.equals(carte.getForme()) && this.couleur.equals(carte.getCouleur()) && this.remplissage == carte.getRemplissage();
	}
}