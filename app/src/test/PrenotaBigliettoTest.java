package test;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Test;

import control.GestioneBiglietteria;
import exception.OperationException;

public class PrenotaBigliettoTest {

	GestioneBiglietteria gb = GestioneBiglietteria.getInstance();
	@Test
	public void test1() {
		try {
			gb.AcquistaBiglietto("Napoli", "Roma", Date.valueOf("2023-06-19"), 2, 1, (Integer) 5000);
			gb.confermaPagamento("progettois2023@libero.it");
		}catch(OperationException op) {fail();}
	}

	public void test2() throws OperationException {
		OperationException op = assertThrows(OperationException.class, ()->{
			gb.AcquistaBiglietto("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "Roma", Date.valueOf("2023-06-19"), 2, 1, null);
		});
		String msg = op.getMessage();
		assertEquals(msg, "Città troppo lunga!");
	}

	@Test
	public void test3() {
		OperationException op = assertThrows(OperationException.class, ()->{
			gb.AcquistaBiglietto("$%!", "Roma", Date.valueOf("2023-06-19"), 2, 1, null);
		});
		String msg = op.getMessage();
		assertEquals(msg, "Città errata");
	}

	@Test
	public void test4() {
		OperationException op = assertThrows(OperationException.class, ()->{
			gb.AcquistaBiglietto("Napoli", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", Date.valueOf("2023-06-19"), 2, 1, null);
		});
		String msg = op.getMessage();
		assertEquals(msg, "Città troppo lunga!");
	}

	@Test
	public void test5() {
		OperationException op = assertThrows(OperationException.class, ()->{
			gb.AcquistaBiglietto("Napoli", "$%!", Date.valueOf("2023-06-19"), 2, 1, null);
		});
		String msg = op.getMessage();
		assertEquals(msg, "Città errata");
	}

	@Test
	public void test6() {
		assertThrows(IllegalArgumentException.class, ()->{
			gb.AcquistaBiglietto("Napoli", "Roma", Date.valueOf("20233-232-444"), 2, 1, null);
		});
		
	}

	@Test
	public void test7() {
		OperationException op = assertThrows(OperationException.class, ()->{
			gb.AcquistaBiglietto("Napoli", "Roma", Date.valueOf("2023-06-19"), -2, 1, null);
		});
		String msg = op.getMessage();
		assertEquals(msg, "Numero posti non valido");
	}

	@Test
	public void test8() {
		OperationException op = assertThrows(OperationException.class, ()->{
			gb.AcquistaBiglietto("Napoli", "Roma", Date.valueOf("2023-06-19"), 2, -1, null);
		});
		String msg = op.getMessage();
		assertEquals(msg, "Numero bagagli non valido");
	}
	
	@Test
	public void test9() {
		OperationException op = assertThrows(OperationException.class, ()->{
			gb.AcquistaBiglietto("Napoli", "Roma", Date.valueOf("2023-06-19"), 2, 3, null);
		});
		String msg = op.getMessage();
		assertEquals(msg, "Ogni persona può portare un solo bagaglio");
	}
	
	@Test
	public void test10() {
		boolean inputValido = false;

			String numeroCarta = "1234";

			try {
				Long.parseLong(numeroCarta);

				if (numeroCarta.length() == 16) {
					inputValido = true;
				}
			} catch (NumberFormatException e) {		
		}
		assert(!inputValido);
	}
	
	@Test
	public void test11() {
		boolean inputValido = false;
		

		String email = "progettois2023.libero.it";

		if (email.contains("@") && email.contains(".")) {
			inputValido = true;
		}
		assert(!inputValido);
	}
	
	@Test
	public void test12() {
		OperationException op = assertThrows(OperationException.class, ()->{
			gb.AcquistaBiglietto("Napoli", "Roma", Date.valueOf("2023-06-19"), 100, 1, null);
		});
		String msg = op.getMessage();
		assertEquals(msg, "Non c'è disponibilità");
	}

	@Test
	public void test13() {
		OperationException op = assertThrows(OperationException.class, ()->{
			gb.AcquistaBiglietto("Napoli", "Roma", Date.valueOf("2023-06-19"), 40, 40, null);
		});
		String msg = op.getMessage();
		assertEquals(msg, "Non c'è disponibilità");
	}
}
