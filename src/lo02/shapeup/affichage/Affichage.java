package lo02.shapeup.affichage;
import java.util.List;

import lo02.shapeup.jeu.*;

public interface Affichage {
	
	public Joueur[] definirJoueurs(int modeDeJeu);
	public void debutTour(Joueur j);
	public Carte afficherCartePiochee(Carte c);
	public int choisirAction();
	public int demanderDeplacement();
	public int[] choisirPositionCarte(List<int[]> positionnementsPossibles);
	public int[] choisirDeplacementCarte(List<int[]> deplacementsPossibles);
	public void afficherPlateau(Plateau plateau);
}