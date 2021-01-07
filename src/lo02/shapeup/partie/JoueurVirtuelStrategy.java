package lo02.shapeup.partie;

import java.util.List;
import java.util.ListIterator;
import java.lang.Math;

public class JoueurVirtuelStrategy extends JoueurStrategy {

	public JoueurVirtuelStrategy() {
		super();
	}

	public JoueurVirtuelStrategy(String nom) {
		super(nom);
	}

	public void jouer(Partie partie) {

		if(partie.peutDeplacer()) {
			List<Integer> dep = this.choisirDeplacement(partie);
			partie.deplacerCarte(dep.get(0), dep.get(1), dep.get(2), dep.get(3));
		}

		if(partie.peutPoser()) {
			List<Integer> pos = this.choisirPosition(partie);
			Carte c = this.choisirCarte(partie);
			partie.poserCarte(pos.get(0), pos.get(1), c);
			this.poserCarte(c);
		}

		partie.finirTour();
	}

	private Carte choisirCarte(Partie partie) {
		return this.jeu.get((int) (Math.random() * this.jeu.size()));

		// PartieElementCalcVisitor visitor = new PartieElementCalcVisitor();
		// this.accept(visitor);
		// partie.getPlateau().accept(visitor);

		// int meilleureOption = 0;
		// int scoreMax = 0;

		// if(this.victorieuse == null) {
		// 	int meilleureVictorieuse = 0;

		// 	for(int i = 0; i < this.jeu.size(); i++) {
		// 		this.victorieuse = this.jeu.get(i);
		// 		int score = visitor.visitPartie(partie).get(0);
		// 		if(score > scoreMax) {
		// 			meilleureVictorieuse = i;
		// 			scoreMax = score;
		// 		}
		// 	}

		// 	this.victorieuse = this.jeu.get(meilleureVictorieuse);

		// 	scoreMax = 0;

		// 	for(int i = 0; i < this.jeu.size(); i++) {
		// 		if(i != meilleureVictorieuse) {
		// 			List<List<Integer>> positions = partie.getPlateau().getPositionnementsPossibles();
		// 			ListIterator<List<Integer>> iterator = positions.listIterator();
		// 			while(iterator.hasNext()) {
		// 				List<Integer> position = iterator.next();
		// 				Partie futurePartie = partie;
		// 				futurePartie.getPlateau().setDisposition(partie.getPlateau().copyPlateau());
		// 				futurePartie.getPlateau().poserCarte(position.get(0), position.get(1), this.jeu.get(i));
		// 				futurePartie.getPlateau().accept(visitor);
		// 				int score = visitor.visitPartie(futurePartie).get(0);
		// 				if(score >= scoreMax) {
		// 					meilleureOption = i;
		// 					scoreMax = score;
		// 				}
		// 			}
		// 		}
		// 	}

		// 	this.victorieuse = null;
		// }
		
		// return this.jeu.get(meilleureOption);
	}

