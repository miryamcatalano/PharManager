package utilities.rendertable.farmacia;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;

import Models.ProdottoPrenotato;
import utilities.rendertable.ButtonColumn;
import utilities.rendertable.CustomTable;

public class VisualizzaProdottiTable extends CustomTable {//VPTable -> Visualizza Prodotti Table
	
	public static final int EAN = 3;
	public static final int AGGIUNGI_O_RIMUOVI = 6;	
	
	private ButtonColumn aggiungiAlCarrelloButtonColumn;
	
	private static final String[] fields = {"Id", "Nome", "Principio attivo", "EAN", "Da Banco", "Costo", "Carrello"};
	
	public VisualizzaProdottiTable(ArrayList<ProdottoPrenotato> prodottiAzienda) {
		super(prodottiToData(prodottiAzienda), fields, AGGIUNGI_O_RIMUOVI);		
		setWidthPercentage(0.03, 0.21, 0.21, 0.16, 0.1, 0.07, 0.12);
	}
	
	public void setAction(AbstractAction carrelloAction) {
		aggiungiAlCarrelloButtonColumn = new ButtonColumn(super.getTable(), carrelloAction, AGGIUNGI_O_RIMUOVI, "[+] Aggiungi");
		aggiungiAlCarrelloButtonColumn.setMnemonic(KeyEvent.VK_D);		
	}
	
	public static String[][] prodottiToData(ArrayList<ProdottoPrenotato> prodotti) {
		
		int lengthP = prodotti.size();
		String[][] data = new String[lengthP][7];
		
		
		for(int i=0;i<lengthP;i++) {//righe
			
			ProdottoPrenotato prodotto = prodotti.get(i);
			
			data[i][0] = Integer.toString(prodotto.getId());
			data[i][1] = prodotto.getNome();
			data[i][2] = prodotto.getPrincipioAttivo();
			data[i][3] = prodotto.getEan();
			data[i][4] = prodotto.isDaBanco() ? "Da banco" : "Non da banco";
			data[i][5] = prodotto.getCosto().toString();
			data[i][6] = "[+] Aggiungi";		
		}
		
		return data;
	}
}
