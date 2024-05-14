package Controller.farmacia;

import Controller.farmacia.dbf.DBFarmacia;
import Models.Farmacia;
import View.farmacia.Info.InfoView;
import utilities.Navigation;

public class FarmaciaController extends DBFarmacia {

	
	public static void visualizzaInfo() {
		Navigation.changePage(InfoView.getInstance());
	}
	public static Farmacia getFarmaciaByUserId(int idUser) {
		return getFarmaciaByIdUser(idUser);
	}
	
	public static Farmacia getFarmaciaByIdFarmacia(int idFarmacia) {
		return getFarmaciaById(idFarmacia);
	}

	public static String aggiornaDataUltimoRifornimento(int idFarmacia) {
		return updateUltimoRifornimento(idFarmacia);
	}
	
	private FarmaciaController() {}
}
