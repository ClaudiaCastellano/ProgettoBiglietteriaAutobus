package test;

import static org.junit.Assert.*;

import org.junit.Test;

import control.GestioneBiglietteria;

public class GeneraReportTest {
	
	GestioneBiglietteria gb = GestioneBiglietteria.getInstance();

	@Test
	public void test1() {
		try {
			gb.GeneraReport();
		}catch(Exception e) {fail();}
	}

}
