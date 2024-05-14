package View.farmacia.Home;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.*;
import Controller.azienda.ProdottoAziendaController;
import Models.Current;
import Models.Tempo;
import View.farmacia.BarraMenuFarmacia;
import utilities.GUItems;

public class HomeViewFarmacia extends JFrame {

	private static HomeViewFarmacia instance = null;
	
	public static HomeViewFarmacia getInstance() {
		//Crea l'oggetto solo se non esiste
		if(null == instance) {
			instance = new HomeViewFarmacia();
		}
		else {
			instance.setJMenuBar(BarraMenuFarmacia.getMenuBar());
		}
		return instance;
	}
	
	private HomeViewFarmacia() {
		
		super("Home");//titolo finestra (frame)
		
		//Menu bar
		setJMenuBar(BarraMenuFarmacia.getMenuBar());
				
		//Layout Grid Bag
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();		
		
		//Riga 0 - Titolo
		JLabel titolo = GUItems.getDefaultTitleLabel("<html><span style='color:blue;'><b>Pharmanager - Farmacia</b></span></html>");
		c.insets = new Insets(30, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.NORTH;
		c.weighty = 0.12;
		
		add(titolo, c);
		
		//Riga 1 - Benvenuto
		JLabel tit = new JLabel("<html><p style='text-align:center;'>Benvenuto <b>" + Current.getCurrentUser().getNomeUtente() + "</b>,<br/>usa la barra del menu in alto per eseguire operazioni</p></html>");
		tit.setFont(GUItems.getDefaultFont(39));
		tit.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		tit.setHorizontalAlignment(SwingConstants.LEFT);
		c.insets = new Insets(0, 0, 0, 0);
		c.weighty = 0.3;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0;
		c.gridy = 1;
		add(tit, c);
		
		//Frame
		setSize(1600, 900);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);//posiziona finestra al centro dello schermo
		setResizable(true);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);//per ultimo
		

		Tempo.eseguiVerifiche();
		
		if(Tempo.scorteSonoDaAggiornare())
			ProdottoAziendaController.aggiornaScorteAzienda();
	}
}
