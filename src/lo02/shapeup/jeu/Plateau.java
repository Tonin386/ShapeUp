package lo02.shapeup.jeu;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.lang.Math;

public class Plateau {

	private Map<List<Integer>, Carte> disposition;
	private int cartesPosees;
	private List<Integer> porteeX;
	private List<Integer> porteeY;

	public Plateau() {
		this.disposition = new HashMap<List<Integer>, Carte>();
		this.cartesPosees = 0;
		this.porteeX = new ArrayList<Integer>();
		this.porteeX.add(0);
		this.porteeX.add(0);
		this.porteeY = new ArrayList<Integer>();
		this.porteeY.add(0);
		this.porteeY.add(0);
	}

	public void poserCarte(int x, int y, Carte c) {
		List<Integer> position = new ArrayList<Integer>();
		position.add(x);
		position.add(y);

		if(position.get(0) < this.porteeX.get(0)) {
			this.porteeX.set(0, position.get(0));
		}
		else if(position.get(0) > this.porteeX.get(1)) {
			this.porteeX.set(1, position.get(0));
		}
		else if(position.get(1) < this.porteeY.get(0)) {
			this.porteeY.set(0, position.get(1));
		}
		else if(position.get(1) > this.porteeY.get(1)) {
			this.porteeY.set(1, position.get(1));
		}

		this.disposition.put(position, c);
		this.cartesPosees++;
	}

	public void deplacerCarte(int xOrigine, int yOrigine, int xArrivee, int yArrivee) {
		List<Integer> positionOrigine = new ArrayList<Integer>();
		positionOrigine.add(xOrigine);
		positionOrigine.add(yOrigine);
		List<Integer> positionArrivee = new ArrayList<Integer>();
		positionArrivee.add(xArrivee);
		positionArrivee.add(yArrivee);

		this.disposition.put(positionArrivee, this.disposition.get(positionOrigine));
		this.disposition.remove(positionOrigine);

		this.porteeX.set(0, xArrivee);
		this.porteeX.set(1, xArrivee);
		this.porteeY.set(0, yArrivee);
		this.porteeY.set(1, yArrivee);

		for(List<Integer> c : this.disposition.keySet()) {
			if(c.get(0) < this.porteeX.get(0)) {
				this.porteeX.set(0, c.get(0));
			}
			else if(c.get(0) > this.porteeX.get(1)) {
				this.porteeX.set(1, c.get(0));
			}
			else if(c.get(1) < this.porteeY.get(0)) {
				this.porteeY.set(0, c.get(1));
			}
			else if(c.get(1) > this.porteeY.get(1)) {
				this.porteeY.set(1, c.get(1));
			}
		}
	}
	
