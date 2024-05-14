package Controller.azienda.dba;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import Controller.azienda.PrenotazioneController;
import Controller.corriere.OrdineCorriereController;
import Models.Current;
import Models.Ordine;
import Models.ProdottoPrenotato;
import Models.Tempo;
import utilities.Helper;

public class DBOrdine extends DBMSBoundary_A {

	protected DBOrdine() {
		super();
	}
	
	//SELECT
	protected static Ordine getOrdineByIdOrdine(int idOrdine) {
		
		Ordine o = null;
		
		try{   		
    		String cmdText = "SELECT ordine.* FROM ordine WHERE ido=?;";
    		
    		PreparedStatement ps = conn().prepareStatement(cmdText);  
    		ps.setInt(1, idOrdine);
    		
    		ResultSet rs = ps.executeQuery();
    		
    		if(rs.next()) {
    			
    			int ido =  rs.getInt("ido");
				Date dataCons = rs.getDate("dataconsegnaprevista");
				Date dataOrdine = rs.getDate("data");
				int stato = rs.getInt("stato");
				
				o = new Ordine(ido, dataCons, dataOrdine, stato);
			}
    		
    		rs.close();
    		ps.close();
    	}
    	
    	catch(Exception e) {
			System.out.println(e.getMessage());
			o = new Ordine(-1, null, null, -1);
    	}
		
		return o;
	}
	
	protected static Ordine getLastOrdineByFarmaciaId(int idFarmacia) {
		
		Ordine o = null;
		
		try{   		
    		String cmdText = "SELECT ordine.* FROM ordine WHERE periodico=1 AND reffarmacia=? ORDER BY data DESC LIMIT 1;";
    		
    		PreparedStatement ps = conn().prepareStatement(cmdText);  
    		ps.setInt(1, idFarmacia);
    		
    		ResultSet rs = ps.executeQuery();
    		
    		if(rs.next()) {
    			
    			int ido =  rs.getInt("ido");
				Date dataCons = rs.getDate("dataconsegnaprevista");
				Date dataOrdine = rs.getDate("data");
				int stato = rs.getInt("stato");
				
				o = new Ordine(ido, dataCons, dataOrdine, stato);
			}
    		
    		rs.close();
    		ps.close();
    	}
    	
    	catch(Exception e) {
			System.out.println(e.getMessage());
			o = new Ordine(-1, null, null, -1);
    	}
		
		return o;
	}

	protected static ArrayList<Ordine> getOrdiniByIdFarmacia(int idFarmacia, boolean delGiorno) {
			
		//campi ordine: ido, dataConsegnaPrev, prenotazioni, note
		
		ArrayList<Ordine> ordini = new ArrayList<>();
		
    	try{
    		
    		String cmdText; 
    		PreparedStatement ps;
    		if(delGiorno) {
    			cmdText = "SELECT ordine.* FROM ordine WHERE reffarmacia=? AND dataconsegnaprevista = ?;";
       		 	ps = conn().prepareStatement(cmdText);
       		 	ps.setInt(1, idFarmacia);
       		 	ps.setDate(2, new java.sql.Date(Tempo.Now().getTime()));
    		}
    		else {
    			cmdText = "SELECT ordine.* FROM ordine WHERE reffarmacia=?;";
    			ps = conn().prepareStatement(cmdText);
       		 	ps.setInt(1, idFarmacia);
    		}

    		
    		ResultSet r = ps.executeQuery();
    		
    		while(r.next()) {
    			
				int ido =  r.getInt("ido");				
				Date dataConsegna = r.getDate("dataconsegnaprevista");
				Date dataOrdine = r.getDate("data");
				int stato = r.getInt("stato");
				ArrayList<ProdottoPrenotato> prenotazioni = PrenotazioneController.getPrenotazioniByOrdine(ido, ProdottoPrenotato.TUTTE_LE_PRENOTAZIONI);
				
				ordini.add(new Ordine(ido, dataConsegna, dataOrdine, stato, prenotazioni));
    		}
    		
    		r.close();
    		ps.close();
    	}
    	catch(Exception e) {
			System.out.println(e.getMessage());
    	}
    	
    	return ordini;
	}
	
	protected static ArrayList<Ordine> getAll() {
		
		//campi ordine: ido, dataConsegnaPrev, prenotazioni, note
		
		ArrayList<Ordine> ordini = new ArrayList<>();
		
    	try{
    		
    		String cmdText = "SELECT ordine.* FROM ordine";
    		Statement s = conn().createStatement();
    		
    		ResultSet r = s.executeQuery(cmdText);
    		
    		while(r.next()) {
    			
				int ido =  r.getInt("ido");				
				Date dataConsegna = r.getDate("dataconsegnaprevista");
				Date dataOrdine = r.getDate("data");
				int stato = r.getInt("stato");
				ArrayList<ProdottoPrenotato> prenotazioni = PrenotazioneController.getPrenotazioniOrdine(ido);
				
				ordini.add(new Ordine(ido, dataConsegna, dataOrdine, stato, prenotazioni));
    		}
    		
    		r.close();
    		s.close();
    	}
    	catch(Exception e) {
			System.out.println(e.getMessage());
    	}
    	
    	return ordini;
	}
	
