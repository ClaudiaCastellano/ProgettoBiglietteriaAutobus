package entity;

public class EntityImpiegato {

    private Integer idImpiegato;
    private String password;

    public EntityImpiegato(int idImpiegato, String password){

        super();

        this.idImpiegato = idImpiegato;
        this.password = password;

    }

    public int getIdImpiegato(){

        return idImpiegato;

    }

    public void setIdImpiegato(int idImpiegato){

        this.idImpiegato = idImpiegato;

    }

    public String getPassword(){

        return password;

    }

    public void setPassword(String password){

        this.password = password;

    }

    
}
