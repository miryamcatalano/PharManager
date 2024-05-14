package Models;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import Controller.farmacia.MagazzinoController;
import Controller.tempo.TempoController;
import utilities.GUItems;
import utilities.Helper;
import utilities.GUItems.AlertType;

public class Tempo {

	private Tempo() {}
	
	public static void eseguiVerifiche() {
		
		alertScadenzeFarmacia();
		
		if(TempoController.sonoPassateLe20(calendarNow())) {
			TempoController.alertAggiornamentoMagazzino();
		}
		
		if(TempoController.devePartireOrdineAutomatico())
			TempoController.eseguiOrdineAutomatico();
	}
	
	//Operazioni automatiche
	private static void alertScadenzeFarmacia() {
		//Avvisi: prodotto in scadenza, prodotti firmati ma non caricati entro le 20
		Magazzino m = MagazzinoController.getMagazzinoFarmaciaCorrente();
		
		boolean ciSonoProdottiScaduti = false;
		
		String prodottiScaduti = "";
		for(ProdottoPrenotato prodotto : m.getProdottiMagazzino()) {
			if(Helper.daysBetweenTwoDates(new Date(), prodotto.getScadenza()) < 30) {
				ciSonoProdottiScaduti = true;
				prodottiScaduti += "<br/>-" + prodotto.getNome() + "(scade il " + Helper.dateToString(prodotto.getScadenza()) + ")";
			}
		}
		
		if(ciSonoProdottiScaduti) {
			GUItems.Alert("<html>I seguenti prodotti stanno per scadere!<br />" + prodottiScaduti + "</html>", "Prodotti in scadenza", AlertType.WARNING);
		}
	}
	
	
	//Informazioni e verifiche data
	public static Date Now() {
		return dateFromFile("data.cfg");
	}
	
	public static Date NowPlusDays(int days) {
		Calendar calendarNow = calendarNow();
		calendarNow.add(Calendar.DATE, days);
		
		return calendarNow.getTime();
	}
	
	public static Date dateFromFile(String fileName) {
		String fileContent = "";
		
		try {  
			//constructor of file class having file as argument  
			File file=new File("./src/data/" + fileName);   
			FileInputStream fis=new FileInputStream(file);     //opens a connection to an actual file			
			
			int r=0;			
			while((r=fis.read())!=-1) {  
				fileContent += (char)r;      //prints the content of the file  
			}			
			fis.close();
			 
			// Input Date String Format
			SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			return inputDateFormat.parse(fileContent);	             
		}  
		
		catch(Exception e)  
		{ 
			e.printStackTrace();
			System.out.println("errore data");
			return new Date();
		}
	}
	
	
	public static Calendar calendarNow() {
		Calendar now = Calendar.getInstance();
		now.setTime(Now());
		
		return now;
	}		
	public static boolean scorteSonoDaAggiornare() {
		return TempoController.scorteSonoDaAggiornare(Current.getCurrentFarmacia().getId());
	}
	
	public static Date getYesterdayDate() {
		Calendar c = calendarNow();
		c.add(Calendar.DATE, -1);
		
		return c.getTime();		
	}
	
}
