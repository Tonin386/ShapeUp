package lo02.shapeup.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JRadioButton;

import lo02.shapeup.partie.Partie;

public class ControleurRegles {
	
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
