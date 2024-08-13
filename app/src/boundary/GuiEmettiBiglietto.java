package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import control.GestioneBiglietteria;
import exception.OperationException;

import javax.swing.JButton;

public class GuiEmettiBiglietto {
	
	JFrame frame;
	
	private JTextField cittàPartenza;
	private JTextField cittàArrivo;
	private JTextField data;
	private JTextField numeroPosti;
	private JTextField numeroBagagli;
	private JTextField textField;
	
	public GuiEmettiBiglietto() {
		
		initialize();
		cittàPartenza.setText("partenza");
		cittàArrivo.setText("arrivo");
		data.setText("2023-01-01");
		numeroPosti.setText("1");
		numeroBagagli.setText("1");
		
		
	}
	
	public void initialize(){
		
		GestioneBiglietteria gb = GestioneBiglietteria.getInstance();
		
		frame = new JFrame();
		frame.setBounds(50, 50, 500, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(176,224,230));
		frame.setResizable(false);
		frame.setTitle("Emetti Biglietto");
		
		cittàPartenza = new JTextField();
		cittàPartenza.setBounds(25, 82, 140, 26);
		frame.getContentPane().add(cittàPartenza);
		cittàPartenza.setColumns(10);
		
		cittàArrivo = new JTextField();
		cittàArrivo.setBounds(25, 135, 140, 26);
		frame.getContentPane().add(cittàArrivo);
		cittàArrivo.setColumns(10);
		
		data = new JTextField();
		data.setBounds(25, 186, 140, 26);
		frame.getContentPane().add(data);
		data.setColumns(10);
		
		numeroPosti = new JTextField();
		numeroPosti.setBounds(168, 224, 47, 26);
		frame.getContentPane().add(numeroPosti);
		numeroPosti.setColumns(1);
		
		numeroBagagli = new JTextField();
		numeroBagagli.setBounds(168, 262, 47, 26);
		frame.getContentPane().add(numeroBagagli);
		numeroBagagli.setColumns(1);
		
		textField = new JTextField();
		textField.setBounds(187, 88, 307, 44);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		
		JLabel lbEmettiBiglietto = new JLabel("Emetti Biglietto");
		lbEmettiBiglietto.setForeground(new Color(30,144,255));
		lbEmettiBiglietto.setFont(new Font("Light 300", Font.PLAIN, 20 ));
		lbEmettiBiglietto.setBounds(168, 20, 164, 26);
		frame.getContentPane().add(lbEmettiBiglietto);
		
		JLabel lbCittàPartenza = new JLabel("Città di partenza: ");
		lbCittàPartenza.setBounds(25, 56, 168, 26);
		frame.getContentPane().add(lbCittàPartenza);
		
		JLabel lbCittàArrivo= new JLabel("Città di arrivo: ");
		lbCittàArrivo.setBounds(25, 108, 168, 26);
		frame.getContentPane().add(lbCittàArrivo);
		
		JLabel lbData= new JLabel("Data ('yyyy-mm-dd'): ");
		lbData.setBounds(25, 159, 168, 26);
		frame.getContentPane().add(lbData);
		
		JLabel lbNumeroPosti= new JLabel("Numero di posti: ");
		lbNumeroPosti.setBounds(25, 224, 168, 26);
		frame.getContentPane().add(lbNumeroPosti);
		
		JLabel lbNumeroBagagli= new JLabel("Numero di bagagli: ");
		lbNumeroBagagli.setBounds(25, 262, 168, 26);
		frame.getContentPane().add(lbNumeroBagagli);
		
		JLabel lbMessaggioSistema = new JLabel("Messaggio di sistema");
		lbMessaggioSistema.setBounds(281, 58, 169, 16);
		frame.getContentPane().add(lbMessaggioSistema);
		
		JLabel lblNewLabel = new JLabel("Dim max bagagli: (60x45x25)cm");
		lblNewLabel.setBounds(25, 289, 217, 27);
		frame.getContentPane().add(lblNewLabel);
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		
		
		JButton btnConferma = new JButton("Conferma"); //calcola prezzo e mostra orario di partenza
		btnConferma.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				float prezzo = 0;
				String orario = null;
				String prezzoString = null;
				String message = null;		
				
				try {
					
					prezzo = gb.AcquistaBiglietto(cittàPartenza.getText(), cittàArrivo.getText(), Date.valueOf(data.getText()), Integer.parseInt(numeroPosti.getText()), Integer.parseInt(numeroBagagli.getText()), GuiLogin.idImpiegato);
					orario = gb.orarioCorsa(cittàPartenza.getText(), cittàArrivo.getText(), Date.valueOf(data.getText()));
					
					prezzoString = Float.toString(prezzo);
					
					message = "Prezzo: € " + prezzoString + " orario di partenza: " + orario + "";
					
					textField.setText(message);
					
				} catch (OperationException e1) {
					textField.setText(e1.getMessage());
				}catch (IllegalArgumentException e2) {
					textField.setText("Input non valido");
				}
			
			}
			
		});
		
		
		btnConferma.setBounds(354, 144, 128, 29);
		frame.getContentPane().add(btnConferma);
		
		
		JButton btnStampa = new JButton("Stampa"); //salva e stampa i biglietti inseriti
		btnStampa.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					gb.confermaBiglietti();
					textField.setText("Stampa effettuata");
					
				} catch (OperationException e1) {
					textField.setText(e1.getMessage());
				}
			
			}
			
		});
		
		btnStampa.setBounds(354, 203, 128, 29);
		frame.getContentPane().add(btnStampa);
		
		JButton btnAnnulla = new JButton("Annulla "); //annulla i biglietti inseriti
		btnAnnulla.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
					
				gb.svuotaListaBigliettiInAttesa();
				textField.setText("Biglietti annullati");
		
			}
			
		});
		
		btnAnnulla.setBounds(235, 144, 117, 29);
		frame.getContentPane().add(btnAnnulla);
		
		
		JButton btnIndietro = new JButton("Indietro"); //torna alla schermata di MenuImpiegato
		btnIndietro.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				gb.svuotaListaBigliettiInAttesa();
				
				try {
					
					GuiMenuImpiegato eb = new GuiMenuImpiegato();
					eb.frame.setVisible(true);
					frame.setVisible(false);
					
				}catch(Exception exc) {
					textField.setText("Errore nella creazione della finestra");					
				}
			
			}
			
		});
		
		btnIndietro.setBounds(235, 203, 117, 29);
		frame.getContentPane().add(btnIndietro);
		
	}
}
