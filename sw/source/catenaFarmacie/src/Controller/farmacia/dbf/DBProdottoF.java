package Controller.farmacia.dbf;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Models.Prodotto;

public class DBProdottoF extends DBMSBoundary_F {

	protected DBProdottoF() {
		super();
	}
	
	//SELECT
	protected static ArrayList<Prodotto> getAll() {
		
		ArrayList<Prodotto> prodotti = new ArrayList<>();
		
    	try{
    		
    		String cmdText = "SELECT prodotto.* FROM prodotto;";
    		Statement s = conn().createStatement();
    		
    		ResultSet rs = s.executeQuery(cmdText);
    		
    		while(rs.next()) {
    			
				int idp =  rs.getInt("idprodotto");
				String ean = rs.getString("ean");
				String nome = rs.getString("nome");
				String principioAttivo = rs.getString("principioattivo");
				boolean daBanco = rs.getBoolean("dabanco");

				prodotti.add(new Prodotto(idp, ean, nome, principioAttivo, daBanco));
    		}
    		
    		rs.close();
    		s.close();
    	}
    	
    	catch(Exception e) {
			System.out.println(e.getMessage());
    	}
    	
    	return prodotti;
	}
	
	protected static Prodotto getById(int idProdotto) {
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
				boolean daBanco = rs.getBoolean("dabanco");
				
				prodotto = new Prodotto(idp, ean, nome, principioAttivo, daBanco);
    		}
    		
    		rs.close();
    		ps.close();
    	}
    	
    	catch(Exception e) {
			System.out.println(e.getMessage());
			prodotto = new Prodotto(-1, e.getMessage(), e.getMessage(), e.getMessage(), false, 0);
    	}
		
		return prodotto;
	}
	
	protected static Prodotto getByEan(String ean) {
		Prodotto prodotto = null;
		
		try{   		
    		String cmdText = "SELECT prodotto.* FROM prodotto WHERE ean=?;";
    		
    		PreparedStatement ps = conn().prepareStatement(cmdText);  
    		ps.setString(1, ean);
    		
    		ResultSet rs = ps.executeQuery();
    		
    		if(rs.next()) {
    			
    			int idp =  rs.getInt("idprodotto");
				String nome = rs.getString("nome");
				String principioAttivo = rs.getString("principioattivo");
				boolean daBanco = rs.getBoolean("dabanco");
				
				prodotto = new Prodotto(idp, ean, nome, principioAttivo, daBanco);
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
	protected static String insertProdotto(Prodotto p) {		
		try {
			
			String cmdText = "INSERT INTO prodotto(ean, nome, principioattivo, dabanco) VALUES(?,?,?,?);";
			
			PreparedStatement ps = conn().prepareStatement(cmdText);
			
			ps.setString(1, p.getEan());
			ps.setString(2, p.getNome());
			ps.setString(3, p.getPrincipioAttivo());
			ps.setBoolean(4,  p.isDaBanco());
			
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
}
