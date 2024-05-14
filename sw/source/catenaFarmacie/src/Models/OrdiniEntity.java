package Models;

import java.util.ArrayList;

public class OrdiniEntity {

	private ArrayList<Ordine> ordini = new ArrayList<>();
	
	public OrdiniEntity(ArrayList<Ordine> ordini) {
		this.ordini = ordini;
	}
	
	public ArrayList<Ordine> getOrdini(){
		return this.ordini;
	}
}
