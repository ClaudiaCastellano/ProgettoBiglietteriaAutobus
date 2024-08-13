package entity;

public class EntityAutobus {

    private int idAutobus;
    private int numeroPosti;
    private int numeroSpazi;
  
    public EntityAutobus(int idAutobus, int numeroPosti, int numeroSpazi) {
        super();

        this.idAutobus = idAutobus;
        this.numeroPosti = numeroPosti;
        this.numeroSpazi = numeroSpazi;
        
    }


    public int getIdAutobus(){

        return idAutobus;
    }

    public void setIdAutobus(int idAutobus){

        this.idAutobus = idAutobus;

    }

    public int getNumeroPosti(){

        return numeroPosti;

    }

    public void setNumeroPosti(int numeroPosti){

        this.numeroPosti = numeroPosti;

    }

    public int getNumeroSpazi(){

        return numeroSpazi;

    }

    public void setNumeroSpazi(int numeroSpazi){

        this.numeroSpazi = numeroSpazi;

    }

}
