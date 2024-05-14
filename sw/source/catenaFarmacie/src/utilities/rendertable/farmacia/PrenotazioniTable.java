package utilities.rendertable.farmacia;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;

import Models.ProdottoPrenotato;
import utilities.GUItems;
import utilities.Helper;
import utilities.rendertable.ButtonColumn;
import utilities.rendertable.CustomTable;

public class PrenotazioniTable extends CustomTable {
	
	public static final int DECREMENTA = 6;
	public static final int INCREMENTA = 7;
	public static final int QTA = 8;
	public static final int CARICA = 10;

	private ButtonColumn caricaButtonColumn;
	private ButtonColumn aggiungiButtonColumn;
	private ButtonColumn sottraiButtonColumn;
	
	private static String[] fields = {GUItems.resize("Id",75),
			GUItems.resize("Nome",75),
			GUItems.resize("P. Attivo",75),
			GUItems.resize("EAN",75),
			GUItems.resize("Scadenza",75),
			GUItems.resize("Costo",75),
			GUItems.resize("Modifica -",75),
			GUItems.resize("Modifica + ",75),
			GUItems.resize("Qta",75),
			GUItems.resize("Consegnato",75),
			GUItems.resize("Carica",75)};
	
	public PrenotazioniTable(ArrayList<ProdottoPrenotato> prenotazioni) {
		super(prenotazioniToData(prenotazioni), fields, DECREMENTA, INCREMENTA, CARICA);
		setPercentages();
	}
	
	public PrenotazioniTable(ArrayList<ProdottoPrenotato> prenotazioni, ArrayList<ArrayList<Integer>> notEditableRows) {
		super(prenotazioniToData(prenotazioni), fields, notEditableRows, DECREMENTA, INCREMENTA, CARICA);
		setPercentages();		
	}
	
	private void setPercentages() {
		setWidthPercentage(0.03, 0.13, 0.12, 0.10, 0.09, 0.05, 0.09, 0.09, 0.03, 0.15,0.12);
	}
	
	public void setCaricaAction(AbstractAction caricaAction) {
		caricaButtonColumn = new ButtonColumn(super.getTable(), caricaAction, CARICA, "Carica");
		caricaButtonColumn.setMnemonic(KeyEvent.VK_D);		
	}
	
	public void setAddAction(AbstractAction addAction) {
		aggiungiButtonColumn = new ButtonColumn(super.getTable(), addAction, INCREMENTA, "[+]");
		aggiungiButtonColumn.setMnemonic(KeyEvent.VK_PLUS);
	}
	
	public void setSubAction(AbstractAction subAction) {
		sottraiButtonColumn = new ButtonColumn(super.getTable(), subAction, DECREMENTA, "[-]");
		sottraiButtonColumn.setMnemonic(KeyEvent.VK_MINUS);
	}
	
	public static String[][] prenotazioniToData(ArrayList<ProdottoPrenotato> prenotazioni) {
		
		int lengthP = prenotazioni.size();
		String[][] data = new String[lengthP][11];
		
		for(int i=0;i<lengthP;i++) {//righe
			
			ProdottoPrenotato prenotato = prenotazioni.get(i);			

			data[i][0] = GUItems.resize(Integer.toString(prenotato.getId()), 70);
			data[i][1] = GUItems.resize(prenotato.getNome(), 70);
			data[i][2] = GUItems.resize(prenotato.getPrincipioAttivo(), 70);
			data[i][3] = GUItems.resize(prenotato.getEan(), 70);
			data[i][4] = GUItems.resize(Helper.dateToString(prenotato.getScadenza()), 70);
			data[i][5] = GUItems.resize(prenotato.getCosto().toString(), 75);
			data[i][6] = "[-]";
			data[i][7] = "[+]";
			data[i][8] = GUItems.resize(Integer.toString(prenotato.getQta()), 75);
				
			if(prenotato.isCaricato()){
				data[i][9] = GUItems.htmlOf("<p style='color:green;'>Caricato</p>"); 
			}
			else
				data[i][9] = prenotato.isConsegnato() ? GUItems.htmlOf("<p style='color:blue;'>Consegnato</p>") : GUItems.htmlOf("<p style='color:red;'>Non consegnato</p>");
			
			data[i][10] = "Carica";
		}
		
		return data;
	}
}

