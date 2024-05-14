package utilities.rendertable.corriere;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;

import Models.ProdottoPrenotato;
import utilities.GUItems;
import utilities.rendertable.ButtonColumn;
import utilities.rendertable.CustomTable;

public class PrenotazioniCorriereTable extends CustomTable {
	
	private static final int FIRMA = 4;
	private ButtonColumn firmaButtonColumn;
	
	private static final String[] fields = {GUItems.resize("Id",70), GUItems.resize("Nome",70), GUItems.resize("Qta",70), GUItems.resize("Consegnato",70), GUItems.resize("Azione",70)};
	
	public PrenotazioniCorriereTable(ArrayList<ProdottoPrenotato> prenotazioni) {
		
		//colonne: id-nomeprodotto-principioattivo-ean-scadenza-cost-qta-stato
		super(prenotazioniToData(prenotazioni), fields, FIRMA);
		setWidthPercentage(0.07, 0.30, 0.07, 0.26, 0.30);
	}
	
	public void setRiceviAction(AbstractAction riceviAction) {
		firmaButtonColumn = new ButtonColumn(super.getTable(), riceviAction, FIRMA, "Firma");
		firmaButtonColumn.setMnemonic(KeyEvent.VK_D);		
	}
	
	public static String[][] prenotazioniToData(ArrayList<ProdottoPrenotato> prenotazioni) {
		
		int lengthP = prenotazioni.size();
		String[][] data = new String[lengthP][5];
		
		for(int i=0;i<lengthP;i++) {//righe
			
			ProdottoPrenotato prodottoPrenotato = prenotazioni.get(i);			
			
			data[i][0] = GUItems.resize(Integer.valueOf(prodottoPrenotato.getIdPrenotazione()).toString(), 70);
			data[i][1] = GUItems.resize(prodottoPrenotato.getNome(), 52);			
			data[i][2] = GUItems.resize(Integer.valueOf(prodottoPrenotato.getQta()).toString(), 70);
			data[i][3] = prodottoPrenotato.isConsegnato() ? "<html><p style='color:green;font-size:75%;'>Consegnato</p></html>" : "<html><p style='color:red;font-size:75%'>Non consegnato</p></html>";
			data[i][4] = "Consegnato";
		}
		
		return data;
	}
}
