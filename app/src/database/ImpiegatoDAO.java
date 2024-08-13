package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.EntityImpiegato;
import exception.DAOException;
import exception.DBConnectionException;

public class ImpiegatoDAO {
	
	public static void createImpiegato(String password) throws DAOException, DBConnectionException{

        System.out.println("Inserimento nuovo impiegato");

        try {

            Connection conn = DBManager.getConnection();
            
            String query = "INSERT INTO Impiegato (password) VALUES (?);";

            try{

                PreparedStatement stm = conn.prepareStatement(query);

                stm.setString(1, password);
                
                stm.executeUpdate();

                System.out.println("Inserimento avvenuto correttamente");

            }catch(SQLException e){
                throw new DAOException("Errore scrittura impiegato");
            }finally{
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore di connessione DB");
        }
	}
	
	
public static EntityImpiegato readImpiegato(int idImpiegato) throws DBConnectionException, DAOException {
        
        System.out.println("Lettura impiegato");

        EntityImpiegato ei = null;

        try{

            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM Impiegato WHERE idImpiegato = ?;";
            try{
               
                PreparedStatement stm = conn.prepareStatement(query);

                stm.setInt(1, idImpiegato);
             

                ResultSet result = stm.executeQuery();

                if(result.next()){
                    ei = new EntityImpiegato(idImpiegato, result.getString(2));
                    
                    System.out.println("Lettura avvenuta correttamente");
                }else {
                	System.out.println("Non è stata trovato un impiegato corrispondente all' id inserito");
                }

                
            } catch (SQLException e){
                throw new DAOException("Errore lettura impiegato");
            }finally{
                DBManager.closeConnection();
            }
            
        } catch (SQLException e) {
            throw new DBConnectionException("Errore di connessione DB");
        } 

        return ei;

    }


public static void updateImpiegato(EntityImpiegato ei) throws DAOException, DBConnectionException{

    if (ei==null ) 
        return;

	System.out.println("Aggiornamento impiegato con ID: "+ei.getIdImpiegato());
	
    try {

        Connection conn = DBManager.getConnection();

        String query = " UPDATE Impiegato SET password = ? WHERE idImpiegato = ?;";

        try {

            PreparedStatement stm = conn.prepareStatement(query);

            stm.setString(1, ei.getPassword());
            

            int result = stm.executeUpdate();
            
            if(result == 0) {
            	
            	System.out.println("Non è stata effettuata alcuna modifica");
            	
            }else {
            	
            	System.out.println("Aggiornamento avvenuto correttamente");
            }
		

        } catch (SQLException e) {
            throw new DAOException("Errore aggiornamento impiegato");
        }

    } catch (SQLException e) {
        throw new DBConnectionException("Errore di connessione DB");
    }

}


public static void deleteImpiegato(int id) throws DAOException, DBConnectionException{

    
	System.out.println("Cancellazione impiegato con ID: "+id);

    try {
        
        Connection conn = DBManager.getConnection();
        
        String query = "DELETE FROM Impiegato WHERE idImpiegato= ?;";

        try{
        	
        	PreparedStatement stm = conn.prepareStatement(query);
        	
        	stm.setInt(1, id);
        
        	int result = stm.executeUpdate();
        	
        	if(result == 0) {
        		
        		System.out.println("Non è stata trovata nessuna impiegato con id: " + id);
        		
        	}else {
        		
        		 System.out.println("Cancellazione avvenuta correttamente.");
        	}
        		
            
        } catch (SQLException e) {
            throw new DAOException("Errore cancellazione impiegato");
        }


    } catch (SQLException e) {
        throw new DBConnectionException("Errore di connessione DB");
    }
	
}


    
}
