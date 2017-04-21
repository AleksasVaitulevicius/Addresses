
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aleksas
 */
public class AddressService implements IAddressService{
   
    Map<String, AddressModel> DB = new HashMap<>();
    
    public AddressService()
    {
        AddressModel m;
        m = new AddressModel("1", "Lietuva", "Vilnius", "Vilniaus g.", "5", "5", "55555");
        DB.put(m.ID, m);
        m = new AddressModel("2", "Lietuva", "Vilnius", "Vilniaus g.", "5", "6", "555555");
        DB.put(m.ID, m);
        m = new AddressModel("3", "Lietuva", "Vilnius", "Didlaukio g.", "5", "7", "555555");
        DB.put(m.ID, m);
    }
    
    @Override
    public void add(AddressModel model){
        DB.put(model.ID, model);
    }
    
    @Override
    public void delete(String id){
        DB.remove(id);
    }
    
    @Override
    public AddressModel getSingle(String id){
        return DB.get(id);
    }

    @Override
    public List<AddressModel> getAll(){
        Collection<AddressModel> values = DB.values();
        List<AddressModel> models = new ArrayList<>();
        for(AddressModel model : values){
            models.add(model);
        }
        return models;
    }
}
