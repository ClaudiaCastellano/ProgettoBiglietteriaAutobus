package entity;

import java.sql.Date;
import java.sql.Time;

public class EntityCorsa {

    private int idCorsa;
    private Date data;
    private Time orarioPartenza;
    private Time orarioArrivo;
    private float prezzoBiglietto;
    private String tipo;
    private int idTratta;

    public EntityCorsa(int idCorsa, Date data, Time orarioPartenza, Time orarioArrivo, float prezzoBiglietto, String tipo, int idTratta){

        super();

        this.idCorsa = idCorsa;
        this.data = data;
        this.orarioPartenza = orarioPartenza;
        this.orarioArrivo = orarioArrivo;
        this.prezzoBiglietto = prezzoBiglietto;
        this.tipo = tipo;
        this.idTratta = idTratta;

    }


    public int getIdCorsa(){

        return idCorsa;
    }

    public void setIdCorsa(int idCorsa){

        this.idCorsa = idCorsa;

    }

    public Date getData(){

        return data;

    }

    public void setData(Date data){

        this.data = data;

    }
    
    public Time getOrarioPartenza(){

        return orarioPartenza;

    }

    public void setOrarioPartenza(Time orarioPartenza){

        this.orarioPartenza = orarioPartenza;

    }

    public Time getOrarioArrivo(){

        return orarioArrivo;

    }

    public void setOrarioArrivo(Time orarioArrivo){

        this.orarioArrivo = orarioArrivo;

    }

    public float getPrezzoBiglietto(){

        return prezzoBiglietto;

    }

    public void setPrezzoBiglietto(float prezzoBiglietto){

        this.prezzoBiglietto = prezzoBiglietto;

    }

    public String getTipo(){

        return tipo;

    }

    public void setTipo(String tipo){

        this.tipo = tipo;

    }

    public int getIdTratta(){

        return idTratta;

    }

    public void setIdTratta(int idTratta){

        this.idTratta = idTratta;

    }


}
