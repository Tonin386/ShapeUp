package lo02.shapeup.partie;

/**
*	L'interface PartieElement permet de décrire un objet de la partie comme étant visitable.
*
*	@author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
*	@version 1.0
*/
public interface PartieElement {
	/**
	 * Permet d'accepter un PartieElementVisitor comme visiteur.
	 * @param visitor le visiteur à accepter
	 */
	void accept(PartieElementVisitor visitor);
}
