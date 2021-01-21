package lo02.shapeup.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import lo02.shapeup.partie.Carte;
import lo02.shapeup.partie.Partie;

/**
*	La classe ControleurPartie permet de contrôler les interactions entre la vue et les modèles pendant le déroulement de la partie.
*	Elle permet d'informer la partie de la pose des cartes, leur déplacement, de définir la carte choisie par le joueur dans son jeu, ainsi que finir le tour du joueur.
*
*	@author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
*	@version 1.0
*	@see lo02.shapeup.partie.Partie
*/
public class ControleurPartie {
	
	/**
	 * Instancie un nouveau ControleurPartie
	 * @param partie la Partie à contrôler
	 * @param bPoser le JButton qui permet au joueur de poser une carte choisie.
	 * @param bDeplacer le JButton qui permet au joueur de déplacer une carte d'une position vers une autre.
	 * @param bFin le JButton qui permet au joueur de finir son tour.
	 * @param rbCarte1 le JRadioButton qui représente le choix de la carte 1 dans le jeu du joueur.
	 * @param rbCarte2 le JRadioButton qui représente le choix de la carte 2 dans le jeu du joueur.
	 * @param rbCarte3 le JRadioButton qui représente le choix de la carte 3 dans le jeu du joueur.
	 * @param cPoser la JComboBox affichant au joueur toutes les positions possibles pour poser une carte.
	 * @param cDeplacer la JComboBox affichant au joueur toutes les positions possibles pour déplacer une carte.
	 */
	public ControleurPartie(Partie partie, JButton bPoser, JButton bDeplacer, JButton bFin, JRadioButton rbCarte1, JRadioButton rbCarte2, JRadioButton rbCarte3, JComboBox<String> cPoser, JComboBox<String> cDeplacer) {

		bPoser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Carte c;
				if(rbCarte3.isSelected()) {
					c = partie.getJoueurs().get(partie.getTour()).getJeu().get(2);
					partie.setIndexChoisi(2);
				}
				else if(rbCarte2.isSelected()) {
					c = partie.getJoueurs().get(partie.getTour()).getJeu().get(1);
					partie.setIndexChoisi(1);
				}
				else {
					c = partie.getJoueurs().get(partie.getTour()).getJeu().get(0);
					partie.setIndexChoisi(0);
				}

				int i = cPoser.getSelectedIndex();
				List<Integer> position = partie.getPositionnementsPossibles().get(i);
				partie.getJoueurs().get(partie.getTour()).poserCarte(c);
				partie.poserCarte(position.get(0), position.get(1), c);
			}
		});

		bDeplacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = cDeplacer.getSelectedIndex();
				List<Integer> deplacement = partie.getDeplacementsPossibles().get(i);
				partie.deplacerCarte(deplacement.get(0), deplacement.get(1), deplacement.get(2), deplacement.get(3));
			}
		});

		bFin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				partie.finirTour();
			}
		});
	}
}
