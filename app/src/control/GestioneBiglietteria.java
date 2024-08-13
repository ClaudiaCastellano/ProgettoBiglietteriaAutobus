package control;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import database.AutobusDAO;
import database.BigliettoDAO;
import database.CorsaDAO;
import database.ImpiegatoDAO;
import database.TrattaDAO;
import entity.EntityAutobus;
import entity.EntityBiglietto;
import entity.EntityCorsa;
import entity.EntityImpiegato;
import entity.EntityTratta;
import exception.DAOException;
import exception.DBConnectionException;
import exception.OperationException;
/**
 * 
 * Questa è la classe Control GestioneBiglietteria
 * @author gaetanoruocco,claudiacastellano,andreatito
 *
 */
public class GestioneBiglietteria {
	
	private static GestioneBiglietteria gB = null;
	private ArrayList<EntityBiglietto> bigliettiInAttesa;
	

	protected GestioneBiglietteria(){
		bigliettiInAttesa = new ArrayList<EntityBiglietto>();
	}

	public static GestioneBiglietteria getInstance() 
	{ 
		if (gB == null) 
			gB = new GestioneBiglietteria(); 

		return gB; 
	}
	
	/**
	 * La funzione AcquistaBiglietto() chiama la funzione {@link #VerificaDisponibilità(EntityCorsa, int, int)} che verifica la disponibilità di una corsa in base ai parametri ricevuti,
	 * chiama la funzione {@link #calcolaPrezzo(EntityCorsa, int, int)} che restituisce il prezzo dei biglietti e crea la lista dei biglietti in attesa del pagamento 
	 * @param cittàPartenza
	 * @param cittàArrivo
	 * @param data, nel formato 'yyyy-mm-dd'
	 * @param numPosti
	 * @param numBagagli
	 * @param idImpiegato, nel caso di EmettiBiglietto sarà fornito al momento dell'emissione,
	 * nel caso di PrenotaBiglietto viene assunto 'null' 
	 * @return prezzo calcolato
	 * @throws OperationException
	 */
	public float AcquistaBiglietto(String cittàPartenza, String cittàArrivo, Date data, int numPosti, int numBagagli, Integer idImpiegato) throws OperationException {
		
		if(cittàPartenza.length() > 100 || cittàArrivo.length() > 100 ) {
			throw new OperationException("Città troppo lunga!");
		}
		
		if(!cittàPartenza.matches("[a-zA-Z0-9'À-ÖØ-öø-ÿ]+") || !cittàArrivo.matches("[a-zA-Z0-9'À-ÖØ-öø-ÿ]+")) {
			throw new OperationException("Città errata");
		}
		
		
		LocalDate currentDate = LocalDate.now();
		LocalTime currentTime = LocalTime.now();
		
		Date dataCorrente = Date.valueOf(currentDate);
		Time oraCorrente = Time.valueOf(currentTime);
		
		if(data.before(dataCorrente)) {
			throw new OperationException("Data non valida");
		}
		
		if(numPosti <= 0 ) {
			throw new OperationException("Numero posti non valido");
		}
		
		if(numBagagli < 0) {
			throw new OperationException("Numero bagagli non valido");
		}
	 
		 float prezzo = 0;
		
		if(numBagagli <= numPosti) {
			
			try {
				
				EntityCorsa ec = CorsaDAO.readCorsa(cittàPartenza, cittàArrivo, data);
				
				if(ec==null) {
					throw new OperationException("Non esiste una corsa che soddisfa le richieste");
				}
				
				if(VerificaDisponibilità(ec, numPosti, numBagagli) == true) {
					
					prezzo =  calcolaPrezzo(ec, numPosti, numBagagli);
					
					boolean presenzaBagaglio = false;
					
					for(int i = 0; i < numPosti; i++) {
						
						if(numBagagli > 0) {
							
							presenzaBagaglio = true;
							numBagagli--;
							
						}
						
						EntityBiglietto eb = new EntityBiglietto(i, dataCorrente, oraCorrente, presenzaBagaglio, ec.getIdCorsa(), idImpiegato);
						bigliettiInAttesa.add(eb);
						
						presenzaBagaglio = false;
						
					}
					
				}else {
					throw new OperationException("Non c'è disponibilità");
				}
				
				
			} catch (DBConnectionException e) {
				throw new OperationException("Errore interno all'applicazione");
			} catch (DAOException e) {
				throw new OperationException("OPS, qualcosa è andato storto!");
			}	
			
		}else {
			throw new OperationException("Ogni persona può portare un solo bagaglio");
		}
		
		return prezzo;
		
	}
	/**
	 * La funzione confermaPagamento() salva nel database i biglietti in attesa chiamando la funzione saveBiglietto() presente nell'EntityBiglietto, 
	 * chiama la funzione {@link #inviaPrenotazione(String, int[])} che gestisce l'invio della mail al cliente 
	 * e chiama la funzione {@link #svuotaListaBigliettiInAttesa()} che rimuove i biglietti dalla lista bigliettiInAttesa
	 * @param email, fornita del cliente
	 * @throws OperationException
	 */
	public void confermaPagamento(String email) throws OperationException {
		
		int[] idArray = new int[bigliettiInAttesa.size()];
		
		for(int i = 0; i < bigliettiInAttesa.size(); i++) {
			
				try {
					
					idArray[i] = bigliettiInAttesa.get(i).saveBiglietto();
					
					
				} catch (DAOException e) {
					throw new OperationException("OPS, qualcosa è andato storto!");
				} catch (DBConnectionException e) {
					throw new OperationException("Errore interno all'applicazione");
				}
		}
		
			try {
				
				inviaPrenotazione(email, idArray);
				
			} catch (IOException e) {
				throw new OperationException("Errore di invio prenotazione");
			}
			
			svuotaListaBigliettiInAttesa();
		
	}
	
