package View.azienda.Prodotti;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.text.InternationalFormatter;

import Controller.azienda.ProdottoAziendaController;
import Models.Prodotto;
import View.azienda.BarraMenuAzienda;
import View.azienda.Home.HomeViewAzienda;
import utilities.GUItems;
import utilities.Helper;
import utilities.Navigation;
import utilities.GUItems.AlertType;

public class AggiungiProdottoView extends JFrame {

	private JLabel nomeProdottoLabel;
	private JTextField nomeProdottoField;
	
	private JLabel eanLabel;
	private JTextField eanField;
	
	private JLabel principioAttivoLabel;
	private JTextField principioAttivoField;
	
	private JLabel costoLabel;	
	private JFormattedTextField costoField;

	private JLabel isDaBancoLabel;	
	private JCheckBox isDaBancoCheckBox;
	
	private JButton aggiungiProdottoButton;
	
	public AggiungiProdottoView() {
		
		super("Aggiungi prodotti azienda");//titolo finestra (frame)
		setJMenuBar(BarraMenuAzienda.getMenuBar());

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

        
		// Riga 0 - titolo
        c.weighty = 0.01;//specifica il "peso" del componente. Se non specificato per nessuno verranno posizionati tutti nella parte centrale
        c.weightx = 0.01;		
		c.gridy = 0;//riga
		c.gridx = 0;//colonna
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(30, 0, 0, 0);
		add(GUItems.getDefaultTitleLabel("Aggiungi prodotto"), c);
		
		
		
		//Riga 1 - Label: Nome prodotto - EAN - Principio attivo
		c.gridy = 1;
		c.anchor = GridBagConstraints.SOUTH;
		c.gridwidth = 1;
		c.weighty = 0.7;
		c.insets = new Insets(25, 30, 25, 30);
		
		c.gridx = 0;
		nomeProdottoLabel = GUItems.getDefaultLabel("Nome prodotto");
		add(nomeProdottoLabel, c);
		
		c.gridx = 1;
		eanLabel = GUItems.getDefaultLabel("EAN");
		add(eanLabel, c);
		
		c.gridx = 2;
		principioAttivoLabel = GUItems.getDefaultLabel("Principio attivo");
		add(principioAttivoLabel, c);
		
		
		
		//Riga 2 - TextField: Nome prodotto - EAN - Principio attivo
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;
		
		c.gridx = 0;
		nomeProdottoField = GUItems.getDefaultTextField();
		add(nomeProdottoField, c);
		
		c.gridx = 1;
		eanField = GUItems.getDefaultTextField();
		add(eanField, c);
		
		c.gridx = 2;
		principioAttivoField = GUItems.getDefaultTextField();
		add(principioAttivoField, c);
		
		
		//Riga 3 - Label: Costo - Da banco
		c.gridy = 3;
		c.anchor = GridBagConstraints.SOUTH;

		c.gridx = 0;
		costoLabel = GUItems.getDefaultLabel("Costo");
		add(costoLabel, c);
		
		c.gridx = 1;
		isDaBancoLabel = GUItems.getDefaultLabel("È da banco");
		add(isDaBancoLabel, c);
		
		
		
		//Riga 4 - Label: Costo - Da banco
		c.gridy = 4;
		c.anchor = GridBagConstraints.NORTH;
		c.gridwidth = 1;
		
		c.gridx = 0;		    
		
		costoField = new JFormattedTextField();
		costoField.setFormatterFactory(new AbstractFormatterFactory() {

	        @Override
	        public AbstractFormatter getFormatter(JFormattedTextField tf) {
	            NumberFormat format = DecimalFormat.getInstance();
	            format.setMinimumFractionDigits(2);
	            format.setMaximumFractionDigits(2);
	            format.setRoundingMode(RoundingMode.HALF_UP);
	            InternationalFormatter formatter = new InternationalFormatter(format);
	            formatter.setAllowsInvalid(false);
	            formatter.setMinimum(0.0);
	            formatter.setMaximum(10000.00);
	            return formatter;
	        }
	    });
		
	    costoField.setAlignmentX(JLabel.LEFT_ALIGNMENT);
	    costoField.setFont(new Font("Dialog", Font.PLAIN, 25));		
		add(costoField, c);
		
		c.gridx = 1;
		c.anchor = GridBagConstraints.NORTH;
		isDaBancoCheckBox = new JCheckBox();
		isDaBancoCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		add(isDaBancoCheckBox, c);
		
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(210, 70, 210, 70);
		c.gridx = 2;
		aggiungiProdottoButton = new JButton("Aggiungi prodotto");
		aggiungiProdottoButton.setFont(GUItems.getDefaultFont(30));
		aggiungiProdottoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aggiungiProdotto(eanField.getText(), nomeProdottoField.getText(), principioAttivoField.getText(), costoField.getText(), isDaBancoCheckBox.isSelected());
			}
		});
		add(aggiungiProdottoButton, c);
		
		//Frame
		setSize(1600, 900);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);//posiziona finestra al centro dello schermo
		setResizable(true);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);//per ultimo
	}
	
	private void aggiungiProdotto(String ean, String nome, String principioAttivo, String costo, boolean isDaBanco) {
		
		ean = ean.trim();
		nome = nome.trim();
		principioAttivo = principioAttivo.trim();
		costo = costo.trim().replace(".", "").replace(",", ".");
		
		if(campiSonoCompilati(ean, nome, principioAttivo, costo)) {
			String result = ProdottoAziendaController.insertProdotto(new Prodotto(ean, nome, principioAttivo, new BigDecimal(costo), isDaBanco));
			if(!Helper.isNumber(result)) {
				alertErroreInserimentoProdotto(result);
			}
			
			else {
				alertProdottoInserito();
				Navigation.changePage(HomeViewAzienda.getInstance());
			}
		}
	}
	
	private boolean campiSonoCompilati(String ean, String nome, String principioAttivo, String costo) {
		
		ArrayList<String> campiMancanti = new ArrayList<>();
		boolean iCampiSonoCompilati = true;
		
		if(ean.isEmpty() || null == ean) {
			campiMancanti.add("- <b>EAN<b/>");
			iCampiSonoCompilati = false;
		}
		
		if(nome.isEmpty() || null == nome) {
			campiMancanti.add("- <b>Nome prodotto<b/>");
			iCampiSonoCompilati = false;
		}
		
		if(principioAttivo.isEmpty() || null == principioAttivo) {
			campiMancanti.add("- <b>Principio attivo<b/>");
			iCampiSonoCompilati = false;
		}
		
		if(costo.isEmpty() || null == costo) {
			campiMancanti.add("- <b>Costo<b/>");
			iCampiSonoCompilati = false;
		}
		
		if(!iCampiSonoCompilati) {
			alertCampoiNonCompilati("Devi compilare tutti i campi per poter aggiungere un nuovo prodotto.<br/>Devi ancora compilare i seguenti<br/>" + String.join("<br/>", campiMancanti));
			return false;
		}
		
		
		if(ean.length() != 12) {
			alertCampoiNonCompilati("Formato codice EAN non corretto. Deve essere di <b>12 cifre</b>");
			return false;
		}
		return true;
	
	}
	
	private void alertProdottoInserito() {
		GUItems.Alert("Nuovo prodotto inserito con successo!", "Prodotto inserito", AlertType.INFO);
	}
	
	private void alertErroreInserimentoProdotto(String result) {
		GUItems.AlertError("Si è verificato un errore..." + result);
	}
	
	private void alertCampoiNonCompilati(String msg) {
		GUItems.Alert(GUItems.htmlOf(msg), "Impossibile aggiungere nuovo prodotto", AlertType.ERROR);
	}
}
