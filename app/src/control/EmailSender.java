package control;

import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import exception.OperationException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * La classe EmailSender gestisce l'invio delle mail contenenti prenotazioni e report
 * Per testare le funzionalità offerte usare:
 * Mail : progettois2023@libero.it
 * Password : Progettis2023!
 * @author gaetanoruocco,claudiacastellano,andreatito
 * 
 */
public class EmailSender {

	/**
	 * La funzione emailSender() configura le proprietà del server di posta e l'autenticazione per l'account di posta,
	 * crea una sessione di posta con le impostazioni definite, imposta gli indirizzi email del mittente e del destinatario
	 * imposta il soggetto ed il contenuto dell'email ed invia l'email.
	 * Per testare le funzionalità offerte usare:
	 * Mail : progettois2023@libero.it
	 * Password : Progettis2023!
	 * @param file
	 * @param email
	 * @param oggetto
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws OperationException
	 */
	    public static void emailSender(String file, String email, String oggetto) throws FileNotFoundException, IOException, OperationException {
	    

	    	String testo = "";

	    	try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	    	    String linea;

	    	    while ((linea = br.readLine()) != null) {
	    	        testo += linea + "\n";
	    	    }
	    	}

	    	
	    	
	        // Configura le proprietà del server di posta
	        Properties properties = new Properties();
	        properties.put("mail.smtp.host", "smtp.libero.it");
	        properties.put("mail.smtp.port", "465");
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.smtp.ssl.enable", "true");

	        // Configura l'autenticazione per l'account di posta
	        final String username = "ciao2_ciao@libero.it";
	        final String password = "Prova_1234";

	        // Crea una sessione di posta con le impostazioni definite
	        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
	            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
	                return new javax.mail.PasswordAuthentication(username, password);
	            }
	        });

	        try {
	            // Crea un oggetto MimeMessage
	            Message message = new MimeMessage(session);

	            // Imposta l'indirizzo email del mittente
	            message.setFrom(new InternetAddress("ciao2_ciao@libero.it"));

	            // Aggiungi l'indirizzo email del destinatario
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

	            // Imposta il soggetto dell'email
	            message.setSubject(oggetto);

	            // Imposta il contenuto dell'email
	            message.setText(testo);

	            // Invia l'email
	            Transport.send(message);

	            System.out.println("Email inviata con successo!");

	        } catch (MessagingException e) {
	            System.out.println("Errore durante l'invio dell'email: " + e.getMessage());
	            throw new OperationException("Email non valida");
	        }
	    }
	  
	}

