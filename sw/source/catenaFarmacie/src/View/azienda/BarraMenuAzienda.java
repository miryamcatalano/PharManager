package View.azienda;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import Controller.azienda.OrdineController;
import View.azienda.Home.HomeViewAzienda;
import View.azienda.Ordini.OrdiniAziendaView;
import View.azienda.Prodotti.AggiungiProdottoView;
import View.azienda.Prodotti.ProdottiView;
import View.commons.Home.HomeView;
import utilities.GUItems;
import utilities.Navigation;
import utilities.GUItems.AlertType;

public class BarraMenuAzienda {

	private static BarraMenuAzienda instance = null;
	
	private JMenuBar menuBar = null;	
	
	private static final int HOME_AZ = 0;
	private static final int ORDINI_AZ = 1;
	private static final int AGGIUNGI_PRODOTTO = 2;
	private static final int VISUALIZZA_PRODOTTI_AZ = 3;	
	
	private BarraMenuAzienda() {
		
		try {
			
			menuBar = new JMenuBar();
			
			JMenu menuAzienda = GUItems.getDefaultJMenu("Pharmanager");
			JMenu menuProdotti = GUItems.getDefaultJMenu("Prodotti");
			JMenu menuAccount = GUItems.getDefaultJMenu("Account");
			
			menuBar.add(menuAzienda);
			menuBar.add(menuProdotti);
			menuBar.add(menuAccount);
			
			
			//--- MENU PHARMANAGER ---			
			//Home
			JMenuItem menuItemHome = GUItems.getDefaultJMenuItem("Home", "./src/View/img/farmacia.png");
			menuItemHome.addActionListener(allaHomeAzienda());
			menuAzienda.add(menuItemHome);//aggiungo elemento menu a menu farmacia
			
			//Visualizza Ordini
			JMenuItem menuItemPrenotazioni = GUItems.getDefaultJMenuItem("Visualizza ordini", "./src/View/img/ordine.png");
			menuItemPrenotazioni.addActionListener(visualizzaOrdiniAzienda());			
			menuAzienda.add(menuItemPrenotazioni);
			
			
			
			//--- MENU PRODOTTI ---
			//Visualizza prodotti
			JMenuItem menuItemVisProdotti = GUItems.getDefaultJMenuItem("Visualizza prodotti", "./src/View/img/farmaco.png");
			menuItemVisProdotti.addActionListener(visualizzaProdottiInVendita());
			menuProdotti.add(menuItemVisProdotti);
			
			//Aggiungi prodotto
			JMenuItem menuItemAddProdotto = GUItems.getDefaultJMenuItem("Aggiungi prodotto", "./src/View/img/add.png");
			menuItemAddProdotto.addActionListener(visualizzaPaginaAggiungiProdotto());
			menuProdotti.add(menuItemAddProdotto);
			
			
			//--- MENU ACCOUNT
			JMenuItem menuItemEsci = GUItems.getDefaultJMenuItem("Esci","./src/View/img/logout.png");
			menuItemEsci.addActionListener(esci());
			menuAccount.add(menuItemEsci);
			
			
			menuBar.setBackground(Color.decode("#C3DBAF"));
		}
		
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
	}
	
	
	//Getters
	public static JMenuBar getMenuBar() {
		if(null == instance) {
			instance = new BarraMenuAzienda();
		}
		return instance.menuBar;
	}
	
	private ActionListener allaHomeAzienda() {
		return new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				Navigation.changePage(getNextFrame(HOME_AZ));
			}
		};
	}
	private ActionListener visualizzaOrdiniAzienda() {
		return new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				OrdineController.visualizzaOrdiniAzienda();
			}
		};
	}	
	private ActionListener visualizzaProdottiInVendita() {
		return new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				Navigation.changePage(getNextFrame(VISUALIZZA_PRODOTTI_AZ));
			}
		};
	}
	private ActionListener visualizzaPaginaAggiungiProdotto() {
		return new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				Navigation.changePage(getNextFrame(AGGIUNGI_PRODOTTO));
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
	
	
	// FRAME
	private static JFrame getNextFrame(int next) {		
		switch (next) {
		
		//Azienda
			case HOME_AZ:
				return HomeViewAzienda.getInstance();
			case ORDINI_AZ:
				return new OrdiniAziendaView();
			case VISUALIZZA_PRODOTTI_AZ:
				return new ProdottiView();
			case AGGIUNGI_PRODOTTO:
				return new AggiungiProdottoView();
				
			default:
				return new HomeView();
		}
	}
}