	public List<List<Integer>> getDeplacementsPossibles() {
		List<List<Integer>> deplacementsPossibles = new ArrayList<List<Integer>>();

		for(Map.Entry<List<Integer>, Carte> entry : this.disposition.entrySet()) {

			List<Integer> positionOrigine = entry.getKey();
			List<Integer> deplacement;

			Map<List<Integer>, Carte> copieDisposition = new HashMap<List<Integer>, Carte>(this.disposition);	
			copieDisposition.remove(positionOrigine);

			List<Integer> porteeXCopie = new ArrayList<Integer>();
			porteeXCopie.add(0);
			porteeXCopie.add(0);
			
			List<Integer> porteeYCopie = new ArrayList<Integer>();
			porteeYCopie.add(0);
			porteeYCopie.add(0);

			for(List<Integer> position : copieDisposition.keySet()) {

				if(position.get(0) < porteeXCopie.get(0)) {
					porteeXCopie.set(0, position.get(0));
				}
				else if(position.get(0) > porteeXCopie.get(1)) {
					porteeXCopie.set(1, position.get(0));
				}
				else if(position.get(1) < porteeYCopie.get(0)) {
					porteeYCopie.set(0, position.get(1));
				}
				else if(position.get(1) > porteeYCopie.get(1)) {
					porteeYCopie.set(1, position.get(1));
				}
			}

			int nombreColonnes = Math.abs(porteeXCopie.get(1) - porteeXCopie.get(0)) + 1;
			int nombreRangees = Math.abs(porteeYCopie.get(1) - porteeYCopie.get(0)) + 1;

			List<List<Integer>> positionsOccupees = new ArrayList<List<Integer>>();
			for(List<Integer> coords : copieDisposition.keySet()) {
				positionsOccupees.add(coords);
			}

			if(nombreRangees < 4 && nombreColonnes < 4) {
				for(List<Integer> coords : positionsOccupees) {
					List<Integer> audessus = new ArrayList<Integer>();
					audessus.add(coords.get(0));
					audessus.add(coords.get(1) - 1); deplacement = new ArrayList<Integer>();
					deplacement = new ArrayList<Integer>();
					deplacement.add(positionOrigine.get(0));
					deplacement.add(positionOrigine.get(1));
					deplacement.add(audessus.get(0));
					deplacement.add(audessus.get(1));
					if(!audessus.equals(positionOrigine) && !positionsOccupees.contains(audessus) && !deplacementsPossibles.contains(deplacement)) {
						deplacementsPossibles.add(deplacement);
					}

					List<Integer> endessous = new ArrayList<Integer>();
					endessous.add(coords.get(0));
					endessous.add(coords.get(1) + 1);
					deplacement = new ArrayList<Integer>();
					deplacement.add(positionOrigine.get(0));
					deplacement.add(positionOrigine.get(1));
					deplacement.add(endessous.get(0));
					deplacement.add(endessous.get(1));
					if(!endessous.equals(positionOrigine) && !positionsOccupees.contains(endessous) && !deplacementsPossibles.contains(deplacement)) {
						deplacementsPossibles.add(deplacement);
					}

					List<Integer> adroite = new ArrayList<Integer>();
					adroite.add(coords.get(0) + 1);
					adroite.add(coords.get(1));
					deplacement = new ArrayList<Integer>();
					deplacement.add(positionOrigine.get(0));
					deplacement.add(positionOrigine.get(1));
					deplacement.add(adroite.get(0));
					deplacement.add(adroite.get(1));
					if(!adroite.equals(positionOrigine) && !positionsOccupees.contains(adroite) && !deplacementsPossibles.contains(deplacement)) {
						deplacementsPossibles.add(deplacement);
					}

					List<Integer> agauche = new ArrayList<Integer>();
					agauche.add(coords.get(0) - 1);
					agauche.add(coords.get(1));
					deplacement = new ArrayList<Integer>();
					deplacement.add(positionOrigine.get(0));
					deplacement.add(positionOrigine.get(1));
					deplacement.add(agauche.get(0));
					deplacement.add(agauche.get(1));
					if(!agauche.equals(positionOrigine) && !positionsOccupees.contains(agauche) && !deplacementsPossibles.contains(deplacement)) {
						deplacementsPossibles.add(deplacement);
					}
				}
			}
			else if(nombreColonnes > 3) {
				for(List<Integer> coords : positionsOccupees) {
					List<Integer> audessus = new ArrayList<Integer>();
					audessus.add(coords.get(0));
					audessus.add(coords.get(1) - 1);
					deplacement = new ArrayList<Integer>();
					deplacement.add(positionOrigine.get(0));
					deplacement.add(positionOrigine.get(1));
					deplacement.add(audessus.get(0));
					deplacement.add(audessus.get(1));
					if(!audessus.equals(positionOrigine) && Math.abs(audessus.get(1) - porteeYCopie.get(1)) + 1 <= 3 && !positionsOccupees.contains(audessus) && !deplacementsPossibles.contains(deplacement)) {					
						deplacementsPossibles.add(deplacement);
					}

					List<Integer> endessous = new ArrayList<Integer>();
					endessous.add(coords.get(0));
					endessous.add(coords.get(1) + 1);
					deplacement = new ArrayList<Integer>();
					deplacement.add(positionOrigine.get(0));
					deplacement.add(positionOrigine.get(1));
					deplacement.add(endessous.get(0));
					deplacement.add(endessous.get(1));
					if(!endessous.equals(positionOrigine) && Math.abs(endessous.get(1) - porteeYCopie.get(0)) + 1 <= 3 && !positionsOccupees.contains(endessous) && !deplacementsPossibles.contains(deplacement)) {					
						deplacementsPossibles.add(deplacement);
					}

					List<Integer> adroite = new ArrayList<Integer>();
					adroite.add(coords.get(0) + 1);
					adroite.add(coords.get(1));
					deplacement = new ArrayList<Integer>();
					deplacement.add(positionOrigine.get(0));
					deplacement.add(positionOrigine.get(1));
					deplacement.add(adroite.get(0));
					deplacement.add(adroite.get(1));
					if(Math.abs(adroite.get(0) - porteeXCopie.get(0)) + 1<= 5 && !adroite.equals(positionOrigine) && !positionsOccupees.contains(adroite) && !deplacementsPossibles.contains(deplacement)) {						
						deplacementsPossibles.add(deplacement);
					}

					List<Integer> agauche = new ArrayList<Integer>();
					agauche.add(coords.get(0) - 1);
					agauche.add(coords.get(1));
					deplacement = new ArrayList<Integer>();
					deplacement.add(positionOrigine.get(0));
					deplacement.add(positionOrigine.get(1));
					deplacement.add(agauche.get(0));
					deplacement.add(agauche.get(1));
					if(Math.abs(agauche.get(0) - porteeXCopie.get(1)) + 1 <= 5 && !agauche.equals(positionOrigine) && !positionsOccupees.contains(agauche) && !deplacementsPossibles.contains(agauche)) {						
						deplacementsPossibles.add(deplacement);
					}
				}
			}
			else {
				for(List<Integer> coords : positionsOccupees) {
					List<Integer> audessus = new ArrayList<Integer>();
					audessus.add(coords.get(0));
					audessus.add(coords.get(1) - 1);
					deplacement = new ArrayList<Integer>();
					deplacement.add(positionOrigine.get(0));
					deplacement.add(positionOrigine.get(1));
					deplacement.add(audessus.get(0));
					deplacement.add(audessus.get(1));
					if(Math.abs(audessus.get(1) - porteeYCopie.get(1)) + 1 <= 5 && !audessus.equals(positionOrigine) && !positionsOccupees.contains(audessus) && !deplacementsPossibles.contains(deplacement)) {						
						deplacementsPossibles.add(deplacement);
					}

					List<Integer> endessous = new ArrayList<Integer>();
					endessous.add(coords.get(0));
					endessous.add(coords.get(1) + 1);
					deplacement = new ArrayList<Integer>();
					deplacement.add(positionOrigine.get(0));
					deplacement.add(positionOrigine.get(1));
					deplacement.add(endessous.get(0));
					deplacement.add(endessous.get(1));
					if(Math.abs(endessous.get(1) - porteeYCopie.get(0)) + 1 <= 5 && !endessous.equals(positionOrigine) && !positionsOccupees.contains(endessous) && !deplacementsPossibles.contains(deplacement)) {						
						deplacementsPossibles.add(deplacement);
					}

					List<Integer> adroite = new ArrayList<Integer>();
					adroite.add(coords.get(0) + 1);
					adroite.add(coords.get(1));
					deplacement = new ArrayList<Integer>();
					deplacement.add(positionOrigine.get(0));
					deplacement.add(positionOrigine.get(1));
					deplacement.add(adroite.get(0));
					deplacement.add(adroite.get(1));
					if(!adroite.equals(positionOrigine) && Math.abs(adroite.get(0) - porteeXCopie.get(0)) + 1 <= 3 && !positionsOccupees.contains(adroite) && !deplacementsPossibles.contains(deplacement)) {						
						deplacementsPossibles.add(deplacement);
					}

					List<Integer> agauche = new ArrayList<Integer>();
					agauche.add(coords.get(0) - 1);
					agauche.add(coords.get(1));
					deplacement = new ArrayList<Integer>();
					deplacement.add(positionOrigine.get(0));
					deplacement.add(positionOrigine.get(1));
					deplacement.add(agauche.get(0));
					deplacement.add(agauche.get(1));
					if(!agauche.equals(positionOrigine) && Math.abs(agauche.get(0) - porteeXCopie.get(1)) + 1 <= 3 && !positionsOccupees.contains(agauche) && !deplacementsPossibles.contains(deplacement)) {						
						deplacementsPossibles.add(deplacement);
					}
				}
			}
		}

		
		return deplacementsPossibles;
	}
	
