package lo02.shapeup.partie;

/**
 *	La classe Carte repr�sente une carte dans la partie. Chaque carte est d�finie par sa couleur, sa forme et son remplissage.
 * 
 * @author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
 * @version 1.0
 */
public class Carte {
	
	/**
	 * Repr�sente l'�quivalent num�rique correspondant � la couleur ROUGE.
	 */
	public final static int ROUGE = 0;

	/**
	 * Repr�sente l'�quivalent num�rique correspondant � la couleur VERT.
	 */
	public final static int VERT = 1;

	/**
	 * Repr�sente l'�quivalent num�rique correspondant � la couleur BLEU.
	 */
	public final static int BLEU = 2;
	
	/**
	 * Repr�sente l'�quivalent num�rique correspondant � la forme CERCLE.
	 */
	public final static int CERCLE = 0;

	/**
	 * Repr�sente l'�quivalent num�rique correspondant � la forme TRIANGLE.
	 */
	public final static int TRIANGLE = 1;

	/**
	 * Repr�sente l'�quivalent num�rique correspondant � la forme CARRE.
	 */
	public final static int CARRE = 2;

	/**
	 * Repr�sente l'�quivalent num�rique correspondant au remplissage VIDE.
	 */
	public final static int VIDE = 0;

	/**
	 * Repr�sente l'�quivalent num�rique correspondant au remplissage PLEINE.
	 */
	public final static int PLEINE = 1;

	/**
	 * Contient les couleurs autoris�es.
	 */
	public final static String[] COULEURS = {"Rouge", "Vert", "Bleu"};

	/**
	 * Contient les formes autoris�es.
	 */
	public final static String[] FORMES = {"Cercle", "Triangle", "Carre"};

	/**
	 * Contient les remplissages autoris�s.
	 */
	public final static String[] REMPLISSAGES = {"Pleine", "Vide"};
	
	/**
	 * Sp�cifie la couleur de la carte.
	 */
	private String couleur;

	/**
	 * Sp�cifie la forme de la carte.
	 */
	private String forme;

	/**
	 * Sp�cifie le remplissage de la carte.
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
	 * Instancie une nouvelle carte selon certains param�tres.
	 * @param couleur la couleur de la carte sous forme num�rique.
	 * @param forme la forme de la carte sous forme num�rique.
	 * @param remplissage le remplissage de la carte sous forme num�rique.
	 */
	@SuppressWarnings("static-access")
	public Carte(int couleur, int forme, int remplissage) {
		this.couleur = this.COULEURS[couleur];
		this.forme = this.FORMES[forme];
		this.remplissage = remplissage;
	}

	/**
	 * Instancie une nouvelle carte selon certains param�tres.
	 * @param couleur la couleur de la carte sous forme d'une cha�ne de caract�res.
	 * @param forme la forme de la carte sous forme d'une cha�ne de caract�res.
	 * @param remplissage le remplissage de la carte sous forme d'une cha�ne de caract�res.
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
	 * @return la repr�sentation de cette en une cha�ne de caract�res.
	 */
	@SuppressWarnings("static-access")
	public String toString() {
		return "Carte " + this.forme + " " + this.couleur + " " + this.REMPLISSAGES[this.remplissage];
	}

	/**
	 * @param o un objet que l'on souhaite comparer � cette carte.
	 * @return true si l'objet a la m�me adresse ou si l'objet de type carte poss�de les m�mes couleur, forme et remplissage, false sinon.
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