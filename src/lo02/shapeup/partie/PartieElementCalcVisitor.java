package lo02.shapeup.partie;

import java.util.List;
import java.util.ArrayList;

/**
*	La classe PartieElementCalcVisitor implémente l'interface PartieElementVisitor et permet de réaliser un calcul des scores sur une partie.
*
*	@author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
*	@version 1.0
*	@see lo02.shapeup.partie.PartieElementVisitor
*/
public class PartieElementCalcVisitor implements PartieElementVisitor {
	
	/**
	 * Contient la liste des joueurs de la partie.
	 */
	private ArrayList<JoueurStrategy> joueurs;

	/**
	 * Contient le plateau de la partie.
	 */
	private Plateau plateau;

	/**
	 * Instancie un nouveau PartieElementCalcVisitor.
	 */
	public PartieElementCalcVisitor() {
		this.joueurs = new ArrayList<JoueurStrategy>();
	}
	
	/**
	 * Visite le plateau spécifié.
	 * @param plateau le plateau à visiter.
	 */
	@Override
	public void visit(Plateau plateau) {
		this.plateau = plateau;
	}

	/**
	 * Visite le joueur spécifié et l'ajoute à la liste des joueurs.
	 * @param joueur le joueur à visiter
	 */
	@Override
	public void visit(JoueurStrategy joueur) {
		this.joueurs.add(joueur);
	}
	
	/**
	 * Permet le calcul des scores d'une partie en fonction des éléments précedemment visités.
	 * @return une liste de taille égale au nombre de joueurs dans la partie, contenant le score de chaque joueur
	 */
	@Override
	public ArrayList<Integer> obtenirScoresPartie() {
		
		ArrayList<Integer> scores = new ArrayList<Integer>();
		for(int i = 0; i < this.joueurs.size(); i++) {
			scores.add(0);
		}
		
		ArrayList<Integer> scoresFormes = this.calculerScoresFormes();
		ArrayList<Integer> scoresRemplissages = this.calculerScoresRemplissages();
		ArrayList<Integer> scoresCouleurs = this.calculerScoresCouleurs();
		
		for(int i = 0; i < scores.size(); i++) {
			scores.set(i, scoresFormes.get(i));
		}

		for(int i = 0; i < scores.size(); i++) {
			scores.set(i, scores.get(i) + scoresRemplissages.get(i));
		}
		
		for(int i = 0; i < scores.size(); i++) {
			scores.set(i, scores.get(i) + scoresCouleurs.get(i));
		}
		
		return scores;
	}
	
	/**
	 * Calcule les points obtenus grâce à la disposition des cartes selon leur forme
	 * @return une liste des points obtenus pour chaque joueur
	 */
	private ArrayList<Integer> calculerScoresFormes() {
		ArrayList<Integer> scores = new ArrayList<Integer>();
		for(int i = 0; i < this.joueurs.size(); i++) {
			scores.add(0);
		}
		Plateau p = this.plateau;
		
		//calcul en horizontal
		for(int i = 0; i < scores.size(); i++) {
			scores.set(i, 0);
			String forme = this.joueurs.get(i).getCarteVictorieuse().getForme();

			for(int j = p.getPorteeY().get(0); j <= p.getPorteeY().get(1); j++) {
				int k = p.getPorteeX().get(0);
				int aLaSuite = 0;
				while(k != p.getPorteeX().get(1) + 1) {

					boolean finirSerie = false;
					List<Integer> key = new ArrayList<Integer>();
					key.add(k);
					key.add(j);

					if(p.getDisposition().get(key) != null) {
						if(p.getDisposition().get(key).getForme() == forme) {
							aLaSuite += 1;
						}
						else {
							finirSerie = true;
						}
					}
					else {
						finirSerie = true;
					}

					if(k+1 == p.getPorteeX().get(1) + 1) {
						finirSerie = true;
					}

					if(finirSerie && aLaSuite >= 2) {
						scores.set(i, scores.get(i) + aLaSuite - 1);
					}

					if(finirSerie) {
						aLaSuite = 0;
					}
					k++;
				}
			}
		}

		//calcul en vertical
		for(int i = 0; i < scores.size(); i++) {
			String forme = this.joueurs.get(i).getCarteVictorieuse().getForme();

			for(int j = p.getPorteeX().get(0); j <= p.getPorteeX().get(1); j++) {
				int k = p.getPorteeY().get(0);
				int aLaSuite = 0;
				while(k != p.getPorteeY().get(1) + 1) {

					boolean finirSerie = false;
					List<Integer> key = new ArrayList<Integer>();
					key.add(j);
					key.add(k);

					if(p.getDisposition().get(key) != null) {
						if(p.getDisposition().get(key).getForme() == forme) {
							aLaSuite += 1;
						}
						else {
							finirSerie = true;
						}
					}
					else {
						finirSerie = true;
					}

					if(k+1 == p.getPorteeY().get(1) + 1) {
						finirSerie = true;
					}

					if(finirSerie && aLaSuite >= 2) {
						scores.set(i, scores.get(i) + aLaSuite - 1);
					}

					if(finirSerie) {
						aLaSuite = 0;
					}

					k++;
				}
			}
		}
		
		return scores;
	}
	