	/**
	 * La funzione inviaPrenotazione() crea il file di testo da inviare al cliente con gli estremi della prenotazione 
	 * e chiama la funzione {@link EmailSender#emailSender(String, String, String)} che invia il file via email
	 * @param email, fornita dal cliente
	 * @param idArray, id biglietti prenotati 
	 * @throws IOException
	 * @throws OperationException
	 */
	private void inviaPrenotazione(String email, int[] idArray) throws IOException, OperationException {
		
		int id = bigliettiInAttesa.get(0).getIdCorsa();
		
		int numeroBagagli = 0;
		
		for(int i = 0; i < bigliettiInAttesa.size(); i++) {
			
			if(bigliettiInAttesa.get(i).getPresenzaBagaglio() == true) {
				numeroBagagli++;
			}
		}
		
		try {
			
			EntityCorsa ec = CorsaDAO.readCorsaId(id);
			
			EntityTratta et = TrattaDAO.readTratta(ec.getIdTratta());
			
			FileWriter fw = new FileWriter("./prenotazione.txt", false);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			
			
			
			pw.println("Copia prenotazione: ");
			
			if(ec.getTipo().compareTo("andata") == 0) {
				pw.println("Id corsa: " + ec.getIdCorsa() + "\n" + "Città di partenza: " + et.getCittàPartenza() + "\n" + "Città di arrivo: " + et.getCittàArrivo() + "\n" + "Data: " + ec.getData() + "\n" + "Orario di partenza: " + ec.getOrarioPartenza() + "\n" + "Orario di Arrivo: " + ec.getOrarioArrivo() + "\n" + "Numero Biglietti: " + bigliettiInAttesa.size() +  "\n" + "Numero Bagagli: " + numeroBagagli);
			}else {
				pw.println("Id corsa: " + ec.getIdCorsa() + "\n" + "Città di partenza: " + et.getCittàArrivo()+ "\n" + "Città di arrivo: " + et.getCittàPartenza() + "\n" + "Data: " + ec.getData() + "\n" + "Orario Partenza: " + ec.getOrarioPartenza() + "\n" + "Orario Arrivo: " + ec.getOrarioArrivo()+ "\n" +  "NumeroBiglietti: " + bigliettiInAttesa.size() + "\n" + "Numero Bagagli: " + numeroBagagli);
				
			}
			
			for(int i = 0; i < idArray.length; i++) {
				
				pw.println("Id biglietto[ " + i + " ]: " + idArray[i]);
				
			}
			
			
			pw.close();
			bw.close();
			fw.close();
			
			
			
			EmailSender.emailSender("./prenotazione.txt", email, "Prenotazione biglietti");
			
		} catch (DBConnectionException e) {
			throw new OperationException("Errore interno all'applicazione");
		} catch (DAOException e) {
			throw new OperationException("OPS, qualcosa è andato storto!");
		}
		
	}
	/**
	 * La funzione confermaBiglietti() salva nel database i biglietti in attesa chiamando la funzione saveBiglietto() presente nell'EntityBiglietto, 
	 * chiama la funzione {@link #StampaBiglietto(EntityBiglietto)} che gestisce la stampa 
	 * e chiama la funzione {@link #svuotaListaBigliettiInAttesa()} che rimuove i biglietti dalla lista bigliettiInAttesa
	 * @throws OperationException
	 */
	
