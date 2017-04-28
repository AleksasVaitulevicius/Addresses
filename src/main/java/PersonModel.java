/**
 * @author Aleksas
 */
public class PersonModel {
    public int id;
    public String name;
    public String surname;
    public String gender;
    public String address;
    
    @Override
    public String toString(){
        return "" + id + ":" + name + " " + surname + " " + gender + " " + address;
    }
}
