package Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import Controller.azienda.PrenotazioneController;
import utilities.GUItems;
import utilities.Helper;

public class Ordine {
	
	//COSTANTI
	public static final int IN_ATTESA = 0;
	public static final int SPEDITO = 1;
	public static final int IN_TRANSITO = 2;
	public static final int IN_CONSEGNA = 3;
	public static final int CONSEGNATO = 4;
	public static final int RESPINTO = 5;
	public static final int CONSEGNATO_IN_PARTE = 6;
	
	
	private int ido;
	private Date dataConsegnaPrev;
	private Date dataOrdine;
	private int stato;
	private ArrayList<ProdottoPrenotato> prenotazioni;
	
	private int refFarmacia;
	
	//Costruttori
	public Ordine(int ido, Date dataCons, Date dataOrdine, int stato) {
		this.ido = ido;
		this.dataConsegnaPrev = dataCons;
		this.dataOrdine = dataOrdine;
		this.stato = stato;
		prenotazioni = new ArrayList<>();
	}
	
	public Ordine(int ido, Date dataCons, Date dataOrdine, int stato, ArrayList<ProdottoPrenotato> prenotazioni) {
		this(ido, dataCons, dataOrdine, stato);
		this.prenotazioni = prenotazioni;
	}
	
	public Ordine(int ido, Date dataCons, Date dataOrdine, int stato, int refFarmacia, ArrayList<ProdottoPrenotato> prenotazioni) {
		this(ido, dataCons, dataOrdine, stato, prenotazioni);
		this.refFarmacia = refFarmacia;
	}
	
	//Getters
	public int getId() {
		return this.ido;
	}
	public Date getDataConsegnaPrevista() {
		return this.dataConsegnaPrev;
	}
	public Date getDataOrdinazione() {
		return this.dataOrdine;
	}
	public int getStato() {
		return this.stato;
	}
	public String getStatoToString() {
		switch (this.stato) {
			case IN_ATTESA:
				return GUItems.corsivoOf("In attesa di spedizione");
			case SPEDITO:
				return GUItems.corsivoOf("Spedito (Roma)");
			case IN_TRANSITO:
				return GUItems.corsivoOf("In transito (Firenze)");
			case IN_CONSEGNA:
				return GUItems.corsivoOf("In consegna (Milano)");
			case CONSEGNATO:
				return GUItems.corsivoOf("Consegnato");
			case RESPINTO:
				return GUItems.corsivoOf("Respinto");
			case CONSEGNATO_IN_PARTE:
				return GUItems.corsivoOf("Consegnato in parte");
			default:
				return "N/A";
		}
	}
	public ArrayList<ProdottoPrenotato> getPrenotazioniOrdine(){
		return this.prenotazioni;
	}
	public int getRefFarmacia() {
		return this.refFarmacia;
	}
	public boolean isAncoraInTempo() {
		return Helper.daysBetweenTwoDates(Tempo.Now(), this.dataConsegnaPrev) > 2;
	}
	
	@Override
	public String toString() {
		return this.ido + "-" + Helper.dateToString(this.dataConsegnaPrev) + "-Data: "+ Helper.dateToString(this.dataOrdine) + "-Prenotazioni-Apri";
	}
	
	public String getTextPrenotazioni() {
		ArrayList<ProdottoPrenotato> prenotazioni = PrenotazioneController.getPrenotazioniOrdine(this.ido);
		
		//in una riga filtro la lista di oggetti prenotazioni, ne ottengo il nome dall'ottenimento del prodotto, li metto insieme unendoli con una virgola e prendo solo i primi 30 caratteri
		return prenotazioni.stream().map(x -> x.getNome() + " (x" + x.getQta() + ")").collect(Collectors.joining(", "));
			
	}
}