package entity;

import java.sql.Date;
import java.sql.Time;

import database.BigliettoDAO;
import exception.DAOException;
import exception.DBConnectionException;

public class EntityBiglietto {
    
    private int idBiglietto;
    private Date dataEmissione;
    private Time oraEmisione;
    private boolean presenzaBagaglio;
    private int idCorsa;
    private Integer idImpiegato;


    public EntityBiglietto(int idBiglietto, Date dataEmissione, Time oraEmissione, boolean presenzaBagaglio, int idCorsa, Integer idImpiegato){

        super();

        this.idBiglietto = idBiglietto;
        this.dataEmissione = dataEmissione;
        this.oraEmisione = oraEmissione;
        this.presenzaBagaglio = presenzaBagaglio;
        this.idCorsa = idCorsa;
        this.idImpiegato = idImpiegato;

    }

    public int getIdBiglietto(){

        return idBiglietto;

    }

    public void setIdBiglietto(int idBiglietto){

        this.idBiglietto = idBiglietto;

    }

    public Date getDataEmissione(){

        return dataEmissione;

    }

    public void setDataEmissione(Date dataEmissione){

        this.dataEmissione = dataEmissione;

    }

    public Time getOraEmissione(){

        return oraEmisione;

    }

    public void setOraEmissione(Time oraEmissione){

        this.oraEmisione = oraEmissione;

    }

    public boolean getPresenzaBagaglio(){

        return presenzaBagaglio;

    }

    public void setPresenzaBagaglio(boolean presenzaBagaglio){

        this.presenzaBagaglio = presenzaBagaglio;

    }

    public int getIdCorsa(){

        return idCorsa;
    }

    public void setIdCorsa(int idCorsa){

        this.idCorsa = idCorsa;

    }

    public int getIdImpiegato(){

        return idImpiegato;

    }

    public void setIdImpiegato(int idImpiegato){

        this.idImpiegato = idImpiegato;

    }

    public int saveBiglietto() throws DAOException, DBConnectionException {
    	     
    		
			return BigliettoDAO.createBiglietto(this.dataEmissione, this.oraEmisione, this.presenzaBagaglio, this.idCorsa, this.idImpiegato);
    }	
}

