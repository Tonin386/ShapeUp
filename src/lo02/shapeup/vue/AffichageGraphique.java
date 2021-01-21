package lo02.shapeup.vue;

import java.awt.EventQueue;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;

import lo02.shapeup.controleur.ControleurJoueurs;
import lo02.shapeup.controleur.ControleurPartie;
import lo02.shapeup.controleur.ControleurRegles;
import lo02.shapeup.partie.Carte;
import lo02.shapeup.partie.JoueurStrategy;
import lo02.shapeup.partie.Partie;
import lo02.shapeup.partie.PartieElementCalcVisitor;

import javax.swing.JRadioButton;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JComboBox;

/**
 *	La classe AffichageGraphique permet de contrôler la partie à travers une interface graphique, en même temps que l'interface console. Elle implémente l'interface Observer.
 *	Cette classe est une vue dans l'architecture MVC.
 *	Elle est donc notifiée de tout changement de la partie.
 * 	
 * @author MATHUBERT Antonin et TOUKO KOUEDJOU Vanelle Tatiana
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class AffichageGraphique implements Observer {
	
	/**
	 * Spécifie la partie contrôler à travers l'interface graphique.
	 */
	private Partie partie;
	
	/**
	 * A chaque fois que l'interface est notifiée d'un changement dans la partie, elle décide quelle vue afficher.
	 * @param o l'objet observé
	 * @param arg argument supplémentaire
	 */
	@Override
	public void update(Observable o, Object arg) {

		frame.getContentPane().removeAll();

		if(this.partie.getReglesDefinies() == false) {
			this.vueRegles();
		}
		else if(this.partie.getJoueursDefinis() == false) {
			this.vueJoueurs();
		}
		else if(this.partie.estFinie() == false) {
			this.vuePartie();
		}
		else {
			this.vueScore();
		}

		frame.revalidate();
		frame.repaint();
	}
	
	/**
	 * L'élement principal de notre fenêtre.
	 */
	private JFrame frame;
	
	/**
	 * La méthode principale du programme.
	 * Elle permet l'exécution en plusieurs threads.
	 * @param args les arguments d'exécution du programme
	 */
	public static void main(String[] args) {
		
		Partie partie = new Partie();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AffichageGraphique window = new AffichageGraphique(partie);
					window.frame.setVisible(true);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		@SuppressWarnings("unused")
		AffichageConsole console = new AffichageConsole(partie);
	}

	/**
	 * Instancie un nouvel AffichageGraphique pour une partie.
	 * @param partie la partie à afficher
	 */
	public AffichageGraphique(Partie partie) {
		
		this.partie = partie;
		this.partie.addObserver(this);
		this.partie.getPlateau().addObserver(this);

		frame = new JFrame();
		
		initialize();
	}
	
	/**
	 * La vue par défaut de la fenêtre.
	 */
	private void initialize() {
		vueRegles();
	}

	/**
	 * Vue qui permet à l'utilisateur de choisir les règles de la partie.
	 */
	private void vueRegles() {
		frame.setBounds(100, 100, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		ButtonGroup buttonGroup = new ButtonGroup();
		
		JRadioButton rb1 = new JRadioButton("R\u00E8gles simples, 2 joueurs");
		rb1.setSelected(true);
		buttonGroup.add(rb1);
		rb1.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(rb1);
		
		JRadioButton rb2 = new JRadioButton("R\u00E8gles simples, 3 joueurs");
		rb2.setHorizontalAlignment(SwingConstants.CENTER);
		buttonGroup.add(rb2);
		frame.getContentPane().add(rb2);
		
		JRadioButton rb3 = new JRadioButton("R\u00E8gles avanc\u00E9es, 2 joueurs");
		rb3.setHorizontalAlignment(SwingConstants.CENTER);
		buttonGroup.add(rb3);
		frame.getContentPane().add(rb3);
		
		JRadioButton rb4 = new JRadioButton("R\u00E8gles avanc\u00E9es, 3 joueurs");
		rb4.setHorizontalAlignment(SwingConstants.CENTER);
		buttonGroup.add(rb4);
		frame.getContentPane().add(rb4);
		
		JButton bValider = new JButton("Valider");
		@SuppressWarnings("unused")
		ControleurRegles cRegles = new ControleurRegles(this.partie, bValider, rb1, rb2, rb3, rb4);
		frame.getContentPane().add(bValider);
	}

	/**
	 * Vue qui permet à l'utilisateur de définir les joueurs de la partie.
	 */
	private void vueJoueurs() {
		frame.setBounds(100, 100, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		RowSpec rows[] = new RowSpec[this.partie.getJoueursADefinir() * 6 + 2];

		for(int i = 0; i < this.partie.getJoueursADefinir(); i++) {
			rows[6 * i] = FormSpecs.RELATED_GAP_ROWSPEC;
			rows[6 * i + 1] = RowSpec.decode("default:grow");
			rows[6 * i + 2] = FormSpecs.RELATED_GAP_ROWSPEC;
			rows[6 * i + 3] = RowSpec.decode("default:grow");
			rows[6 * i + 4] = FormSpecs.RELATED_GAP_ROWSPEC;
			rows[6 * i + 5] = FormSpecs.DEFAULT_ROWSPEC;
		}

		rows[rows.length - 2] = FormSpecs.RELATED_GAP_ROWSPEC;
		rows[rows.length - 1] = FormSpecs.DEFAULT_ROWSPEC;

		frame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
			FormSpecs.RELATED_GAP_COLSPEC,
			ColumnSpec.decode("default:grow"),
			FormSpecs.RELATED_GAP_COLSPEC,
			ColumnSpec.decode("default:grow")
		},
		rows));


		ControleurJoueurs cJoueurs = new ControleurJoueurs(partie);

		int i;
		for(i = 0; i < this.partie.getJoueursADefinir(); i++) {
			ButtonGroup buttonGroup = new ButtonGroup();

			JLabel lJoueur = new JLabel("Nom joueur "+ i +" : ");
			frame.getContentPane().add(lJoueur, "2, "+ (i*6 + 2) +", center, default");

			JTextField tfJoueur = new JTextField();
			tfJoueur.setText("Joueur " + i);
			frame.getContentPane().add(tfJoueur, "4, "+ (i*6 + 2));
			tfJoueur.setColumns(10);
			cJoueurs.addTextField(tfJoueur);

			JRadioButton rbPhysique = new JRadioButton("Physique");
			rbPhysique.setSelected(true);
			frame.getContentPane().add(rbPhysique, "2, "+ (i*6 + 4) +", center, default");
			buttonGroup.add(rbPhysique);
			cJoueurs.addRadioButton(rbPhysique);

			JRadioButton rbVirtuel = new JRadioButton("Virtuel");
			frame.getContentPane().add(rbVirtuel, "4, "+ (i*6 + 4) +", center, default");
			buttonGroup.add(rbVirtuel);
			cJoueurs.addRadioButton(rbVirtuel);

			JSeparator separator = new JSeparator();
			frame.getContentPane().add(separator, "2, "+ (i*6 + 6) +", 3, 1");
		}

		JButton bValider = new JButton("Valider");
		frame.getContentPane().add(bValider, "2, "+ ((i-1)*6 + 8) +", 3, 1");
		cJoueurs.setBoutonValider(bValider);
		cJoueurs.activate();
	}

	/**
	 * Vue principale de la partie avec l'affichage du plateau et du jeu des joueurs, ainsi que de leur carte victorieuse et des actions possibles.
	 */
	private void vuePartie() {
		frame.setBounds(100, 100, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(
			new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
			},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
			}));

		ButtonGroup buttonGroup_1 = new ButtonGroup();
		
		//de 2 à 18

		Map<List<Integer>, Carte> plateau = this.partie.getPlateau().getDisposition();

		for(List<Integer> pos : plateau.keySet()) {
			Icon carteIcon = new ImageIcon(plateau.get(pos).getPath());
			JLabel label = new JLabel();
			label.setIcon(carteIcon);
			frame.getContentPane().add(label, (10 + pos.get(0) * 2) + ", " + (10 + pos.get(1) * 2) +", center, default");
		}
		
		JSeparator separator = new JSeparator();
		frame.getContentPane().add(separator, "2, 20, 17, 1");
		
		JRadioButton rbCarte1;
		if(this.partie.getJoueurs().get(partie.getTour()).getJeu().size() > 0) {
			rbCarte1 = new JRadioButton("", new ImageIcon(this.partie.getJoueurs().get(partie.getTour()).getJeu().get(0).getPath()));
		}
		else {
			rbCarte1 = new JRadioButton("Vide");
			rbCarte1.setEnabled(false);
		}
		if(this.partie.getIndexChoisi() == 0) {
			rbCarte1.setSelected(true);
		}
		buttonGroup_1.add(rbCarte1);
		frame.getContentPane().add(rbCarte1, "2, 22");
		
		JRadioButton rbCarte2;
		if(this.partie.getJoueurs().get(partie.getTour()).getJeu().size() > 1) {
			rbCarte2 = new JRadioButton("", new ImageIcon(this.partie.getJoueurs().get(partie.getTour()).getJeu().get(1).getPath()));
			frame.getContentPane().add(rbCarte2, "4, 22");
		}
		else {
			if(this.partie.getJoueurs().get(partie.getTour()).getCarteVictorieuse() != null) {
				Icon carteVIcon = new ImageIcon(this.partie.getJoueurs().get(partie.getTour()).getCarteVictorieuse().getPath());
				JLabel carteV = new JLabel();
				carteV.setIcon(carteVIcon);
				frame.getContentPane().add(carteV, "4, 22");				
			}
			rbCarte2 = new JRadioButton("Vide");
			rbCarte2.setEnabled(false);
		}

		if(this.partie.getIndexChoisi() == 1) {
			rbCarte2.setSelected(true);
		}
		buttonGroup_1.add(rbCarte2);
		
		JRadioButton rbCarte3;
		if(this.partie.getJoueurs().get(partie.getTour()).getJeu().size() > 2) {
			rbCarte3 = new JRadioButton("", new ImageIcon(this.partie.getJoueurs().get(partie.getTour()).getJeu().get(2).getPath()));
		}
		else {
			rbCarte3 = new JRadioButton("Vide");
			rbCarte3.setEnabled(false);
		}

		if(this.partie.getIndexChoisi() == 2) {
			rbCarte3.setSelected(true);
		}

		buttonGroup_1.add(rbCarte3);
		frame.getContentPane().add(rbCarte3, "6, 22");
		
		JComboBox<String> cbPoser = new JComboBox<String>();
		for(int i = 0; i < this.partie.getPositionnementsPossibles().size(); i++) {
			cbPoser.addItem(this.partie.getPositionnementsPossibles().get(i).get(0) + ", " + this.partie.getPositionnementsPossibles().get(i).get(1));
		}
		frame.getContentPane().add(cbPoser, "8, 22, 2, 1, fill, default");
		
		JButton bPoser = new JButton("Poser");
		if(!this.partie.peutPoser()) {
			bPoser.setEnabled(false);
		}

		frame.getContentPane().add(bPoser, "10, 22");
		
		JComboBox<String> cbDeplacer = new JComboBox<String>();
		for(int i = 0; i < this.partie.getDeplacementsPossibles().size(); i++) {
			List<List<Integer>> dplcmts = this.partie.getDeplacementsPossibles();
			cbDeplacer.addItem(dplcmts.get(i).get(0) + ", " + dplcmts.get(i).get(1) + " -> " + dplcmts.get(i).get(2) + ", " + dplcmts.get(i).get(3));
		}
		frame.getContentPane().add(cbDeplacer, "12, 22, 3, 1, fill, default");
		
		JButton bDeplacer = new JButton("Deplacer");
		if(!this.partie.peutDeplacer()) {
			bDeplacer.setEnabled(false);
		}
		frame.getContentPane().add(bDeplacer, "16, 22");
		
		JButton bFinTour = new JButton("Fin tour");
		if(this.partie.doitPoser()) {
			bFinTour.setEnabled(false);
		}
		else {
			bFinTour.setEnabled(true);
		}
		frame.getContentPane().add(bFinTour, "18, 22");

		@SuppressWarnings("unused")
		ControleurPartie cPartie = new ControleurPartie(this.partie, bPoser, bDeplacer, bFinTour, rbCarte1, rbCarte2, rbCarte3, cbPoser, cbDeplacer);
	}

	/**
	 * Vue finale de la partie où les joueurs voient leurs scores affichés.
	 */
	private void vueScore() {
		frame.setBounds(100, 100, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(
			new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
			},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
			}));
		
		Map<List<Integer>, Carte> plateau = this.partie.getPlateau().getDisposition();

		for(List<Integer> pos : plateau.keySet()) {
			Icon carteIcon = new ImageIcon(plateau.get(pos).getPath());
			JLabel label = new JLabel();
			label.setIcon(carteIcon);
			frame.getContentPane().add(label, (10 + pos.get(0) * 2) + ", " + (10 + pos.get(1) * 2) +", center, default");
		}
		
		JSeparator separator = new JSeparator();
		frame.getContentPane().add(separator, "2, 20, 17, 1");

		PartieElementCalcVisitor scoresVisit = new PartieElementCalcVisitor();
		for(JoueurStrategy j : this.partie.getJoueurs()) {
			j.accept(scoresVisit);
		}
		this.partie.getPlateau().accept(scoresVisit);
		List<Integer> scores = scoresVisit.obtenirScoresPartie();
		
		JLabel lblNewLabel = new JLabel("Score Joueur 1 : " + scores.get(0));
		frame.getContentPane().add(lblNewLabel, "2, 22, 3, 1");
		
		JLabel lblNewLabel_1 = new JLabel("Score Joueur 2 : " + scores.get(1));
		frame.getContentPane().add(lblNewLabel_1, "8, 22, 3, 1");
		
		if(scores.size() > 2) {
			JLabel lblNewLabel_2 = new JLabel("Score Joueur 3 : " + scores.get(2));
			frame.getContentPane().add(lblNewLabel_2, "12, 22, 3, 1");	
		}
	}
}
