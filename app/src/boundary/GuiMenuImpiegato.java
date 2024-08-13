package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import control.GestioneBiglietteria;
import exception.OperationException;


public class GuiMenuImpiegato {

	JFrame frame;
		
		public GuiMenuImpiegato() {
			initialize();
		}

		/**
		 * Initialize the contents of the frame.
		 */
		private void initialize() {
			
			GestioneBiglietteria gb = GestioneBiglietteria.getInstance();
			
			frame = new JFrame();
			frame.setBounds(50, 50, 500, 350);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(null);
			frame.setTitle("Gestione Biglietteria");
			frame.getContentPane().setBackground(new Color(176,224,230));//sfondo frame
			frame.setResizable(false);
		
			
			JLabel lbBiglietteria = new JLabel("CityPilot");
			lbBiglietteria.setForeground(new Color(30,144,255)); //colore testo
			lbBiglietteria.setFont(new Font("Light 300", Font.PLAIN, 30));
			lbBiglietteria.setBounds(185, 6, 183, 64);
			frame.getContentPane().add(lbBiglietteria);
			
	        	
			
			JButton btnEmettiBiglietto = new JButton("Emetti Biglietto");
			btnEmettiBiglietto.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					
					try {
						
						GuiEmettiBiglietto window = new GuiEmettiBiglietto();
						window.frame.setVisible(true);
						frame.setVisible(false);
						
					}catch(Exception exc) {
						System.out.println("Errore nella creazione della finestra");
					}
				}
			});
			
			
			frame.getContentPane().add(btnEmettiBiglietto);
			btnEmettiBiglietto.setBounds(110, 95, 270, 29);
			
			JButton btnGeneraReport = new JButton("Genera Report");
			btnGeneraReport.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					
					try {
						
						gb.GeneraReport();
						
					}catch(OperationException e1) {
						System.out.println("Errore");
					}
				}
			});
			
			frame.getContentPane().add(btnGeneraReport);
			btnGeneraReport.setBounds(110, 204, 270, 29);
			
			
			JButton btnStampaPrenotazione = new JButton("Stampa Prenotazione");
			btnStampaPrenotazione.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					
					try {
						
						GuiStampaPrenotazione window = new GuiStampaPrenotazione();
						window.frame.setVisible(true);
						frame.setVisible(false);
						
					}catch(Exception exc) {
						System.out.println("Errore nella creazione della finestra");
					}
				}
			});
			
			frame.getContentPane().add(btnStampaPrenotazione);
			btnStampaPrenotazione.setBounds(110, 146, 270, 29);
			
			JButton btnLogout = new JButton("Logout");
			btnLogout.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					
					try {
						
						GuiLogin eb = new GuiLogin();
						eb.frame.setVisible(true);
						frame.setVisible(false);
						
					}catch(Exception exc) {
						System.out.println("Errore nella creazione della finestra");
					}
						
				}
				
			});
			
			btnLogout.setBounds(25, 258, 117, 29);
			frame.getContentPane().add(btnLogout);
			
		}
}