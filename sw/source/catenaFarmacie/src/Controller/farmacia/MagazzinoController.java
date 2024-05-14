package Controller.farmacia;

import java.math.BigDecimal;
import java.util.Date;

import Controller.farmacia.dbf.DBMagazzino;
import Models.Current;
import Models.Magazzino;
import Models.Prodotto;
import View.farmacia.Magazzino.MagazzinoView;
import utilities.Navigation;

public class MagazzinoController extends DBMagazzino {

	public static void paginaMagazzino() {
		Navigation.changePage(new MagazzinoView());
	}
	
	public static Magazzino getMagazzinoFarmaciaCorrente() {
		return getMagazzinoByIdFarmacia(Current.getCurrentFarmacia().getId());
	}
	
	public static String insertProdottoInMagazzino(Prodotto prodottoFarmacia, Date scadenza, int qta, BigDecimal costo) {
		if(prodottoFarmacia.getId() > 0) {
				return insertInMagazzino(prodottoFarmacia, scadenza, qta, costo);
		}
		else {
			return "error";
		}
	}
	
	private MagazzinoController() {}
}
