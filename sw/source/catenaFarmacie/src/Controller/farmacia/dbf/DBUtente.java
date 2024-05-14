package Controller.farmacia.dbf;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Models.User;

public class DBUtente extends DBMSBoundary_F {
 	/*
	 * attributi classe farmacia
	 * int idFarmacia, String piva, String nome, String indirizzo, String citta
	 * HashMap<Integer, prodottoMagazzino> magazzino, utente currentUser
	 */
	
	protected DBUtente() {
		super();
	}
	
	//metodi
	protected static User getUtenteById(int id) {
    
    	User u = null;
    	
    	try{
    		
    		String cmdText = "SELECT * FROM utente WHERE idutente=?;";
    		PreparedStatement ps = conn().prepareStatement(cmdText);    		
    		ps.setInt(1, 1);
    		
    		ResultSet r = ps.executeQuery();
    		
    		if(r.next()) {
    			
				int idu =  Integer.parseInt(r.getString("idutente"));
				String nome = r.getString("nomeutente");
				
				u = new User(idu, nome, User.FARMACISTA);
    		}
    		
    		r.close();
    		ps.close();
    	}
    	
    	catch(Exception e) {
			System.out.println(e.getMessage());
    	}
    	
    	return u;
    }
	
	protected static User getUtenteByFarmacia(int idFarmacia) {
        
    	User u = null;
    	
    	try{
    		
    		String cmdText = "SELECT * FROM utente WHERE reffarmacia=?;";
    		PreparedStatement ps = conn().prepareStatement(cmdText);    		
    		ps.setInt(1, 1);
    		
    		ResultSet r = ps.executeQuery();
    		
    		if(r.next()) {
    			
				int idu =  Integer.parseInt(r.getString("idutente"));
				String nome = r.getString("nomeutente");
				
				u = new User(idu, nome, User.FARMACISTA);
    		}
    		
    		r.close();
    		ps.close();
    	}
    	
    	catch(Exception e) {
			System.out.println(e.getMessage());
    	}
    	
    	return u;
    }
	
	protected static ArrayList<User> getUtenti() {
    	
    	ArrayList<User> results = new ArrayList<>();
    	
    	try{    		
    		
    		Statement stmt = conn().createStatement();
    		String cmdText = "SELECT * FROM farmacia;";    		    		
    		ResultSet rs = stmt.executeQuery(cmdText);
    		
    		while(rs.next()) {    			
    			int idu =  Integer.parseInt(rs.getString("idutente"));
				String nome = rs.getString("nomeutente");
				
				results.add(new User(idu, nome, User.FARMACISTA));
    		}
    		
    		rs.close();
    		stmt.close();
    		
    	}
    	
    	catch(Exception e) {
			System.out.println(e.getMessage());
    	}
    	
    	return results;
    }
}
