package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import control.GestioneBiglietteria;
import exception.OperationException;

public class GuiLogin {
	
	JFrame frame;
	
	private JTextField textFieldIdImpiegato;
	private JTextField textFieldPassword;
	private JTextField textFieldMessaggioSistema;
	static Integer idImpiegato = null;
	
	public GuiLogin() {
		
		initialize();
		textFieldIdImpiegato.setText("idImpiegato");
		textFieldPassword.setText("password");
		textFieldMessaggioSistema.setText(null);
		
	}

	private void initialize() {
		
		GestioneBiglietteria gb = GestioneBiglietteria.getInstance();
		
		frame = new JFrame();
		frame.setBounds(50, 50, 500, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(176,224,230));
		frame.setResizable(false);
		frame.setTitle("Login");
		
		textFieldIdImpiegato = new JTextField();
		textFieldIdImpiegato.setBounds(25, 110, 168, 26);
		frame.getContentPane().add(textFieldIdImpiegato);
		textFieldIdImpiegato.setColumns(10);
		
		textFieldPassword = new JTextField();
		textFieldPassword.setBounds(25, 170, 168, 26);
		frame.getContentPane().add(textFieldPassword);
		textFieldPassword.setColumns(10);
		
		textFieldMessaggioSistema = new JTextField();
		textFieldMessaggioSistema.setBounds(300, 170, 168, 26);
		frame.getContentPane().add(textFieldMessaggioSistema);
		textFieldPassword.setColumns(10);
		
		JLabel lbLogin = new JLabel("Login");
		lbLogin.setBounds(210, 25, 94, 48);
		lbLogin.setFont(new Font("Light 300", Font.PLAIN, 30));
		lbLogin.setForeground(new Color(30,144,255));
		frame.getContentPane().add(lbLogin);
		
		JLabel lbIdImpiegato = new JLabel("Id");
		lbIdImpiegato.setBounds(30, 90, 61, 16);
		frame.getContentPane().add(lbIdImpiegato);
		
		JLabel lbPassword = new JLabel("Password");
		lbPassword.setBounds(30, 150, 61, 16);
		frame.getContentPane().add(lbPassword);
		
		JLabel lbEccezione = new JLabel("Messaggio di sistema");
		lbEccezione.setBounds(300, 150, 160, 16);
		frame.getContentPane().add(lbEccezione);
		
		JButton btnAccedi = new JButton("Accedi");
		btnAccedi.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				GuiMenuImpiegato eb = new GuiMenuImpiegato();
				boolean result = false;
				
				
				try {
					
					idImpiegato =Integer.parseInt(textFieldIdImpiegato.getText());
					
					result = gb.login(idImpiegato, textFieldPassword.getText());
					
					if(!result) {
						
						textFieldMessaggioSistema.setText("Credenziali errate");
						
					}else {
						
						
						eb.frame.setVisible(true);
						frame.setVisible(false);
					}
					
					
				}catch(OperationException  e1) {
					textFieldMessaggioSistema.setText(e1.getMessage());
				}catch(NumberFormatException e2) {
					textFieldMessaggioSistema.setText("Input non valido");
				}
				
			}
			
		});
		
		btnAccedi.setBounds(30, 200, 61, 16);
		frame.getContentPane().add(btnAccedi);
		
		
	}

}
