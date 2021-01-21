package lo02.shapeup.partie;

/**
*	La classe Banque représente la pile de cartes à piocher de la partie.
*	Elle permet le mélange des cartes ainsi que leur pioche tout au long de la partie.
*
*	@author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
*	@version 1.0
*	@see lo02.shapeup.partie.Carte
*/
public class Banque {

	/**
	 * Représente toutes les cartes de la partie.
	 */
	private Carte[] deck;

	/**
	 * Permet de suivre la prochaine carte à piocher.
	 */
	private int index;

	/**
	 * Instancie une nouvelle Banque.
	 * Crée tous les objets de type Carte disponibles dans la partie, puis les mélange aléatoirement afin d'obtenir un paquet mélangé, et donc un tirage aléatoire.
	 */
	public Banque() {

		this.deck = new Carte[18];
		this.index = 0;

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 2; k++) {
					this.deck[index] = new Carte(i, j, k);
					this.index++;
				}
			}
		}

		this.melanger();
	}

	/**
	 * Effectue l'action de piocher une carte.
	 * @return la carte piochée.
	 */
	public Carte piocher() {
		this.index--;
		return this.deck[index];
	}
	
	/**
	 * Permet d'obtenir la position de la pioche de la banque.
	 * @return l'index actuel de la carte à piocher.
	 */
	public int getIndex() {
		return this.index;
	}
	
	/**
	 * Permet le mélange des cartes à la création de la banque.
	 */
	private void melanger() {

		for(int i = 0; i < 50; i++) {

			int pos1 = (int) Math.round(Math.random() * 17);
			int pos2 = (int) Math.round(Math.random() * 17);
			Carte c = this.deck[pos1];

			this.deck[pos1] = this.deck[pos2];
			this.deck[pos2] = c;
		}
	}
}