package View.farmacia.Carrello;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import Controller.azienda.PrenotazioneController;
import Controller.farmacia.CarrelloController;
import Models.ProdottoPrenotato;
import View.farmacia.BarraMenuFarmacia;
import View.farmacia.Home.HomeViewFarmacia;
import utilities.GUItems;
import utilities.Helper;
import utilities.Navigation;
import utilities.GUItems.AlertType;
import utilities.rendertable.farmacia.CarrelloTable;
import utilities.rendertable.farmacia.VisualizzaProdottiTable;

public class CarrelloView extends JFrame {

	private CarrelloTable carrelloTable;
	private JButton acquistaButton;
	
	private ArrayList<ProdottoPrenotato> prodottiCarrello;
	
	public CarrelloView() {
		
		super("Carrello");//titolo finestra (frame)
		setJMenuBar(BarraMenuFarmacia.getMenuBar());
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
        
		//Riga 0 - Titolo
        c.weighty = 0.01;//specifica il "peso" del componente. Se non specificato per nessuno verranno posizionati tutti nella parte centrale
        c.weightx = 0.01;
		
		c.gridy = 0;//riga
		c.gridx = 0;//colonna
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(30, 0, 0, 0);
		add(GUItems.getDefaultTitleLabel("Carrello"), c);
		

		//Riga 1 - carrello
		
		acquistaButton = new JButton();
		acquistaButton.setIcon(new ImageIcon("./src/View/img/buy.png"));
		acquistaButton.setText("Acquista");
		acquistaButton.setFont(GUItems.getDefaultFont(25));
		acquistaButton.addActionListener(acquista());
		
		c.weighty = 0.01;//specifica il "peso" del componente. Se non specificato per nessuno verranno posizionati tutti nella parte centrale
        c.weightx = 0.01;
		
		c.gridy = 1;//riga
		c.gridx = 0;//colonna
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(0, 0, 0, 120);
		add(acquistaButton, c);
		
		//Riga 2 - tabella
		//tabella carrello		
		initializeCarrelloTable();
		JScrollPane scrollPane = GUItems.getScrollPane(carrelloTable.getTable());
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
		setSize(1600, 900);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);//posiziona finestra al centro dello schermo
		setResizable(true);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);//per ultimo
	}
	
	//INIZIALIZZA TABLE
	private void initializeCarrelloTable() {
		
		prodottiCarrello = CarrelloController.getProdottiCarrello();
		carrelloTable = new CarrelloTable(prodottiCarrello);
		
		//INCREMENTO QUANTITA
		carrelloTable.setAddAction(incrementaQta());
		
		//DECREMENTO QUANTITA
		carrelloTable.setSubAction(decrementaQta());
	}
	
	
	//INCREMENTA ACTION
	private AbstractAction incrementaQta() {
		return new AbstractAction() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = Integer.valueOf(e.getActionCommand());				
				Integer qta = Integer.parseInt(carrelloTable.getCellValue(row, CarrelloTable.QUANTITA)) + 1;
				
				//Incremento il valore della cella
				carrelloTable.setCellValue(qta.toString(), row, CarrelloTable.QUANTITA);
				
				CarrelloController.updateQta(carrelloTable.getCellValue(row, VisualizzaProdottiTable.EAN), qta);
			}
		};
	}

	//DECREMENTA ACTION
	private AbstractAction decrementaQta() {
		
		return new AbstractAction() {				
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = Integer.valueOf(e.getActionCommand());
				
				Integer qta = Integer.parseInt(carrelloTable.getCellValue(row, CarrelloTable.QUANTITA)) - 1;
				
				//se la quantita (gia decrementata) è uguale a 0 significa che bisogna eliminare la riga
				if(qta.equals(0)) {
					
					//Chiedo se si è sicuri di eliminare
					int esitoElimina = JOptionPane.showConfirmDialog(null,
							"Sei sicuro di voler rimuovere questo prodotto dal carrello?",
							"Elimina prodotto", JOptionPane.OK_CANCEL_OPTION);
					
					if(JOptionPane.OK_OPTION == esitoElimina) {						
						//Rimuovo dalla lista del carrello il prodotto
						CarrelloController.removeProdottoCarrello(carrelloTable.getCellValue(row, CarrelloTable.EAN));
						//Elimino la riga
							carrelloTable.deleteRow(row);
					}
				}
				
				else {//altrimenti si sostituisce la quantita nel relativo campo
					//Decremento il valore della cella
					carrelloTable.setCellValue(qta.toString(), row, CarrelloTable.QUANTITA);
					CarrelloController.updateQta(carrelloTable.getCellValue(row, CarrelloTable.EAN), qta);
				}
			}
		};
	}
	
	private ActionListener acquista() {
		return new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(JOptionPane.OK_OPTION == alertConfermiAcquisto()) {					
					
					String result = PrenotazioneController.prenota(CarrelloController.getProdottiCarrello());//per verificare che non ci siano errori
					
					if(!Helper.isNumber(result)) {
						alertErroreInvioOrdine(GUItems.htmlOf("Si è verificato un errore durante l'elaborazione dell'ordine.<br/>(" + result +")"));
					}
					
					else {
						CarrelloController.reset();
						alertOrdineEseguito();
						Navigation.changePage(HomeViewFarmacia.getInstance());
					}
				}
			}
		};
	}
	
	private int alertConfermiAcquisto() {
		return GUItems.Alert("Confermi di voler acquistare i prodotti presenti nel carrello?", "Acquista", AlertType.CONFIRM);
	}
	
	private void alertOrdineEseguito() {
		GUItems.Alert("Prenotazione effettuata con successo!", "Prenotazione", AlertType.INFO);
	}
	
	private void alertErroreInvioOrdine(String msg) {
		GUItems.Alert(GUItems.htmlOf("Si è verificato un errore durante l'invio dell'ordine.<br/>(" + msg + ")"), "Errore invio ordine", AlertType.ERROR);
	}
}
