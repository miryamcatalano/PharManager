package Controller.farmacia.dbf;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Models.Current;
import Models.Magazzino;
import Models.Prodotto;
import Models.ProdottoPrenotato;

public class DBMagazzino extends DBMSBoundary_F {
 	/*
	 * attributi classe farmacia
	 * int idFarmacia, String piva, String nome, String indirizzo, String citta
	 * HashMap<Integer, prodottoMagazzino> magazzino, utente currentUser
	 */
	
	protected DBMagazzino() {
		super();
	}
	
	//SELECT
	protected static Magazzino getMagazzinoByIdFarmacia(int idFarmacia) {
    	
		ArrayList<ProdottoPrenotato> prodotti = new ArrayList<>();
		
    	try{
    		
    		String cmdText = "SELECT prodotto.*, magazzino.qta, magazzino.idm, magazzino.scadenza, magazzino.costo FROM prodotto, magazzino WHERE magazzino.reffarmacia=? AND prodotto.idprodotto = magazzino.refprodotto;";
    		PreparedStatement ps = conn().prepareStatement(cmdText);    		
    		ps.setInt(1, Current.getCurrentFarmacia().getId());
    		
    		ResultSet rs = ps.executeQuery();
    		
    		while(rs.next()) {
    			
				int idp =  rs.getInt("idprodotto");				
				int idm = rs.getInt("idm");
				
				String ean = rs.getString("ean");
				String nome = rs.getString("nome");
				String principioAttivo = rs.getString("principioattivo");
				BigDecimal costo = rs.getBigDecimal("costo");
				boolean daBanco = rs.getBoolean("dabanco");
				Date scadenza = rs.getDate("scadenza");
				int qta = rs.getInt("qta");
				
				Prodotto prodotto = new Prodotto(idp, ean, nome, principioAttivo, daBanco, costo);
				prodotti.add(new ProdottoPrenotato(idm, prodotto, scadenza, qta));
    		}
    		
    		rs.close();
    		ps.close();
    	}
    	
    	catch(Exception e) {
			System.out.println(e.getMessage());
    	}
    	
    	return new Magazzino(prodotti);
    }
	
	//INSERT
	protected static String insertInMagazzino(Prodotto p, java.util.Date scadenza, int qta, BigDecimal costo) {
		try {
			
			String cmdText = "INSERT INTO magazzino(reffarmacia, refprodotto, scadenza, qta, costo) VALUES(?,?,?,?,?);";
			
			PreparedStatement ps = conn().prepareStatement(cmdText);
			
			ps.setInt(1, Current.getCurrentFarmacia().getId());//reffarmacia
			ps.setInt(2, p.getId());//refprodotto
			ps.setDate(3, new java.sql.Date(scadenza.getTime()));//scadenza
			ps.setInt(4,  qta);//qta
			ps.setBigDecimal(5,  costo);//costo
			
			ps.execute();			
			ps.close();
			
			return "0";//0 -> no errori
		}
		
		catch(SQLException e) {
			return e.getMessage();
		}
		
		catch (Exception e) {
			return e.getMessage();
		}
	}
}
