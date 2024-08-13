package entity;

public class EntityTratta {
    
    private int idTratta;
    private String cittàPartenza;
    private String cittàArrivo;
    private int idAutobus;
    
    public EntityTratta(int idTratta, String cittàPartenza, String cittàArrivo, int idAutobus){

        super();

        this.idTratta = idTratta;
        this.cittàPartenza = cittàPartenza;
        this.cittàArrivo = cittàArrivo;
        this.idAutobus = idAutobus;

    }

    public int getIdTratta(){

        return idTratta;

    }

    public void setIdTratta(int idTratta){

        this.idTratta = idTratta;

    }

    public String getCittàPartenza(){

        return cittàPartenza;

    }

    public void setCittàPartenza(String cittàPartenza){

        this.cittàPartenza = cittàPartenza;

    }

    public String getCittàArrivo(){

        return cittàArrivo;

    }

    public void setCittàArrivo(String cittàArrivo){

        this.cittàArrivo = cittàArrivo;

    }

    public int getIdAutobus(){

        return idAutobus;
    }

    public void setIdAutobus(int idAutobus){

        this.idAutobus = idAutobus;

    }



}
