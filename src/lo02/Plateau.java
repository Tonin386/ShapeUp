package lo02;

import java.util.HashMap;
import java.util.Arrays;
import java.util.Map;
import java.util.List;

public class Plateau {

	private Map<List<Integer>, Carte> disposition;
	private int cartesPosees;

	public Plateau() {
		this.disposition = new HashMap<List<Integer>, Carte>();
		this.cartesPosees = 0;
	}

	public void poserCarte(int x, int y, Carte c) {
		List<Integer> position = Arrays.asList(x, y);
		this.disposition.put(position, c);
		this.cartesPosees++;
	}

	public void deplacerCarte(int xOrigine, int yOrigine, int xArrivee, int yArrivee) {
		List<Integer> positionOrigine = Arrays.asList(xOrigine, yOrigine);
		List<Integer> positionArrivee = Arrays.asList(xArrivee, yArrivee);
		this.disposition.put(positionArrivee, this.disposition.get(positionOrigine));
	}
	
	public Map<List<Integer>, Carte> getDisposition() {
		return this.disposition;
	}

	public int getCartesPosees() {
		return this.cartesPosees;
	}
}
