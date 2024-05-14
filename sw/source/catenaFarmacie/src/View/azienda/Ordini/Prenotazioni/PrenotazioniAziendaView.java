package View.azienda.Ordini.Prenotazioni;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.*;
import Controller.azienda.OrdineController;
import Controller.azienda.PrenotazioneController;
import Models.Ordine;
import Models.ProdottoPrenotato;
import View.azienda.BarraMenuAzienda;
import utilities.GUItems;
import utilities.rendertable.azienda.*;;

public class PrenotazioniAziendaView extends JFrame {

	private JTable prenotazioniAziendaTable;
	private static Ordine ordine;
	
	public PrenotazioniAziendaView(int ido) {
		
		
		super("Prenotazioni ordine");//titolo finestra (frame)		
		
		setJMenuBar(BarraMenuAzienda.getMenuBar());
		
		setOrdine(ido);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
        
		//Riga 0 - Titolo
        c.weighty = 0.01;//specifica il "peso" del componente. Se non specificato per nessuno verranno posizionati tutti nella parte centrale
        c.weightx = 0.01;
		
		c.gridy = 0;//riga
		c.gridx = 0;//colonna
		c.gridwidth = 3;
		c.insets = new Insets(30, 0, 0, 0);
		c.anchor = GridBagConstraints.CENTER;
		add(GUItems.getDefaultTitleLabel("Prenotazioni ordine"), c);
				
		
		//Riga 1 - tabella
		//tabella prenotazioni
		
		prenotazioniAziendaTable = tablePrenotazioni();
		JScrollPane scrollPane = GUItems.getScrollPane(prenotazioniAziendaTable);
		
		
		c.gridx = 0;
		c.gridy = 1;
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
	
	private JTable tablePrenotazioni() {
		
		ArrayList<ProdottoPrenotato> prenotazioni = PrenotazioneController.getPrenotazioniOrdine(ordine.getId());
		PrenotazioniAziendaTable pt = new PrenotazioniAziendaTable(prenotazioni);
				
		return pt.getTable();
	}
	
	private static void setOrdine(int ido) {
		ordine = OrdineController.getOrdineById(ido);
	}
	
}
