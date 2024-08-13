package boundary;

import java.sql.Date;
import java.util.Scanner;

import control.GestioneBiglietteria;
import exception.OperationException;

public class BoundaryImpiegato {
	
	static Scanner scan = new Scanner(System.in);
	static GestioneBiglietteria gb = GestioneBiglietteria.getInstance();
	static int idImpiegato = 0;
	
	public static void main(String[] args) {
		
		boolean login = false;
		boolean exit = false;
		
		
		while(!login) {
			
			login = login();
	
		}
		
		System.out.println("Accesso confermato");
		
		
		while(!exit) {
			
			System.out.println("Premi 1 per accedere alla funzionalità emetti biglietti");
			System.out.println("Premi 2 per accedere alla funzionalità stampa prenotazione");
			System.out.println("Premi 3 per eseguire il logout");
			
			String op = scan.nextLine();
			
			if(op.equals("1")) {
				
				emettiBiglietto();
				
			} else if(op.equals("2")) { 
				
				stampaPrenotazione();
				
			}else if(op.equals("3")) {
				
				System.out.println("Logout in corso..");
				exit = true;
				
			}else {
				
				System.out.println("Operazione non valida");
				
			}
			
		}
		
	}
	
	
	private static boolean login() {
		
		String password = null;
		boolean login = false;
		
		System.out.println("Inserisci id: ");
		
		idImpiegato = Integer.parseInt(scan.nextLine());
		
		System.out.println("Inserisci password: ");
		
		password = scan.nextLine();
		
		try {
			login = gb.login(idImpiegato, password);
			
		} catch (OperationException e) {
			System.out.println(e.getMessage());
		}
		
		if(login) {
			
			return true;
			
		}else {
			
			System.out.println("Credenziali errate");
			return false;
		}
		
	}
	

	private static void emettiBiglietto() {
		
		String cittàPartenza = null;
		String cittàArrivo = null;
		Date data = null;
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
				
			prezzo = gb.AcquistaBiglietto(cittàPartenza, cittàArrivo, data, numeroPosti, numeroBagagli, idImpiegato);
				
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
			gb.confermaBiglietti();
				
			
		}catch (OperationException e) {
			System.out.println(e.getMessage());
		}catch(NumberFormatException e1) {
			System.out.println("Input non valido");
		}	
		
	}
	
	public static void stampaPrenotazione() {
		
		int idBiglietto = 0;
		boolean result = true;
		
		while(result) {
			
			System.out.println("Inserisci id del biglietto da stampare: ");
			
			idBiglietto = Integer.parseInt(scan.nextLine());
			
			try {
				
				gb.StampaPrenotazione(idBiglietto);
				
			} catch (OperationException e) {
				System.out.println(e.getMessage());
			}
			
			System.out.println("Vuoi stampare altri biglietti? (Digita S per si oppure qualsiasi altro tasto per no)");
			
			String conferma = scan.nextLine();
			
			if(!conferma.equals("S") && !conferma.equals("s")) {
				
				result = false;
				
			}else {
				
				result = true;
			}
			
		}
		
	}

}
