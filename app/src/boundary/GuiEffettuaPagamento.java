package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import control.GestioneBiglietteria;
import exception.OperationException;

import javax.swing.JLabel;

public class GuiEffettuaPagamento {

	JFrame frame;
	private JTextField cartaCredito;
	private JTextField email;
	private JTextField messaggioSistema;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	
	public GuiEffettuaPagamento() {
		
		initialize();
		cartaCredito.setText("numero carta");
		email.setText("email@libero.it");
		messaggioSistema.setText(null);
		
		lblNewLabel = new JLabel("Effettua Pagamento");
		lblNewLabel.setBounds(205, 18, 216, 26);
		frame.getContentPane().add(lblNewLabel);
		lblNewLabel.setForeground(new Color(30,144,255));
		lblNewLabel.setFont(new Font("Light 300", Font.PLAIN, 20));
		
		lblNewLabel_1 = new JLabel("Carta di credito:");
		lblNewLabel_1.setBounds(118, 85, 115, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Email:");
		lblNewLabel_2.setBounds(172, 141, 61, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("Messaggio di Sistema");
		lblNewLabel_3.setBounds(140, 186, 153, 16);
		frame.getContentPane().add(lblNewLabel_3);
		
	}
	
	public void initialize() {
		
GestioneBiglietteria gb = GestioneBiglietteria.getInstance();
		
		frame = new JFrame();
		frame.setBounds(50, 50, 500, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(176,224,230));
		frame.setResizable(false);
		frame.setTitle("Effettua Pagamento");
		
		
		cartaCredito = new JTextField();
		cartaCredito.setBounds(235, 80, 167, 26);
		frame.getContentPane().add(cartaCredito);
		cartaCredito.setColumns(10);
		
		email = new JTextField();
		email.setBounds(235, 136, 167, 26);
		frame.getContentPane().add(email);
		email.setColumns(10);
		
		messaggioSistema = new JTextField();
		messaggioSistema.setBounds(139, 201, 332, 36);
		frame.getContentPane().add(messaggioSistema);
		messaggioSistema.setColumns(10);
		
		JButton btnPaga = new JButton("Conferma Pagamento");
		btnPaga.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				String numeroCarta = cartaCredito.getText(); 
				String mail = email.getText();
				
				
				try {
					
					Long.parseLong(numeroCarta);

					
					if (numeroCarta.length() != 16) {
						messaggioSistema.setText("Numero carta non valido, deve essere di 16 cifre");
						return;
						
					} 
					
					if (!(mail.contains("@") && mail.contains("."))) {
						
						messaggioSistema.setText("Email non valida");
						return;
					} 
					
					
					gb.confermaPagamento(mail);
					messaggioSistema.setText("Prenotazione inviata");
					
					
				} catch (OperationException e1) {
					messaggioSistema.setText(e1.getMessage());
				} catch(NumberFormatException e2) {
					messaggioSistema.setText("Input non valido");
				}catch(IndexOutOfBoundsException e2) {
					messaggioSistema.setText("Non ci sono biglietti da inviare");
				}
			}
			
		});
		
		btnPaga.setBounds(274, 249, 197, 29);
		frame.getContentPane().add(btnPaga);
		
		
		JButton btnIndietro = new JButton("Indietro"); //torna alla schermata di PrenotaBiglietto
		btnIndietro.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				gb.svuotaListaBigliettiInAttesa();
				
				try {
					
					GuiPrenotaBiglietto eb = new GuiPrenotaBiglietto();
					eb.frame.setVisible(true);
					frame.setVisible(false);
					
				}catch(Exception exc) {
					messaggioSistema.setText("Errore nella creazione della finestra");					
				}
			
			}
			
		});
		
		btnIndietro.setBounds(139, 249, 117, 29);
		frame.getContentPane().add(btnIndietro);
		
	}
	
}
