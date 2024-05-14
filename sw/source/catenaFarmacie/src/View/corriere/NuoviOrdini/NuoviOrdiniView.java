package View.corriere.NuoviOrdini;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import Controller.corriere.OrdineCorriereController;
import Models.Ordine;
import Models.OrdiniEntity;
import View.corriere.Home.HomeViewCorriere;
import utilities.GUItems;
import utilities.Helper;
import utilities.Navigation;
import utilities.GUItems.AlertType;
import utilities.rendertable.corriere.*;

public class NuoviOrdiniView extends JFrame {

	private JTable prenotazioniTable;	
	private JButton indietroButton;
	
	private OrdiniEntity ordini;
	
	public NuoviOrdiniView() {
		
		super("Nuovi ordini");//titolo finestra (frame)
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
        
		//Riga 0 - Titolo
        c.weighty = 0.01;//specifica il "peso" del componente. Se non specificato per nessuno verranno posizionati tutti nella parte centrale
        c.weightx = 0.01;
		
		c.gridy = 0;//riga
		c.gridx = 0;//colonna
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.CENTER;
		add(GUItems.getDefaultTitleLabel("Nuovi ordini"), c);

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
		
		prenotazioniTable = tableOrdini();
		JScrollPane scrollPane = GUItems.getScrollPane(prenotazioniTable);
		
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
		setSize(720, 1280);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
		setResizable(true);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);//per ultimo
	}
	
	private JTable tableOrdini() {
		
		
		ordini = new OrdiniEntity(OrdineCorriereController.getNuoviOrdini());
		
		NuoviOrdiniTable ot = new NuoviOrdiniTable(ordini.getOrdini());
		
		AbstractAction prendiInCaricoAction = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub				
				
				 /*int row = Integer.valueOf( e.getActionCommand() );
				 Ordine o = ordini.getOrdini().get(row);
				 
				 String result = OrdineCorriereController.prendiInCarico(o.getId());
				 
				 if(!Helper.isNumber(result)){
					 alertErroreAssegnazioneOrdine(result);
					return;
				}
				
				else {
					alertOrdineAssegnato();
					Navigation.changePage(HomeViewCorriere.getInstance());
				}	*/	
			}
		};
		
		ot.setAction(prendiInCaricoAction);
		
		return ot.getTable();
	}
	
	private void alertOrdineAssegnato() {
		GUItems.Alert("Ordine assegnato con successo!", "Lavorazione ordine", AlertType.INFO);
	}
	
	private void alertErroreAssegnazioneOrdine(String msg) {
		GUItems.AlertError(GUItems.htmlOf("Errore nell'elaborazione della richiesta.<br/>" + msg));
	}
}
