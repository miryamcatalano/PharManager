package Models;

import Controller.farmacia.FarmaciaController;

public class Current  {

	private static Farmacia instanceF = null;
	private static User instanceU = null;
	
	
	//Farmacia
	public static void loadFarmacia(int idUtente) {
		try {
			instanceF = FarmaciaController.getFarmaciaByUserId(idUtente);
		}
		
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	public static Farmacia getCurrentFarmacia() {
		//Crea l'oggetto solo se non esiste
		if(null == instanceF && instanceU != null) {
			instanceF = FarmaciaController.getFarmaciaByUserId(instanceU.getId());
		}
		return instanceF;
	}
	
	//Utente
	public static void loadUtente(int idUtente, String username, int tipoUtente) {
		try {
			instanceU = new User(idUtente,  username, tipoUtente);
		}
		
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	public static User getCurrentUser() {
		//Crea l'oggetto solo se non esiste
		return instanceU; //durante l'utilizzo, se utente sarà uguale a null si ritornerà automaticamente alla pagina di login
	}
}
