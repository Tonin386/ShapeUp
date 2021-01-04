package lo02.shapeup.partie;

import java.util.List;
import java.util.ArrayList;


public class PartieElementCalcVisitor implements PartieElementVisitor {
	
	private Carte carteC;
	private ArrayList<Joueur> joueurs;
	private Plateau plateau;
	private Banque banque;

	public PartieElementCalcVisitor() {
		this.joueurs = new ArrayList<Joueur>();
	}
	
	@Override
	public void visit(Carte carte) {
		this.carteC = carte;
	}
	

	@Override
	public void visit(Plateau plateau) {
		this.plateau = plateau;
	}
	

	@Override
	public void visit(Joueur joueur) {
		this.joueurs.add(joueur);
	}
	

	@Override
	public void visit(Banque banque) {
		this.banque = banque;
	}

	@Override
	public ArrayList<Integer> visitPartie(Partie partie) {
		
		PartieElement[] elements = partie.getElements();
		
		for(PartieElement e : elements) {
			if(e != null) e.accept(this);
		}
		
		ArrayList<Integer> scores = new ArrayList<Integer>();
		for(int i = 0; i < this.joueurs.size(); i++) {
			scores.add(0);
		}
		
		ArrayList<Integer> scoresFormes = this.calculerScoresFormes(partie);
		ArrayList<Integer> scoresRemplissages = this.calculerScoresRemplissages(partie);
		ArrayList<Integer> scoresCouleurs = this.calculerScoresCouleurs(partie);
		
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
	
	private ArrayList<Integer> calculerScoresFormes(Partie partie) {
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
	
	private ArrayList<Integer> calculerScoresRemplissages(Partie partie) {
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
	
	private ArrayList<Integer> calculerScoresCouleurs(Partie partie) {
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