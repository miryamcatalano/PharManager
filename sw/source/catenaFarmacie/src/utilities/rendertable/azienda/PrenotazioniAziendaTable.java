package utilities.rendertable.azienda;

import java.util.ArrayList;

import Models.ProdottoPrenotato;
import utilities.Helper;
import utilities.rendertable.CustomTable;

public class PrenotazioniAziendaTable extends CustomTable {
		
	private static final String[] fields = {"Id", "Nome", "P. Attivo", "EAN", "Scadenza", "Costo", "Qta", "Consegnato"};
	
	public PrenotazioniAziendaTable(ArrayList<ProdottoPrenotato> prenotazioni) {
		
		//colonne: id-nomeprodotto-principioattivo-ean-scadenza-cost-qta-stato
		super(prenotazioniToData(prenotazioni), fields);
		setWidthPercentage(0.07, 0.16, 0.16, 0.16, 0.12, 0.09, 0.12, 0.12);
	}
	
	public static String[][] prenotazioniToData(ArrayList<ProdottoPrenotato> prenotazioni) {
		
		int lengthP = prenotazioni.size();
		String[][] data = new String[lengthP][8];
		
		for(int i=0;i<lengthP;i++) {//righe
			
			ProdottoPrenotato p = prenotazioni.get(i);
			
			data[i][0] = Integer.valueOf(p.getIdPrenotazione()).toString();
			data[i][1] = p.getNome();
			data[i][2] = p.getPrincipioAttivo();
			data[i][3] = p.getEan();
			data[i][4] = Helper.dateToString(p.getScadenza());
			data[i][5] = p.getCosto().toString();
			data[i][6] = Integer.valueOf(p.getQta()).toString();
			data[i][7] = p.isConsegnato() ? "<html><p style='color:green;'>Consegnato</p></html>" : "<html><p style='color:red;'>Non consegnato</p></html>";			
		}
		
		return data;
	}
}
