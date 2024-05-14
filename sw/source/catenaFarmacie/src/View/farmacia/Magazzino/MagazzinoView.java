package View.farmacia.Magazzino;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.*;
import Controller.farmacia.MagazzinoController;
import Models.Magazzino;
import View.farmacia.BarraMenuFarmacia;
import utilities.GUItems;
import utilities.rendertable.farmacia.MagazzinoTable;

public class MagazzinoView extends JFrame {

	private JTable prodottiMagazzinoTable;
	
	public MagazzinoView() {
		
		super("Magazzino");//titolo finestra (frame)
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Imposto barra menu
		setJMenuBar(BarraMenuFarmacia.getMenuBar());
        
        c.weighty = 0.01;//specifica il "peso" del componente. Se non specificato per nessuno verranno posizionati tutti nella parte centrale
        c.weightx = 0.01;        
		
		//Label Titolo Magazzino
		c.gridy = 0;//riga
		c.gridx = 0;//colonna
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(30, 0, 0, 0);
		add(GUItems.getDefaultTitleLabel("Magazzino"), c);
		
		
		//tabella magazzino
		prodottiMagazzinoTable = getTableMagazzino();
		JScrollPane scrollPane = GUItems.getScrollPane(prodottiMagazzinoTable);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(70, 25, 25, 25);
		c.weightx = 1.0;
		c.weighty = 1.0;
		
		add(scrollPane, c);
		
		//Frame
		setSize(1600, 900);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);//posiziona finestra al centro dello schermo
		setResizable(true);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);//per ultimo
	}
	
	private JTable getTableMagazzino() {

		Magazzino magazzino = MagazzinoController.getMagazzinoFarmaciaCorrente();
		MagazzinoTable mt = new MagazzinoTable(magazzino.getProdottiMagazzino());
		return mt.getTable();		
	}
}
