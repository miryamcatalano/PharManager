package Controller.corriere;

import java.util.ArrayList;

import Controller.corriere.dbc.DBOrdineC;
import Models.Ordine;

public class OrdineCorriereController extends DBOrdineC {

	//SELECT - GET
	public static ArrayList<Ordine> getNuoviOrdini(){
		return getOrdiniLiberi();
	}
	
	public static ArrayList<Ordine> getOrdiniInCarico(int idUtente){
		return getOrdiniByIdCorriere(idUtente);
	}
	
	//INSERT	
	public static String assegnaOrdineCausalmente(int idOrdine) {
		return insertConsegna(idOrdine);
	}
	
	//UPDATE
	public static String aggiornaOrdine(Ordine ordine) {
		int prossimoStato = ordine.getStato() + 1;
		return updateStatoOrdine(ordine.getId(), prossimoStato);
	}
}
