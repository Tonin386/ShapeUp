package lo02.shapeup.partie;

/**
*	L'interface PartieElement permet de d�crire un objet de la partie comme �tant visitable.
*
*	@author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
*	@version 1.0
*/
public interface PartieElement {
	/**
	 * Permet d'accepter un PartieElementVisitor comme visiteur.
	 * @param visitor le visiteur � accepter
	 */
	void accept(PartieElementVisitor visitor);
}
