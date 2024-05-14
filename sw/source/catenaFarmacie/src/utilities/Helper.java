package utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public final class Helper {
	
	private static final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	public static boolean isNumber(String data) {
		if(null == data) {
			return false;
		}
		//con regular expression verifico che siano presenti solo numeri
		return pattern.matcher(data).matches();
	}
	
	public static Date getRandomDate(int add2Today, int maxGiorniDopo,  Date startDate) {
		
		Calendar dateCalendar = Calendar.getInstance();
		dateCalendar.setTime(startDate);
		
		//Parte da oggi + numero di giorni
		dateCalendar.add(Calendar.DAY_OF_MONTH, add2Today);
		long start = dateCalendar.getTime().getTime();
		
		//Aggiungo giorni
		dateCalendar.add(Calendar.DAY_OF_MONTH, maxGiorniDopo); 
		long end = dateCalendar.getTime().getTime();
		
		long ms=(long)(((end-start)*Math.random())+start);	//The qualified number of 13-bit milliseconds is obtained.
		return new Date(ms);
		
	}
	
	public static long daysBetweenTwoDates(Date date1, Date date2) {
		
		Calendar dateCalendar = Calendar.getInstance();
		
		dateCalendar.setTime(date1);		
		long todayTime = dateCalendar.getTime().getTime();
		
		//Fino ad anno prossimo
		dateCalendar.setTime(date2); 
		long scadenzaTime = dateCalendar.getTime().getTime();
		
		//2 mesi uguale a 1000ms * 60s * 60m * 24h * 60g
		
		return TimeUnit.MILLISECONDS.toDays(scadenzaTime - todayTime);
	}
	
	public static String dateToString(Date d) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	    return formatter.format(d);
	}
}
