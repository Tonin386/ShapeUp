package lo02.shapeup.affichage;
import java.util.List;

import lo02.shapeup.partie.Carte;
import lo02.shapeup.partie.Joueur;
import lo02.shapeup.partie.Plateau;

public interface Affichage {
	
	public Joueur[] definirJoueurs(int modeDeJeu);
	public void debutTour(Joueur j);
	public Carte afficherCartePiochee(Carte c);
	public void afficherJeu(List<Carte> jeu);
	public Carte choisirCarteJeu(List<Carte> jeu);
	public int choisirAction();
	public int demanderDeplacement();
	public List<Integer> choisirPositionCarte(List<List<Integer>> positionnementsPossibles);
	public List<Integer> choisirDeplacementCarte(List<List<Integer>> deplacementsPossibles);
	public void afficherPlateau(Plateau plateau);
}