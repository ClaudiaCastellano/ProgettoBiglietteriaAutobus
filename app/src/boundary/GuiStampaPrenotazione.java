package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import control.GestioneBiglietteria;
import exception.OperationException;

import javax.swing.JButton;

public class GuiStampaPrenotazione {
	
	JFrame frame;
	
	private JTextField textFieldIdBiglietto;
	private JTextField textFieldMessaggioSistema;
	
	public GuiStampaPrenotazione() {
		
		initialize();
		
		
	}
	
	public void initialize(){
		
		GestioneBiglietteria gb = GestioneBiglietteria.getInstance();
		
		frame = new JFrame();
		frame.setBounds(50, 50, 500, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(176,224,230));
		frame.setResizable(false);
		frame.setTitle("Stampa Prenotazione");
		
		textFieldIdBiglietto = new JTextField();
		textFieldIdBiglietto.setBounds(172, 118, 150, 26);
		frame.getContentPane().add(textFieldIdBiglietto);
		textFieldIdBiglietto.setColumns(10);
		
		textFieldMessaggioSistema = new JTextField();
		textFieldMessaggioSistema.setBounds(109, 180, 279, 44);
		frame.getContentPane().add(textFieldMessaggioSistema);
		textFieldMessaggioSistema.setColumns(10);
		
		
		JLabel lbStampaPrenotazione = new JLabel("Stampa Prenotazione");
		lbStampaPrenotazione.setForeground(new Color(30,144,255));
		lbStampaPrenotazione.setFont(new Font("Light 300", Font.PLAIN, 20 ));
		lbStampaPrenotazione.setBounds(155, 20, 211, 26);
		frame.getContentPane().add(lbStampaPrenotazione);
		
		JLabel lbIdBiglietto = new JLabel("idBiglietto");
		lbIdBiglietto.setBounds(172, 94, 168, 26);
		frame.getContentPane().add(lbIdBiglietto);
		
		JLabel lbMessaggioSistema = new JLabel("Messaggio di sistema");
		lbMessaggioSistema.setBounds(109, 164, 169, 16);
		frame.getContentPane().add(lbMessaggioSistema);
		
		
		JButton btnStampa = new JButton("Stampa");
		btnStampa.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					gb.StampaPrenotazione(Integer.parseInt(textFieldIdBiglietto.getText()));
					textFieldMessaggioSistema.setText("Stampa effettuata");
					
				} catch ( OperationException e1) {
					textFieldMessaggioSistema.setText(e1.getMessage());
				}catch(NumberFormatException e1) {
					textFieldMessaggioSistema.setText("Input non valido ");
				}
			
			}
			
		});
		
		
		btnStampa.setBounds(248, 258, 140, 29);
		frame.getContentPane().add(btnStampa);
		
		
		JButton btnIndietro = new JButton("Indietro");
		btnIndietro.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					GuiMenuImpiegato eb = new GuiMenuImpiegato();
					eb.frame.setVisible(true);
					frame.setVisible(false);
					
				}catch(Exception exc) {
					System.out.println("Errore nella creazione della finestra");
				}
				
				
				
			}
			
		});
		
		btnIndietro.setBounds(109, 258, 117, 29);
		frame.getContentPane().add(btnIndietro);
		
	}
}
