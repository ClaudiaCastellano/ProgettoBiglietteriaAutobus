package boundary;


import control.GestioneBiglietteria;
import exception.OperationException;

public class BoundaryTempo {
	
	public static void main(String[] args) {
		
		GestioneBiglietteria gb = GestioneBiglietteria.getInstance();
		
		try {
			gb.GeneraReport();
			
		} catch (OperationException e) {
			e.printStackTrace();
		}
		
	}

}
