package Controller.farmacia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Controller.farmacia.dbf.DBUtente;
import Models.Current;
import Models.User;
import View.farmacia.Home.HomeViewFarmacia;
import utilities.Navigation;
import utilities.security.Password;

public class AutenticazioneFarmacistaController extends DBUtente {

	private AutenticazioneFarmacistaController() {}
	
	public static void loginFarmacista() {
		Navigation.changePage(HomeViewFarmacia.getInstance());
	}
	
	public static boolean validateFarmacista(String nomeutente, String password) {
				
		boolean esitoAutenticazione;		
		try{
			
    		String cmdText = "SELECT * FROM utente WHERE nomeutente=?;";
    		PreparedStatement ps = conn().prepareStatement(cmdText);
    		ps.setString(1, nomeutente);
    		
    		ResultSet r = ps.executeQuery();
    		
    		if(r.next()) {
				String storedHashedPwd = r.getString("password");
				int saltFromDB = r.getInt("salt");
    		
				Password pwd = new Password(password, saltFromDB);//test user password:mimmo aaa
				esitoAutenticazione = pwd.ComputeSaltedHash().equals(storedHashedPwd);
				
				if(esitoAutenticazione) {
					
					int idUtente = r.getInt("idutente");
					Current.loadUtente(idUtente, nomeutente, User.FARMACISTA);
					Current.loadFarmacia(idUtente);
				}
    		}
    		
    		else {
    			esitoAutenticazione = false;
    			System.out.println("Non autenticato");
    		}
    		
    		r.close();
    		ps.close();
    		
    	}
    	
    	catch(Exception e) {
			System.out.println("exc validateuser: " + e.getMessage() + " " + e.getLocalizedMessage());
			esitoAutenticazione = false;
    	}
    	
    	return esitoAutenticazione;
	}
	
	public static boolean farmacistaFirma(String nomeutente, String password) {
		boolean auth;		
		try{
			
    		String cmdText = "SELECT * FROM utente WHERE nomeutente=?;";
    		PreparedStatement ps = conn().prepareStatement(cmdText);
    		ps.setString(1, nomeutente);
    		
    		ResultSet r = ps.executeQuery();
    		
    		if(r.next()) {
    			
				String storedHashedPwd = r.getString("password");
				int saltFromDB = r.getInt("salt");
				
				Password pwd = new Password(password, saltFromDB);//test user password:mimmo aaa
				auth = pwd.ComputeSaltedHash().equals(storedHashedPwd);
    		}
    		
    		else {
    			auth = false;
    			System.out.println("Non autenticato");
    		}
    		
    		r.close();
    		ps.close();
    		//m.CloseConnection();
    	}
    	
    	catch(Exception e) {
			System.out.println("exc validateuser: " + e.getMessage() + " " + e.getLocalizedMessage());
			auth = false;
    	}
    	
    	return auth;
	}
}
