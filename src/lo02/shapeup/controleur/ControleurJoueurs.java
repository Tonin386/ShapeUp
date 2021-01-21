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

/**
*	La classe ControleurJoueurs permet de contr�ler les interactions entre la vue et les mod�les dans le menu de la d�finition des joueurs.
*	Elle permet la d�finition du type des joueurs, et de leur nom.
*
*	@author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
*	@version 1.0
*	@see lo02.shapeup.partie.Partie
*/
public class ControleurJoueurs {

	/**
	 * La partie contr�l�e par cette classe.
	 */
	private Partie partie;

	/**
	 * Une liste de JTextField contenant les pseudos des joueurs.
	 */
	private List<JTextField> tfNoms;

	/**
	 * Une liste de JRadioButton contenant les types des joueurs.
	 */
	private List<JRadioButton> rbTypes;

	/**
	 * le JButton permettant de valider les choix de r�gles.
	 */
	private JButton valider;
	
	/**
	 * Instancie un nouveau ControleurJoueurs.
	 * @param partie la Partie � contr�ler.
	 */
	public ControleurJoueurs(Partie partie) {

		this.partie = partie;
		this.tfNoms = new ArrayList<JTextField>();
		this.rbTypes = new ArrayList<JRadioButton>();
	}

	/**
	 * Permet d'ajouter un objet de la vue � contr�ler
	 * @param tf le JTextField que l'on veut contr�ler.
	 */
	public void addTextField(JTextField tf) {
		this.tfNoms.add(tf);
	}

	
	/**
	 * Permet d'ajouter un objet de la vue � contr�ler
	 * @param rb le JRadioButton que l'on veut contr�ler.
	 */
	public void addRadioButton(JRadioButton rb) {
		this.rbTypes.add(rb);
	}

	
	/**
	 * Permet d'ajouter un objet de la vue � contr�ler
	 * @param b le JButton que l'on veut contr�ler.
	 */
	public void setBoutonValider(JButton b) {
		this.valider = b;
	}

	/**
	 * Une fois que tous les objets de la vue � contr�ler ont �t� pr�cis�s, on peut activer le contr�leur pour celui-ci joue son r�le.
	 */
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