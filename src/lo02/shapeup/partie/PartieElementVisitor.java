package lo02.shapeup.partie;

import java.util.ArrayList;

public interface PartieElementVisitor {
	void visit(Plateau plateau);
	void visit(JoueurStrategy joueur);
	ArrayList<Integer> visitPartie(Partie partie);
}
