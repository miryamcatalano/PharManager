package utilities.rendertable.corriere;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;

import Models.Ordine;
import utilities.Helper;
import utilities.rendertable.ButtonColumn;
import utilities.rendertable.CustomTable;

public class NuoviOrdiniTable extends CustomTable {
	
	public static final int ID_ORDINE = 0;
	public static final int CONSEGNA_PREVISTA = 1;
	public static final int PRENDI_IN_CARICO = 2;
	
	private static String[] arrayFields = {"Id", "Consegna entro", "Prendi in carico"};
	
	private ButtonColumn prendiInCaricoButtonColumn;
	
	public NuoviOrdiniTable(ArrayList<Ordine> ordini) {
		
		//colonne: id-consegna-data-prendi in carico
		super(ordiniToData(ordini), arrayFields, PRENDI_IN_CARICO);
		setWidthPercentage(0.10, 0.52, 0.48);
	}
	
	public void setAction(AbstractAction caricaAction) {
		prendiInCaricoButtonColumn = new ButtonColumn(super.getTable(), caricaAction, PRENDI_IN_CARICO, "Prendi in carico");
		prendiInCaricoButtonColumn.setMnemonic(KeyEvent.VK_D);		
	}
	
	public static String[][] ordiniToData(ArrayList<Ordine> ordini) {
		/*
		 * Colonne:
		 * IdOrdine-DataConsegnaPrevista-DataPrenotazione-Prenotazioni-Button
		 */
		
		int lengthP = ordini.size();
		String[][] data = new String[lengthP][3];
		
		for(int i=0;i<lengthP;i++) {//righe
			
			Ordine o = ordini.get(i);			
			data[i][0] = Integer.valueOf(o.getId()).toString();
			data[i][1] = Helper.dateToString(o.getDataConsegnaPrevista());
			data[i][2] = "Prendi in carico";
		}
		
		return data;
	}
}
