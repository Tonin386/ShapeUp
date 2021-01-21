package lo02.shapeup.partie;

/**
 *	La classe Carte représente une carte dans la partie. Chaque carte est définie par sa couleur, sa forme et son remplissage.
 * 
 * @author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
 * @version 1.0
 */
public class Carte {
	
	/**
	 * Représente l'équivalent numérique correspondant à la couleur ROUGE.
	 */
	public final static int ROUGE = 0;

	/**
	 * Représente l'équivalent numérique correspondant à la couleur VERT.
	 */
	public final static int VERT = 1;

	/**
	 * Représente l'équivalent numérique correspondant à la couleur BLEU.
	 */
	public final static int BLEU = 2;
	
	/**
	 * Représente l'équivalent numérique correspondant à la forme CERCLE.
	 */
	public final static int CERCLE = 0;

	/**
	 * Représente l'équivalent numérique correspondant à la forme TRIANGLE.
	 */
	public final static int TRIANGLE = 1;

	/**
	 * Représente l'équivalent numérique correspondant à la forme CARRE.
	 */
	public final static int CARRE = 2;

	/**
	 * Représente l'équivalent numérique correspondant au remplissage VIDE.
	 */
	public final static int VIDE = 0;

	/**
	 * Représente l'équivalent numérique correspondant au remplissage PLEINE.
	 */
	public final static int PLEINE = 1;

	/**
	 * Contient les couleurs autorisées.
	 */
	public final static String[] COULEURS = {"Rouge", "Vert", "Bleu"};

	/**
	 * Contient les formes autorisées.
	 */
	public final static String[] FORMES = {"Cercle", "Triangle", "Carre"};

	/**
	 * Contient les remplissages autorisés.
	 */
	public final static String[] REMPLISSAGES = {"Pleine", "Vide"};
	
	/**
	 * Spécifie la couleur de la carte.
	 */
	private String couleur;

	/**
	 * Spécifie la forme de la carte.
	 */
	private String forme;

	/**
	 * Spécifie le remplissage de la carte.
	 */
	private int remplissage;

	/**
	 * Instancie une nouvelle carte.
	 */
	public Carte() {
		this.couleur = "";
		this.forme = "";
		this.remplissage = -1;
	}
	
	/**
	 * Instancie une nouvelle carte selon certains paramètres.
	 * @param couleur la couleur de la carte sous forme numérique.
	 * @param forme la forme de la carte sous forme numérique.
	 * @param remplissage le remplissage de la carte sous forme numérique.
	 */
	@SuppressWarnings("static-access")
	public Carte(int couleur, int forme, int remplissage) {
		this.couleur = this.COULEURS[couleur];
		this.forme = this.FORMES[forme];
		this.remplissage = remplissage;
	}

	/**
	 * Instancie une nouvelle carte selon certains paramètres.
	 * @param couleur la couleur de la carte sous forme d'une chaîne de caractères.
	 * @param forme la forme de la carte sous forme d'une chaîne de caractères.
	 * @param remplissage le remplissage de la carte sous forme d'une chaîne de caractères.
	 */
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

	/**
	 * Permet d'obtenir la couleur de la carte.
	 * @return la couleur de la carte
	 */
	public String getCouleur() {
		return this.couleur;
	}

	/**
	 * Permet d'obtenir la forme de la carte.
	 * @return la forme de la carte
	 */
	public String getForme() {
		return this.forme;
	}

	/**
	 * Permet d'obtenir le remplissage de la carte.
	 * @return le remplissage de la carte.
	 */
	public int getRemplissage() {
		return this.remplissage;
	}

	/**
	 * Permet d'obtenir le chemin de l'image de la carte.
	 * @return le chemin de l'image de la carte.
	 */
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

	/**
	 * Permet l'affichage de cette carte sous forme de texte.
	 * @return la représentation de cette en une chaîne de caractères.
	 */
	@SuppressWarnings("static-access")
	public String toString() {
		return "Carte " + this.forme + " " + this.couleur + " " + this.REMPLISSAGES[this.remplissage];
	}

	/**
	 * @param o un objet que l'on souhaite comparer à cette carte.
	 * @return true si l'objet a la même adresse ou si l'objet de type carte possède les mêmes couleur, forme et remplissage, false sinon.
	 */
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