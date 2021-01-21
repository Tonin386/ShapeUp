package lo02.shapeup.regles;
import lo02.shapeup.partie.Partie;


/**
*	L'interface RegleStrategy permet de décrire la stratégie à adopter pour différentes règles du jeu. 
* 
*	@author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
*	@version 1.0
*/
public interface RegleStrategy {

	/**
	 * Permet d'effectuer les actions du début de jeu.
	 * @param partie la partie à commencer
	 */
	public void debutJeu(Partie partie);
	
	/**
	 * Permet de simuler l'avancement de la partie
	 * @param partie la partie dans laquelle on avance
	 */
	public void jouer(Partie partie);
}