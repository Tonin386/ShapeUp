package lo02.shapeup.jeu;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.List;
import java.lang.Math;

public class Plateau {

	private Map<int[], Carte> disposition;
	private int cartesPosees;
	private int[] porteeX;
	private int[] porteeY;

	public Plateau() {
		this.disposition = new HashMap<int[], Carte>();
		this.cartesPosees = 0;
		this.porteeX = new int[]{0,0};
		this.porteeY = new int[]{0,0};
	}

	public void poserCarte(int x, int y, Carte c) {
		int[] position = new int[]{x, y};

		if(position[0] < this.porteeX[0]) {
			this.porteeX[0] = position[0];
		}
		else if(position[0] > this.porteeX[1]) {
			this.porteeX[1] = position[0];
		}
		else if(position[1] < this.porteeY[0]) {
			this.porteeY[0] = position[1];
		}
		else if(position[1] > this.porteeY[1]) {
			this.porteeX[1] = position[1];
		}

		this.disposition.put(position, c);
		this.cartesPosees++;
	}

	public void deplacerCarte(int xOrigine, int yOrigine, int xArrivee, int yArrivee) {
		int[] positionOrigine = new int[]{xOrigine, yOrigine};
		int[] positionArrivee = new int[]{xArrivee, yArrivee};

		if(positionArrivee[0] < this.porteeX[0]) {
			this.porteeX[0] = positionArrivee[0];
		}
		else if(positionArrivee[0] > this.porteeX[1]) {
			this.porteeX[1] = positionArrivee[0];
		}
		else if(positionArrivee[1] < this.porteeY[0]) {
			this.porteeY[0] = positionArrivee[1];
		}
		else if(positionArrivee[1] > this.porteeY[1]) {
			this.porteeX[1] = positionArrivee[1];
		}

		this.disposition.put(positionArrivee, this.disposition.get(positionOrigine));
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public List<int[]> getDeplacementsPossibles() {
		List<int[]> deplacementsPossibles = new ArrayList<int[]>();

		for(Map.Entry<int[], Carte> entry : this.disposition.entrySet()) {

			int[] positionOrigine = entry.getKey();
			Map<int[], Carte> copieDisposition = new HashMap<int[], Carte>(this.disposition);	
			copieDisposition.remove(positionOrigine);
			int[] porteeXCopie = new int[]{0, 0};
			int[] porteeYCopie = new int[]{0, 0};

			for(int[] position : copieDisposition.keySet()) {

				if(position[0] < porteeXCopie[0]) {
					porteeXCopie[0] = position[0];
				}
				else if(position[0] > porteeXCopie[1]) {
					porteeXCopie[1] = position[0];
				}
				else if(position[1] < porteeYCopie[0]) {
					porteeYCopie[0] = position[1];
				}
				else if(position[1] > porteeYCopie[1]) {
					porteeXCopie[1] = position[1];
				}
			}

			int nombreColonnes = Math.abs(porteeXCopie[1] - porteeXCopie[0]);
			int nombreRangees = Math.abs(porteeYCopie[1] - porteeYCopie[0]);

			List<int[]> positionsOccupees = new ArrayList<int[]>();
			for(int coords[] : copieDisposition.keySet()) {
				positionsOccupees.add(coords);
			}

			if(nombreRangees < 4 && nombreColonnes < 4) {
				for(int[] coords : positionsOccupees) {
					int audessus[] = new int[] {coords[0], coords[1] - 1};
					if(audessus != positionOrigine && !Arrays.asList(positionsOccupees).contains(audessus) && !Arrays.asList(deplacementsPossibles).contains(new int[] {positionOrigine[0], positionOrigine[1], audessus[0], audessus[1]})) {
						deplacementsPossibles.add(new int[] {positionOrigine[0], positionOrigine[1], audessus[0], audessus[1]});
					}

					int endessous[] = new int[] {coords[0], coords[1] + 1};
					if(endessous != positionOrigine && !Arrays.asList(positionsOccupees).contains(endessous) && !Arrays.asList(deplacementsPossibles).contains(new int[] {positionOrigine[0], positionOrigine[1], endessous[0], endessous[1]})) {
						deplacementsPossibles.add(new int[] {positionOrigine[0], positionOrigine[1], endessous[0], endessous[1]});
					}

					int adroite[] = new int[] {coords[0] + 1, coords[1]};
					if(adroite != positionOrigine && !Arrays.asList(positionsOccupees).contains(adroite) && !Arrays.asList(deplacementsPossibles).contains(new int[] {positionOrigine[0], positionOrigine[1], adroite[0], adroite[1]})) {
						deplacementsPossibles.add(new int[] {positionOrigine[0], positionOrigine[1], adroite[0], adroite[1]});
					}

					int agauche[] = new int[] {coords[0] - 1, coords[1]};
					if(agauche != positionOrigine && !Arrays.asList(positionsOccupees).contains(agauche) && !Arrays.asList(deplacementsPossibles).contains(new int[] {positionOrigine[0], positionOrigine[1], agauche[0], agauche[1]})) {
						deplacementsPossibles.add(new int[] {positionOrigine[0], positionOrigine[1], agauche[0], agauche[1]});
					}
				}
			}
			else if(nombreColonnes > 3) {
				for(int[] coords : positionsOccupees) {
					int audessus[] = new int[] {coords[0], coords[1] - 1};
					if(audessus != positionOrigine && audessus[1] >= porteeYCopie[0] && !Arrays.asList(positionsOccupees).contains(audessus) && !Arrays.asList(deplacementsPossibles).contains(new int[] {positionOrigine[0], positionOrigine[1], audessus[0], audessus[1]})) {
						deplacementsPossibles.add(new int[] {positionOrigine[0], positionOrigine[1], audessus[0], audessus[1]});
					}

					int endessous[] = new int[] {coords[0], coords[1] + 1};
					if(endessous != positionOrigine && endessous[1] <= porteeYCopie[1] && !Arrays.asList(positionsOccupees).contains(endessous) && !Arrays.asList(deplacementsPossibles).contains(new int[] {positionOrigine[0], positionOrigine[1], endessous[0], endessous[1]})) {
						deplacementsPossibles.add(new int[] {positionOrigine[0], positionOrigine[1], endessous[0], endessous[1]});
					}

					int adroite[] = new int[] {coords[0] + 1, coords[1]};
					if(adroite != positionOrigine && !Arrays.asList(positionsOccupees).contains(adroite) && !Arrays.asList(deplacementsPossibles).contains(new int[] {positionOrigine[0], positionOrigine[1], adroite[0], adroite[1]})) {
						deplacementsPossibles.add(new int[] {positionOrigine[0], positionOrigine[1], adroite[0], adroite[1]});
					}

					int agauche[] = new int[] {coords[0] - 1, coords[1]};
					if(agauche != positionOrigine && !Arrays.asList(positionsOccupees).contains(agauche) && !Arrays.asList(deplacementsPossibles).contains(new int[] {positionOrigine[0], positionOrigine[1], agauche[0], agauche[1]})) {
						deplacementsPossibles.add(new int[] {positionOrigine[0], positionOrigine[1], agauche[0], agauche[1]});
					}
				}
			}
			else {
				for(int[] coords : positionsOccupees) {
					int audessus[] = new int[] {coords[0], coords[1] - 1};
					if(audessus != positionOrigine && !Arrays.asList(positionsOccupees).contains(audessus) && !Arrays.asList(deplacementsPossibles).contains(new int[] {positionOrigine[0], positionOrigine[1], audessus[0], audessus[1]})) {
						deplacementsPossibles.add(new int[] {positionOrigine[0], positionOrigine[1], audessus[0], audessus[1]});
					}

					int endessous[] = new int[] {coords[0], coords[1] + 1};
					if(endessous != positionOrigine && !Arrays.asList(positionsOccupees).contains(endessous) && !Arrays.asList(deplacementsPossibles).contains(new int[] {positionOrigine[0], positionOrigine[1], endessous[0], endessous[1]})) {
						deplacementsPossibles.add(new int[] {positionOrigine[0], positionOrigine[1], endessous[0], endessous[1]});
					}

					int adroite[] = new int[] {coords[0] + 1, coords[1]};
					if(adroite != positionOrigine && adroite[0] <= porteeXCopie[1] && !Arrays.asList(positionsOccupees).contains(adroite) && !Arrays.asList(deplacementsPossibles).contains(new int[] {positionOrigine[0], positionOrigine[1], adroite[0], adroite[1]})) {
						deplacementsPossibles.add(new int[] {positionOrigine[0], positionOrigine[1], adroite[0], adroite[1]});
					}

					int agauche[] = new int[] {coords[0] - 1, coords[1]};
					if(agauche != positionOrigine && agauche[0] >= porteeXCopie[0] && !Arrays.asList(positionsOccupees).contains(agauche) && !Arrays.asList(deplacementsPossibles).contains(new int[] {positionOrigine[0], positionOrigine[1], agauche[0], agauche[1]})) {
						deplacementsPossibles.add(new int[] {positionOrigine[0], positionOrigine[1], agauche[0], agauche[1]});
					}
				}
			}
		}

		
		return deplacementsPossibles;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public List<int[]> getPositionnementsPossibles() {
		List<int[]> positionnementsPossibles = new ArrayList<int[]>();

		if(this.cartesPosees == 0) {
			int coords[] = new int[]{0,0};
			positionnementsPossibles.add(coords);
		}
		else {
			int nombreColonnes = Math.abs(this.porteeX[1] - this.porteeX[0]);
			int nombreRangees = Math.abs(this.porteeY[1] - this.porteeY[0]);

			List<int[]> positionsOccupees = new ArrayList<int[]>();
			for(int coords[] : this.disposition.keySet()) {
				positionsOccupees.add(coords);
			}

			if(nombreRangees < 4 && nombreColonnes < 4) {
				for(int[] coords : positionsOccupees) {
					int audessus[] = new int[] {coords[0], coords[1] - 1};
					if(!Arrays.asList(positionsOccupees).contains(audessus) && !Arrays.asList(positionnementsPossibles).contains(audessus)) {
						positionnementsPossibles.add(audessus);
					}

					int endessous[] = new int[] {coords[0], coords[1] + 1};
					if(!Arrays.asList(positionsOccupees).contains(endessous) && !Arrays.asList(positionnementsPossibles).contains(endessous)) {
						positionnementsPossibles.add(endessous);
					}

					int adroite[] = new int[] {coords[0] + 1, coords[1]};

					if(!Arrays.asList(positionsOccupees).contains(adroite) && !Arrays.asList(positionnementsPossibles).contains(adroite)) {
						positionnementsPossibles.add(adroite);
					}

					int agauche[] = new int[] {coords[0] - 1, coords[1]};
					if(!Arrays.asList(positionsOccupees).contains(agauche) && !Arrays.asList(positionnementsPossibles).contains(agauche)) {
						positionnementsPossibles.add(agauche);
					}
				}
			}
			else if(nombreColonnes > 3) {
				for(int[] coords : positionsOccupees) {
					int audessus[] = new int[] {coords[0], coords[1] - 1};
					if(audessus[1] >= this.porteeY[0] && !Arrays.asList(positionsOccupees).contains(audessus) && !Arrays.asList(positionnementsPossibles).contains(audessus)) {
						positionnementsPossibles.add(audessus);
					}

					int endessous[] = new int[] {coords[0], coords[1] + 1};
					if(endessous[1] <= this.porteeY[1] && !Arrays.asList(positionsOccupees).contains(endessous) && !Arrays.asList(positionnementsPossibles).contains(endessous)) {
						positionnementsPossibles.add(endessous);
					}

					int adroite[] = new int[] {coords[0] + 1, coords[1]};

					if(!Arrays.asList(positionsOccupees).contains(adroite) && !Arrays.asList(positionnementsPossibles).contains(adroite)) {
						positionnementsPossibles.add(adroite);
					}

					int agauche[] = new int[] {coords[0] - 1, coords[1]};
					if(!Arrays.asList(positionsOccupees).contains(agauche) && !Arrays.asList(positionnementsPossibles).contains(agauche)) {
						positionnementsPossibles.add(agauche);
					}
				}
			}
			else {
				for(int[] coords : positionsOccupees) {
					int audessus[] = new int[] {coords[0], coords[1] - 1};
					if(!Arrays.asList(positionsOccupees).contains(audessus) && !Arrays.asList(positionnementsPossibles).contains(audessus)) {
						positionnementsPossibles.add(audessus);
					}

					int endessous[] = new int[] {coords[0], coords[1] + 1};
					if(!Arrays.asList(positionsOccupees).contains(endessous) && !Arrays.asList(positionnementsPossibles).contains(endessous)) {
						positionnementsPossibles.add(endessous);
					}

					int adroite[] = new int[] {coords[0] + 1, coords[1]};

					if(adroite[0] <= this.porteeX[1] && !Arrays.asList(positionsOccupees).contains(adroite) && !Arrays.asList(positionnementsPossibles).contains(adroite)) {
						positionnementsPossibles.add(adroite);
					}

					int agauche[] = new int[] {coords[0] - 1, coords[1]};
					if(agauche[0] >= this.porteeX[0] && !Arrays.asList(positionsOccupees).contains(agauche) && !Arrays.asList(positionnementsPossibles).contains(agauche)) {
						positionnementsPossibles.add(agauche);
					}
				}
			}
		}
		
		return positionnementsPossibles;
	}
	
	public int[] getPorteeX() {
		return this.porteeX;
	}

	public int[] getPorteeY() {
		return this.porteeY;
	}

	public Map<int[], Carte> getDisposition() {
		return this.disposition;
	}

	public int getCartesPosees() {
		return this.cartesPosees;
	}
}