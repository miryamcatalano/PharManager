package utilities.rendertable.azienda;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;

import Models.Prodotto;
import Models.ProdottoPrenotato;
import utilities.rendertable.ButtonColumn;
import utilities.rendertable.CustomTable;

public class ProdottiAziendaTable extends CustomTable {//VPTable -> Visualizza Prodotti Table

	private static final int ELIMINA = 6;
	private static final String[] fields = {"Id","Nome", "Principio attivo", "EAN", "Da Banco", "Costo", "Elimina prodotto"};
	
	private ButtonColumn eliminaButtonColumn;
	
	
	public ProdottiAziendaTable(ArrayList<ProdottoPrenotato> prodottiAzienda) {
		super(prodottiAziendaToData(prodottiAzienda), fields, ELIMINA);
		setWidthPercentage(0.05, 0.21, 0.19, 0.16, 0.12, 0.07, 0.19);
	}
	
	public void setEliminaAction(AbstractAction eliminaAction) {
		eliminaButtonColumn = new ButtonColumn(super.getTable(), eliminaAction, ELIMINA, "Elimina");
		eliminaButtonColumn.setMnemonic(KeyEvent.VK_CANCEL);
	}
	
	public static String[][] prodottiAziendaToData(ArrayList<ProdottoPrenotato> prodotti) {
		
		int lengthP = prodotti.size();
		String[][] data = new String[lengthP][7];
		
		for(int i=0;i<lengthP;i++) {//righe
			
			Prodotto p = prodotti.get(i);
			
			data[i][0] = Integer.toString(p.getId());
			data[i][1] = p.getNome();
			data[i][2] = p.getPrincipioAttivo();
			data[i][3] = p.getEan();
			data[i][4] = p.isDaBanco() ? "Da banco" : "Non da banco";
			data[i][5] = p.getCosto().toString();
			data[i][6] = "Elimina";
		}
		
		return data;
	}
}
