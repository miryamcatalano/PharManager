package View.farmacia.Ordini.Prenotazioni;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import Controller.azienda.OrdineController;
import Controller.azienda.PrenotazioneController;
import Controller.farmacia.MagazzinoController;
import Controller.farmacia.ProdottoFarmaciaController;
import Models.Ordine;
import Models.Prodotto;
import Models.ProdottoPrenotato;
import View.farmacia.BarraMenuFarmacia;
import utilities.GUItems;
import utilities.Helper;
import utilities.Navigation;
import utilities.GUItems.AlertType;
import utilities.rendertable.farmacia.PrenotazioniTable;

public class PrenotazioniView extends JFrame {

	private PrenotazioniTable prenotazioniTable;
	private JButton confermaModificheButton;
	
	private static Ordine ordine;
	private static HashMap<Integer, Integer> prenotazioniDaAggiornare = new HashMap<>();
	
	private ArrayList<ProdottoPrenotato> prenotazioni;
	
	public PrenotazioniView(int ido) {
		
		
		super("Carica ordine");//titolo finestra (frame)
		setJMenuBar(BarraMenuFarmacia.getMenuBar());

		setOrdine(ido);		
		
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
		add(GUItems.getDefaultTitleLabel("Prenotazioni ordine"), c);

		
		//Riga 1 - conferma modifiche
		
		confermaModificheButton = new JButton("Conferma modifiche");
		confermaModificheButton.setFont(GUItems.getDefaultFont(25));
		confermaModificheButton.addActionListener(modificaOrdine());
		
		c.weighty = 0.01;//specifica il "peso" del componente. Se non specificato per nessuno verranno posizionati tutti nella parte centrale
        c.weightx = 0.01;
		
		c.gridy = 1;//riga
		c.gridx = 0;//colonna
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(0, 0, 0, 120);
		add(confermaModificheButton, c);
				
		
		//Riga 2 - tabella
		//tabella prenotazioni
		
		initializePrenotazioniTable();
		JScrollPane scrollPane = GUItems.getScrollPane(prenotazioniTable.getTable());
		
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
	
	private void initializePrenotazioniTable() {
		
		prenotazioni = PrenotazioneController.getPrenotazioniOrdine(ordine.getId());
		
		prenotazioniTable = new PrenotazioniTable(prenotazioni);
		prenotazioniTable.setCaricaAction(caricaInMagazzino());
		prenotazioniTable.setAddAction(incrementaQta());
		prenotazioniTable.setSubAction(decrementaQta());
	}

	private AbstractAction caricaInMagazzino() {
		return new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = Integer.valueOf( e.getActionCommand() );
				 
				ProdottoPrenotato p = prenotazioni.get(row);
				 
				//la prenotazione puo essere caricata solo se è gia stata consegnata
				if(p.isConsegnato()) {
					
					if(!p.isCaricato()) {
						caricaPrenotazione(prenotazioni.get(row));
					}
					 
					else {
						alertAzioneNonConsentita("I prodotti di questa prenotazione sono già stati caricati in magazzino");
					}
				}
				
				else {
					alertAzioneNonConsentita("Per caricare i prodotti di un ordine in magazzino questo deve essere consegnato");
				}
			}
		};
	}
	
	private void caricaPrenotazione(ProdottoPrenotato prodottoPrenotato) {
		
		try {
			String result;
			
			//PRODOTTO
			Prodotto prodotto = prodottoPrenotato;
			Prodotto prodottoF;
			
			//AGGIORNO EVENTUALMENTE LA TABELLA PRODOTTO DEL DBMS_F
			if(!Prodotto.contiene(ProdottoFarmaciaController.getProdotti(), prodotto)) {
				result = ProdottoFarmaciaController.aggiungiProdotto(prodotto);
				
				if(!Helper.isNumber(result)) {
					alertErroreAggiornamentoMagazzino("Errore nell'inserimento del prodotto nel sistema - " + result);
					prodottoF = null;
					return;
				}
				
				else
					prodottoF = ProdottoFarmaciaController.getProdottoById(Integer.valueOf(result));
			}
			
			else {				
				//ottieni prodotto da ean
				prodottoF = ProdottoFarmaciaController.getProdottoByEan(prodotto.getEan());
			}
			
			//INSERISCO NEL MAGAZZINO
			result = MagazzinoController.insertProdottoInMagazzino(prodottoF, prodottoPrenotato.getScadenza(), prodottoPrenotato.getQta(), prodottoPrenotato.getCosto());			
			if(!Helper.isNumber(result)){
				alertErroreAggiornamentoMagazzino("Errore nell'inserimento del prodotto nel magazzino" + result);
				return;
			}
			
			else {
				
				//PRENOTAZIONE - Se è stato caricato nel magazzino imposto caricato = 1
				result = PrenotazioneController.caricaPrenotazione(prodottoPrenotato.getIdPrenotazione());//imposta caricato = 1
				
				if(!Helper.isNumber(result)) {//verifico che tutto sia andato bene
					alertErroreModificaOrdine("Errore durante l'aggiornamento dello stato del prodotto");
					return;
				}
					
				alertMagazzinoAggiornato();
				Navigation.changePage(new PrenotazioniView(ordine.getId()));
			}
		}
		
		catch (Exception e) {
		}
	}

	//INCREMENTA QTA
	private AbstractAction incrementaQta() {
		return new AbstractAction() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = Integer.valueOf(e.getActionCommand());
				
				ProdottoPrenotato p = prenotazioni.get(row);
				
				//verifico che sia ancora modificabile e quindi non consegnato e prima di 2 giorni dalla consenga
				if(ordine.isAncoraInTempo()) {
					
					if(!p.isConsegnato()) {
						
						Integer qta = Integer.parseInt(prenotazioniTable.getCellValue(row, PrenotazioniTable.QTA)) + 1;
						//Incremento il valore della cella
						prenotazioniDaAggiornare.put(p.getIdPrenotazione(), qta);
						prenotazioniTable.setCellValue(qta.toString(), row, PrenotazioniTable.QTA);
						return;
					}
					
					else {
						alertAzioneNonConsentita("La prenotazione è già stata consegnata. Non puoi più modificarla");						
					}
				}
				
				else {
					alertAzioneNonConsentita("L'ordine non è più modificabile, la sua consegna è prevista tra meno di 2 giorni");
				}
			}
		};
	}
	
	//DECREMENTA QTA
	private AbstractAction decrementaQta() {
		return new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = Integer.valueOf(e.getActionCommand());				
				
				ProdottoPrenotato p = prenotazioni.get(row);
				
				//verifico che sia ancora modificabile e quindi non consegnato e prima di 2 giorni dalla consenga
				if(ordine.isAncoraInTempo()) {
					
					if(!p.isConsegnato()) {
					
						Integer qta = Integer.parseInt(prenotazioniTable.getCellValue(row, PrenotazioniTable.QTA)) - 1;
						
						if(qta > 0) {
							//Decremento il valore della cella
							prenotazioniDaAggiornare.put(p.getIdPrenotazione(), qta);
							prenotazioniTable.setCellValue(qta.toString(), row, PrenotazioniTable.QTA);
						}
						
						else {
							alertAzioneNonConsentita("Non è possibile rimuovere del tutto una prenotazione di un ordine già emesso");
						}
					}
					
					else {
						alertAzioneNonConsentita("La prenotazione è già stata consegnata. Non puoi più modificarla");
					}
				}
				
				else {//ordine arriva tra meno di due giorni
					alertAzioneNonConsentita("L'ordine non è più modificabile, la sua consegna è prevista tra meno di 2 giorni");
				}
				
			}
		};
	}
	
	private ActionListener modificaOrdine() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(ordine.isAncoraInTempo()) {
					
					if(prenotazioniDaAggiornare.size() > 0) {
					
						if(JOptionPane.OK_OPTION == alertConfermaModificaOrdine()) {
							String result;
							
							result = PrenotazioneController.updatePrenotazioniOrdine(prenotazioniDaAggiornare);
							if(!Helper.isNumber(result)) {
								alertErroreModificaOrdine("Errore nella modifica dell'ordine");
								return;
							}							
							alertOrdineModificato();
							Navigation.changePage(new PrenotazioniView(ordine.getId()));
						}
					}				
					else {
						alertAzioneNonConsentita("La prenotazione è già stata consegnata. Non puoi più modificarla");
					}
				}				
				else {
					alertAzioneNonConsentita("L'ordine non è più modificabile, la sua consegna è prevista tra meno di 2 giorni");
				}
			}
		};
	}
	
	private static void setOrdine(int ido) {
		ordine = OrdineController.getOrdineById(ido);
	}
	private int alertConfermaModificaOrdine() {
		return GUItems.Alert("Sei sicuro di voler modificare l'ordine?", "Modifica ordine", AlertType.CONFIRM);
	}
	private void alertOrdineModificato() {
		GUItems.Alert("Modifica ordine eseguita con successo!", "Modifica ordine", AlertType.INFO);
	}
	private void alertMagazzinoAggiornato() {
		GUItems.Alert("Caricati con successo i prodotti della prenotazione!", "Prodotti caricati in magazzino", AlertType.INFO);
	}
	private void alertErroreModificaOrdine(String msg) {
		GUItems.AlertError(msg);
	}
	private void alertErroreAggiornamentoMagazzino(String msg) {
		GUItems.AlertError(msg);
	}
	private void alertAzioneNonConsentita(String msg) {
		GUItems.Alert(msg, "Azione non consentita", AlertType.INFO);
	}
	
}
