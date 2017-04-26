
import java.io.Serializable;

/**
 * @author Aleksas
 */
public class ResidentModel {
    public int ID;
    public String name;
    public String surname;
    public String IDCode;
    public String addressID;
    
    public ResidentModel(int ID, String name, String surname, String IDCode, String AddressID){
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.IDCode = IDCode;
        this.addressID = AddressID;
    }
    
    @Override
    public String toString(){
        return ID + ":" + name + " " + surname + " " + IDCode + " " + addressID;
    }
}
