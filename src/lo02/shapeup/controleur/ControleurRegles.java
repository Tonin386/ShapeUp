package lo02.shapeup.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JRadioButton;

import lo02.shapeup.partie.Partie;

/**
*	La classe ControleurRegles permet de contr�ler les interactions entre la vue et les mod�les dans le menu du choix des r�gles.
*	Elle permet d'informer la partie des r�gles choisies, ainsi que du nombre de joueurs.
*
*	@author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
*	@version 1.0
*	@see lo02.shapeup.partie.Partie
*/
public class ControleurRegles {
	
	/**
	 * Instancie un nouveau ControleurRegles
	 * @param partie la Partie � contr�ler
	 * @param b le JButton permettant de valider la s�lection des r�gles.
	 * @param b1 le JRadioButton repr�sentant le choix des r�gles num�ro 1 pour la partie. 
	 * @param b2 le JRadioButton repr�sentant le choix des r�gles num�ro 2 pour la partie.
	 * @param b3 le JRadioButton repr�sentant le choix des r�gles num�ro 3 pour la partie.
	 * @param b4 le JRadioButton repr�sentant le choix des r�gles num�ro 4 pour la partie.
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
