package View.commons.Login;

import javax.swing.*;

import Controller.azienda.AutenticazioneDipendenteController;
import Controller.corriere.AutenticazioneCorriereController;
import Controller.farmacia.AutenticazioneFarmacistaController;
import Models.User;
import utilities.GUItems;
import utilities.GUItems.AlertType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginView extends JFrame {

	private JButton loginButton;
	private CredenzialiPanel cPanel;
	
	private String username;
	private String password;
	
	private Integer _loginAs = 0;
	
	public LoginView(int loginAs) {
		
		super("Login");//titolo finestra (frame)
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Titolo
		c.gridy = 0;//riga
		c.gridx = 0;//colonna
		c.weighty = 0.01;//specifica il "peso" del componente. Se non specificato per nessuno verranno posizionati tutti nella parte centrale
		c.weightx = 0.01;
		
		JLabel titoloLabel = GUItems.getDefaultTitleLabel(GUItems.htmlOf("<p style='font-size:100%;text-align:center;'>Login</p><p style='font-size:70%;'>Inserisci le tue credenziali per accedere</p>"));
		titoloLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titoloLabel.setAlignmentX(JLabel.CENTER);
		add(titoloLabel, c);
		
		//Campi
		c.gridx = 0;
		c.gridy = 1;
		
		c.weightx = 0.01;
		c.weighty = 0.01;
		
		cPanel = new CredenzialiPanel();
		add(cPanel, c);
		
		//Button
		c.gridx = 0;
		c.gridy = 2;
		
		c.weightx = 0.05;
		c.weighty = 0.05;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 552, 0, 552);
		
		this._loginAs = loginAs;
		
		loginButton = GUItems.getDefaultBtn("Accedi", 21, true);
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				inserisciCredenziali();
				accediCome(_loginAs);
			}
		});
		
		add(loginButton, c);

		
		//Frame
		setSize(1280, 700);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);//posiziona finestra al centro dello schermo
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);//per ultimo
	}
	
	private void inserisciCredenziali() {
		this.username = cPanel.getUsernameValue();
		this.password = cPanel.getPasswordValue();
	}
	
	private void accediCome(Integer typeUser) {
		
		try {
			
			if(typeUser != null) {
			switch (typeUser) {
			
				case User.FARMACISTA:					
					if(AutenticazioneFarmacistaController.validateFarmacista(username, password)) {
						AutenticazioneFarmacistaController.loginFarmacista();
					}
					
					else {
						alertAutenticazioneNonRiuscita();
					}
					return;
					
				case User.DIP_AZIENDA:
					if(AutenticazioneDipendenteController.validateDipendente(username, password)) {
						AutenticazioneDipendenteController.loginDipendente();						
					}
					else {
						alertAutenticazioneNonRiuscita();
					}
					return;				
					
				case User.CORRIERE:
					if(AutenticazioneCorriereController.validateCorriere(username, password)) {
						AutenticazioneCorriereController.loginCorriere();
					}
					else {
						alertAutenticazioneNonRiuscita();
					}
					return;
					
				default:
					alertAutenticazioneNonRiuscita();
			}
			}
		}
		
		catch (Exception e) {
			alertAutenticazioneNonRiuscita(e.getCause() + " " + e.getMessage());
		}
	}
	
	private void alertAutenticazioneNonRiuscita(String msg) {
		GUItems.Alert(GUItems.htmlOf("Autenticazione non riuscita.<br/>" + msg), "Accesso non consentito", AlertType.ERROR);
	}

	private void alertAutenticazioneNonRiuscita() {
		alertAutenticazioneNonRiuscita("");
	}
	
	
}
