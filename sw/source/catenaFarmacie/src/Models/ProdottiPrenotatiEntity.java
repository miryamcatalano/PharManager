package Models;

import java.util.ArrayList;

public class ProdottiPrenotatiEntity {

private ArrayList<ProdottoPrenotato> prodotti = new ArrayList<>();
	
	public ProdottiPrenotatiEntity(ArrayList<ProdottoPrenotato> prodotti) {
		this.prodotti = prodotti;
	}	
	public ArrayList<ProdottoPrenotato> getProdottiPrenotati(){
		return this.prodotti;
	}
}