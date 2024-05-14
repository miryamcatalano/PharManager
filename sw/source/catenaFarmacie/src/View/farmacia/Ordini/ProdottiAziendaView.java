package View.farmacia.Ordini;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

import Controller.azienda.ProdottoAziendaController;
import Controller.farmacia.CarrelloController;
import Models.ProdottoPrenotato;
import View.farmacia.BarraMenuFarmacia;
import View.farmacia.Carrello.CarrelloView;
import utilities.GUItems;
import utilities.Helper;
import utilities.Navigation;
import utilities.GUItems.AlertType;
import utilities.rendertable.farmacia.VisualizzaProdottiTable;

public class ProdottiAziendaView extends JFrame {

	private JTable prodottiTable;
	private JButton carrelloButton;
	
	private ArrayList<ProdottoPrenotato> prodottiAzienda;
	
	public ProdottiAziendaView() {
		
		super("Visualizza prodotti azienda");//titolo finestra (frame)
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
		add(GUItems.getDefaultTitleLabel("Lista prodotti azienda"), c);
		

		//Riga 1 - carrello
		
		carrelloButton = new JButton();
		carrelloButton.setIcon(new ImageIcon("./src/View/img/carrelloButton.png"));
		carrelloButton.setText("(" + CarrelloController.getProdottiCarrello().size()  + ")");
		carrelloButton.setFont(GUItems.getDefaultFont(25));
		
		
		c.weighty = 0.01;//specifica il "peso" del componente. Se non specificato per nessuno verranno posizionati tutti nella parte centrale
        c.weightx = 0.01;
		
		c.gridy = 1;//riga
		c.gridx = 0;//colonna
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(0, 0, 0, 120);
		add(carrelloButton, c);
		
		//Riga 2 - tabella
		//tabella magazzino
		
		prodottiTable = tableProdotti();
		JScrollPane scrollPane = GUItems.getScrollPane(prodottiTable);
		setActionCarrelloButton();
		
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
	
	private JTable tableProdotti() {
		
		prodottiAzienda = ProdottoAziendaController.getProdottiAzienda();
		
		VisualizzaProdottiTable ct = new VisualizzaProdottiTable(prodottiAzienda);
		correggiTasti(ct, prodottiAzienda);//qui imposto tasto [-] Rimuovi se già è presente il prodotto nel carrello
		
		
		AbstractAction carrelloAction = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub				
				
				 int row = Integer.valueOf( e.getActionCommand() );
				 
				 ProdottoPrenotato prodotto = prodottiAzienda.get(row);
				 
				 // RIMUOVO - se il carrello contiene il prod. (testo button "Rimuovi prodotto") bisognerà rimuovere il prod. e cambiare il testo in "Aggiungi prodotto"
				 if(ProdottoPrenotato.contiene(CarrelloController.getProdottiCarrello(),prodottiAzienda.get(row))) {					 

					 CarrelloController.removeProdottoCarrello(prodotto.getEan());//rimuovo prodotto da carrello
					 ct.setCellValue("[+] Aggiungi",row, VisualizzaProdottiTable.AGGIUNGI_O_RIMUOVI);//imposto testo button "Aggiungi al carrello"
				 }
				 
				 else {//AGGIUNGO - altrimenti lo si aggiungerà e cambieremo il testo in "Rimuovi prodotto" 

					 //il farmacista deve confermare l'aggiunta al carrello se la scadenza è nei prossimi 2 mesi
					 boolean aggiungiProdotto = true;
					 
					 if(prodotto.isInScadenza()) {
								 
						 String msg = "<p style='font-size:100%;'>Il prodotto scadrà tra meno di 2 mesi (<b>" + Helper.dateToString(prodotto.getScadenza()) + "</b>).<br/>Sei sicuro di voler aggiungere il prodotto al carrello?</p>";
						 int alertConfermaProdottoInScadenza = alertProdottoInScadenza(GUItems.htmlOf(msg));					 
					 
						 aggiungiProdotto = (JOptionPane.OK_OPTION == alertConfermaProdottoInScadenza);
					 }
					 
					 if(aggiungiProdotto){
						 CarrelloController.aggiungiProdottoAlCarrello(prodotto);//aggiungo prodotto al carrello
						 ct.setCellValue("[-] Rimuovi",row, VisualizzaProdottiTable.AGGIUNGI_O_RIMUOVI);//imposto testo in "Aggiungi prodotto"
					 }
				 }
				 
				 carrelloButton.setText("(" + CarrelloController.getProdottiCarrello().size() + ")");
			}
		};
		
		ct.setAction(carrelloAction);
		
		return ct.getTable();
	}
	
	private void correggiTasti(VisualizzaProdottiTable table, ArrayList<ProdottoPrenotato> prodottiAzienda) {//se clicco aggiungi diventa rimuovi
		for(int i=0;i<prodottiAzienda.size();i++) {
			
			if(ProdottoPrenotato.contiene(CarrelloController.getProdottiCarrello(), prodottiAzienda.get(i))) {
				table.setCellValue("[-] Rimuovi", i, VisualizzaProdottiTable.AGGIUNGI_O_RIMUOVI);
			}
		}
	}
	
	private void setActionCarrelloButton() {
		carrelloButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CarrelloController.visualizzaCarrello();
			}
		});
	}
	
	private int alertProdottoInScadenza(String msg) {
		return GUItems.Alert(msg, "Prodotto selezionato in scadenza", AlertType.CONFIRM);
	}
}
