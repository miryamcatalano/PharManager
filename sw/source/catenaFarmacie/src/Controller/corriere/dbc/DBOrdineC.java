package Controller.corriere.dbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import Controller.azienda.dba.DBMSBoundary_A;
import Models.Current;
import Models.Ordine;
import Models.Tempo;

public class DBOrdineC extends DBMSBoundary_A {

	protected DBOrdineC() {
		super();
	}
	
	//SELECT
	protected static ArrayList<Ordine> getOrdiniLiberi() {
		//ottengo tutti gli ordini non in carico da nessuno
		ArrayList<Ordine> ordini = new ArrayList<>();
		
		try{   		
    		String cmdText = "SELECT ordine.*"
    				+ " FROM azienda.ordine"
    				+ " WHERE ordine.ido NOT IN "
    					+ "(SELECT ordine.ido FROM azienda.corriere, azienda.ordine, azienda.consegna "
    					+ "WHERE ordine.ido = consegna.refordine AND corriere.idcorriere=consegna.refcorriere);";
    		
    		Statement s = conn().createStatement();
    		ResultSet rs = s.executeQuery(cmdText);
    		
    		while(rs.next()) {
    			
    			int ido =  rs.getInt("ido");
				Date dataCons = rs.getDate("dataconsegnaprevista");
				Date dataOrdine = rs.getDate("data");
				int stato = rs.getInt("stato");
				
				ordini.add(new Ordine(ido, dataCons, dataOrdine, stato));			
    		}
    		
    		rs.close();
    		s.close();
    	}
    	
    	catch(Exception e) {
			System.out.println(e.getMessage());
			ordini.add(new Ordine(-1, null, null, -1));
    	}
		
		return ordini;
	}
	
	protected static ArrayList<Ordine> getOrdiniByIdCorriere(int idUtente) {
		//ottengo tutti gli ordini non in carico da nessuno
		ArrayList<Ordine> ordini = new ArrayList<>();
		
		try{   		
			
    		String cmdText = "SELECT ordine.* FROM ordine, consegna WHERE ordine.stato < ? AND ordine.ido = consegna.refordine AND consegna.refcorriere = ? AND data <= ?";
    		
    		PreparedStatement ps = conn().prepareStatement(cmdText);
    		ps.setInt(1, Ordine.CONSEGNATO);
    		ps.setInt(2, idUtente);
    		ps.setDate(3, new java.sql.Date(Tempo.Now().getTime()));
			
    		ResultSet rs = ps.executeQuery();
    		
    		while(rs.next()) {
    			
    			int ido =  rs.getInt("ido");
				Date dataCons = rs.getDate("dataconsegnaprevista");
				Date dataOrdine = rs.getDate("data");
				int stato = rs.getInt("stato");
				
				ordini.add(new Ordine(ido, dataCons, dataOrdine, stato));			
    		}
    		
    		rs.close();
    		ps.close();
    	}
    	
    	catch(Exception e) {
			System.out.println(e.getMessage());
			ordini.add(new Ordine(-1, null, null, -1));
    	}
		
		return ordini;
	}
	
	
	//INSERT
	protected static String insertConsegna(int idOrdine) {
		//ottengo id casuale
		int randomId = Current.getCurrentUser().getId();
		
		try{   		
    		String cmdText = "SELECT corriere.idcorriere FROM corriere ORDER BY RAND() LIMIT 1";
    		
    		Statement s = conn().createStatement();
    		ResultSet rs = s.executeQuery(cmdText);
    		
    		if(rs.next()) {
    			
    			randomId = rs.getInt("idcorriere");
    		}
    		
    		s.close();
    		rs.close();
    		
    	}
    	
    	catch(Exception e) {
			System.out.println(e.getMessage());
    	}
		
		return insertConsegna(idOrdine, randomId);
	}

	protected static String insertConsegna(int idOrdine, int idUser) {
		
		try {
			
			String cmdText = "INSERT INTO consegna(refordine, refcorriere) VALUES(?,?);";
			
			PreparedStatement ps = conn().prepareStatement(cmdText);
			
			ps.setInt(1,  idOrdine);
			ps.setInt(2, idUser);
			
			ps.execute();
			
			//ottengo id ultimo record inserito
			ps = conn().prepareStatement("SELECT last_insert_id() FROM consegna;");
			ResultSet rs = ps.executeQuery();
			rs.next();
			String result = rs.getString(1);//si spera ritorni l'id di ultimo inserimento
			
			ps.close();
			
			return result;
		}
		
		catch (Exception e) {
			return e.getMessage();
		}
	}

	//UPDATE
	protected static String updateStatoOrdine(int idOrdine, int stato) {
		try {			
			
			String cmdText = "UPDATE ordine SET stato=? WHERE ido=?";
			PreparedStatement ps = conn().prepareStatement(cmdText);
			
			ps.setInt(1,  stato);//refprenotazione
			ps.setInt(2,  idOrdine);//refprenotazione
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