package Models;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Prodotto {
	
	private int idProdotto;
	private String ean;
	private String nome;
	private String principioAttivo;
	private boolean daBanco;
	private BigDecimal costo;
	private int qta;
	
	public Prodotto(int idProdotto, String ean, String nome, String principioAttivo, boolean daBanco) {
		this.idProdotto = idProdotto;
		this.ean = ean;
		this.nome = nome;
		this.principioAttivo = principioAttivo;
		this.daBanco = daBanco;
	}

	public Prodotto(String ean, String nome, String principioAttivo, BigDecimal costo, boolean daBanco) {
		this.ean = ean;
		this.nome = nome;
		this.principioAttivo = principioAttivo;
		this.costo = costo;
		this.daBanco = daBanco;
	}
	
	public Prodotto(int idProdotto, String ean, String nome, String principioAttivo, boolean daBanco, int qta) {
		this(idProdotto, ean, nome, principioAttivo, daBanco);
		this.qta = qta;
	}
	
	public Prodotto(String ean, String nome, String principioAttivo, BigDecimal costo, boolean daBanco, int qta) {
		this(qta, ean, nome, principioAttivo, daBanco);
		this.qta = qta;
	}
	
	public Prodotto(int idProdotto, String ean, String nome, String principioAttivo, boolean daBanco, BigDecimal costo) {
		this(idProdotto, ean, nome, principioAttivo, daBanco);
		this.costo = costo;
	}
	
	
	public Prodotto(int idProdotto, String ean, String nome, String principioAttivo, boolean daBanco, BigDecimal costo, int qta) {
		this(idProdotto, ean, nome, principioAttivo, daBanco, qta);
		this.costo = costo;
	}
	
	//Setters
	public void setQta(int qta) {
		this.qta = qta;
	}
	
	//Getters
	public int getId() {
		return this.idProdotto;
	}
	
	public String getEan() {
		return this.ean;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getPrincipioAttivo() {
		return this.principioAttivo;
	}
	
	public boolean isDaBanco() {
		return this.daBanco;
	}
	
	public BigDecimal getCosto() {
		return this.costo;
	}
	public int getQta() {
		return this.qta;
	}
	
	public static boolean contiene(ArrayList<Prodotto> prodotti, Prodotto prodotto) {
		for(Prodotto p : prodotti) {
			if(p.getEan().equals(prodotto.getEan()))
				return true;
		}		
		return false;
	}
	
	@Override
	public String toString() {
		return this.idProdotto + "-" + this.nome + "-" + this.principioAttivo+ "-" + this.ean + "-" + this.daBanco + "-" + this.costo; 
	}
}
