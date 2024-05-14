package utilities;

import java.awt.Frame;
import java.sql.SQLException;

import javax.swing.JFrame;

import Controller.azienda.dba.DBMSBoundary_A;
import Controller.farmacia.dbf.DBMSBoundary_F;
import Models.Current;
import Models.User;
import View.commons.Home.HomeView;

public class Navigation {

	public static void changePage(JFrame frameSuccessivo) {
		
		if(!isUserAuthorized(frameSuccessivo)) {//se non è autorizzato lo rimando alla login
			frameSuccessivo = new HomeView();
		}
		
		for(Frame f : Frame.getFrames()) {
			f.setVisible(false);
		}
		
		frameSuccessivo.setVisible(true);	
	}
	
	public static void changePage(JFrame oldFrame, JFrame newFrame) {		
		oldFrame.setVisible(false);
		newFrame.setVisible(true);
	}
	
	public static void esci(boolean confermaUscita) {
		
		if(confermaUscita) {
			
			try {
				DBMSBoundary_F.closeConnection();
				DBMSBoundary_A.closeConnection();
				changePage(new HomeView());
			}
			catch (SQLException sqlE) {
				GUItems.AlertError(sqlE.getMessage());
			}
			catch (Exception exception) {
				GUItems.AlertError(exception.getMessage());
			}
			
		}
	}
	
	public static boolean isUserAuthorized(Frame f) {
		
		String[] packageArray = f.getClass().getPackage().toString().split(" ")[1].split("\\.");
		String packageString = packageArray[0] + "." + packageArray[1];
		
		int tipoUtente = Current.getCurrentUser().getTipoUtente();
		
		if(packageString.equals("View.farmacia")) {
			return tipoUtente == User.FARMACISTA;
		}
		
		if(packageString.equals("View.azienda")) {
			return tipoUtente == User.DIP_AZIENDA;
		}
		
		if(packageString.equals("View.corriere")) {
			return tipoUtente == User.CORRIERE;
		}
		
		
		if(packageString.equals("View.commons")) {
			return true;
		}
		
		return true;
	}
	
	
}
