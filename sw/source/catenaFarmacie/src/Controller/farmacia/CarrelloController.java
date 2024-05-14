package Controller.farmacia;

import java.util.ArrayList;

import Models.Prodotto;
import Models.ProdottoPrenotato;
import View.farmacia.Carrello.CarrelloView;
import utilities.Navigation;

public class CarrelloController {
	
	private static ArrayList<ProdottoPrenotato> prodottiCarrello = new ArrayList<>();
	
	private CarrelloController() {}
	
	
	public static void visualizzaCarrello() {
		Navigation.changePage(new CarrelloView());
	}
	
	public static void aggiungiProdottoAlCarrello(ProdottoPrenotato prodottoPrenotato) {
		prodottoPrenotato.setQta(1);
		prodottiCarrello.add(prodottoPrenotato);
	}
	
	public static void removeProdottoCarrello(String ean) {
		prodottiCarrello.removeIf(p -> p.getEan().equals(ean));
	}
	
	public static void reset() {
		prodottiCarrello = new ArrayList<>();
	}
	
	public static boolean containsProdotto(Prodotto prodotto) {
		int id = prodotto.getId();
		
		for(Prodotto p : prodottiCarrello) {
			if(p.getId() == id)
				return true;
		}		
		return false;
	}
	
	public static void updateQta(String ean, int qta) {
		for(ProdottoPrenotato p : prodottiCarrello) {
			if(p.getEan().equals(ean)) {
				p.setQta(qta);
			}
		}
	}
	
	public static ArrayList<ProdottoPrenotato> getProdottiCarrello(){
		return prodottiCarrello;				
	}
	
	public static String carrelloToString() {
		
		String carrelloString = "";
		
		for(ProdottoPrenotato  p : prodottiCarrello) {
			carrelloString+=p.toString() + "-" + p.getQta() + "\n";
		}
		
		return carrelloString + "\n\n\n";
	}
	
}
