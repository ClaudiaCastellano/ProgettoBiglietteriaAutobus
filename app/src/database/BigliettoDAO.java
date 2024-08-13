package database;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

import entity.EntityBiglietto;
import exception.DAOException;
import exception.DBConnectionException;

public class BigliettoDAO {
	
	public static int createBiglietto(Date dataEmissione, Time oraEmissione, boolean presenzaBagaglio, int idCorsa, Integer idImpiegato) throws DAOException, DBConnectionException{

        System.out.println("Inserimento nuovo biglietto");
        
        int chiaveGenerata = 0;

        try {

            Connection conn = DBManager.getConnection();
            
            String query = "INSERT INTO Biglietto (dataEmissione, oraEmissione, presenzaBagaglio, idCorsa, idImpiegato ) VALUES (?, ?, ?, ?, ?);";

            try{

                PreparedStatement stm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                stm.setDate(1, dataEmissione);
                stm.setTime(2, oraEmissione);
                stm.setBoolean(3, presenzaBagaglio);
                stm.setInt(4, idCorsa);
                
                if(idImpiegato == null) {
                	
                	stm.setNull(5, java.sql.Types.INTEGER);
                	
                }else {
                	stm.setInt(5, idImpiegato);
                }
                
                

                stm.executeUpdate();
                
                ResultSet generatedKeys = stm.getGeneratedKeys();

                if (generatedKeys.next()) {
                     chiaveGenerata = generatedKeys.getInt(1);
                }

                generatedKeys.close();

                System.out.println("Inserimento avvenuto correttamente");
               

            }catch(SQLException e){
            	e.printStackTrace();
                throw new DAOException("Errore scrittura biglietto");
            }finally{
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore di connessione DB");
        }

        return chiaveGenerata;

    }

	
	public static EntityBiglietto readBiglietto(int idBiglietto) throws DBConnectionException, DAOException {
        
        System.out.println("Lettura biglietto");

        EntityBiglietto eb = null;

        try{

            Connection conn = DBManager.getConnection();

            try{
                String query = "SELECT * FROM Biglietto WHERE idBiglietto = ?;";

                PreparedStatement stm = conn.prepareStatement(query);

                stm.setInt(1, idBiglietto);

                ResultSet result = stm.executeQuery();

                if(result.next()){
                    eb = new EntityBiglietto(idBiglietto, result.getDate(2), result.getTime(3), result.getBoolean(4), result.getInt(5), result.getInt(6));
                    
                    System.out.println("Lettura avvenuta correttamente ");
                }else {
                	System.out.println("Non è stata trovato un biglietto corrispondente all'id inserito");
                }

                

            } catch (SQLException e){
                throw new DAOException("Errore lettura biglietto");
            }finally{
                DBManager.closeConnection();
            }
            
        } catch (SQLException e) {
            throw new DBConnectionException("Errore di connessione DB");
        } 

        return eb;

    }



	public static void updateBiglietto(EntityBiglietto eb) throws DAOException, DBConnectionException{
	
	    if (eb==null ) 
	        return;
	
		System.out.println("Aggiornamento biglietto con ID: "+eb.getIdCorsa());
		
	    try {
	
	        Connection conn = DBManager.getConnection();
	
	        String query = " UPDATE Biglietto SET dataEmissione = ?, oraEmissione = ?, presenzaBagaglio = ?, idCorsa = ?, idImpiegato = ? WHERE idBiglietto = ?;";
	
	        try {
	
	            PreparedStatement stm = conn.prepareStatement(query);
	
	            stm.setDate(1, eb.getDataEmissione());
	            stm.setTime(2, eb.getOraEmissione());
	            stm.setBoolean(3, eb.getPresenzaBagaglio());
	            stm.setInt(4, eb.getIdCorsa());
	            stm.setInt(5, eb.getIdImpiegato());
	            stm.setInt(6, eb.getIdBiglietto());
	
	            int result = stm.executeUpdate();
	            
	            if(result == 0) {
	            	
	            	System.out.println("Non è stata effettuata alcuna modifica");
	            	
	            }else {
	            	
	            	System.out.println("Aggiornamento avvenuto correttamente");
	            }
			
	
	        } catch (SQLException e) {
	            throw new DAOException("Errore aggiornamento biglietto");
	        }
	
	    } catch (SQLException e) {
	        throw new DBConnectionException("Errore di connessione DB");
	    }
	
	}
	
	
public static void deleteBiglietto(int id) throws DAOException, DBConnectionException{

        
		System.out.println("Cancellazione biglietto con ID: "+id);

        try {
            
            Connection conn = DBManager.getConnection();
            
            String query = "DELETE FROM Biglietto WHERE idBiglietto= ?;";

            try{
            	
            	PreparedStatement stm = conn.prepareStatement(query);
            	
            	stm.setInt(1, id);
            
            	int result = stm.executeUpdate();
            	
            	if(result == 0) {
            		
            		System.out.println("Non è stata trovata il biglietto con id: " + id);
            		
            	}else {
            		
            		 System.out.println("Cancellazione avvenuta correttamente.");
            	}
            		
                
            } catch (SQLException e) {
                throw new DAOException("Errore cancellazione biglietto");
            }
    

        } catch (SQLException e) {
            throw new DBConnectionException("Errore di connessione DB");
        }
		
    }


	public static void readBigliettiReport() throws DAOException, DBConnectionException{
		
		LocalDate currentDate = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        LocalDate startOfWeek = currentDate.with(weekFields.dayOfWeek(), 1);
        LocalDate endOfWeek = currentDate.with(weekFields.dayOfWeek(), 7);

		
		try {
			
			Connection conn = DBManager.getConnection();
			
			String query = "SELECT COUNT(*) AS \"NUMERO BIGLIETTI VENDUTI\", T.idTratta, T.cittàPartenza, T.cittàArrivo\n"
					+ "FROM (biglietto B JOIN corsa C ON B.idcorsa=C.idCorsa) JOIN TRATTA T ON C.idTratta = T.idTratta\n"
					+ "WHERE ISO_WEEK(B.dataEmissione) = ISO_WEEK(CURRENT_DATE())\n"
					+ "GROUP BY T.idTratta, T.cittàPartenza, T.cittàArrivo ORDER BY T.cittàPartenza";
			
			try {
				
				Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				ResultSet result = stm.executeQuery(query);
				
				FileWriter fw = new FileWriter("./report.txt", false);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);
				
				pw.println("Report biglietti venduti nella settimana dal " + startOfWeek + " al " + endOfWeek);
				//pw.println(" " + "NumeroBiglietti " + " idTratta " + "CittàPartenza" + " CittàArrivo" );
				
				
				if(!result.next()) {
					
					pw.println("Non sono stati venduti biglietti in questa settimana");
				}
				
				result.beforeFirst();
				
				while(result.next()) {
					
					pw.println(" "+result.getInt(1)+ " " + result.getInt(2) + " " + result.getString(3) + " " + result.getString(4));
				}
				
				pw.close();
				bw.close();
				fw.close();
				
				
			}catch(SQLException | IOException e) {
				throw new DAOException("Errore lettura biglietti report");
			}
			
		} catch (SQLException e) {
			throw new DBConnectionException("Errore di connessione DB");
		}
		
	}
	
