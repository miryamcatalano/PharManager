package Controller.azienda;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;

import Controller.azienda.dba.DBPrenotato;
import Models.Current;
import Models.Ordine;
import Models.ProdottoPrenotato;
import Models.Tempo;
import View.farmacia.Ordini.Prenotazioni.PrenotazioniView;
import utilities.Helper;
import utilities.Navigation;

public class PrenotazioneController extends DBPrenotato {
	
	private PrenotazioneController() {}
	
	public static void visualizzaPrenotazioniOrdine(Ordine o) {
		 Navigation.changePage(new PrenotazioniView(o.getId()));
	}
	
	public static ArrayList<ProdottoPrenotato> getPrenotazioniOf(int idOrdine, int tipoPrenotazione){
		return getPrenotazioniByOrdine(idOrdine, tipoPrenotazione);
	}
	
	public static ArrayList<ProdottoPrenotato> getPrenotazioniOrdine(int idOrdine){
		return getPrenotazioniByOrdine(idOrdine, ProdottoPrenotato.TUTTE_LE_PRENOTAZIONI);
	}
	
	public static String prenota(ArrayList<ProdottoPrenotato> prodotti, int idOrdine, boolean controllaQta) {
		
		String result = "";
		ArrayList<ProdottoPrenotato> prodottiRimandati = new ArrayList<>();
		
		for(ProdottoPrenotato prodotto : prodotti) {
			
			//verifico qta disponibile per ogni prodotto
			int qtaDisponibile = ProdottoAziendaController.getProdottoByEan(prodotto.getEan()).getQta();
			
			
			//QTA NON SUFFICIENTE
			if(controllaQta && qtaDisponibile < prodotto.getQta()) {//qta non interamente disponibile
				
				//alert fare solo in parte o aspetti?
				int esito = alertAcquistaConQtaInsufficiente(prodotto.getNome(), qtaDisponibile);
				
				if(JOptionPane.YES_OPTION == esito) {//desidera che l'ordine venga comunque completato anche attendendo
					int qtaMancante = prodotto.getQta() - qtaDisponibile;
					
					ProdottoPrenotato p = new ProdottoPrenotato(prodotto);
					p.setQta(qtaMancante);
					prodottiRimandati.add(p);
					
					prodotto.setQta(qtaDisponibile);
					result = insertPrenotazione(prodotto, idOrdine, controllaQta);
				}
				
				else if(JOptionPane.NO_OPTION == esito){//desidera che il prodotto venga venduta solo nella qta subito disponibil
					result = insertPrenotazione(prodotto,  idOrdine, controllaQta);
				}
				
				else {//ha cliccato annulla
					result = "-1";
				}
			}
			
			else {//QTA SUFFICIENTE
				result = insertPrenotazione(prodotto, idOrdine, controllaQta);
			}

			if(prodottiRimandati.size() > 0) {
				return prenota(prodottiRimandati, Tempo.NowPlusDays(7), false);
			}
			
			//se si presenta un errore mando l'avviso e interrompo
			if(!Helper.isNumber(result)) {
				return result;
			}
		}
		
		return "0";		
	}

	public static String prenota(ArrayList<ProdottoPrenotato> prodotti) {
		return prenota(prodotti, Tempo.Now(), true);
	}
	
	private static String prenota(ArrayList<ProdottoPrenotato> prodotti, Date dataOrdine, boolean controllaQta) {
		
		String result = OrdineController.creaOrdinePerFarmacia(Current.getCurrentFarmacia().getId(), dataOrdine);
		
		//se si verificano errori restituisco il messaggio
		if(!Helper.isNumber(result)) {
			return result;
		}
		
		int idOrdine = Integer.parseInt(result);		
		return prenota(prodotti, idOrdine, controllaQta);
	}	
	
	public static String consegnaPrenotazione(int idPrenotazione) {
		return consegnaPrenotazioneById(idPrenotazione);//ritorna l'id dell'ordine
	}
	
	public static String consegnaPrenotazione(ProdottoPrenotato prodottoPrenotato) {
		return consegnaPrenotazione(prodottoPrenotato.getIdPrenotazione());//ritorna l'id dell'ordine
	}
	
	public static String caricaPrenotazione(int idPrenotazione) {
		return caricaPrenotazioneById(idPrenotazione);
	}
	
	public static String caricaPrenotazione(ProdottoPrenotato prodottoPrenotato) {
		return caricaPrenotazioneById(prodottoPrenotato.getIdPrenotazione());
	}
	
	public static String updatePrenotazione(Integer idPrenotazione, Integer qta) {
		return updateQtaPrenotazione(idPrenotazione, qta);
	}
	
	public static String updatePrenotazioniOrdine(HashMap<Integer, Integer> prenotazioniDaAggiornare) {
		String result = "";
		
		for(Integer key : prenotazioniDaAggiornare.keySet()) {	
			result += PrenotazioneController.updatePrenotazione(key, prenotazioniDaAggiornare.get(key));								
		}
		
		return result;
	}
	
	private static int alertAcquistaConQtaInsufficiente(String nomeProdotto, int qtaDisp) {
		
		String[] options = {"Acquista tutto e attendi completamento", "Acquista solo quantita disponibile"};
		
		return JOptionPane.showOptionDialog(
				null,
				String.format("La quantità da te richiesta per il prodotto %s non è attualmente disponibile (%d pezzi presenti in magazzino). Vuoi acquistare e attendere la disponibilità del prodotto per completare l'ordine?", nomeProdotto, qtaDisp),
				"Scorte insufficienti",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null, //no custom icon
				options,
				options[0]
			);
	}
}
