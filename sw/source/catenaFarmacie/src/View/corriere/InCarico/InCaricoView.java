package View.corriere.InCarico;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import Controller.azienda.OrdineController;
import Controller.corriere.OrdineCorriereController;
import Models.Current;
import Models.Ordine;
import View.corriere.Home.HomeViewCorriere;
import View.corriere.InCarico.Prenotazioni.PrenotazioniCorriereView;
import utilities.GUItems;
import utilities.Helper;
import utilities.Navigation;
import utilities.GUItems.AlertType;
import utilities.rendertable.corriere.*;

public class InCaricoView extends JFrame {

	private InCaricoTable ordiniInCaricoTable;	
	private JButton indietroButton;

	private ArrayList<Ordine> ordini; 
	
	public InCaricoView() {
		
		super("In carico");//titolo finestra (frame)
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
        
		//Riga 0 - Titolo
        c.weighty = 0.01;//specifica il "peso" del componente. Se non specificato per nessuno verranno posizionati tutti nella parte centrale
        c.weightx = 0.01;
		
		c.gridy = 0;//riga
		c.gridx = 0;//colonna
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.CENTER;
		add(GUItems.getDefaultTitleLabel("Ordini in carico"), c);

		//Riga 1 - indietro
		indietroButton = GUItems.getDefaultBtn("Indietro", 25, false);
		indietroButton.setIcon(new ImageIcon("./src/View/img/goBack.png"));
		indietroButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Navigation.changePage(HomeViewCorriere.getInstance());
			}
		});
		
		c.gridy = 1;//riga
		c.gridx = 0;//colonna
		c.insets = new Insets(0, 25, 0, 0);
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.WEST;
		add(indietroButton, c);
		
		//Riga 2 - tabella
		//tabella magazzino
		
		initializeOrdiniInCaricoTable();
		JScrollPane scrollPane = GUItems.getScrollPane(ordiniInCaricoTable.getTable());
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(70, 25, 25, 25);
		c.weightx = 1.0;
		c.weighty = 1.0;
		
		add(scrollPane, c);
		
		//Frame
		setSize(592, 880);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
		setResizable(true);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);//per ultimo
	}
	
	private void initializeOrdiniInCaricoTable() {
		
		ordini = OrdineCorriereController.getOrdiniInCarico(Current.getCurrentUser().getId());
		
		ordiniInCaricoTable = new InCaricoTable(ordini);
		ordiniInCaricoTable.setAction(aggiornaStato());
	}
	
	private AbstractAction aggiornaStato() {
		
		return new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 int row = Integer.valueOf( e.getActionCommand() );
				 Ordine o = ordini.get(row);
				 
				 /*
				  * Se lo stato è spedito o in transito il corriere puo aggiornarlo.
				  * Se lo stato gia è in consegna il farmacista dovrà firmare le ricezioni
				  */
				 
				 int stato = o.getStato();
				 
				 if(stato < Ordine.IN_CONSEGNA) {
					 String result = OrdineController.aggiornaStatoOrdine(o.getId(), ++stato);
					 if(!Helper.isNumber(result)) {
						 alertErroreAggiornamentoStatoOrdine(result);
					 }
					 else {
						 alertStatoOrdineAggiornato();
						 Navigation.changePage(new InCaricoView());
					 }
				 }
				 
				 else {
					 Navigation.changePage(new PrenotazioniCorriereView(o.getId()));
				 }
				
			}
		};
	}
	
	private void alertStatoOrdineAggiornato() {
		GUItems.Alert("Aggiornamento avvenuto con successo!", "Stato ordine", AlertType.INFO);
	}
	
	private void alertErroreAggiornamentoStatoOrdine(String msg) {
		GUItems.AlertError(GUItems.htmlOf("Si è verificato un errore nell'aggiornamento dello stato dell'ordine.<br/>" + msg));
	}
}
