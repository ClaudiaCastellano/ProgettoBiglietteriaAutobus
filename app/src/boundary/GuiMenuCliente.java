package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;



public class GuiMenuCliente {

	JFrame frame;
		
		public GuiMenuCliente() {
			initialize();
		}

		/**
		 * Initialize the contents of the frame.
		 */
		private void initialize() {
			
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
			
	        	
			JButton btnPrenotaBiglietto = new JButton("Prenota Biglietto");
			btnPrenotaBiglietto.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					
					try {
						
						GuiPrenotaBiglietto window = new GuiPrenotaBiglietto();
						window.frame.setVisible(true);
						frame.setVisible(false);
						
					}catch(Exception exc) {
						System.out.println("Errore nella creazione della finestra");
					}
				}
			});
			
			
			frame.getContentPane().add(btnPrenotaBiglietto);
			btnPrenotaBiglietto.setBounds(108, 95, 270, 29);
			
			
		}
}