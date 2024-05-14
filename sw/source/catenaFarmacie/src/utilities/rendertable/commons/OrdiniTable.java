package utilities.rendertable.commons;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;

import Models.Ordine;
import utilities.GUItems;
import utilities.Helper;
import utilities.rendertable.ButtonColumn;
import utilities.rendertable.CustomTable;

public class OrdiniTable extends CustomTable {
	
	private static final int APRI = 5;
	private ButtonColumn apriButtonColumn;
	
	private static String[] fields = {"Id", "Consegna prevista il", "Ordinato il", "Prodotti prenotati", "Stato consegna", "Apri ordine"};	
	
	public OrdiniTable(ArrayList<Ordine> ordini) {
		super(ordiniToData(ordini), fields, APRI);	
		setWidthPercentage(0.03, 0.18, 0.12, 0.37, 0.21, 0.09);
	}
	
	public void setAction(AbstractAction caricaAction) {
		apriButtonColumn = new ButtonColumn(super.getTable(), caricaAction, APRI, "Apri");
		apriButtonColumn.setMnemonic(KeyEvent.VK_D);		
	}
	
	public static String[][] ordiniToData(ArrayList<Ordine> ordini) {
		
		int lengthP = ordini.size();
		String[][] data = new String[lengthP][6];
		
		for(int i=0;i<lengthP;i++) {//righe
			
			Ordine ordine = ordini.get(i);
			
			data[i][0]= Integer.toString(ordine.getId());
			data[i][1]= Helper.dateToString(ordine.getDataConsegnaPrevista());
			data[i][2]= Helper.dateToString(ordine.getDataOrdinazione());;
			data[i][3]= ordine.getTextPrenotazioni();
			data[i][4]= GUItems.htmlOf(ordine.getStatoToString());
			data[i][5] = "Apri";
		}
		
		return data;
	}
}
