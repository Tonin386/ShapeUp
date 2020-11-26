package lo02.shapeup.jeu;

public class Banque {

	private Carte[] deck;
	private int index;

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

	public Carte piocher() {
		this.index--;
		return this.deck[index];
	}
	
	private void melanger() {

		for(int i = 0; i < 50; i++) {

			int pos1 = (int)Math.random() * 18;
			int pos2 = (int)Math.random() * 18;
			Carte c = this.deck[pos1];

			this.deck[pos1] = this.deck[pos2];
			this.deck[pos2] = c;
		}
	}
}