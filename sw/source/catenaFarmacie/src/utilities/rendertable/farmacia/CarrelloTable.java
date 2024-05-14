package utilities.rendertable.farmacia;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;

import Models.ProdottoPrenotato;
import utilities.rendertable.ButtonColumn;
import utilities.rendertable.CustomTable;

public class CarrelloTable extends CustomTable {
	
	public static final int EAN = 3;
	public static final int COSTO = 5;
	public static final int DECREMENTA = 6;
	public static final int INCREMENTA = 7;
	public static final int QUANTITA = 8;
	
	private ButtonColumn aggiungiButtonColumn;
	private ButtonColumn sottraiButtonColumn;
	
	private static final String[] fields = {"Id", "Nome", "Principio attivo", "EAN", "Da Banco", "Costo", "Sottrai", "Aggiungi", "Quantità"};
	
	public CarrelloTable(ArrayList<ProdottoPrenotato> prodottiPrenotati) {
		super(prodottiToData(prodottiPrenotati), fields, DECREMENTA, INCREMENTA);
		setWidthPercentage(0.03, 0.21, 0.21, 0.12, 0.09, 0.08, 0.07, 0.07, 0.12);
	}
	
	//Gli action sono 2, in quanto i tasti saranno due. Uno per aggiungere, l'altro per togliere
	public void setSubAction(AbstractAction sottraiAction) {
		sottraiButtonColumn = new ButtonColumn(super.getTable(), sottraiAction, DECREMENTA, "[-]");
		sottraiButtonColumn.setMnemonic(KeyEvent.VK_MINUS);
	}
	
	public void setAddAction(AbstractAction aggiungiAction) {	
		aggiungiButtonColumn = new ButtonColumn(super.getTable(), aggiungiAction, INCREMENTA, "[+]");
		aggiungiButtonColumn.setMnemonic(KeyEvent.VK_PLUS);
	}
	
	public static String[][] prodottiToData(ArrayList<ProdottoPrenotato> prodotti) {
				
		int lengthP = prodotti.size();
		String[][] data = new String[lengthP][9];
		
		for(int i=0;i<lengthP;i++) {//righe
						
			ProdottoPrenotato prodotto = prodotti.get(i);			
			
			data[i][0] = Integer.toString(prodotto.getId());
			data[i][1] = prodotto.getNome();
			data[i][2] = prodotto.getPrincipioAttivo();
			data[i][3] = prodotto.getEan();
			data[i][4] = prodotto.isDaBanco() ? "Da banco" : "Non da banco";
			data[i][5] = prodotto.getCosto().toString();
			data[i][6] = "[-]";
			data[i][7] = "[+]";
			data[i][8] = "1";
		}
		return data;
	}
}
