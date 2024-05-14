package View.corriere.Home;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import Models.Current;
import View.corriere.InCarico.InCaricoView;
import utilities.GUItems;
import utilities.Navigation;
import utilities.GUItems.AlertType;

public class HomeViewCorriere extends JFrame {

	private static HomeViewCorriere instance = null;
	
	private JButton ordiniInCaricoButton;
	private JButton esciButton;
	
	public static HomeViewCorriere getInstance() {
		//Crea l'oggetto solo se non esiste
		if(null == instance) {
			instance = new HomeViewCorriere();
		}		
		return instance;
	}
	
	private HomeViewCorriere() {
		
		super("Home");//titolo finestra (frame)
		
		
		//Layout Grid Bag
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();		
		
		//Riga 0 - Titolo
		JLabel titolo = GUItems.getDefaultTitleLabel("<html><span style='color:green;'><b>Pharmanager</b></span></html>");
		c.insets = new Insets(25, 12, 0, 12);
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.NORTH;
		c.weighty = 0.12;
		
		add(titolo, c);
		
		//Riga 1 - Benvenuto
		JLabel tit = new JLabel("<html><p style='text-align:center;'>Benvenuto <b>" + Current.getCurrentUser().getNomeUtente());
		
		tit.setFont(GUItems.getDefaultFont(39));
		tit.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		tit.setHorizontalAlignment(SwingConstants.CENTER);
		c.insets = new Insets(0, 57, 0, 57);
		c.weighty = 0.10;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0;
		c.gridy = 2;
		add(tit, c);
	

		
		//Button prendi in carico
		ordiniInCaricoButton = GUItems.getDefaultBtn("Ordini in carico", 39, false);
		ordiniInCaricoButton.addActionListener(ordiniInCarico());
		
		c.weighty = 0.5;
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0;
		c.gridy = 3;
		add(ordiniInCaricoButton, c);
		
		
		//Button prendi in carico
		esciButton = GUItems.getDefaultBtn("Esci", 39, false);
		esciButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int alertConfermaUscita = GUItems.Alert("Sei sicuro di voler uscire?", "Esci", AlertType.CONFIRM);
				Navigation.esci(JOptionPane.OK_OPTION == alertConfermaUscita);
			}
		});
		
		c.gridx = 0;
		c.gridy = 4;
		add(esciButton, c);
		
		
		//Frame
		setSize(592, 880);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);//posiziona finestra al centro dello schermo
		setResizable(true);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);//per ultimo
		
	}
	
	private ActionListener ordiniInCarico() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Navigation.changePage(new InCaricoView());
			}
		};
	}
}
