package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Date;

import entity.EntityCorsa;
import exception.DAOException;
import exception.DBConnectionException;

public class CorsaDAO {

    public static void createCorsa(Date data, Time orarioPartenza, Time orarioArrivo, float prezzoBiglietto, String tipo, int idTratta ) throws DAOException, DBConnectionException{

        System.out.println("Inserimento nuova corsa");

        try {

            Connection conn = DBManager.getConnection();
            
            String query = "INSERT INTO Corsa (data, orarioPartenza, orarioArrivo, prezzoBiglietto, tipo, idTratta) VALUES (?, ?, ?, ?, ?, ?);";

            try{

                PreparedStatement stm = conn.prepareStatement(query);

                stm.setDate(1, data);
                stm.setTime(2, orarioPartenza);
                stm.setTime(3, orarioArrivo);
                stm.setFloat(4, prezzoBiglietto);
                stm.setString(5, tipo);
                stm.setInt(6, idTratta);

                stm.executeUpdate();

                System.out.println("Inserimento avvenuto correttamente");

            }catch(SQLException e){
                throw new DAOException("Errore scrittura corsa");
            }finally{
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore di connessione DB");
        }


    }


    public static EntityCorsa readCorsa(String cittàPartenza, String cittàArrivo, Date data) throws DBConnectionException, DAOException {
        
        System.out.println("Lettura corsa");

        EntityCorsa ec = null;

        try{

            Connection conn = DBManager.getConnection();

            try{
                String query = "SELECT * FROM Corsa C JOIN Tratta T ON (C.idTratta = T.idTratta) WHERE (T.cittàPartenza = ? AND T.cittàArrivo = ? AND C.data = ? AND C.tipo = 'andata') OR (T.cittàPartenza = ? AND T.cittàArrivo = ? AND C.data = ? AND C.tipo = 'ritorno') ;";

                PreparedStatement stm = conn.prepareStatement(query);

                stm.setString(1, cittàPartenza);
                stm.setString(2, cittàArrivo);
                stm.setDate(3, data);
                stm.setString(4, cittàArrivo);
                stm.setString(5, cittàPartenza);
                stm.setDate(6, data);
                

                ResultSet result = stm.executeQuery();

                if(result.next()){
                    ec = new EntityCorsa(result.getInt(1), data, result.getTime(3), result.getTime(4), result.getFloat(5), result.getString(6), result.getInt(7));
                    System.out.println("Lettura avvenuta correttamente, idCorsa: " + ec.getIdCorsa());
                }else {
                	System.out.println("Non è stata trovata una corsa corrispondente ai parametri richiesti");
                }

                

            } catch (SQLException e){
                throw new DAOException("Errore lettura corsa");
            }finally{
                DBManager.closeConnection();
            }
            
        } catch (SQLException e) {
            throw new DBConnectionException("Errore di connessione DB");
        } 

        return ec;

    }
    
public static EntityCorsa readCorsaId(int idCorsa) throws DBConnectionException, DAOException {
        
        System.out.println("Lettura corsa");

        EntityCorsa ec = null;

        try{

            Connection conn = DBManager.getConnection();

            try{
                String query = "SELECT * FROM Corsa WHERE idCorsa = ? ;";

                PreparedStatement stm = conn.prepareStatement(query);

                stm.setInt(1, idCorsa);

                ResultSet result = stm.executeQuery();

                if(result.next()){
                    ec = new EntityCorsa(result.getInt(1), result.getDate(2), result.getTime(3), result.getTime(4), result.getFloat(5), result.getString(6), result.getInt(7));
                    System.out.println("Lettura avvenuta correttamente");
                }else {
                	System.out.println("Non è stata trovata una corsa corrispondente ai parametri richiesti");
                }
               
            } catch (SQLException e){
                throw new DAOException("Errore lettura corsa");
            }finally{
                DBManager.closeConnection();
            }
            
        } catch (SQLException e) {
            throw new DBConnectionException("Errore di connessione DB");
        } 

        return ec;

    }


    public static void updateCorsa(EntityCorsa ec) throws DAOException, DBConnectionException{

        if (ec==null ) 
            return;

		System.out.println("Aggiornamento corsa con ID: "+ec.getIdCorsa());
		
        try {

            Connection conn = DBManager.getConnection();

            String query = " UPDATE Corsa SET Data = ?, orarioPartenza = ?, orarioArrivo = ?, prezzoBiglietto = ?, tipo = ?, idTratta = ? WHERE idCorsa = ?;";

            try {

                PreparedStatement stm = conn.prepareStatement(query);

                stm.setDate(1, ec.getData());
                stm.setTime(2, ec.getOrarioPartenza());
                stm.setTime(3, ec.getOrarioArrivo());
                stm.setFloat(4, ec.getPrezzoBiglietto());
                stm.setString(5, ec.getTipo());
                stm.setInt(6, ec.getIdTratta());
                stm.setInt(7, ec.getIdCorsa());

                int result = stm.executeUpdate();
                
                if(result == 0) {
                	
                	System.out.println("Non è stata effettuata alcuna modifica");
                	
                }else {
                	
                	System.out.println("Aggiornamento avvenuto correttamente");
                }
			

            } catch (SQLException e) {
                throw new DAOException("Errore aggiornamento corsa");
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore di connessione DB");
        }

    }

    public static void deleteCorsa(int id) throws DAOException, DBConnectionException{

        
		System.out.println("Cancellazione corsa con ID: "+id);

        try {
            
            Connection conn = DBManager.getConnection();
            
            String query = "DELETE FROM Corsa WHERE idCorsa= ?;";

            try{
            	
            	PreparedStatement stm = conn.prepareStatement(query);
            	
            	stm.setInt(1, id);
            
            	int result = stm.executeUpdate();
            	
            	if(result == 0) {
            		
            		System.out.println("Non è stata trovata la corsa con id: " + id);
            		
            	}else {
            		
            		 System.out.println("Cancellazione avvenuta correttamente.");
            	}
            		
                
            } catch (SQLException e) {
                throw new DAOException("Errore cancellazione corsa");
            }
    

        } catch (SQLException e) {
            throw new DBConnectionException("Errore di connessione DB");
        }
		
    }

}
