package lo02.shapeup.jeu;

import java.util.List;
import java.util.ArrayList;

public class Score {

	private Partie partie;

	public Score(Partie p) {
		this.partie = p;
	}

	public int[] calculerScoresJoueurs() {
		int[] scores = new int[this.partie.getJoueurs().length];
		int[] scoresFormes = this.calculerScoresFormes();
		int[] scoresRemplissages = this.calculerScoresRemplissages();
		int[] scoresCouleurs = this.calculerScoresCouleurs();
		
		for(int i = 0; i < scores.length; i++) {
			scores[i] = scoresFormes[i];
		}

		for(int i = 0; i < scores.length; i++) {
			scores[i] += scoresRemplissages[i];
		}
		
		for(int i = 0; i < scores.length; i++) {
			scores[i] += scoresCouleurs[i];
		}
		
		return scores;
	}
	
	private int[] calculerScoresFormes() {
		int[] scores = new int[this.partie.getJoueurs().length];
		Plateau p = this.partie.getPlateau();
		
		//calcul en horizontal
		for(int i = 0; i < scores.length; i++) {
			scores[i] = 0;
			String forme = this.partie.getJoueurs()[i].getCarteVictorieuse().getForme();

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
						scores[i] += aLaSuite - 1;
					}

					if(finirSerie) {
						aLaSuite = 0;
					}
					k++;
				}
			}
		}

		//calcul en vertical
		for(int i = 0; i < scores.length; i++) {
			String forme = this.partie.getJoueurs()[i].getCarteVictorieuse().getForme();

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
						scores[i] += aLaSuite - 1;
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
	
	private int[] calculerScoresRemplissages() {
		int[] scores = new int[this.partie.getJoueurs().length];
		Plateau p = this.partie.getPlateau();
		
		//calcul en horizontal
		for(int i = 0; i < scores.length; i++) {
			scores[i] = 0;
			int remplissage = this.partie.getJoueurs()[i].getCarteVictorieuse().getRemplissage();

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
						scores[i] += aLaSuite;
					}

					if(finirSerie) {
						aLaSuite = 0;
					}
					k++;
				}
			}
		}

		//calcul en vertical
		for(int i = 0; i < scores.length; i++) {
			int remplissage = this.partie.getJoueurs()[i].getCarteVictorieuse().getRemplissage();

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
						scores[i] += aLaSuite;
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
	
	private int[] calculerScoresCouleurs() {
		int[] scores = new int[this.partie.getJoueurs().length];
		Plateau p = this.partie.getPlateau();
		
		//calcul en horizontal
		for(int i = 0; i < scores.length; i++) {
			scores[i] = 0;
			String couleur = this.partie.getJoueurs()[i].getCarteVictorieuse().getCouleur();

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
						scores[i] += aLaSuite + 1;
					}

					if(finirSerie) {
						aLaSuite = 0;
					}
					k++;
				}
			}
		}

		//calcul en vertical
		for(int i = 0; i < scores.length; i++) {
			String couleur = this.partie.getJoueurs()[i].getCarteVictorieuse().getCouleur();

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
						scores[i] += aLaSuite + 1;
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