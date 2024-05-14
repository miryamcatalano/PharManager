package Models;

import java.sql.Date;

public class Farmacia {
	
	private int idFarmacia;
	private String piva;
	private String nome;
	private String indirizzo;
	private String citta;
	private String telefono;
	private int giorniOrdinePeriodico;
	private java.util.Date ultimoRifornimento;
	
	public Farmacia(int id, String piva, String nome, String indirizzo, String citta, String telefono, int giorniOrdinePeriodico) {
		this.idFarmacia = id;
		this.piva = piva;
		this.nome = nome;
		this.indirizzo = indirizzo;
		this.citta = citta;
		this.telefono = telefono;
		this.giorniOrdinePeriodico = giorniOrdinePeriodico;
	}
	
	public Farmacia(int id, String piva, String nome, String indirizzo, String citta, String telefono, int giorniOrdinePeriodico, Date ultimoRifornimento) {
		this(id, piva, nome, indirizzo, citta, telefono, giorniOrdinePeriodico);
		this.ultimoRifornimento = ultimoRifornimento;
	}
	
	//Getters
	public int getId() {
		return this.idFarmacia;
	}	
	public String getPiva() {
		return this.piva;
	}	
	public String getNome() {
		return this.nome;
	}	
	public String getIndirizzo() {
		return this.indirizzo;
	}	
	public String getCitta() {
		return this.citta;
	}
	public String getTelefono() {
		return this.telefono;
	}
	public int getGiornoOrdinePeriodico() {
		return this.giorniOrdinePeriodico;
	}
	public java.util.Date getUltimoRifornimento() {
		return this.ultimoRifornimento;
	}
	
	//override
	@Override
	public String toString() {
		return this.idFarmacia + " | " + this.piva + " | " + this.nome + " | " + this.indirizzo + " | " + this.citta; 
	}
}
