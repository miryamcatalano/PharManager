package Controller.corriere.dbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Controller.azienda.dba.DBMSBoundary_A;
import Models.User;

public class DBCorriere extends DBMSBoundary_A {
 	/*
	 * attributi classe farmacia
	 * int idFarmacia, String piva, String nome, String indirizzo, String citta
	 * HashMap<Integer, prodottoMagazzino> magazzino, utente currentUser
	 */
	
	protected DBCorriere() {
		super();
	}
	
	//metodi
	protected static User getUtenteById(int id) {
    
    	User u = null;
    	
    	try{
    		
    		String cmdText = "SELECT * FROM corriere WHERE idcorriere=?;";
    		PreparedStatement ps = conn().prepareStatement(cmdText);    		
    		ps.setInt(1, 1);
    		
    		ResultSet r = ps.executeQuery();
    		
    		if(r.next()) {
    			
				int idu =  Integer.parseInt(r.getString("idcorriere"));
				String nome = r.getString("nomeutente");
				
				u = new User(idu, nome, User.CORRIERE);
    		}
    		
    		r.close();
    		ps.close();
    	}
    	
    	catch(Exception e) {
			System.out.println(e.getMessage());
    	}
    	
    	return u;
    }
}
