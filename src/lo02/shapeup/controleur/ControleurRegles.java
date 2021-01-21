package lo02.shapeup.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JRadioButton;

import lo02.shapeup.partie.Partie;

/**
*	La classe ControleurRegles permet de contrôler les interactions entre la vue et les modèles dans le menu du choix des règles.
*	Elle permet d'informer la partie des règles choisies, ainsi que du nombre de joueurs.
*
*	@author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
*	@version 1.0
*	@see lo02.shapeup.partie.Partie
*/
public class ControleurRegles {
	
	/**
	 * Instancie un nouveau ControleurRegles
	 * @param partie la Partie à contrôler
	 * @param b le JButton permettant de valider la sélection des règles.
	 * @param b1 le JRadioButton représentant le choix des règles numéro 1 pour la partie. 
	 * @param b2 le JRadioButton représentant le choix des règles numéro 2 pour la partie.
	 * @param b3 le JRadioButton représentant le choix des règles numéro 3 pour la partie.
	 * @param b4 le JRadioButton représentant le choix des règles numéro 4 pour la partie.
	 */
	public ControleurRegles(Partie partie, JButton b, JRadioButton b1, JRadioButton b2, JRadioButton b3, JRadioButton b4) {

		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(b1.isSelected()) {
					partie.setModeDeJeu(0);
				}
				else if(b2.isSelected()) {
					partie.setModeDeJeu(1);
				}
				else if(b3.isSelected()) {
					partie.setModeDeJeu(2);
				}
				else {
					partie.setModeDeJeu(3);
				}
			}
		});
	}
}
