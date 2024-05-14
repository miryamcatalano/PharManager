package View.farmacia.Ordini;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;
import Controller.azienda.OrdineController;
import Controller.azienda.PrenotazioneController;
import Models.Current;
import Models.Ordine;
import View.farmacia.BarraMenuFarmacia;
import utilities.GUItems;
import utilities.rendertable.commons.OrdiniTable;

public class OrdiniView extends JFrame {

	private OrdiniTable ordiniTable;
	private ArrayList<Ordine> ordini = new ArrayList<>();
	
	public OrdiniView() {
		
		super("Visualizza ordini");//titolo finestra (frame)
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
		add(GUItems.getDefaultTitleLabel("Visualizza Ordini"), c);

		
		//Riga 1 - tabella
		//tabella magazzino
		
		initializeOrdiniTable();
		JScrollPane scrollPane = GUItems.getScrollPane(ordiniTable.getTable());
		
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
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
		setResizable(true);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);//per ultimo
	}
	
	private void initializeOrdiniTable() {		
		
		ordini = OrdineController.getOrdiniByFarmaciaId(Current.getCurrentFarmacia().getId());
		
		ordiniTable = new OrdiniTable(ordini);
		ordiniTable.setAction(apriOrdine());
	}
	
	private AbstractAction apriOrdine() {
		return new AbstractAction() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				 int row = Integer.valueOf( e.getActionCommand() );
				 Ordine o = ordini.get(row);
				 PrenotazioneController.visualizzaPrenotazioniOrdine(o);
			}
		};
	}
}
