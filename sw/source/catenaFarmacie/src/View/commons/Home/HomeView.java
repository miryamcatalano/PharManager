package View.commons.Home;

import java.awt.Dimension;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Models.User;
import View.commons.Login.LoginView;
import utilities.GUItems;
import utilities.Navigation;

public class HomeView extends JFrame {

	
	private JButton aziendaButton;
	private JButton farmaciaButton;
	private JButton corriereButton;

	private JLabel labelTitolo;
	private JLabel labelDescrizione;
	
	public HomeView() {
		
		super("Home");//titolo finestra (frame)
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
		
		//Button corriere
		corriereButton = GetDefaultBtn("Corriere", 39, false);
		
		corriereButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Navigation.changePage(HomeView.this, new LoginView(User.CORRIERE));
			}
		});
				
		//Button azienda
		aziendaButton = GetDefaultBtn("Azienda", 39, false);
		aziendaButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Navigation.changePage(HomeView.this, new LoginView(User.DIP_AZIENDA));
			}
		});
        
		//Button farmacia
		farmaciaButton = GetDefaultBtn("Farmacia", 39, false);
		
		farmaciaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Navigation.changePage(HomeView.this, new LoginView(User.FARMACISTA));
			}
		});
		
		labelTitolo = GUItems.getDefaultTitleLabel("<html><h1 style='font-size:120%; font-style:italic;'>PHARMANAGER</h1></html>");
		labelTitolo.setHorizontalAlignment(JLabel.CENTER);
		
        labelDescrizione = new JLabel("Accedi come");
		labelDescrizione.setFont(GUItems.getDefaultFont(30));
		labelDescrizione.setHorizontalAlignment(JLabel.CENTER);
		
		
		
		//Riga 0 - Titolo
        c.weighty = 0.16;//specifica il "peso" del componente. Se non specificato per nessuno verranno posizionati tutti nella parte centrale
        c.weightx = 0.01;
        
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 0;
		add(labelTitolo, c);
        
		
		//Riga 1 - Accedi come
		c.weighty = 0.1;//specifica il "peso" del componente. Se non specificato per nessuno verranno posizionati tutti nella parte centrale
        c.weightx = 0.01;
		c.anchor = GridBagConstraints.SOUTH;
		c.gridx = 0;
		c.gridy = 1;
		
		add(labelDescrizione, c);

		
		//Riga 2 - Tasti
		c.weighty = 0.3;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;
		c.gridwidth = 1;
		c.insets = new Insets(100, 52, 0, 52);
        c.gridy = 2;//riga
		c.gridx = 0;//colonna		
		add(aziendaButton, c);

		c.gridx = 1;
		c.gridy = 2;
		add(corriereButton, c);
		
		c.gridy = 2;//riga
		c.gridx = 2;//colonna
		add(farmaciaButton, c);
        
		//Frame
		setSize(1280, 720);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);//posiziona finestra al centro dello schermo
		setResizable(true);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);//per ultimo
	}
	
	private JButton GetDefaultBtn(String text, int fontSize, boolean defaultSize) {
		
		JButton btn = new JButton(text);
		btn.setFont(new Font("Dialog", Font.PLAIN, fontSize));
		
		if(!defaultSize) {
			btn.setPreferredSize(new Dimension(250, 120));			
		}
		
		return btn;
	}
}
