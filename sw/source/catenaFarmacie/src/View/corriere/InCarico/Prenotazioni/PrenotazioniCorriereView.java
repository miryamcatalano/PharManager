package View.corriere.InCarico.Prenotazioni;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import Controller.azienda.OrdineController;
import Controller.azienda.PrenotazioneController;
import Controller.farmacia.AutenticazioneFarmacistaController;
import Models.Ordine;
import Models.ProdottoPrenotato;
import View.corriere.Home.HomeViewCorriere;
import View.corriere.InCarico.InCaricoView;
import utilities.GUItems;
import utilities.Helper;
import utilities.Navigation;
import utilities.GUItems.AlertType;
import utilities.rendertable.corriere.PrenotazioniCorriereTable;

public class PrenotazioniCorriereView extends JFrame {

	private PrenotazioniCorriereTable prenotazioniTable;
	private JButton confermaButton;
	private JButton indietroButton;
	
	private static Ordine ordine;
	
	private ArrayList<ProdottoPrenotato> prenotazioniConsengate = new ArrayList<>();
	private ArrayList<ProdottoPrenotato> prenotazioni;
	
	public PrenotazioniCorriereView(int ido) {
		
		super("Prenotazioni ordine");//titolo finestra (frame)
		setOrdine(ido);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
        
		//faccio visualizzare solo le prenotazioni da consegnare
		prenotazioni = PrenotazioneController.getPrenotazioniOrdine(ordine.getId());
		
		
		//Riga 0 - Titolo
        c.weighty = 0.01;//specifica il "peso" del componente. Se non specificato per nessuno verranno posizionati tutti nella parte centrale
        c.weightx = 0.01;
		
		c.gridy = 0;//riga
		c.gridx = 0;//colonna
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.CENTER;
		add(GUItems.getDefaultTitleLabel("<html><p style='text-align:center;'>Prenotazioni</p></html>"), c);

		
		//Riga 1 - indietro
		c.gridy = 1;//riga
		c.gridx = 0;//colonna
		c.insets = new Insets(0, 25, 0, 0);
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 0.3;
		
		indietroButton = GUItems.getDefaultBtn("Indietro", 25, false);
		indietroButton.setIcon(new ImageIcon("./src/View/img/goBack.png"));
		indietroButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Navigation.changePage(new InCaricoView());
			}
		});		
		
		add(indietroButton, c);
				
		//Riga 1 - conferma modifiche
		
		c.gridy = 1;//riga
		c.gridx = 2;//colonna
		c.insets = new Insets(0, 0, 0, 25);
		c.anchor = GridBagConstraints.EAST;
		c.weightx = 0.7;
		
		confermaButton = new JButton("Firma");
		confermaButton.setFont(GUItems.getDefaultFont(25));
		confermaButton.addActionListener(firma());
		
		add(confermaButton, c);
		
		//Riga 2 - tabella
		//tabella prenotazioni
		
		initializePrenotazioniTable();
		JScrollPane scrollPane = GUItems.getScrollPane(prenotazioniTable.getTable());
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(52, 12, 25, 12);
		c.weightx = 1.0;
		c.weighty = 1.0;
		
		add(scrollPane, c);
		
		//Frame
		setSize(592, 880);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);//posiziona finestra al centro dello schermo
		setResizable(true);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);//per ultimo
	}
	
	private void initializePrenotazioniTable() {
		
		prenotazioniTable = new PrenotazioniCorriereTable(prenotazioni);
		prenotazioniTable.setRiceviAction(confermaConsegna());
	}
	
	private static void setOrdine(int ido) {
		ordine = OrdineController.getOrdineById(ido);
	}
	
	private ActionListener firma() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * Tutte le prenotazioni contenute in idListPrenotazioniConsengate
				 * verranno aggiornate nel db con stato consegnato = 1, tutte le altre RIMARRANNO 0.
				 * Se la lista delle altre avrà grandezza = 0 allora l'ordine sarà stato consegnato interamente
				 */
				
				//Lista prenotazioniNonConsegnate che conterrà tutte le prenotazioni il cui statò dovrà essere impostato su 0
				ArrayList<ProdottoPrenotato> prenotazioniNonConsegnate = prenotazioni;
				prenotazioniNonConsegnate.removeAll(prenotazioniConsengate);
				if(ordine.getStato() == Ordine.IN_CONSEGNA) {
					
					if(validateUserWithPopUp()) {
					
						//aggiorno lo stato delle prenotazioni
						for(ProdottoPrenotato p : prenotazioniConsengate) {
							PrenotazioneController.consegnaPrenotazione(p);
						}					
						
						/*
						 * se il numero di prenotazioni non consegnate è uguale a 0 l'ordine puo essere contrassegnato come CONSEGNATO
						 * alrimenti come CONSEGNATO_IN_PARTE
						 */
						String result = OrdineController.aggiornaStatoOrdine(ordine.getId(), 
								prenotazioniNonConsegnate.size() == 0 ? Ordine.CONSEGNATO : Ordine.CONSEGNATO_IN_PARTE);
						
						if(!Helper.isNumber(result)) {
							alertErroreConsegnaOrdine(result);
						}
						
						else {
							alertOrdineConsegnato();
							Navigation.changePage(HomeViewCorriere.getInstance());
						}
					}
				}
				
				else {
					if(ordineGiaFirmato()) {
						alertOrdineGiaFirmato();
					}
					
					else {
						alertOrdineNonAncoraInConsegna();
					}
					
				}
				
			}
		};
	}
	private AbstractAction confermaConsegna() {
		return new AbstractAction() {			
			@Override
			public void actionPerformed(ActionEvent e) {

				//verifico che siano state fatte tutte le tappe
				if(ordine.getStato() == Ordine.IN_CONSEGNA) {
					
					 int row = Integer.valueOf( e.getActionCommand() );
					 ProdottoPrenotato p = prenotazioni.get(row);
					 
					 if(!p.isConsegnato()) {
						 //aggiungo la prenotazione all'elenco di quelle consengate/ricevute
						 prenotazioniConsengate.add(p);
						 //elimino la riga dalla tabella
						 prenotazioniTable.deleteRow(row);
					}
					
					else {
						alertOrdineGiaConsegnato();
					}		 
				}
				
				else {
					
					if(ordineGiaFirmato()) {
						alertOrdineGiaFirmato();
					}
					
					else {
						alertOrdineNonAncoraInConsegna();
					}
				}
			}
		};
	}
	private boolean validateUserWithPopUp() {
		
		JTextField username = new JTextField();
		JTextField password = new JPasswordField();
		Object[] message = {
		    "Username:", username,
		    "Password:", password
		};

		int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
		    boolean haFirmato = AutenticazioneFarmacistaController.farmacistaFirma(username.getText(), password.getText());
		    if(!haFirmato) {
		    	alertCredenzialiFirmaErrate();
		    }
		    
		    return haFirmato;
		}
		else
			return false;
	}
	
	private boolean ordineGiaFirmato() {
		return ordine.getStato() > Ordine.IN_CONSEGNA;
	}
	
	
	//ALERT
	private void alertOrdineConsegnato() {
		GUItems.Alert("Il tuo ordine è stato consegnato e firmato!", "Consegna avvenuta con successo", AlertType.INFO);
	}
	
	private void alertErroreConsegnaOrdine(String msg) {
		GUItems.AlertError(GUItems.htmlOf("Si è verificato un errore durante l'elaborazione della consegna dell'ordine.<br/>" + msg));
	}
	
	private void alertOrdineGiaConsegnato() {
		GUItems.Alert("La prenotazione è già stata consegnata. Non puoi più firmarne la consegna", "Consegna non possibile", AlertType.WARNING);
	}
	
	private void alertOrdineNonAncoraInConsegna() {
		GUItems.Alert(GUItems.htmlOf("L'ordine non è ancora \"" + GUItems.corsivoOf("In Consegna") + "\", necessario per poterne firmare l'avvenuta consegna"), "Consegna non possibile", AlertType.WARNING);
	}
	
	private void alertOrdineGiaFirmato() {
		GUItems.AlertError("Quest'ordine è già stato firmato");
	}
	
	private void alertCredenzialiFirmaErrate() {
		GUItems.Alert("L'username o la password inserite non sono corrette. Riprovare", "Credenziali non corrette", AlertType.ERROR);
	}
	
}
