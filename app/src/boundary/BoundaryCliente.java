package boundary;

import java.sql.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import control.GestioneBiglietteria;
import exception.OperationException;

public class BoundaryCliente {

	static Scanner scan = new Scanner(System.in);
	static GestioneBiglietteria gb = GestioneBiglietteria.getInstance();
	
	public static void main(String[] args) {
		
		boolean exit = false;
		
		while(!exit) {
			
			System.out.println("Premi 1 per accedere alla funzionalità prenota biglietti");
			System.out.println("Premi 2 per uscire dall'applicazione");
			
			String op = scan.nextLine();
			
			if(op.equals("1")) {
				
				prenotaBiglietto();
				
			} else if(op.equals("2")) {
				
				System.out.println("Uscita...");
				exit = true;
				
			}else {
				
				System.out.println("Operazione non valida");
				
			}
			
		}
	
		
	}
	
	
	
	private static void prenotaBiglietto() {
		
		String cittàPartenza = null;
		String cittàArrivo = null;
		Date data = null;
		String email = null;
		String numeroCarta = null;
		int numeroPosti = 0;
		int numeroBagagli = 0;
		float prezzo = 0;
		boolean inputValido = false;
		
		try {
			
		
		
			System.out.println("Inserisci città di partenza: ");
			
			cittàPartenza = scan.nextLine();
			
			System.out.println("Inserisci città di arrivo: ");
			
			cittàArrivo = scan.nextLine();
			
			while(!inputValido) {
				
				try {
					
					System.out.println("Inserisci data nel formato 'yyyy-mm-dd': ");
					
					String dataTemp = scan.nextLine();
					data = Date.valueOf(dataTemp);
					
					inputValido = true;
					
				}catch (IllegalArgumentException e){
					
					System.out.println("Errore nell'acquisizione della data, riprovare!");
				}
				
			}
			
			System.out.println("Inserisci numero posti ");
			
			numeroPosti = Integer.parseInt(scan.nextLine());
			
			System.out.println("Inserisci numero bagagli (dim max: 60x45x25) cm");
			
			numeroBagagli = Integer.parseInt(scan.nextLine());
			
				
			prezzo = gb.AcquistaBiglietto(cittàPartenza, cittàArrivo, data, numeroPosti, numeroBagagli, null);
			
			System.out.println("Importo totale: € " + prezzo);
			System.out.println("Orario di partenza: " + gb.orarioCorsa(cittàPartenza, cittàArrivo, data));
			
			System.out.println("Digita C per confermare o qualunque altro carattere per annullare");
			
			String conferma = scan.nextLine();
			
			if(!conferma.equals("C") && !conferma.equals("c")) {
				
				System.out.println("Operazione annullata");
				gb.svuotaListaBigliettiInAttesa();
				return;
				
			}
			
			System.out.println("Operazione confermata");
			
			
			inputValido = false;
			
			while (!inputValido) {
				System.out.println("Inserire il numero di carta:");

				numeroCarta = scan.nextLine();

				try {
					Long.parseLong(numeroCarta);

					if (numeroCarta.length() == 16) {
						inputValido = true;
					} else {
						System.out.println("Errore inserimento carta, deve essere di 16 cifre..");
					}
				} catch (NumberFormatException e) {
					System.out.println("Errore inserimento carta, deve contenere solo numeri..");
				}
			}
			
			inputValido = false;
			
			while (!inputValido) {
				System.out.println("Inserisci la email");

				email = scan.nextLine();

				if (email.contains("@") && email.contains(".")) {
					inputValido = true;
				} else {
					System.out.println("Email non valida..");
				}
			}
			
			System.out.println();
			System.out.println("Pagamento in corso..");
			TimeUnit.SECONDS.sleep(3);
			System.out.println("Pagamento effettuato!");
			
			gb.confermaPagamento(email);
				
			
		}catch (OperationException | InterruptedException e) {
			e.printStackTrace();
		}	
		
	}
	
	
}