	private List<Integer> choisirPosition(Partie partie) {
		return partie.getPositionnementsPossibles().get((int) (Math.random() * partie.getPositionnementsPossibles().size()));

		// System.out.println("Bonjour !");
		// PartieElementCalcVisitor visitor = new PartieElementCalcVisitor();
		// this.accept(visitor);
		// partie.getPlateau().accept(visitor);

		// List<Integer> meilleureOption = null;
		// int scoreMax = 0;

		// if(this.victorieuse == null) {
		// 	int meilleureVictorieuse = 0;
		// 	for(int i = 0; i < this.jeu.size(); i++) {
		// 		this.victorieuse = this.jeu.get(i);
		// 		int score = visitor.visitPartie(partie).get(0);
		// 		System.out.println("Score calculé 1 : " + score);
		// 		if(score >= scoreMax) {
		// 			meilleureVictorieuse = i;
		// 			scoreMax = score;
		// 		}
		// 	}

		// 	this.victorieuse = this.jeu.get(meilleureVictorieuse);

		// 	scoreMax = 0;

		// 	for(int i = 0; i < this.jeu.size(); i++) {
		// 		if(i != meilleureVictorieuse) {
		// 			List<List<Integer>> positions = partie.getPlateau().getPositionnementsPossibles();
		// 			System.out.println("Taille positions : " + positions.size());
		// 			ListIterator<List<Integer>> iterator = positions.listIterator();
		// 			while(iterator.hasNext()) {
		// 				List<Integer> position = iterator.next();
		// 				Partie futurePartie = partie;
		// 				futurePartie.getPlateau().setDisposition(partie.getPlateau().copyPlateau());
		// 				futurePartie.getPlateau().poserCarte(position.get(0), position.get(1), this.jeu.get(i));
		// 				futurePartie.getPlateau().accept(visitor);
		// 				int score = visitor.visitPartie(futurePartie).get(0);
		// 				System.out.println("Score calculé 2 : " + score);
		// 				if(score >= scoreMax) {
		// 					meilleureOption = position;
		// 					scoreMax = score;
		// 				}
		// 			}
		// 		}
		// 	}

		// 	this.victorieuse = null;
		// }
		// else {
		// 	List<List<Integer>> positions = partie.getPlateau().getPositionnementsPossibles();
		// 	System.out.println("Taille positions : " + positions.size());
		// 	ListIterator<List<Integer>> iterator = positions.listIterator();
		// 	while(iterator.hasNext()) {
		// 		List<Integer> position = iterator.next();
		// 		Partie futurePartie = partie;
		// 		futurePartie.getPlateau().setDisposition(partie.getPlateau().copyPlateau());
		// 		futurePartie.getPlateau().poserCarte(position.get(0), position.get(1), this.jeu.get(0));
		// 		futurePartie.getPlateau().accept(visitor);
		// 		int score = visitor.visitPartie(futurePartie).get(0);
		// 		System.out.println("Score calculé 2 : " + score);
		// 		if(score >= scoreMax) {
		// 			meilleureOption = position;
		// 			scoreMax = score;
		// 		}
		// 	}
		// }

		// return meilleureOption;
	}

	private List<Integer> choisirDeplacement(Partie partie) {
		return partie.getDeplacementsPossibles().get((int) (Math.random() * partie.getDeplacementsPossibles().size()));
		// PartieElementCalcVisitor visitor = new PartieElementCalcVisitor();
		// this.accept(visitor);
		// partie.getPlateau().accept(visitor);

		// List<Integer> meilleureOption = null;
		// int scoreMax = 0;

		// if(this.victorieuse == null) {
		// 	int meilleureVictorieuse = 0;
		// 	for(int i = 0; i < this.jeu.size(); i++) {
		// 		this.victorieuse = this.jeu.get(i);
		// 		int score = visitor.visitPartie(partie).get(0);
		// 		if(score > scoreMax) {
		// 			meilleureVictorieuse = i;
		// 			scoreMax = score;
		// 		}
		// 	}

		// 	this.victorieuse = this.jeu.get(meilleureVictorieuse);

		// 	scoreMax = 0;

		// 	List<List<Integer>> deplacements = partie.getPlateau().getDeplacementsPossibles();
		// 	ListIterator<List<Integer>> iterator = deplacements.listIterator();
		// 	while(iterator.hasNext()) {
		// 		List<Integer> deplacement = iterator.next();
		// 		Partie futurePartie = partie;
		// 		futurePartie.getPlateau().setDisposition(partie.getPlateau().copyPlateau());
		// 		futurePartie.getPlateau().deplacerCarte(deplacement.get(0), deplacement.get(1), deplacement.get(2), deplacement.get(3));
		// 		futurePartie.getPlateau().accept(visitor);
		// 		int score = visitor.visitPartie(futurePartie).get(0);
		// 		if(score >= scoreMax) {
		// 			meilleureOption = deplacement;
		// 			scoreMax = score;
		// 		}
		// 	}
		// }
		// else {
		// 	List<List<Integer>> deplacements = partie.getPlateau().getDeplacementsPossibles();
		// 	ListIterator<List<Integer>> iterator = deplacements.listIterator();
		// 	while(iterator.hasNext()) {
		// 		List<Integer> deplacement = iterator.next();
		// 		Partie futurePartie = partie;
		// 		futurePartie.getPlateau().setDisposition(partie.getPlateau().copyPlateau());
		// 		futurePartie.getPlateau().deplacerCarte(deplacement.get(0), deplacement.get(1), deplacement.get(2), deplacement.get(3));
		// 		futurePartie.getPlateau().accept(visitor);
		// 		int score = visitor.visitPartie(futurePartie).get(0);
		// 		if(score >= scoreMax) {
		// 			meilleureOption = deplacement;
		// 			scoreMax = score;
		// 		}
		// 	}
		// }

		// return meilleureOption;
	}
}