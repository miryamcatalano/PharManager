package utilities.rendertable.corriere;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;

import Models.Ordine;
import utilities.GUItems;
import utilities.Helper;
import utilities.rendertable.ButtonColumn;
import utilities.rendertable.CustomTable;

public class InCaricoTable extends CustomTable {
	
	private static final int AGGIORNA = 3;
	private static String[] arrayFields = {"Id", "Entro il", "Stato", "Stato"};
	
	private ButtonColumn aggiornaButtonColumn;
	
	public InCaricoTable(ArrayList<Ordine> ordini) {
		
		//colonne: id-consegna-data-prendi in carico
		super(ordiniToData(ordini), arrayFields, AGGIORNA);
		setWidthPercentage(0.04, 0.28, 0.45, 0.27);
	}
	
	public void setAction(AbstractAction aggiornaAction) {
		aggiornaButtonColumn = new ButtonColumn(super.getTable(), aggiornaAction, AGGIORNA, "Aggiorna");
		aggiornaButtonColumn.setMnemonic(KeyEvent.VK_D);		
	}
	
	public static String[][] ordiniToData(ArrayList<Ordine> ordini) {
				
		int lengthP = ordini.size();
		String[][] data = new String[lengthP][4];
		
		for(int i=0;i<lengthP;i++) {//righe
			
			Ordine o = ordini.get(i);			
			data[i][0] = Integer.valueOf(o.getId()).toString();
			data[i][1] = Helper.dateToString(o.getDataConsegnaPrevista());
			data[i][2] = GUItems.htmlOf("<span style='font-size:70%;'>" + o.getStatoToString() + "</span>");
			data[i][3] = "Aggiorna";
		}
		
		return data;
	}
}