	public void confermaBiglietti() throws OperationException {
		
		if(bigliettiInAttesa.size() == 0) {
			throw new OperationException("Non ci sono biglietti da stampare");
		}
		
		System.out.println("Stampa dei biglietti...");
		
		for(int i = 0; i < bigliettiInAttesa.size(); i++) {
			
			try {
				
				bigliettiInAttesa.get(i).saveBiglietto();
				
				StampaBiglietto(bigliettiInAttesa.get(i));
				
			} catch (DAOException e) {
				throw new OperationException("OPS, qualcosa è andato storto!");
			} catch (DBConnectionException e) {
				throw new OperationException("Errore interno all'applicazione");
			}
		}
		
			svuotaListaBigliettiInAttesa();
		
	}
	
	/**
	 * La funzione svuotaListaBigliettiInAttesa() rimuove i biglietti dalla lista temporanea
	 */
	public void svuotaListaBigliettiInAttesa() {
		
		for(int i = bigliettiInAttesa.size()-1 ; i >= 0; i--) {
			
			System.out.println("Rimozione biglietto con id " + bigliettiInAttesa.get(i).getIdBiglietto() );
			bigliettiInAttesa.remove(i);
			
		}
		
	}
	
	
	/**
	 * La funzione VerificaDisponibilità() controlla la validità dei parametri richiesti, 
	 * calcola il numero di posti e spazi residui dell'autobus relativo alla corsa scelta
	 * @param ec, corsa scelta
	 * @param numPosti
	 * @param numBagagli
	 * @return result, booleano che indica la disponibilità per la corsa scelta 
	 * @throws OperationException
	 */
	private boolean VerificaDisponibilità(EntityCorsa ec, int numPosti, int numBagagli) throws OperationException {
		
		
		boolean result = false;
		
		try {
			
			int idTratta = ec.getIdTratta();
			
			EntityTratta et = TrattaDAO.readTratta(idTratta);
			
			int idAutobus = et.getIdAutobus();
			
			EntityAutobus ea = AutobusDAO.readAutobus(idAutobus);
			
			int numeroPosti = ea.getNumeroPosti();
			int numeroSpazi = ea.getNumeroSpazi();
			
			int numeroBagagli = BigliettoDAO.readNumBagagli(ec.getIdCorsa());
			int numeroBiglietti = BigliettoDAO.readNumBigliettiVenduti(ec.getIdCorsa());
			
			if(((numeroPosti - numeroBiglietti) >= numPosti) && (numeroSpazi - numeroBagagli) >= numBagagli) {
				
				result = true;;
				
			}else {
				result = false;
			}	
			
		} catch (DBConnectionException e) {
			throw new OperationException("Errore interno all'applicazione");
		} catch (DAOException e) {
			throw new OperationException("OPS, qualcosa è andato storto!");
		}
		
		return result;
		
		
	}
	/**
	 * La funzione calcolaPrezzo() calcola il prezzo dei biglietti sulla base del numero di posti e bagagli e della corsa selezionata
	 * @param ec
	 * @param numPosti
	 * @param numBagagli
	 * @return il prezzo calcolato
	 */
	private float calcolaPrezzo(EntityCorsa ec, int numPosti, int numBagagli) {
		
		
		
		float prezzoCorsa = ec.getPrezzoBiglietto();
		
		float prezzoBiglietto = prezzoCorsa * numPosti;
		
		if(numBagagli != 0) {
			float sovrapprezzo = ApplicaSovrapprezzo(numBagagli);
			prezzoBiglietto += sovrapprezzo;
		}
		
		 return prezzoBiglietto;
		
	}
	
