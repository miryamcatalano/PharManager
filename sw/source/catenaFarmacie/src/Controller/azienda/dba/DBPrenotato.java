package Controller.azienda.dba;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import Controller.azienda.ProdottoAziendaController;
import Models.Prodotto;
import Models.ProdottoPrenotato;

public class DBPrenotato extends DBMSBoundary_A {

	protected DBPrenotato() {
		super();
	}
	
	//SELECT
	private static ArrayList<ProdottoPrenotato> getPrenotazioniByOrdine(int idOrdine, String cmdText){
		//campi prenotazione: Prodotto prodottoRef, Date scadenza, int qta, Date data, BigDecimal costo;
		
		ArrayList<ProdottoPrenotato> prenotazioni = new ArrayList<>();
		
    	try{
    		
    		PreparedStatement ps = conn().prepareStatement(cmdText);  
    		ps.setInt(1, idOrdine);
    		
    		ResultSet r = ps.executeQuery();
    		
    		
    		while(r.next()) {
    			
    			int id = r.getInt("idp");
    			Prodotto prodotto = ProdottoAziendaController.getProdottoById(r.getInt("refprodotto"));
    			Date scadenza = r.getDate("scadenza");
    			int qta = r.getInt("qta");
				boolean consegnato = r.getBoolean("consegnato");
				boolean caricato = r.getBoolean("caricato");
				
				prenotazioni.add(new ProdottoPrenotato(id, prodotto, scadenza, qta, consegnato, caricato));
    		}
    		
    		r.close();
    		ps.close();
    	}
    	
    	catch(Exception e) {
			System.out.println(e.getMessage());
    	}
    	
    	return prenotazioni;
	}
	
	protected static ArrayList<ProdottoPrenotato> getPrenotazioniByOrdine(int idOrdine, int tipoPrenotazione){
		
		if(ProdottoPrenotato.NON_CONSEGNATE == tipoPrenotazione) {
			return getPrenotazioniByOrdine(idOrdine, "SELECT prenotato.* FROM prenotato WHERE refordine=? AND consegnato=FALSE;");
		}
		
		if(ProdottoPrenotato.NON_CARICATE == tipoPrenotazione) {
			return getPrenotazioniByOrdine(idOrdine, "SELECT prenotato.* FROM prenotato WHERE refordine=? AND caricato=FALSE;");
		}
		
		return getPrenotazioniByOrdine(idOrdine, "SELECT prenotato.* FROM prenotato WHERE refordine=?;");
	}
	
	//INSERT
	protected static String insertPrenotazione(ProdottoPrenotato prodotto, int refOrdine, boolean defalcaDaAzienda) {		
		try {
			
			String cmdText = "INSERT INTO prenotato(refprodotto, scadenza, qta, refordine) VALUES(?,?,?,?);";
			PreparedStatement ps = conn().prepareStatement(cmdText);
			
			ps.setInt(1,  prodotto.getId());//refprodotto
			ps.setDate(2,  new java.sql.Date(prodotto.getScadenza().getTime()));//scadenza
			
			int qta = (prodotto.getQta() > 0) ? prodotto.getQta() : 1;
			ps.setInt(3, qta);//qta
			ps.setInt(4, refOrdine);
			
			ps.execute();
			ps.close();
			
			
			if(defalcaDaAzienda) {
				ProdottoAziendaController.defalcaScorteProdottoAzienda(prodotto.getEan(), qta);
			}
			
			return "0";
		}
		
		catch(SQLException e) {
			return e.getMessage();
		}
		
		catch (Exception e) {
			return e.getMessage();
		}
	}

	//UPDATE
	protected static String consegnaPrenotazioneById(int idPrenotazione) {
		try {			
			
			String cmdText = "UPDATE prenotato SET consegnato=1 WHERE idp=?";
			PreparedStatement ps = conn().prepareStatement(cmdText);
			
			ps.setInt(1,  idPrenotazione);//refprenotazione
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
	
	protected static String caricaPrenotazioneById(int idPrenotazione) {
		try {			
			
			String cmdText = "UPDATE prenotato SET caricato=1 WHERE idp=?";
			PreparedStatement ps = conn().prepareStatement(cmdText);
			
			ps.setInt(1,  idPrenotazione);//refprenotazione
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
	
	
	protected static String updateQtaPrenotazione(int idPrenotazione, int qta) {
		try {			
			
			String cmdText = "UPDATE prenotato SET qta=? WHERE idp=?";
			PreparedStatement ps = conn().prepareStatement(cmdText);
			
			ps.setInt(1,  qta);//refprenotazione
			ps.setInt(2,  idPrenotazione);//refprenotazione
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
