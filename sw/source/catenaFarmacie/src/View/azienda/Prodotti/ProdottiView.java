package View.azienda.Prodotti;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;
import Controller.azienda.ProdottoAziendaController;
import Models.ProdottoPrenotato;
import View.azienda.BarraMenuAzienda;
import utilities.GUItems;
import utilities.Helper;
import utilities.Navigation;
import utilities.GUItems.AlertType;
import utilities.rendertable.azienda.ProdottiAziendaTable;

public class ProdottiView extends JFrame {

	private JTable prodottiTable;
	
	public ProdottiView() {
		
		super("Prodotti azienda");//titolo finestra (frame)
		setJMenuBar(BarraMenuAzienda.getMenuBar());
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

        
        c.weighty = 0.01;//specifica il "peso" del componente. Se non specificato per nessuno verranno posizionati tutti nella parte centrale
        c.weightx = 0.01;
		
		c.gridy = 0;//riga
		c.gridx = 0;//colonna
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(30, 0, 0, 0);
		add(GUItems.getDefaultTitleLabel("Lista prodotti"), c);
		
		
		
		//tabella magazzino
		prodottiTable = getTableProdotti();
		JScrollPane scrollPane = GUItems.getScrollPane(prodottiTable);
		
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
	
	private JTable getTableProdotti() {		
		
		ArrayList<ProdottoPrenotato> prodottiAzienda = ProdottoAziendaController.getProdottiAzienda();
		ProdottiAziendaTable ct = new ProdottiAziendaTable(prodottiAzienda);
		
		AbstractAction eliminaAction = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = Integer.valueOf( e.getActionCommand() );
				String result = ProdottoAziendaController.deleteProdotto(prodottiAzienda.get(row).getId());
				
				if(!Helper.isNumber(result)) {
					alertErroreEliminazioneProdotto(result);
				}
				
				else {
					alertProdottoEliminato();
					Navigation.changePage(new ProdottiView());
				}
			}
		};
		
		ct.setEliminaAction(eliminaAction);
		return ct.getTable();
	}
	
	private void alertProdottoEliminato() {
		GUItems.Alert("Prodotto eliminato con successo", "Prodotto eliminato", AlertType.INFO);
	}
	
	private void alertErroreEliminazioneProdotto(String msg) {
		GUItems.AlertError(GUItems.htmlOf("Si è verificato un errore durante l'eliminazione del prodotto.<br/>" + msg));
	}
}
