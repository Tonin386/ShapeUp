package lo02.shapeup.regles;
import lo02.shapeup.partie.Partie;


/**
*	L'interface RegleStrategy permet de d�crire la strat�gie � adopter pour diff�rentes r�gles du jeu. 
* 
*	@author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
*	@version 1.0
*/
public interface RegleStrategy {

	/**
	 * Permet d'effectuer les actions du d�but de jeu.
	 * @param partie la partie � commencer
	 */
	public void debutJeu(Partie partie);
	
	/**
	 * Permet de simuler l'avancement de la partie
	 * @param partie la partie dans laquelle on avance
	 */
	public void jouer(Partie partie);
}