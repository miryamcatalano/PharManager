package utilities.rendertable.farmacia;

import java.util.ArrayList;

import Models.ProdottoPrenotato;
import utilities.Helper;
import utilities.rendertable.CustomTable;

public class MagazzinoTable extends CustomTable {//VPTable -> Visualizza Prodotti Table
	
	private static final String[] fields = {"Nome","Principio attivo", "Costo", "EAN", "Scadenza", "Qta"};	
	
	public MagazzinoTable(ArrayList<ProdottoPrenotato> prodotti) {
		super(prodottiToData(prodotti), fields);
		setWidthPercentage(0.30, 0.21, 0.15, 0.21, 0.10, 0.03);
	}
	
	public static String[][] prodottiToData(ArrayList<ProdottoPrenotato> prodotti){
		
		int lengthP = prodotti.size();
		String[][] data = new String[lengthP][6];
		
		for(int i=0;i<lengthP;i++) {
			
			ProdottoPrenotato prodotto = prodotti.get(i);
			
			data[i][0] = prodotto.getNome();
			data[i][1] = prodotto.getPrincipioAttivo();
			data[i][2] = prodotto.getCosto().toString();
			data[i][3] = prodotto.getEan();
			data[i][4] = Helper.dateToString(prodotto.getScadenza());
			data[i][5] = Integer.toString(prodotto.getQta());
			
		}
		return data;
	}
}