	protected static ArrayList<Ordine> getOrdiniNonCompletati() {
		
		//campi ordine: ido, dataConsegnaPrev, prenotazioni, note
		
		ArrayList<Ordine> ordini = new ArrayList<>();
		
    	try{
    		
    		String cmdText = "SELECT ordine.* FROM ordine WHERE (stato=? OR stato=?) AND stato_consegna_visualizzato=FALSE";
    		PreparedStatement ps;
			ps = conn().prepareStatement(cmdText);  
    		ps.setInt(1, Ordine.CONSEGNATO_IN_PARTE);
    		ps.setInt(2, Ordine.RESPINTO);        		
    		    		
    		ResultSet r = ps.executeQuery();
    		
    		while(r.next()) {
    			
				int ido =  r.getInt("ido");				
				Date dataConsegna = r.getDate("dataconsegnaprevista");
				Date dataOrdine = r.getDate("data");
				int stato = r.getInt("stato");
				int refFarmacia = r.getInt("reffarmacia");
				ArrayList<ProdottoPrenotato> prenotazioni = PrenotazioneController.getPrenotazioniOrdine(ido);
				ordini.add(new Ordine(ido, dataConsegna, dataOrdine, stato, refFarmacia, prenotazioni));
    		}
    		
    		r.close();
    		ps.close();
    	}
    	catch(Exception e) {
			System.out.println(e.getMessage());
    	}
    	
    	return ordini;
	}
	
	protected static ArrayList<Ordine> getOrdiniNonCompletatiDiIeri() {
		
		//campi ordine: ido, dataConsegnaPrev, prenotazioni, note
		
		ArrayList<Ordine> ordini = new ArrayList<>();
		
    	try{
    		
    		String cmdText = "SELECT ordine.* FROM ordine WHERE (stato=? OR stato=?) AND date(data)=?";
    		PreparedStatement ps;
			ps = conn().prepareStatement(cmdText);  
    		ps.setInt(1, Ordine.CONSEGNATO_IN_PARTE);
    		ps.setInt(2, Ordine.RESPINTO);
    		ps.setDate(3, new java.sql.Date(Tempo.getYesterdayDate().getTime()));
    		
    		
    		ResultSet r = ps.executeQuery();
    		
    		while(r.next()) {
    			
				int ido =  r.getInt("ido");				
				Date dataConsegna = r.getDate("dataconsegnaprevista");
				Date dataOrdine = r.getDate("data");
				int stato = r.getInt("stato");
				int refFarmacia = r.getInt("reffarmacia");
				ArrayList<ProdottoPrenotato> prenotazioni = PrenotazioneController.getPrenotazioniOrdine(ido);
				ordini.add(new Ordine(ido, dataConsegna, dataOrdine, stato, refFarmacia, prenotazioni));
    		}
    		
    		r.close();
    		ps.close();
    	}
    	catch(Exception e) {
			System.out.println(e.getMessage());
    	}
    	
    	return ordini;
	}


	protected static boolean ordinePuoCompletarsi(int refOrdine) {
		
		try {
			
			String cmdText = "SELECT COUNT(consegnato) FROM azienda.prenotato WHERE refordine=? and consegnato=0";
			
			PreparedStatement ps = conn().prepareStatement(cmdText);
			ps.setInt(1, refOrdine);
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			int nonConsegnati = rs.getInt(1);
			ps.close();
			
			return nonConsegnati == 0;//conto le prenotazioni non ancora consegnate 
		}
		
		catch(SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
	}
	
	//INSERT
	protected static String creaOrdine(int refFarmacia, boolean isPeriodico, Date dataOrdine) {
	
		Date consegnaPrevista = Helper.getRandomDate(3, 10, dataOrdine);//consegna stimata tra i 3 e i 10 giorni
		
		try {
			
			String cmdText = isPeriodico ? "INSERT INTO ordine(data, dataconsegnaprevista, reffarmacia, periodico) VALUES(?,?,?,1);" : "INSERT INTO ordine(data, dataconsegnaprevista, reffarmacia) VALUES(?,?,?);";
			
			PreparedStatement ps = conn().prepareStatement(cmdText);
			
			ps.setDate(1,  new java.sql.Date(dataOrdine.getTime()));
			ps.setDate(2,  new java.sql.Date(consegnaPrevista.getTime()));
			ps.setInt(3, Current.getCurrentFarmacia().getId());
			ps.execute();
			
			//ottengo id ultimo record inserito
			ps = conn().prepareStatement("SELECT last_insert_id() FROM ordine;");
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			int idOrdine = rs.getInt(1);
			OrdineCorriereController.assegnaOrdineCausalmente(idOrdine);
			
			String result = rs.getString(1);//si spera ritorni l'id di ultimo inserimento
			
			ps.close();
			
			return result;
		}
		
		catch(SQLException e) {
			return e.getMessage();
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
	
	protected static String updateVisualizzazioneOrdine(int idOrdine) {
		try {
			
			String cmdText = "UPDATE ordine SET stato_consegna_visualizzato=TRUE WHERE ido=?";
			PreparedStatement ps = conn().prepareStatement(cmdText);
			
			ps.setInt(1,  idOrdine);//refprenotazione
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
	
	protected static void updateOrdineSuddiviso(int idOrdine, ProdottoPrenotato prodottoPrenotato, int qtaDisponibile) {
		//aggiorno
		/*
		 * disponibile = qtaDisponibile;
		 * mancanti = mancanti - qtaDisponibile;
		 * consegna e cose varie azzerate, come stato ordine che torna a quello iniziale
		*/
		
		
	}
}
