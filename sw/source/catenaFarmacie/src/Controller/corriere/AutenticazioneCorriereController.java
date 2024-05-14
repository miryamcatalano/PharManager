package Controller.corriere;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Controller.corriere.dbc.DBCorriere;
import Models.Current;
import Models.User;
import View.corriere.Home.HomeViewCorriere;
import utilities.Navigation;
import utilities.security.Password;

public class AutenticazioneCorriereController extends DBCorriere {

	private AutenticazioneCorriereController() {}
	
	public static void loginCorriere() {
		Navigation.changePage(HomeViewCorriere.getInstance());
	}
	
	public static boolean validateCorriere(String nomeutente, String password) {
				
		boolean auth;		
		try{
			
    		String cmdText = "SELECT * FROM corriere WHERE nomeutente=?;";
    		
    		PreparedStatement ps = conn().prepareStatement(cmdText);
    		ps.setString(1, nomeutente);
    		
    		ResultSet r = ps.executeQuery();
    		
    		if(r.next()) {
    			
				String storedHashedPwd = r.getString("password");
				int saltFromDB = r.getInt("salt");
				
				Password pwd = new Password(password, saltFromDB);//test user:password=ciro.verdi:corriere				
				auth = pwd.ComputeSaltedHash().equals(storedHashedPwd);
				
				if(auth) {
					
					int idUtente = r.getInt("idcorriere");
					
					Current.loadUtente(idUtente, nomeutente, User.CORRIERE);
					Current.loadFarmacia(idUtente);
				}
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
