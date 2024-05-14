package View.farmacia.Info;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.*;

import Models.Current;
import Models.Farmacia;
import Models.User;
import View.farmacia.BarraMenuFarmacia;
import utilities.GUItems;

public class InfoView extends JFrame {

	private static InfoView instance = null;
	
	public static InfoView getInstance() {
		//Crea l'oggetto solo se non esiste
		if(null == instance) {
			instance = new InfoView();
		}
		
		return instance;
	}
	
	private InfoView() {
		
		super("Info");//titolo finestra (frame)

		//Menu bar
		setJMenuBar(BarraMenuFarmacia.getMenuBar());
		
		//Layout Grid Bag
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
		//Riga 0 - Titolo
		JLabel titolo = GUItems.getDefaultTitleLabel("Info");
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		c.insets = new Insets(30, 0, 0, 0);
		c.weighty = 0.07;
		
		add(titolo, c);
        
		Farmacia farmacia = Current.getCurrentFarmacia();
		
		//comuni per le prossime righe
		
		c.weightx = 1.00;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0, 52, 0, 0);
		
		//Riga 1 - Farmacia
		JLabel tit = GUItems.getDefaultTitleLabel("Farmacia");
		tit.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		tit.setHorizontalAlignment(SwingConstants.LEFT);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 1;
		add(tit, c);
		
		//comune per prossime righe
		c.weighty = 0.01;
		
		
		//Riga 2 - Nome farmacia
		JLabel nomeFarmaciaLabel = new JLabel("Farmacia - " + farmacia.getNome());
		nomeFarmaciaLabel.setFont(GUItems.getDefaultFont(25));
		nomeFarmaciaLabel.setHorizontalAlignment(JLabel.LEFT);
		c.gridx = 0;
		c.gridy = 2;
		add(nomeFarmaciaLabel, c);
		
		//Riga 2 - P Iva
		JLabel pivaFarmaciaLabel = new JLabel("P. Iva " + farmacia.getPiva());
		pivaFarmaciaLabel.setFont(GUItems.getDefaultFont(25));
		pivaFarmaciaLabel.setHorizontalAlignment(JLabel.LEFT);
		c.gridx = 0;
		c.gridy = 3;
		add(pivaFarmaciaLabel, c);
		
		//Riga 3 - Indirizzo
		JLabel indirizzoFarmaciaLabel = new JLabel(farmacia.getCitta() + ", " + farmacia.getIndirizzo());
		indirizzoFarmaciaLabel.setFont(GUItems.getDefaultFont(25));
		indirizzoFarmaciaLabel.setHorizontalAlignment(JLabel.LEFT);
		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(0, 52, 25, 0);
		add(indirizzoFarmaciaLabel, c);
		
		
		
		//Utente
		User user = Current.getCurrentUser();
		
		//Riga 4 - Utente
		tit = GUItems.getDefaultTitleLabel("Utente");
		tit.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		tit.setHorizontalAlignment(SwingConstants.LEFT);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 5;
		c.insets = new Insets(0, 52, 0, 0);
		add(tit, c);
		
		//comune per prossime righe
		c.weighty = 0.01;
		
		
		//Riga 5 - Nome utente
		JLabel nomeUtenteLabel = new JLabel("Utente - " + user.getNomeUtente());
		nomeUtenteLabel.setFont(GUItems.getDefaultFont(25));
		nomeUtenteLabel.setHorizontalAlignment(JLabel.LEFT);
		c.gridx = 0;
		c.gridy = 6;
		c.insets = new Insets(0,52,0,50);
		c.anchor = GridBagConstraints.NORTHWEST;
		add(nomeUtenteLabel, c);
		
		//Riga 6 - Utente
		tit = new JLabel("Realizzato da", SwingConstants.CENTER);
		tit.setFont(GUItems.getDefaultFont(30));
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.SOUTH;
		c.gridx = 0;
		c.gridy = 7;
		c.weighty = 0.50;
		c.insets = new Insets(0, 0, 0, 0);
		add(tit, c);		
		
		//Riga 7 - Nome utente
		JLabel realizzatoDaJLabel = new JLabel("Busalacchi Giuseppe - Catalano Miryam - Ferrara Domenico - Virga Luca Pio", SwingConstants.CENTER);
		realizzatoDaJLabel.setFont(GUItems.getDefaultFont(21));
		realizzatoDaJLabel.setHorizontalAlignment(JLabel.CENTER);
		c.gridx = 0;
		c.gridy = 8;
		c.weighty = 0.50;
		c.anchor = GridBagConstraints.NORTH;
		c.insets = new Insets(0, 30, 0, 0);
		add(realizzatoDaJLabel, c);
		
		
		//Frame
		setSize(1600, 900);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);//posiziona finestra al centro dello schermo
		setResizable(true);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);//per ultimo
	}
}
