package lo02.shapeup.partie;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.List;
import java.lang.Math;

/**
 *	La classe Plateau représente le plateau de la partie. Cette classe hérite de la classe Observable, et implémente l'interface PartieElement.
 *	Elle est donc observable et visitable.
 * 
 * @author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
 * @version 1.0
 * @see lo02.shapeup.partie.PartieElement
 */
@SuppressWarnings("deprecation")
public class Plateau extends Observable implements PartieElement {

	/**
	 * Représente la dispotion des cartes sur le plateau. A chaque position est associée une carte.
	 */
	private Map<List<Integer>, Carte> disposition;

	/**
	 * Contient le nombre de cartes posées sur le plateau.
	 */
	private int cartesPosees;

	/**
	 * Contient les abscisses minimum et maximum des cartes posées sur le plateau.
	 */
	private List<Integer> porteeX;

	/**
	 * Contient les ordonnées minimum et maximum des cartes posées sur le plateau.
	 */
	private List<Integer> porteeY;

	/**
	 * Instancie un nouveau Plateau.
	 */
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

	/**
	 * Accepte un PartieElementVisitor comme visiteur.
	 * @param visitor le visiteur à accepter
	 */
	public void accept(PartieElementVisitor visitor) {
		visitor.visit(this);
	}
	
	/**
	 * Permet d'ajouter une carte sur le plateau à une certaine position.
	 * @param x l'abscisse de la position de la carte
	 * @param y l'ordonnée de la position de la carte
	 * @param c la carte à poser
	 */
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
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Permet de déplacer une carte sur le plateau vers un nouvel emplacement.
	 * @param xOrigine l'abscisse d'origine de la carte à deplacer
	 * @param yOrigine l'ordonnée d'origine de la carte à déplacer
	 * @param xArrivee la nouvelle abscisse de la carte à deplacer
	 * @param yArrivee la nouvelle ordonnée de la carte à déplacer
	 */
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
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Permet d'obtenir une liste de tous les déplacements possibles.
	 * @return une liste de tous les déplacements possibles
	 */
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
	
	/**
	 * Permet d'obtenir une liste de tous les positionnements possibles.
	 * @return une liste de tous les positionnements possibles
	 */
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
	
	/**
	 * Permet d'obtenir les abscisses minimum et maximum des cartes posées sur le plateau.
	 * @return une liste contenant les abscisses minimum et maximum des cartes posées
	 */
	public List<Integer> getPorteeX() {
		return this.porteeX;
	}

	/**
	 * Permet d'obtenir les ordonnées minimum et maximum des cartes posées sur le plateau.
	 * @return une liste contenant les ordonnées minimum et maximum des cartes posées
	 */
	public List<Integer> getPorteeY() {
		return this.porteeY;
	}

	/**
	 * Permet d'obtenir une carte posée à une certaine position sur le plateau.
	 * @param x l'abscisse de la carte à récupérer
	 * @param y l'ordonnée de la carte à récupérer
	 * @return la carte récupérée
	 */
	public Carte getCartePosee(int x, int y) {
		List<Integer> position = new ArrayList<Integer>();
		position.add(x);
		position.add(y);
		return this.disposition.get(position);
	}

	/**
	 * Permet d'obtenir la map des cartes posées selon leur position.
	 * @return la map des cartes posées selon leur position
	 */
	public Map<List<Integer>, Carte> getDisposition() {
		return this.disposition;
	}

	/**
	 * Permet de définir une disposition des cartes sur le plateau.
	 * @param d la nouvelle disposition
	 */
	public void setDisposition(Map<List<Integer>, Carte> d) {
		this.disposition = d;
	}

	/**
	 * Permet de récupérer le nombre de cartes posées sur le plateau.
	 * @return le nombre de cartes posées
	 */
	public int getCartesPosees() {
		return this.cartesPosees;
	}
}