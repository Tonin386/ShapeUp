package lo02.shapeup.regles;
import lo02.shapeup.partie.Partie;

//Strategy

public interface RegleStrategy {
	public void debutJeu(Partie partie);
	public void jouer(Partie partie);
}