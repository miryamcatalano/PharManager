package Models;

import java.util.ArrayList;

public class Magazzino {
	
	private ArrayList<ProdottoPrenotato> prodotti;
	
	public Magazzino(ArrayList<ProdottoPrenotato> prodotti) {
		this.prodotti = prodotti;
	}
	
	//Getter
	public ArrayList<ProdottoPrenotato> getProdottiMagazzino(){
		return this.prodotti;
	}
	
	public void addProdotto(ProdottoPrenotato pm) {
		this.prodotti.add(pm);
	}
}
