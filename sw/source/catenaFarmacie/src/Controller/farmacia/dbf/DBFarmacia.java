package Controller.farmacia.dbf;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Models.Farmacia;

public class DBFarmacia extends DBMSBoundary_F {
 	/*
	 * attributi classe farmacia
	 * int idFarmacia, String piva, String nome, String indirizzo, String citta
	 * HashMap<Integer, prodottoMagazzino> magazzino, utente currentUser
	 */
	
	protected DBFarmacia() {
		super();
	}
	
	//metodi
	protected static Farmacia getFarmaciaByIdUser(int idUser) {
    	
		Farmacia f = null;
		
    	try{
    		
    		String cmdText = "SELECT farmacia.* FROM farmacia, utente WHERE utente.idutente=? AND utente.reffarmacia = farmacia.idfarmacia LIMIT 1;";
    		PreparedStatement ps = conn().prepareStatement(cmdText);  
    		ps.setInt(1, idUser);
    		
    		ResultSet r = ps.executeQuery();
    		
    		if(r.next()) {
    			
				int idf =  r.getInt("idfarmacia");
				
				String piva = r.getString("piva");
				String nome = r.getString("nome");
				String indirizzo = r.getString("indirizzo");
				String citta = r.getString("citta");
				String telefono = r.getString("telefono");
				int giorniOrdine = r.getInt("giorni_ordine_periodico");
				Date ultimoRifornimento = r.getDate("ultimo_rifornimento");
				
				f = new Farmacia(idf, piva, nome, indirizzo, citta, telefono, giorniOrdine, ultimoRifornimento);
    		}
    		
    		r.close();
    		ps.close();
    	}
    	
    	catch(Exception e) {
			System.out.println(e.getMessage());
    	}
    	
    	return f;
    }
	
	protected static Farmacia getFarmaciaById(int idFarmacia) {
    	
		Farmacia f = null;
		
    	try{
    		
    		String cmdText = "SELECT farmacia.* FROM farmacia WHERE farmacia.idfarmacia=? LIMIT 1;";
    		PreparedStatement ps = conn().prepareStatement(cmdText);  
    		ps.setInt(1, idFarmacia);
    		
    		ResultSet r = ps.executeQuery();
    		
    		if(r.next()) {
    			
				int idf =  r.getInt("idfarmacia");				
				
				String piva = r.getString("piva");
				String nome = r.getString("nome");
				String indirizzo = r.getString("indirizzo");
				String citta = r.getString("citta");		
				String telefono = r.getString("telefono");
				int giorniOrdine = r.getInt("giorni_ordine_periodico");
				
				f = new Farmacia(idf, piva, nome, indirizzo, citta, telefono, giorniOrdine);
    		}
    		
    		r.close();
    		ps.close();
    	}
    	
    	catch(Exception e) {
			System.out.println(e.getMessage());
    	}
    	
    	return f;
    }
	
	protected static String updateUltimoRifornimento(int idFarmacia) {
		try {			
			
			String cmdText = "UPDATE farmacia SET ultimo_rifornimento=NOW() WHERE idfarmacia=?";
			PreparedStatement ps = conn().prepareStatement(cmdText);
			
			ps.setInt(1,  idFarmacia);//idfarmacia
			ps.execute();
			
			ps.close();
			
			return "0";
		}
		
		catch(SQLException e) {
			return e.getMessage();
		}
		
		catch (Exception e) {
			return e.getMessage();
		}
	}
}
