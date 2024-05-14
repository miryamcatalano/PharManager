package View.farmacia;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import Controller.azienda.OrdineController;
import Controller.azienda.ProdottoAziendaController;
import Controller.farmacia.CarrelloController;
import Controller.farmacia.FarmaciaController;
import Controller.farmacia.MagazzinoController;
import Models.Current;
import View.farmacia.Home.HomeViewFarmacia;
import utilities.GUItems;
import utilities.GUItems.AlertType;
import utilities.Navigation;

public class BarraMenuFarmacia {

	private static BarraMenuFarmacia instance = null;
	private JMenuBar menuBar = null;
	
	private BarraMenuFarmacia() {
		
		try {
			
			menuBar = new JMenuBar();
			
			JMenu menuFarmacia = GUItems.getDefaultJMenu(Current.getCurrentFarmacia().getNome());
			JMenu menuOrdini = GUItems.getDefaultJMenu("Ordini");
			JMenu menuAccount = GUItems.getDefaultJMenu("Account");
			
			menuBar.add(menuFarmacia);
			menuBar.add(menuOrdini);
			menuBar.add(menuAccount);
			
			
			//--- MENU FARMACIA ---
			
			//Tasto Home
			JMenuItem menuItemHome = GUItems.getDefaultJMenuItem("Home", "./src/View/img/farmacia.png");
			menuItemHome.addActionListener(allaHome());
			menuFarmacia.add(menuItemHome);//aggiungo elemento menu a menu farmacia
			
			//Tasto Magazzino
			JMenuItem menuItemMagazzino = GUItems.getDefaultJMenuItem("Magazzino", "./src/View/img/mag.png");
			menuItemMagazzino.addActionListener(visualizzaMagazzino());			
			menuFarmacia.add(menuItemMagazzino);
			
			
			
			//--- MENU PRENOTAZIONI ---
			
			//Tasto visualizza ordini
			JMenuItem menuItemVisPrenotazioni = GUItems.getDefaultJMenuItem("Visualizza ordini", "./src/View/img/ordine.png");
			menuItemVisPrenotazioni.addActionListener(visualizzaOrdini());
			menuOrdini.add(menuItemVisPrenotazioni);
			menuOrdini.addSeparator();//aggiunge riga di separazione
			
			//Tasto visualizza prodotti
			JMenuItem menuItemVisProdotti = GUItems.getDefaultJMenuItem("Visualizza lista farmaci", "./src/View/img/farmaco.png");
			menuItemVisProdotti.addActionListener(visualizzaProdotti());
			menuOrdini.add(menuItemVisProdotti);
			
			//Tasto carrello
			JMenuItem menuItemCarrello = GUItems.getDefaultJMenuItem("Carrello", "./src/View/img/carrello.png");
			menuItemCarrello.addActionListener(visualizzaCarrello());
			menuOrdini.add(menuItemCarrello);
			
			
			//--- MENU ACCOUNT ---
			
			//Tasto info
			JMenuItem menuItemInfo = GUItems.getDefaultJMenuItem("Info","./src/View/img/info.png");
			menuItemInfo.addActionListener(cliccaInfo());
			
			//Taso esci
			JMenuItem menuItemEsci = GUItems.getDefaultJMenuItem("Esci","./src/View/img/logout.png");
			menuItemEsci.addActionListener(esci());
			
			menuAccount.add(menuItemInfo);
			menuAccount.addSeparator();//aggiunge riga di separazione
			menuAccount.add(menuItemEsci);
			
			
			menuBar.setBackground(Color.CYAN);
		}
		
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	//Getters
	public static JMenuBar getMenuBar() {
		if(null == instance) {
			instance = new BarraMenuFarmacia();
		}
		return instance.menuBar;
	}
	
	private ActionListener visualizzaCarrello() {
		return new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				CarrelloController.visualizzaCarrello();
			}
		};
	}	
	private ActionListener cliccaInfo() {
		return new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				FarmaciaController.visualizzaInfo();
			}
		};
	}
	private ActionListener visualizzaMagazzino() {
		return new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				MagazzinoController.paginaMagazzino();
			}
		};
	}
	private ActionListener visualizzaOrdini() {
		return new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				OrdineController.visualizzaOrdini();
			}
		};
	}
	private ActionListener visualizzaProdotti() {
		return new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				ProdottoAziendaController.visualizzaProdotti();
			}
		};
	}
	private ActionListener allaHome() {
		return new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				Navigation.changePage(HomeViewFarmacia.getInstance());
			}
		};
	}
	private ActionListener esci() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int alertConfermaUscita = GUItems.Alert("Sei sicuro di voler uscire?", "Esci", AlertType.CONFIRM);
				Navigation.esci(JOptionPane.OK_OPTION == alertConfermaUscita);
			}
		};
	}
}