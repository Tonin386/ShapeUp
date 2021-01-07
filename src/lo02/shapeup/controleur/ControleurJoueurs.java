package lo02.shapeup.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import lo02.shapeup.partie.JoueurPhysiqueStrategy;
import lo02.shapeup.partie.JoueurStrategy;
import lo02.shapeup.partie.JoueurVirtuelStrategy;
import lo02.shapeup.partie.Partie;

public class ControleurJoueurs {

	private Partie partie;
	private List<JTextField> tfNoms;
	private List<JRadioButton> rbTypes;
	private JButton valider;
	
	public ControleurJoueurs(Partie partie) {

		this.partie = partie;
		this.tfNoms = new ArrayList<JTextField>();
		this.rbTypes = new ArrayList<JRadioButton>();
	}

	public void addTextField(JTextField tf) {
		this.tfNoms.add(tf);
	}

	public void addRadioButton(JRadioButton rb) {
		this.rbTypes.add(rb);
	}

	public void setBoutonValider(JButton b) {
		this.valider = b;
	}

	public void activate() {
		Partie partie = this.partie;
		List<JTextField> tf = this.tfNoms;
		List<JRadioButton> rb = this.rbTypes;

		this.valider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = partie.getJoueurADefinir() ; i < partie.getJoueursADefinir(); i++) {
					JoueurStrategy j;
					if(rb.get(i*2).isSelected()) {
						j = new JoueurPhysiqueStrategy();
					}
					else {
						j = new JoueurVirtuelStrategy();
					}

					j.setNom(tf.get(i).getText());
					partie.ajouterJoueur(j);
					partie.signalJoueurDefini();
				}

				partie.signalJoueursDefinis();
				partie.debutJeu();
				partie.jouer();
			};
		});
	}
}