package boundary;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;



public class MainMenu {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainMenu() {
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
		frame.setVisible(true);
		frame.setTitle("Gestione Biglietteria");
		frame.getContentPane().setBackground(new Color(176,224,230));//sfondo frame
		frame.setResizable(false);
	
		
		JLabel lbBiglietteria = new JLabel("CityPilot");
		lbBiglietteria.setForeground(new Color(30,144,255)); //colore testo
		lbBiglietteria.setFont(new Font("Light 300", Font.PLAIN, 30));
		lbBiglietteria.setBounds(185, 6, 183, 64);
		frame.getContentPane().add(lbBiglietteria);
		
        	
		
		JButton btnImpiegato = new JButton("Accedi come Impiegato");
		btnImpiegato.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					GuiLogin window = new GuiLogin();
					window.frame.setVisible(true);
					frame.setVisible(false);
					
				}catch(Exception exc) {
					System.out.println("Errore nella creazione della finestra");
				}
			}
		});
		
		
		frame.getContentPane().add(btnImpiegato);
		btnImpiegato.setBounds(110, 102, 270, 29);
		
		JButton btnCliente = new JButton("Continua come cliente");
		btnCliente.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					GuiMenuCliente window = new GuiMenuCliente();
					window.frame.setVisible(true);
					frame.setVisible(false);
					
				}catch(Exception exc) {
					System.out.println("Errore nella creazione della finestra");
				}
			}
		});
		
		
		frame.getContentPane().add(btnCliente);
		btnCliente.setBounds(110, 170, 270, 29);
		
		
	}
}
