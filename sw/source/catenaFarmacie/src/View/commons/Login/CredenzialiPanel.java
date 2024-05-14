package View.commons.Login;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;


public class CredenzialiPanel extends JPanel {

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	private JLabel labelUser;
	private JLabel labelPassword;
	
	private JTextField usernameField;
	private JPasswordField passwordField;
	
	public CredenzialiPanel() {
		
		GridLayout gl = new GridLayout(2, 2, 10, 25);
		setLayout(gl);
		
		setAlignmentX(JPanel.CENTER_ALIGNMENT);
		
		labelUser = new JLabel("Username");
		labelUser.setFont(new Font("Dialog", Font.PLAIN, 25));
		labelUser.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
		add(labelUser);
		
		usernameField = new JTextField(25);//max length
		usernameField.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		usernameField.setFont(new Font("Dialog", Font.PLAIN, 21));
		add(usernameField);
		
		labelPassword = new JLabel("Password");
		labelPassword.setFont(new Font("Dialog", Font.PLAIN, 25));
		labelPassword.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
		add(labelPassword);
		
		passwordField = new JPasswordField(21);//max length
		passwordField.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		passwordField.setFont(new Font("Dialog", Font.PLAIN, 25));
		add(passwordField);		
	}
	
	public String getUsernameValue() {
		return this.usernameField.getText();
	}
	
	public String getPasswordValue() {
		return String.valueOf(this.passwordField.getPassword());
	}
}