package Models;

public class User {
	
	public static final int FARMACISTA = 0;
	public static final int DIP_AZIENDA = 1;
	public static final int CORRIERE = 2;
	
	private int idUtente;
	private String nomeUtente;
	private int tipoUtente;
	
	public User(int id, String usern, int tipoUtente) {
		idUtente = id;
		nomeUtente = usern;
		this.tipoUtente = tipoUtente;
	}
	
	//Getters
	public int getId() {
		return this.idUtente;
	}
	public String getNomeUtente() {
		return this.nomeUtente;
	}
	public int getTipoUtente() {
		return this.tipoUtente;
	}
}
