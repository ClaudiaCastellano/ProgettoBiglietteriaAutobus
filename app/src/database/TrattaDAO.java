package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.EntityTratta;
import exception.DAOException;
import exception.DBConnectionException;

public class TrattaDAO {
	
	public static void createTratta(String cittàPartenza, String cittàArrivo, int idAutobus) throws DAOException, DBConnectionException{

        System.out.println("Inserimento nuova tratta");

        try {

            Connection conn = DBManager.getConnection();
            
            String query = "INSERT INTO Tratta (cittàPartenza, cittàArrivo, idAutobus) VALUES (?, ?, ?);";

            try{

                PreparedStatement stm = conn.prepareStatement(query);

                stm.setString(1, cittàPartenza);
                stm.setString(2, cittàArrivo);
                stm.setInt(3, idAutobus);
                
                stm.executeUpdate();

                System.out.println("Inserimento avvenuto correttamente");

            }catch(SQLException e){
                throw new DAOException("Errore scrittura tratta");
            }finally{
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore di connessione DB");
        }


    }
	
	
	public static EntityTratta readTratta(int idTratta) throws DBConnectionException, DAOException {
        
        System.out.println("Lettura tratta");

        EntityTratta et = null;

        try{

            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM Tratta WHERE (idTratta = ?);";

            try{
               
                PreparedStatement stm = conn.prepareStatement(query);

                stm.setInt(1, idTratta);

                ResultSet result = stm.executeQuery();

                if(result.next()){
                    et = new EntityTratta(result.getInt(1), result.getString(2), result.getString(3), result.getInt(4));
                    
                    System.out.println("Lettura avvenuta correttamente");
                }else {
                	System.out.println("Non è stata trovata una tratta corrispondente alle città inserite");
                }

                
            } catch (SQLException e){
                throw new DAOException("Errore lettura tratta");
            }finally{
                DBManager.closeConnection();
            }
            
        } catch (SQLException e) {
            throw new DBConnectionException("Errore di connessione DB");
        } 

        return et;

    }
	
	public static void updateTratta(EntityTratta et) throws DAOException, DBConnectionException{

        if (et==null ) 
            return;

		System.out.println("Aggiornamento tratta con ID: "+et.getIdTratta());
		
        try {

            Connection conn = DBManager.getConnection();

            String query = " UPDATE Tratta SET cittàPartenza = ?, cittàArrivo = ?, idAutobus = ? WHERE idTratta = ?;";

            try {

                PreparedStatement stm = conn.prepareStatement(query);

                stm.setString(1, et.getCittàPartenza());
                stm.setString(2, et.getCittàArrivo());
                stm.setInt(3, et.getIdAutobus());
                stm.setInt(4, et.getIdTratta());
                

                int result = stm.executeUpdate();
                
                if(result == 0) {
                	
                	System.out.println("Non è stata effettuata alcuna modifica");
                	
                }else {
                	
                	System.out.println("Aggiornamento avvenuto correttamente");
                }
			

            } catch (SQLException e) {
                throw new DAOException("Errore aggiornamento tratta");
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore di connessione DB");
        }

    }
	
	
	public static void deleteTratta(int id) throws DAOException, DBConnectionException{

        
		System.out.println("Cancellazione tratta con ID: "+id);

        try {
            
            Connection conn = DBManager.getConnection();
            
            String query = "DELETE FROM Tratta WHERE idTratta= ?;";

            try{
            	
            	PreparedStatement stm = conn.prepareStatement(query);
            	
            	stm.setInt(1, id);
            
            	int result = stm.executeUpdate();
            	
            	if(result == 0) {
            		
            		System.out.println("Non è stata trovata nessuna tratta con id: " + id);
            		
            	}else {
            		
            		 System.out.println("Cancellazione avvenuta correttamente.");
            	}
            		
                
            } catch (SQLException e) {
                throw new DAOException("Errore cancellazione tratta");
            }
    

        } catch (SQLException e) {
            throw new DBConnectionException("Errore di connessione DB");
        }
		
    }

 
    
}
