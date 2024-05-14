package Controller.tempo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import Controller.azienda.OrdineController;
import Controller.azienda.PrenotazioneController;
import Controller.azienda.ProdottoAziendaController;
import Controller.farmacia.FarmaciaController;
import Models.Current;
import Models.Farmacia;
import Models.Ordine;
import Models.ProdottoPrenotato;
import Models.Tempo;
import View.farmacia.Ordini.OrdiniView;
import utilities.GUItems;
import utilities.Helper;
import utilities.Navigation;
import utilities.GUItems.AlertType;

public class TempoController {
	
	public static boolean sonoPassateLe20(Calendar calendar) {
		return calendar.get(Calendar.HOUR_OF_DAY) >= 20 && calendar.get(Calendar.MINUTE) >= 0;
	}
	
	public static void alertAggiornamentoMagazzino() {
		
		ArrayList<Ordine> ordini = OrdineController.getOrdiniDelGiornoByFarmaciaId(Current.getCurrentFarmacia().getId());
		ArrayList<ProdottoPrenotato> prenotazioni;
		
		String avviso = "Attenzione<br/>Ricorda di caricare i farmaci";
		
		if(ordini.size() > 0) {
			for(Ordine o : ordini) {
			prenotazioni = PrenotazioneController.getPrenotazioniOf(o.getId(), ProdottoPrenotato.NON_CARICATE);
			
				
				String prodottiPrenotazione = "";
				
				for(ProdottoPrenotato p : prenotazioni) {
					prodottiPrenotazione += "<br/>- " + p.getNome();
				}
				
				int risposta = GUItems.Alert(String.format("<html>%s<br/>I prodotti delle seguenti prenotazioni, appartenenti ad ordine con <b>data di consegna prevista per oggi</b> non sono ancora stati caricati:<br/>%s. <br/>Vuoi andare alla pagina degli ordini?",avviso, prodottiPrenotazione), "Prodotti non caricati", AlertType.CONFIRM);
				if(JOptionPane.OK_OPTION == risposta) {
					Navigation.changePage(new OrdiniView());
				}	
			}
		}
		
		else {
			GUItems.Alert(String.format("<html>%s</html>", avviso), "Prodotti non caricati", AlertType.WARNING);
		}
	}
	
	public static boolean devePartireOrdineAutomatico(Farmacia farmacia) {
		
		Ordine ultimoOrdinePeriodico = OrdineController.getUltimoOrdinePeriodicoFarmacia(farmacia.getId());//ottengo data ultimo orgine
		long giorniPassatiDaUltimoOrdine = Helper.daysBetweenTwoDates(ultimoOrdinePeriodico.getDataOrdinazione(), Tempo.Now());
		
		return ultimoOrdinePeriodico != null && giorniPassatiDaUltimoOrdine >= farmacia.getGiornoOrdinePeriodico();
	}	
	public static boolean devePartireOrdineAutomatico() {
		return devePartireOrdineAutomatico(Current.getCurrentFarmacia());
	}
	
	public static void eseguiOrdineAutomatico() {
		
		String result = OrdineController.creaOrdinePeriodico(Current.getCurrentFarmacia().getId());
		
		if(!Helper.isNumber(result)) {
			GUItems.AlertError("Si è verificato un errore durante l'elaborazione dell'ordine periodico");
		}
		
		else {
			ArrayList<ProdottoPrenotato> prodottiOrdinePeriodico = ProdottoAziendaController.getProdottiOrdinePeriodico();
			int idOrdine = Integer.parseInt(result);
			
			result = PrenotazioneController.prenota(prodottiOrdinePeriodico, idOrdine, true);
			
			if(!Helper.isNumber(result)) {
				alertErroreOrdinePeriodico(result);
			}
			
			else {
				
				result = FarmaciaController.aggiornaDataUltimoRifornimento(Current.getCurrentFarmacia().getId());
				
				if(!Helper.isNumber(result)) {
					alertErroreOrdinePeriodico(result);
				}
				else {
					alertOrdinePeriodicoEseguito();
				}
			}
		}
	}
	
	private static void alertErroreOrdinePeriodico(String msg) {
		GUItems.AlertError(GUItems.htmlOf("Si è verificato un errore durante l'elaborazione dell'ordine periodico<br/>(" + msg + ")"));
	}
	private static void alertOrdinePeriodicoEseguito() {
		GUItems.Alert("È stato elaborato l'ordine periodico dei prodotti da banco", "Ordine periodico eseguito", AlertType.INFO);
	}
	
	public static boolean scorteSonoDaAggiornare(int idFarmacia) {
		Date ultimoRifornimento = Current.getCurrentFarmacia().getUltimoRifornimento();
		long giorniPassatiDaUltimoRifornimento = Helper.daysBetweenTwoDates(ultimoRifornimento, Tempo.Now());		
		return giorniPassatiDaUltimoRifornimento > 7;
	}
}