	/**
	 * La funzione ApplicaSovrapprezzo() aggiunge 5€ per ogni bagaglio
	 * @param numBagagli
	 * @return
	 */
	private float ApplicaSovrapprezzo(int numBagagli) {
		
		return (float)(5*numBagagli);
		
	}
	
	/**
	 * La funzione StampaBiglietto() stampa a video il biglietto passato come parametro
	 * @param eb
	 */
	public void StampaBiglietto(EntityBiglietto eb) {
		
		System.out.println("Data di emissione: " + eb.getDataEmissione() + " Ora di emissione: " + eb.getOraEmissione() + " bagaglio: " + eb.getPresenzaBagaglio());
		
	}
	
	/**
	 * La funzione StampaPrenotazione() cerca il biglietto associato ad un id specifico e chiama la funzione {@link #StampaBiglietto(EntityBiglietto)} 
	 * @param idBiglietto
	 */
	public void StampaPrenotazione(int idBiglietto) throws OperationException {
		
		try {
			
			EntityBiglietto eb = BigliettoDAO.readBiglietto(idBiglietto);
			
			if(eb != null) {
				
				StampaBiglietto(eb);
				
			}else {
				throw new OperationException("Non esiste biglietto con questo id");
			}
			
			
			
		} catch (DBConnectionException e) {
			throw new OperationException("Errore interno all'applicazione");
		} catch (DAOException e) {
			throw new OperationException("Ops qualcosa è andato storto");
		}
		
	}
	
	/**
	 * La funzione GeneraReport() calcola i biglietti venduti nella settimana corrente per ogni tratta ordinati per città di partenza
	 * e chiama la funzione {@link EmailSender#emailSender(String, String, String)} che invia il report al direttore
	 * @throws OperationException
	 */
	public void GeneraReport() throws OperationException {
		
		String email = new String("progettois2023@libero.it");
		
		try {
			BigliettoDAO.readBigliettiReport();
			
			
		} catch (DAOException e) {
			throw new OperationException("Errore interno all'applicazione");
		} catch (DBConnectionException e) {
			throw new OperationException("Ops qualcosa è andato storto");
		}
		
		try {
			EmailSender.emailSender("./report.txt", email, "Report settimanale");
			
		} catch (FileNotFoundException e) {
			throw new OperationException("Errore file");
		} catch (IOException e) {
			throw new OperationException("Errore I/O");
		}
		
	}
	
	/**
	 * La funzione login() controlla che le credenziali inserite siano corrette
	 * @param idImpiegagto
	 * @param password
	 * @return vero se le credenziali sono giuste, falso se sbagliate
	 * @throws OperationException
	 */
	public boolean login(int idImpiegagto, String password) throws OperationException {
		
		boolean result = false;
		
		try {
			
			EntityImpiegato ei = ImpiegatoDAO.readImpiegato(idImpiegagto);
			
			if(ei != null) {
				
				if(ei.getPassword().equals(password)) {
					result = true;
				}	
			}
			
			
		} catch (DBConnectionException e) {
			throw new OperationException("Errore interno all'applicazione");
		} catch (DAOException e) {
			throw new OperationException("Ops qualcosa è andato storto");
		}
		
		return result;
		
	}
	
	/**
	 * La funzione orarioCorsa resitutisce l'orario di partenza della corsa che soddisfa i parametri inseriti
	 * @param cittàPartenza
	 * @param cittàArrivo
	 * @param data
	 * @return orario di partenza, come stringa nel formato 'hh:mm:ss'
	 * @throws OperationException
	 */
	public String orarioCorsa(String cittàPartenza, String cittàArrivo, Date data) throws OperationException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String timeString = null;
		
		try {
			
			EntityCorsa ec = CorsaDAO.readCorsa(cittàPartenza, cittàArrivo, data);
			
			if(ec==null) {
				throw new OperationException("Non esiste una corsa che soddisfa le richieste");
			}
			
			 timeString = dateFormat.format(ec.getOrarioPartenza()); 
		
			
		} catch (DBConnectionException e) {
			throw new OperationException("Errore interno all'applicazione");
		} catch (DAOException e) {
			throw new OperationException("Ops qualcosa è andato storto");
		}
		
		return timeString;

		
	}
	
	

}
