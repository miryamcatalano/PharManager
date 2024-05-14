package Controller.farmacia;

import java.util.ArrayList;

import Controller.farmacia.dbf.DBProdottoF;
import Models.Prodotto;

public class ProdottoFarmaciaController extends DBProdottoF {
	
	public static Prodotto getProdottoByEan(String ean) {
		return getByEan(ean);
	}
	
	public static Prodotto getProdottoById(int idProdotto) {
		return getById(idProdotto);
	}
	
	public static ArrayList<Prodotto> getProdotti() {
		return getAll();
	}
	
	public static String aggiungiProdotto(Prodotto p) {
		
		try {
			return insertProdotto(p);//restituisce l'id del prodotto
		}
		
		catch (Exception e) {
			return e.getMessage();
		}
	}
	
	private ProdottoFarmaciaController() {}
}

