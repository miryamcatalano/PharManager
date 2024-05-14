package Controller.azienda;

import java.util.ArrayList;

import Controller.azienda.dba.DBProdottoA;
import Models.Prodotto;
import Models.ProdottoPrenotato;
import View.farmacia.Ordini.ProdottiAziendaView;
import utilities.Navigation;

public class ProdottoAziendaController extends DBProdottoA {

	private ProdottoAziendaController() {}
	
	public static void visualizzaProdotti() {
		Navigation.changePage(new ProdottiAziendaView());
	}
	
	//SELECT
	public static ArrayList<ProdottoPrenotato> getProdottiAzienda(){
		return getProdotti();
	}
	
	public static Prodotto getProdottoByEan(String ean) {
		return getProdottoByEAN(ean);
	}
	
	public static ArrayList<ProdottoPrenotato> getProdottiOrdinePeriodico(){
		//i prodotti devono essere di tutte le categorie di farmaci da banco
		return prodottiOrdinePeriodico();
		
	}
	
	//INSERT
	public static String insertProdotto(Prodotto prodotto) {
		return insertNewProdotto(prodotto);
	}
	
	//DELETE
	public static String deleteProdotto(int idProdotto) {
		return deleteProdottoById(idProdotto);
	}
	
	//UPDATE
	public static String aggiornaScorteAzienda() {
		return updateScorteAzienda();
	}
	
	public static String defalcaScorteProdottoAzienda(String eanProdotto, int qta) {
		return updateScorteProdottoAzienda(eanProdotto, qta);
	}
}
