package View.azienda.Home;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.*;

import Controller.azienda.OrdineController;
import Controller.azienda.ProdottoAziendaController;
import Controller.farmacia.FarmaciaController;
import Models.Current;
import Models.Farmacia;
import Models.Ordine;
import Models.Tempo;
import View.azienda.BarraMenuAzienda;
import utilities.GUItems;
import utilities.Helper;
import utilities.GUItems.AlertType;

public class HomeViewAzienda extends JFrame {

	private static HomeViewAzienda instance = null;
	
	public static HomeViewAzienda getInstance() {
		//Crea l'oggetto solo se non esiste
		if(null == instance) {
			instance = new HomeViewAzienda();
		}
		else {
			instance.setJMenuBar(BarraMenuAzienda.getMenuBar());
		}
		
		return instance;
	}
	
	private HomeViewAzienda() {
		
		super("Home");//titolo finestra (frame)
		
		//Menu bar
		setJMenuBar(BarraMenuAzienda.getMenuBar());
		
		
		//Layout Grid Bag
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();		
		
		//Riga 0 - Titolo
		JLabel titolo = GUItems.getDefaultTitleLabel("<html><span style='color:blue;'><b>Pharmanager - Azienda</b></span></html>");
		c.insets = new Insets(30, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.NORTH;
		c.weighty = 0.12;
		
		add(titolo, c);
		
		//Riga 1 - Benvenuto
		JLabel tit = new JLabel("<html>Benvenuto <b>" + Current.getCurrentUser().getNomeUtente() + "</b>,</html>");
		tit.setFont(GUItems.getDefaultFont(43));
		tit.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		tit.setHorizontalAlignment(SwingConstants.LEFT);
		c.insets = new Insets(0, 0, 0, 0);
		c.weighty = 0.3;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.SOUTH;
		c.gridx = 0;
		c.gridy = 1;
		add(tit, c);
	
		tit = new JLabel("Seleziona un'opzione dalla barra dei menu");
		tit.setFont(GUItems.getDefaultFont(39));
		tit.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		tit.setHorizontalAlignment(SwingConstants.LEFT);
		c.weighty = 1.00;
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0;
		c.gridy = 2;
		add(tit, c);
        
		//Frame
		setSize(1600, 900);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);//posiziona finestra al centro dello schermo
		setResizable(true);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);//per ultimo
		
		
		ArrayList<Ordine> ordiniRifiutati = listaOrdiniRifiutati(); 
		if(ordiniRifiutati.size() > 0)
			alertConsegnaIncompleta(ordiniRifiutati);
		
		
		ArrayList<Ordine> ordiniRifiutatiDiIeri = listaOrdiniRifiutatiDiIeri();
		if(ordiniRifiutatiDiIeri.size() > 0)
			alertChiamateFarmacie(ordiniRifiutatiDiIeri);
		
		
		//si verifica se è il primo del mese e quindi se è il momento di aggiornare le scorte
		if(Tempo.scorteSonoDaAggiornare())
			ProdottoAziendaController.aggiornaScorteAzienda();
	}
	
	private ArrayList<Ordine> listaOrdiniRifiutati(){
		return OrdineController.getOrdiniRifiutati();
	}
	
	private ArrayList<Ordine> listaOrdiniRifiutatiDiIeri(){
		return OrdineController.getRifiutatiDiIeri();
	}
	
	
	private void alertConsegnaIncompleta(ArrayList<Ordine> ordiniRifiutati) {
		/*
		 * Ottiene ordini la cui consegna è stata totalmente o parzialmente rifiutata e il cui stato non è ancora stato visualizzato
		 * per evitare che vengano dati sempre gli stessi avvisi
		 */
		String msg = "";
		for(Ordine ordine : ordiniRifiutati) {
			Farmacia farmaciaOrdine = FarmaciaController.getFarmaciaByIdFarmacia(ordine.getRefFarmacia());
			msg += "<br/>-Da: " + farmaciaOrdine.getNome() + " - " + ordine.getStatoToString();
			
			OrdineController.visualizzatoUltimoStatoOrdine(ordine.getId());
		}
		
		GUItems.Alert("<html>Presenti ordini rifiutati o consegnati solo in parte:" + msg + "</html>", "Presenti ordini non completati", AlertType.WARNING);
	}
	
	private void alertChiamateFarmacie(ArrayList<Ordine> rifiutatiDiIeri) {
		
		String msg = "<html>";
		
		for(Ordine ordine : rifiutatiDiIeri) {
			Farmacia farmacia = FarmaciaController.getFarmaciaByIdFarmacia(ordine.getRefFarmacia());
			msg+= "<br/>L'ordine eseguito in data " + Helper.dateToString(ordine.getDataOrdinazione()) + " dalla farmacia " + farmacia.getNome() + " è stato " + ordine.getStatoToString() + ". Chiamalo al numero <b>" + farmacia.getTelefono() + "</b>";
		}
		
		GUItems.Alert(msg + "</html>", "Farmacie da contattare", AlertType.WARNING);
	}
}