	/**
	 * Calcule les points obtenus grâce à la disposition des cartes selon leur remplissage
	 * @return une liste des points obtenus pour chaque joueur
	 */
	private ArrayList<Integer> calculerScoresRemplissages() {
		ArrayList<Integer> scores = new ArrayList<Integer>();
		for(int i = 0; i < this.joueurs.size(); i++) {
			scores.add(0);
		}
		Plateau p = this.plateau;
		
		//calcul en horizontal
		for(int i = 0; i < scores.size(); i++) {
			scores.set(i, 0);
			int remplissage = this.joueurs.get(i).getCarteVictorieuse().getRemplissage();

			for(int j = p.getPorteeY().get(0); j <= p.getPorteeY().get(1); j++) {
				int k = p.getPorteeX().get(0);
				int aLaSuite = 0;
				while(k != p.getPorteeX().get(1) + 1) {

					boolean finirSerie = false;
					List<Integer> key = new ArrayList<Integer>();
					key.add(k);
					key.add(j);

					if(p.getDisposition().get(key) != null) {
						if(p.getDisposition().get(key).getRemplissage() == remplissage) {
							aLaSuite += 1;
						}
						else {
							finirSerie = true;
						}
					}
					else {
						finirSerie = true;
					}
					
					if(k+1 == p.getPorteeX().get(1) + 1) {
						finirSerie = true;
					}

					if(finirSerie && aLaSuite >= 3) {
						scores.set(i, scores.get(i) + aLaSuite);
					}

					if(finirSerie) {
						aLaSuite = 0;
					}
					k++;
				}
			}
		}

		//calcul en vertical
		for(int i = 0; i < scores.size(); i++) {
			int remplissage = this.joueurs.get(i).getCarteVictorieuse().getRemplissage();

			for(int j = p.getPorteeX().get(0); j <= p.getPorteeX().get(1); j++) {
				int k = p.getPorteeY().get(0);
				int aLaSuite = 0;
				while(k != p.getPorteeY().get(1) + 1) {

					boolean finirSerie = false;
					List<Integer> key = new ArrayList<Integer>();
					key.add(j);
					key.add(k);

					if(p.getDisposition().get(key) != null) {
						if(p.getDisposition().get(key).getRemplissage() == remplissage) {
							aLaSuite += 1;
						}
						else {
							finirSerie = true;
						}
					}
					else {
						finirSerie = true;
					}
					
					if(k+1 == p.getPorteeY().get(1) + 1) {
						finirSerie = true;
					}

					if(finirSerie && aLaSuite >= 3) {
						scores.set(i, scores.get(i) + aLaSuite);
					}

					if(finirSerie) {
						aLaSuite = 0;
					}
					k++;
				}
			}
		}
		
		return scores;
	}
	
	/**
	 * Calcule les points obtenus grâce à la disposition des cartes selon leur couleur
	 * @return une liste des points obtenus pour chaque joueur
	 */
	private ArrayList<Integer> calculerScoresCouleurs() {
		ArrayList<Integer> scores = new ArrayList<Integer>();
		for(int i = 0; i < this.joueurs.size(); i++) {
			scores.add(0);
		}
		Plateau p = this.plateau;
		
		//calcul en horizontal
		for(int i = 0; i < scores.size(); i++) {
			scores.set(i, 0);
			String couleur = this.joueurs.get(i).getCarteVictorieuse().getCouleur();

			for(int j = p.getPorteeY().get(0); j <= p.getPorteeY().get(1); j++) {
				int k = p.getPorteeX().get(0);
				int aLaSuite = 0;
				while(k != p.getPorteeX().get(1) + 1) {

					boolean finirSerie = false;
					List<Integer> key = new ArrayList<Integer>();
					key.add(k);
					key.add(j);

					if(p.getDisposition().get(key) != null) {
						if(p.getDisposition().get(key).getCouleur() == couleur) {
							aLaSuite += 1;
						}
						else {
							finirSerie = true;
						}
					}
					else {
						finirSerie = true;
					}
					
					if(k+1 == p.getPorteeX().get(1) + 1) {
						finirSerie = true;
					}

					if(finirSerie && aLaSuite >= 3) {
						scores.set(i, scores.get(i) + aLaSuite + 1);
					}

					if(finirSerie) {
						aLaSuite = 0;
					}
					k++;
				}
			}
		}

		//calcul en vertical
		for(int i = 0; i < scores.size(); i++) {
			String couleur = this.joueurs.get(i).getCarteVictorieuse().getCouleur();

			for(int j = p.getPorteeX().get(0); j <= p.getPorteeX().get(1); j++) {
				int k = p.getPorteeY().get(0);
				int aLaSuite = 0;
				while(k != p.getPorteeY().get(1) + 1) {

					boolean finirSerie = false;
					List<Integer> key = new ArrayList<Integer>();
					key.add(j);
					key.add(k);

					if(p.getDisposition().get(key) != null) {
						if(p.getDisposition().get(key).getCouleur() == couleur) {
							aLaSuite += 1;
						}
						else {
							finirSerie = true;
						}
					}
					else {
						finirSerie = true;
					}
					
					if(k+1 == p.getPorteeY().get(1) + 1) {
						finirSerie = true;
					}

					if(finirSerie && aLaSuite >= 3) {
						scores.set(i, scores.get(i) + aLaSuite + 1);
					}

					if(finirSerie) {
						aLaSuite = 0;
					}
					k++;
				}
			}
		}
		
		return scores;
	}
}