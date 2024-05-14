package Controller.azienda;

import Controller.azienda.dba.DBMSBoundary_A;
import Models.Current;
import Models.User;
import View.azienda.Home.HomeViewAzienda;
import utilities.Navigation;

public class AutenticazioneDipendenteController extends DBMSBoundary_A {

	private AutenticazioneDipendenteController() {}
	
	public static void loginDipendente() {
		Navigation.changePage(HomeViewAzienda.getInstance());
	}
	
	public static boolean validateDipendente(String nomeutente, String password) {		
		try {
			if(nomeutente.equals("dipaz") && password.equals("pass")) {
				Current.loadUtente(1, nomeutente, User.DIP_AZIENDA);
				return true;
			}
			return false;
		}
		catch (Exception e) {			
			return false;
		}
	}
}
