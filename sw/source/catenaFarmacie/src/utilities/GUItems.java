package utilities;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

public class GUItems {
	
	//Label
	public static JLabel getDefaultTitleLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(getDefaultFont(52));
		label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		label.setHorizontalAlignment(JLabel.CENTER);
		
		return label;
	}
	
	public static JLabel getDefaultLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(getDefaultFont(25));
		label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		label.setHorizontalAlignment(JLabel.CENTER);
		
		return label;
	}
	
	
	//TextField	
	public static JTextField getDefaultTextField() {
		
		JTextField textField = new JTextField();//max length
		textField.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		textField.setFont(new Font("Dialog", Font.PLAIN, 25));
		
		return textField;
	}
	
	//Buttons
	public static JButton getDefaultBtn(String text, int fontSize, boolean defaultDimension) {
		
		JButton btn = new JButton(text);
		btn.setFocusPainted(false);
		btn.setFont(getDefaultFont(fontSize));
		
		if(!defaultDimension) {
			btn.setPreferredSize(new Dimension(210, 115));			
		}
		
		return btn;
	}
	
	public static JButton getIndietroButton(JFrame frameAttuale, JFrame framePrecedente) {
		//Aggiungo button Indietro
		JButton btnIndietro = getDefaultBtn("<- Indietro", 21, true);
		btnIndietro.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				Navigation.changePage(framePrecedente);
			}
		});
		
		return btnIndietro;
	}
	
	
	public static Font getDefaultFont(int fontSize) {
		return new Font("Dialog", Font.PLAIN, fontSize);
	}
	
	public static String corsivoOf(String text) {
		return "<span style='font-style:italic;'>" + text + "</span>";
	}
	
	public static String htmlOf(String text) {
		return "<html>" + text + "</html>";
	}
	
	public static String resize(String text, int size) {
		return htmlOf("<span style='font-size:" + size + "%;'>" + text + "</span>");
	}
	
	//SCROLLPANE
	public static JScrollPane getScrollPane(JTable table) {		
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		table.setPreferredScrollableViewportSize(Toolkit.getDefaultToolkit().getScreenSize());
		
		JTableHeader th = table.getTableHeader();
		th.setFont(GUItems.getDefaultFont(30));
		th.setPreferredSize(new Dimension(scrollPane.getWidth(), 43));		
		
		return scrollPane;
	}

	//MENU
	public static JMenu getDefaultJMenu(String text) {
		JMenu menu = new JMenu(text);
		menu.setFont(getDefaultFont(21));
		
		return menu;
	}
	
	public static JMenuItem getDefaultJMenuItem(String text) {
		JMenuItem menuItem = new JMenuItem(text);
		menuItem.setFont(getDefaultFont(21));
		
		return menuItem;
	}
	
	public static JMenuItem getDefaultJMenuItem(String text, String pathIcon) {
		JMenuItem menuItem = new JMenuItem(text, new ImageIcon(pathIcon));
		menuItem.setFont(getDefaultFont(21));	
		
		return menuItem;
	}
	
	// ALERT
	public enum AlertType{ CONFIRM, ERROR, INFO, WARNING };
	
	public static int Alert(String text, String titolo, AlertType alertType) {
		
		JLabel label = new JLabel(text);
		label.setFont(getDefaultFont(21));
		
		switch (alertType) {
		case CONFIRM:
			return JOptionPane.showConfirmDialog(null, label, titolo, JOptionPane.OK_CANCEL_OPTION);
		case ERROR:
			JOptionPane.showMessageDialog(null, label, titolo, JOptionPane.ERROR_MESSAGE);
			return 1;
		case WARNING:
			JOptionPane.showMessageDialog(null, label, titolo, JOptionPane.WARNING_MESSAGE);
			return 0;
		case INFO:
		default:
			JOptionPane.showMessageDialog(null, label, titolo, JOptionPane.INFORMATION_MESSAGE);
			return 0;
		}
	}
	
	public static int AlertError(String textError) {
		return Alert(textError, "Ops... qualcosa è andato storto", AlertType.ERROR);
	}
}
