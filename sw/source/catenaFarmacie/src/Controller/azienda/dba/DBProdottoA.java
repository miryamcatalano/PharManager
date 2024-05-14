package Controller.azienda.dba;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Models.Prodotto;
import Models.ProdottoPrenotato;

public class DBProdottoA extends DBMSBoundary_A {//estende per ereditare i metodi getConn

	protected DBProdottoA() {
		super();
	}
	
	//metodi
	
	//SELECT
	protected static ArrayList<ProdottoPrenotato> getProdotti() {
		
		ArrayList<ProdottoPrenotato> prodotti = new ArrayList<>();
		
    	try{
    		
    		String cmdText = "SELECT prodotto.* FROM prodotto;";
    		Statement s = conn().createStatement();
    		
    		ResultSet rs = s.executeQuery(cmdText);
    		
    		while(rs.next()) {
    			
				int idp =  rs.getInt("idprodotto");				
				String ean = rs.getString("ean");
				String nome = rs.getString("nome");
				String principioAttivo = rs.getString("principioattivo");
				BigDecimal costo = rs.getBigDecimal("costo");
				boolean daBanco = rs.getBoolean("dabanco");
				
				prodotti.add(new ProdottoPrenotato(new Prodotto(idp, ean, nome, principioAttivo, daBanco, costo)));
    		}
    		
    		rs.close();
    		s.close();
    	}
    	
    	catch(Exception e) {
			System.out.println(e.getMessage());
    	}
    	
    	return prodotti;
	}
	
	protected static ArrayList<ProdottoPrenotato> prodottiOrdinePeriodico() {
		
		ArrayList<ProdottoPrenotato> prodotti = new ArrayList<>();
		
    	try{
    		
    		String cmdText = "SELECT prodotto.* FROM prodotto GROUP BY principioattivo;";
    		Statement s = conn().createStatement();
    		
    		ResultSet rs = s.executeQuery(cmdText);
    		
    		while(rs.next()) {
    			
				int idp =  rs.getInt("idprodotto");
				String ean = rs.getString("ean");
				String nome = rs.getString("nome");
				String principioAttivo = rs.getString("principioattivo");
				BigDecimal costo = rs.getBigDecimal("costo");
				boolean daBanco = rs.getBoolean("dabanco");

				prodotti.add(new ProdottoPrenotato(new Prodotto(idp, ean, nome, principioAttivo, daBanco, costo)));
    		}
    		
    		rs.close();
    		s.close();
    	}
    	
    	catch(Exception e) {
			System.out.println(e.getMessage());
    	}
    	
    	return prodotti;
	}
	
	protected static Prodotto getProdottoById(int idProdotto) {
		
		Prodotto prodotto = null;
		
		try{   		
    		String cmdText = "SELECT prodotto.* FROM prodotto WHERE idprodotto=?;";
    		
    		PreparedStatement ps = conn().prepareStatement(cmdText);  
    		ps.setInt(1, idProdotto);
    		
    		ResultSet rs = ps.executeQuery();
    		
    		if(rs.next()) {
    			
    			int idp =  rs.getInt("idprodotto");
				String ean = rs.getString("ean");
				String nome = rs.getString("nome");
				String principioAttivo = rs.getString("principioattivo");
				BigDecimal costo = rs.getBigDecimal("costo");
				boolean daBanco = rs.getBoolean("dabanco");
				int qta = rs.getInt("scorte");
				
				prodotto = new Prodotto(idp, ean, nome, principioAttivo, daBanco, costo, qta);
				
    		}
    		
    		rs.close();
    		ps.close();
    	}
    	
    	catch(Exception e) {
			System.out.println(e.getMessage());
			prodotto = new Prodotto(-1, e.getMessage(), e.getMessage(), e.getMessage(), false);
    	}
		
		return prodotto;
	}
	
	protected static Prodotto getProdottoByEAN(String EAN) {
		
		Prodotto prodotto = null;
		
		try{   		
    		String cmdText = "SELECT prodotto.* FROM prodotto WHERE ean=?;";
    		
    		PreparedStatement ps = conn().prepareStatement(cmdText);  
    		ps.setString(1, EAN);
    		
    		ResultSet rs = ps.executeQuery();
    		
    		if(rs.next()) {
    			
    			int idp =  rs.getInt("idprodotto");
				String ean = rs.getString("ean");
				String nome = rs.getString("nome");
				String principioAttivo = rs.getString("principioattivo");
				BigDecimal costo = rs.getBigDecimal("costo");
				boolean daBanco = rs.getBoolean("dabanco");
				int qta = rs.getInt("scorte");
				
				prodotto = new Prodotto(idp, ean, nome, principioAttivo, daBanco, costo, qta);
				
    		}
    		
    		rs.close();
    		ps.close();
    	}
    	
    	catch(Exception e) {
			System.out.println(e.getMessage());
			prodotto = new Prodotto(-1, e.getMessage(), e.getMessage(), e.getMessage(), false);
    	}
		
		return prodotto;
	}
	
	
	//INSERT
	protected static String insertNewProdotto(Prodotto p) {
		
		try {
			
			String cmdText = "INSERT INTO prodotto(ean, nome, principioattivo, costo, dabanco) VALUES (?,?,?,?,?);";			
			PreparedStatement ps = conn().prepareStatement(cmdText);
			
			ps.setString(1, p.getEan());
			ps.setString(2,  p.getNome());
			ps.setString(3,  p.getPrincipioAttivo());
			ps.setBigDecimal(4,  p.getCosto());
			ps.setBoolean(5, p.isDaBanco());
			
			ps.execute();
			
			//ottengo id ultimo record inserito
			ps = conn().prepareStatement("SELECT last_insert_id() FROM prodotto;");
			ResultSet rs = ps.executeQuery();
			rs.next();
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
	
	
	//DELETE
	protected static String deleteProdottoById(int idProdotto) {
		
		try {
			
			String cmdText = "DELETE FROM prodotto WHERE idprodotto=?";
			PreparedStatement ps = conn().prepareStatement(cmdText);
			
			ps.setInt(1,  idProdotto);//idprodotto
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
	
	//UPDATE
	protected static String updateScorteAzienda() {
		
		try {			
			
			String cmdText = "UPDATE prodotto SET scorte = scorte + 500 WHERE 1=1";
			
			Statement s = conn().createStatement();
			s.execute(cmdText);
    		s.close();
    					
			return "0";
		}
		
		catch(SQLException e) {
			return e.getMessage();
		}
		
		catch (Exception e) {
			return e.getMessage();
		}
	}

	protected static String updateScorteProdottoAzienda(String eanProdotto, int qta) {
		
		try {
			
			String cmdText = "UPDATE prodotto SET scorte = scorte - ? WHERE ean = ?";
			PreparedStatement ps = conn().prepareStatement(cmdText);
			
			ps.setInt(1,  qta);//refprenotazione
			ps.setString(2,  eanProdotto);//refprenotazione
			
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

