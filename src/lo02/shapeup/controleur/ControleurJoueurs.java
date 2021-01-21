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
*	La classe ControleurJoueurs permet de contrôler les interactions entre la vue et les modèles dans le menu de la définition des joueurs.
*	Elle permet la définition du type des joueurs, et de leur nom.
*
*	@author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
*	@version 1.0
*	@see lo02.shapeup.partie.Partie
*/
public class ControleurJoueurs {

	/**
	 * La partie contrôlée par cette classe.
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
	 * le JButton permettant de valider les choix de règles.
	 */
	private JButton valider;
	
	/**
	 * Instancie un nouveau ControleurJoueurs.
	 * @param partie la Partie à contrôler.
	 */
	public ControleurJoueurs(Partie partie) {

		this.partie = partie;
		this.tfNoms = new ArrayList<JTextField>();
		this.rbTypes = new ArrayList<JRadioButton>();
	}

	/**
	 * Permet d'ajouter un objet de la vue à contrôler
	 * @param tf le JTextField que l'on veut contrôler.
	 */
	public void addTextField(JTextField tf) {
		this.tfNoms.add(tf);
	}

	
	/**
	 * Permet d'ajouter un objet de la vue à contrôler
	 * @param rb le JRadioButton que l'on veut contrôler.
	 */
	public void addRadioButton(JRadioButton rb) {
		this.rbTypes.add(rb);
	}

	
	/**
	 * Permet d'ajouter un objet de la vue à contrôler
	 * @param b le JButton que l'on veut contrôler.
	 */
	public void setBoutonValider(JButton b) {
		this.valider = b;
	}

	/**
	 * Une fois que tous les objets de la vue à contrôler ont été précisés, on peut activer le contrôleur pour celui-ci joue son rôle.
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