	public List<List<Integer>> getPositionnementsPossibles() {
		List<List<Integer>> positionnementsPossibles = new ArrayList<List<Integer>>();

		if(this.cartesPosees == 0) {
			List<Integer> coords = new ArrayList<Integer>();
			coords.add(0);
			coords.add(0);
			positionnementsPossibles.add(coords);
		}
		else {
			int nombreColonnes = Math.abs(this.porteeX.get(1) - this.porteeX.get(0)) + 1;
			int nombreRangees = Math.abs(this.porteeY.get(1) - this.porteeY.get(0)) + 1;

			List<List<Integer>> positionsOccupees = new ArrayList<List<Integer>>();
			for(List<Integer> coords : this.disposition.keySet()) {
				positionsOccupees.add(coords);
			}

			if(nombreRangees < 4 && nombreColonnes < 4) {
				for(List<Integer> coords : positionsOccupees) {
					List<Integer> audessus = new ArrayList<Integer>();
					audessus.add(coords.get(0));
					audessus.add(coords.get(1) - 1);
					if(!positionsOccupees.contains(audessus) && !positionnementsPossibles.contains(audessus)) {
						positionnementsPossibles.add(audessus);
					}

					List<Integer> endessous = new ArrayList<Integer>();
					endessous.add(coords.get(0));
					endessous.add(coords.get(1) + 1);
					if(!positionsOccupees.contains(endessous) && !positionnementsPossibles.contains(endessous)) {
						positionnementsPossibles.add(endessous);
					}

					List<Integer> adroite = new ArrayList<Integer>();
					adroite.add(coords.get(0) + 1);
					adroite.add(coords.get(1));
					if(!positionsOccupees.contains(adroite) && !positionnementsPossibles.contains(adroite)) {
						positionnementsPossibles.add(adroite);
					}

					List<Integer> agauche = new ArrayList<Integer>();
					agauche.add(coords.get(0) - 1);
					agauche.add(coords.get(1));
					if(!positionsOccupees.contains(agauche) && !positionnementsPossibles.contains(agauche)) {
						positionnementsPossibles.add(agauche);
					}
				}
			}
			else if(nombreColonnes > 3) {
				System.out.println("Colonnes : " + nombreColonnes);
				for(List<Integer> coords : positionsOccupees) {
					List<Integer> audessus = new ArrayList<Integer>();
					audessus.add(coords.get(0));
					audessus.add(coords.get(1) - 1);
					if(Math.abs(audessus.get(1) - this.porteeY.get(1)) + 1 <= 3 && !positionsOccupees.contains(audessus) && !positionnementsPossibles.contains(audessus)) {					
						positionnementsPossibles.add(audessus);
					}

					List<Integer> endessous = new ArrayList<Integer>();
					endessous.add(coords.get(0));
					endessous.add(coords.get(1) + 1);
					if(Math.abs(endessous.get(1) - this.porteeY.get(0)) + 1 <= 3 && !positionsOccupees.contains(endessous) && !positionnementsPossibles.contains(endessous)) {						
						positionnementsPossibles.add(endessous);
					}

					List<Integer> adroite = new ArrayList<Integer>();
					adroite.add(coords.get(0) + 1);
					adroite.add(coords.get(1));
					if(Math.abs(adroite.get(0) - this.porteeX.get(0)) + 1 <= 5 && !positionsOccupees.contains(adroite) && !positionnementsPossibles.contains(adroite)) {					
						positionnementsPossibles.add(adroite);
					}

					List<Integer> agauche = new ArrayList<Integer>();
					agauche.add(coords.get(0) - 1);
					agauche.add(coords.get(1));
					if(Math.abs(agauche.get(0) - this.porteeX.get(1)) + 1 <= 5 && !positionsOccupees.contains(agauche) && !positionnementsPossibles.contains(agauche)) {						
						positionnementsPossibles.add(agauche);
					}
				}
			}
			else {
				System.out.println("Rangees : " + nombreRangees);
				for(List<Integer> coords : positionsOccupees) {
					List<Integer> audessus = new ArrayList<Integer>();
					audessus.add(coords.get(0));
					audessus.add(coords.get(1) - 1);
					if(Math.abs(audessus.get(1) - this.porteeY.get(1)) + 1 <= 5 && !positionsOccupees.contains(audessus) && !positionnementsPossibles.contains(audessus)) {						
						positionnementsPossibles.add(audessus);
					}

					List<Integer> endessous = new ArrayList<Integer>();
					endessous.add(coords.get(0));
					endessous.add(coords.get(1) + 1);
					if(Math.abs(endessous.get(1) - this.porteeY.get(0)) + 1 <= 5 && !positionsOccupees.contains(endessous) && !positionnementsPossibles.contains(endessous)) {						
						positionnementsPossibles.add(endessous);
					}

					List<Integer> adroite = new ArrayList<Integer>();
					adroite.add(coords.get(0) + 1);
					adroite.add(coords.get(1));
					if(Math.abs(adroite.get(0) - this.porteeX.get(0)) + 1 <= 3 && !positionsOccupees.contains(adroite) && !positionnementsPossibles.contains(adroite)) {						
						positionnementsPossibles.add(adroite);
					}

					List<Integer> agauche = new ArrayList<Integer>();
					agauche.add(coords.get(0) - 1);
					agauche.add(coords.get(1));
					if(Math.abs(agauche.get(0) - this.porteeX.get(1)) + 1 <= 3 && !positionsOccupees.contains(agauche) && !positionnementsPossibles.contains(agauche)) {
						positionnementsPossibles.add(agauche);
					}
				}
			}
		}
		
		return positionnementsPossibles;
	}
	
	public List<Integer> getPorteeX() {
		return this.porteeX;
	}

	public List<Integer> getPorteeY() {
		return this.porteeY;
	}

	public Map<List<Integer>, Carte> getDisposition() {
		return this.disposition;
	}

	public int getCartesPosees() {
		return this.cartesPosees;
	}
}