package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.EntityAutobus;
import exception.DAOException;
import exception.DBConnectionException;

public class AutobusDAO {

	 public static void createAutobus(int numeroPosti, int numeroSpazi) throws DAOException, DBConnectionException{

	        System.out.println("Inserimento nuovo autobus");

	        try {

	            Connection conn = DBManager.getConnection();
	            
	            String query = "INSERT INTO Autobus (numeroPosti, numeroSpazi) VALUES (?, ?);";

	            try{

	                PreparedStatement stm = conn.prepareStatement(query);

	                stm.setInt(1, numeroPosti);
	                stm.setInt(2, numeroSpazi);
	               
	                stm.executeUpdate();

	                System.out.println("Inserimento avvenuto correttamente");

	            }catch(SQLException e){
	                throw new DAOException("Errore scrittura autobus");
	            }finally{
	                DBManager.closeConnection();
	            }

	        } catch (SQLException e) {
	            throw new DBConnectionException("Errore di connessione DB");
	        }


	    }
	 
	 
	 public static EntityAutobus readAutobus(int idAutobus) throws DBConnectionException, DAOException {
	        
	        System.out.println("Lettura autobus");

	        EntityAutobus ea = null;

	        try{

	            Connection conn = DBManager.getConnection();
	            String query = "SELECT * FROM Autobus WHERE idAutobus = ?;";

	            try{
	               
	                PreparedStatement stm = conn.prepareStatement(query);

	                stm.setInt(1, idAutobus);

	                ResultSet result = stm.executeQuery();

	                if(result.next()){
	                    ea = new EntityAutobus(idAutobus, result.getInt(2), result.getInt(3));
	                    
	                    System.out.println("Lettura avvenuta correttamente");
	                }else {
	                	System.out.println("Non è stata trovato un autobus corrispondente all'id richiesto");
	                }

	                
	            } catch (SQLException e){
	                throw new DAOException("Errore lettura autobus");
	            }finally{
	                DBManager.closeConnection();
	            }
	            
	        } catch (SQLException e) {
	            throw new DBConnectionException("Errore di connessione DB");
	        } 

	        return ea;

	    }

	 
	 public static void updateAutobus(EntityAutobus ea) throws DAOException, DBConnectionException{

	        if (ea==null ) 
	            return;

			System.out.println("Aggiornamento autobus con ID: "+ea.getIdAutobus());
			
	        try {

	            Connection conn = DBManager.getConnection();

	            String query = " UPDATE Autobus SET numeroPosti = ?, numeroSpazi = ? WHERE idAutobus = ?;";

	            try {

	                PreparedStatement stm = conn.prepareStatement(query);

	                stm.setInt(1, ea.getNumeroPosti());
	                stm.setInt(2, ea.getNumeroSpazi());
	                stm.setInt(6, ea.getIdAutobus());
	                

	                int result = stm.executeUpdate();
	                
	                if(result == 0) {
	                	
	                	System.out.println("Non è stata effettuata alcuna modifica");
	                	
	                }else {
	                	
	                	System.out.println("Aggiornamento avvenuto correttamente");
	                }
				

	            } catch (SQLException e) {
	                throw new DAOException("Errore aggiornamento autobus");
	            }

	        } catch (SQLException e) {
	            throw new DBConnectionException("Errore di connessione DB");
	        }

	    }
	 
	 public static void deleteAutobus(int id) throws DAOException, DBConnectionException{

	        
			System.out.println("Cancellazione autobus con ID: "+id);

	        try {
	            
	            Connection conn = DBManager.getConnection();
	            
	            String query = "DELETE FROM Autobus WHERE idAutobus= ?;";

	            try{
	            	
	            	PreparedStatement stm = conn.prepareStatement(query);
	            	
	            	stm.setInt(1, id);
	            
	            	int result = stm.executeUpdate();
	            	
	            	if(result == 0) {
	            		
	            		System.out.println("Non è stata trovato nessun autobus con id: " + id);
	            		
	            	}else {
	            		
	            		 System.out.println("Cancellazione avvenuta correttamente.");
	            	}
	            		
	                
	            } catch (SQLException e) {
	                throw new DAOException("Errore cancellazione autobus");
	            }
	    

	        } catch (SQLException e) {
	            throw new DBConnectionException("Errore di connessione DB");
	        }
			
	    }

    
}
