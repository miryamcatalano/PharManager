

import java.util.ArrayList;
import java.util.Date;

import Controller.azienda.dba.DBOrdine;
import Models.Ordine;
import Models.Tempo;
import View.azienda.Ordini.OrdiniAziendaView;
import View.farmacia.Ordini.OrdiniView;
import utilities.Navigation;

public class OrdineController extends DBOrdine {
	
	public static void visualizzaOrdini() {
		Navigation.changePage(new OrdiniView());
	}
	
	public static void visualizzaOrdiniAzienda() {
		Navigation.changePage(new OrdiniAziendaView());
	}
	
	//GET - SELECT
	public static ArrayList<Ordine> getOrdiniByFarmaciaId(int idFarmacia){
		return getOrdiniByIdFarmacia(idFarmacia, false);
	}
	public static ArrayList<Ordine> getOrdiniDelGiornoByFarmaciaId(int idFarmacia){
		return getOrdiniByIdFarmacia(idFarmacia, true);
	}
	public static Ordine getUltimoOrdinePeriodicoFarmacia(int idFarmacia) {
		return getLastOrdineByFarmaciaId(idFarmacia);
	}
	
	public static ArrayList<Ordine> getOrdini(){
		return getAll();
	}
	
	public static ArrayList<Ordine> getOrdiniRifiutati(){
		return getOrdiniNonCompletati();//ottiene ordini la cui consegna è stata totalmente o parzialmente rifiutata
	}
	
	public static ArrayList<Ordine> getRifiutatiDiIeri(){
		return getOrdiniNonCompletatiDiIeri();//ottiene ordini la cui consegna è stata totalmente o parzialmente rifiutata
	}
	
	public static Ordine getOrdineById(int idOrdine){
		return getOrdineByIdOrdine(idOrdine);
	}
	
	public static boolean ordinePuoEssereCompletato(int refOrdine) {//tutte le prenotazioni sono ancora in stato consegnato = 0
		return ordinePuoCompletarsi(refOrdine);
	}
	
	//INSERT
	public static String creaOrdinePerFarmacia(int idFarmacia, Date dataOrdine) {
		return creaOrdine(idFarmacia, false, dataOrdine);
	}
	
	public static String creaOrdinePeriodico(int idFarmacia) {
		return creaOrdine(idFarmacia, true, Tempo.Now());
	}
	
	//UPDATE
	public static String aggiornaStatoOrdine(int idOrdine, int stato) {
		return updateStatoOrdine(idOrdine, stato);
	}
	
	public static String visualizzatoUltimoStatoOrdine(int idOrdine) {
		return updateVisualizzazioneOrdine(idOrdine);
	}
	
	private OrdineController() {}

}
