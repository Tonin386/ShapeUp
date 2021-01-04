package lo02.shapeup.partie;

import java.util.ArrayList;

public interface PartieElementVisitor {
	void visit(Carte carte);
	void visit(Plateau plateau);
	void visit(Joueur joueur);
	void visit(Banque banque);
	ArrayList<Integer> visitPartie(Partie partie);
}
