package Models;

import java.util.ArrayList;
import java.util.Date;
import utilities.Helper;

public class ProdottoPrenotato extends Prodotto {
	
	public final static int TUTTE_LE_PRENOTAZIONI = 0;
	public final static int NON_CONSEGNATE = 1;
	public final static int NON_CARICATE = 2;
	
	private int idPrenotazione;
	private Date scadenza;
	private boolean consegnato;
	private boolean caricato;
	
	public ProdottoPrenotato(int idPrenotazione, Prodotto prodotto, Date scadenza, int qta, boolean consegnato, boolean caricato) {
		super(prodotto.getId(), prodotto.getEan(), prodotto.getNome(), prodotto.getPrincipioAttivo(), prodotto.isDaBanco(), prodotto.getCosto(), qta);
		
		this.idPrenotazione = idPrenotazione;
		this.scadenza = scadenza;
		this.consegnato = consegnato;
		this.caricato = caricato;		
	}
	
	public ProdottoPrenotato(Prodotto prodotto) {
		super(prodotto.getId(), prodotto.getEan(), prodotto.getNome(), prodotto.getPrincipioAttivo(), prodotto.isDaBanco(), prodotto.getCosto());
		this.scadenza = Helper.getRandomDate(1, 115, Tempo.Now());
	}
	
	public ProdottoPrenotato(int idPrenotazione, Prodotto prodotto, Date scadenza, int qta) {
		super(prodotto.getId(), prodotto.getEan(), prodotto.getNome(), prodotto.getPrincipioAttivo(), prodotto.isDaBanco(), prodotto.getCosto(), qta);
		this.idPrenotazione = idPrenotazione;
		this.scadenza = scadenza;		
	}
	
	//Getters
	public int getIdPrenotazione() {
		return this.idPrenotazione;
	}	
	public Date getScadenza() {
		return this.scadenza;
	}
	public boolean isConsegnato() {
		return this.consegnato;
	}
	public boolean isCaricato() {
		return this.caricato;
	}

	public boolean isInScadenza() {				
		return Helper.daysBetweenTwoDates(Tempo.Now(), this.scadenza) < 60;		
	}
	
	public static boolean contiene(ArrayList<ProdottoPrenotato> prodotti, ProdottoPrenotato prodotto) {
		for(Prodotto p : prodotti) {
			if(p.getEan().equals(prodotto.getEan()))
				return true;
		}		
		return false;
	}
	
	
	@Override
	public String toString() {
		return getNome() + "-" + Helper.dateToString(this.scadenza);
	}
}