	public static int readNumBigliettiVenduti(int idCorsa) throws DAOException, DBConnectionException {
		
		int numeroBiglietti = 0;
		
		try {
			
			Connection conn = DBManager.getConnection();
			
			String query = "SELECT COUNT (*) AS \"NumeroBigliettiVenduti\" FROM Biglietto WHERE idCorsa = ?;";
			
			try {
				
				PreparedStatement stm = conn.prepareStatement(query);
				
				stm.setInt(1, idCorsa);
				
				ResultSet result = stm.executeQuery();
				
				if(result.next()) {
					numeroBiglietti = result.getInt(1);
				}	
				
				
			}catch(SQLException e) {
				throw new DAOException("Errore lettura biglietti venduti");
			}
			
		} catch (SQLException e) {
			throw new DBConnectionException("Errore di connessione DB");
		}
		
		return numeroBiglietti;
	}
	
public static int readNumBagagli(int idCorsa) throws DAOException, DBConnectionException {
		
		int numeroBagagli = 0;
		
		try {
			
			Connection conn = DBManager.getConnection();
			
			String query = "SELECT COUNT (*) AS \"NumeroBagagli\" FROM Biglietto WHERE idCorsa = ? AND presenzaBagaglio = true ;";
			
			try {
				
				PreparedStatement stm = conn.prepareStatement(query);
				
				stm.setInt(1, idCorsa);
				
				ResultSet result = stm.executeQuery();
				
				if(result.next()) {
					numeroBagagli = result.getInt(1);
				}	
				
				
			}catch(SQLException e) {
				e.printStackTrace();
				throw new DAOException("Errore lettura biglietti venduti");
			}
			
		} catch (SQLException e) {
			throw new DBConnectionException("Errore di connessione DB");
		}
		
		return numeroBagagli;
	}

    
}
