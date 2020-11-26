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
			this.porteeX.set(1, position.get(1));
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

		if(positionArrivee.get(0) < this.porteeX.get(0)) {
			this.porteeX.set(0, positionArrivee.get(0));
		}
		else if(positionArrivee.get(0) > this.porteeX.get(1)) {
			this.porteeX.set(1, positionArrivee.get(0));
		}
		else if(positionArrivee.get(1) < this.porteeY.get(0)) {
			this.porteeY.set(0, positionArrivee.get(1));
		}
		else if(positionArrivee.get(1) > this.porteeY.get(1)) {
			this.porteeX.set(1, positionArrivee.get(1));
		}

		this.disposition.put(positionArrivee, this.disposition.get(positionOrigine));
		this.disposition.remove(positionOrigine);
	}
	
	public List<List<Integer>> getDeplacementsPossibles() {
		List<List<Integer>> deplacementsPossibles = new ArrayList<List<Integer>>();

		for(Map.Entry<List<Integer>, Carte> entry : this.disposition.entrySet()) {

			List<Integer> positionOrigine = entry.getKey();
			List<Integer> deplacement = new ArrayList<Integer>();
			deplacement.add(0);
			deplacement.add(0);
			deplacement.add(0);
			deplacement.add(0);

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
					porteeXCopie.set(1, position.get(1));
				}
			}

			int nombreColonnes = Math.abs(porteeXCopie.get(1) - porteeXCopie.get(0));
			int nombreRangees = Math.abs(porteeYCopie.get(1) - porteeYCopie.get(0));

			List<List<Integer>> positionsOccupees = new ArrayList<List<Integer>>();
			for(List<Integer> coords : copieDisposition.keySet()) {
				positionsOccupees.add(coords);
			}

			if(nombreRangees < 4 && nombreColonnes < 4) {
				for(List<Integer> coords : positionsOccupees) {
					List<Integer> audessus = new ArrayList<Integer>();
					audessus.add(coords.get(0));
					audessus.add(coords.get(1) - 1);
					deplacement.set(0, positionOrigine.get(0));
					deplacement.set(1, positionOrigine.get(1));
					deplacement.set(2, audessus.get(0));
					deplacement.set(3, audessus.get(1));
					if(audessus != positionOrigine && !positionsOccupees.contains(audessus) && !deplacementsPossibles.contains(deplacement)) {
						deplacementsPossibles.add(deplacement);
					}

					List<Integer> endessous = new ArrayList<Integer>();
					endessous.add(coords.get(0));
					endessous.add(coords.get(1) + 1);
					deplacement.set(0, positionOrigine.get(0));
					deplacement.set(1, positionOrigine.get(1));
					deplacement.set(2, endessous.get(0));
					deplacement.set(3, endessous.get(1));
					if(endessous != positionOrigine && !positionsOccupees.contains(endessous) && !deplacementsPossibles.contains(deplacement)) {
						deplacementsPossibles.add(deplacement);
					}

					List<Integer> adroite = new ArrayList<Integer>();
					adroite.add(coords.get(0) + 1);
					adroite.add(coords.get(1));
					deplacement.set(0, positionOrigine.get(0));
					deplacement.set(1, positionOrigine.get(1));
					deplacement.set(2, adroite.get(0));
					deplacement.set(3, adroite.get(1));
					if(adroite != positionOrigine && !positionsOccupees.contains(adroite) && !deplacementsPossibles.contains(deplacement)) {
						deplacementsPossibles.add(deplacement);
					}

					List<Integer> agauche = new ArrayList<Integer>();
					agauche.add(coords.get(0) - 1);
					agauche.add(coords.get(1));
					deplacement.set(0, positionOrigine.get(0));
					deplacement.set(1, positionOrigine.get(1));
					deplacement.set(2, agauche.get(0));
					deplacement.set(3, agauche.get(1));
					if(agauche != positionOrigine && !positionsOccupees.contains(agauche) && !deplacementsPossibles.contains(deplacement)) {
						deplacementsPossibles.add(deplacement);
					}
				}
			}
			else if(nombreColonnes > 3) {
				for(List<Integer> coords : positionsOccupees) {
					List<Integer> audessus = new ArrayList<Integer>();
					audessus.add(coords.get(0));
					audessus.add(coords.get(1) - 1);
					deplacement.set(0, positionOrigine.get(0));
					deplacement.set(1, positionOrigine.get(1));
					deplacement.set(2, audessus.get(0));
					deplacement.set(3, audessus.get(1));
					if(audessus != positionOrigine && audessus.get(1) >= porteeYCopie.get(0) && !positionsOccupees.contains(audessus) && !deplacementsPossibles.contains(deplacement)) {					
						deplacementsPossibles.add(deplacement);
					}

					List<Integer> endessous = new ArrayList<Integer>();
					endessous.add(coords.get(0));
					endessous.add(coords.get(1) + 1);
					deplacement.set(0, positionOrigine.get(0));
					deplacement.set(1, positionOrigine.get(1));
					deplacement.set(2, endessous.get(0));
					deplacement.set(3, endessous.get(1));
					if(endessous != positionOrigine && endessous.get(1) <= porteeYCopie.get(1) && !positionsOccupees.contains(endessous) && !deplacementsPossibles.contains(deplacement)) {					
						deplacementsPossibles.add(deplacement);
					}

					List<Integer> adroite = new ArrayList<Integer>();
					adroite.add(coords.get(0) + 1);
					adroite.add(coords.get(1));
					deplacement.set(0, positionOrigine.get(0));
					deplacement.set(1, positionOrigine.get(1));
					deplacement.set(2, adroite.get(0));
					deplacement.set(3, adroite.get(1));
					if(adroite != positionOrigine && !positionsOccupees.contains(adroite) && !deplacementsPossibles.contains(deplacement)) {						
						deplacementsPossibles.add(deplacement);
					}

					List<Integer> agauche = new ArrayList<Integer>();
					agauche.add(coords.get(0) - 1);
					agauche.add(coords.get(1));
					deplacement.set(0, positionOrigine.get(0));
					deplacement.set(1, positionOrigine.get(1));
					deplacement.set(2, agauche.get(0));
					deplacement.set(3, agauche.get(1));
					if(agauche != positionOrigine && !positionsOccupees.contains(agauche) && !deplacementsPossibles.contains(agauche)) {						
						deplacementsPossibles.add(deplacement);
					}
				}
			}
			else {
				for(List<Integer> coords : positionsOccupees) {
					List<Integer> audessus = new ArrayList<Integer>();
					audessus.add(coords.get(0));
					audessus.add(coords.get(1) - 1);
					deplacement.set(0, positionOrigine.get(0));
					deplacement.set(1, positionOrigine.get(1));
					deplacement.set(2, audessus.get(0));
					deplacement.set(3, audessus.get(1));
					if(audessus != positionOrigine && !positionsOccupees.contains(audessus) && !deplacementsPossibles.contains(deplacement)) {						
						deplacementsPossibles.add(deplacement);
					}

					List<Integer> endessous = new ArrayList<Integer>();
					endessous.add(coords.get(0));
					endessous.add(coords.get(1) + 1);
					deplacement.set(0, positionOrigine.get(0));
					deplacement.set(1, positionOrigine.get(1));
					deplacement.set(2, endessous.get(0));
					deplacement.set(3, endessous.get(1));
					if(endessous != positionOrigine && !positionsOccupees.contains(endessous) && !deplacementsPossibles.contains(deplacement)) {						
						deplacementsPossibles.add(deplacement);
					}

					List<Integer> adroite = new ArrayList<Integer>();
					adroite.add(coords.get(0) + 1);
					adroite.add(coords.get(1));
					deplacement.set(0, positionOrigine.get(0));
					deplacement.set(1, positionOrigine.get(1));
					deplacement.set(2, adroite.get(0));
					deplacement.set(3, adroite.get(1));
					if(adroite != positionOrigine && adroite.get(0) <= porteeXCopie.get(1) && !positionsOccupees.contains(adroite) && !deplacementsPossibles.contains(deplacement)) {						
						deplacementsPossibles.add(deplacement);
					}

					List<Integer> agauche = new ArrayList<Integer>();
					agauche.add(coords.get(0) - 1);
					agauche.add(coords.get(1));
					deplacement.set(0, positionOrigine.get(0));
					deplacement.set(1, positionOrigine.get(1));
					deplacement.set(2, agauche.get(0));
					deplacement.set(3, agauche.get(1));
					if(agauche != positionOrigine && agauche.get(0) >= porteeXCopie.get(0) && !positionsOccupees.contains(agauche) && !deplacementsPossibles.contains(deplacement)) {						
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
			int nombreColonnes = Math.abs(this.porteeX.get(1) - this.porteeX.get(0));
			int nombreRangees = Math.abs(this.porteeY.get(1) - this.porteeY.get(0));

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
				for(List<Integer> coords : positionsOccupees) {
					List<Integer> audessus = new ArrayList<Integer>();
					audessus.add(coords.get(0));
					audessus.add(coords.get(1) - 1);
					if(audessus.get(1) >= this.porteeY.get(0) && !positionsOccupees.contains(audessus) && !positionnementsPossibles.contains(audessus)) {					
						positionnementsPossibles.add(audessus);
					}

					List<Integer> endessous = new ArrayList<Integer>();
					endessous.add(coords.get(0));
					endessous.add(coords.get(1) + 1);
					if(endessous.get(1) <= this.porteeY.get(1) && !positionsOccupees.contains(endessous) && !positionnementsPossibles.contains(endessous)) {						
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
			else {
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
					if(adroite.get(0) <= this.porteeX.get(1) && !positionsOccupees.contains(adroite) && !positionnementsPossibles.contains(adroite)) {						
						positionnementsPossibles.add(adroite);
					}

					List<Integer> agauche = new ArrayList<Integer>();
					agauche.add(coords.get(0) - 1);
					agauche.add(coords.get(1));
					if(agauche.get(0) >= this.porteeX.get(0) && !positionsOccupees.contains(agauche) && !positionnementsPossibles.contains(agauche)) {
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