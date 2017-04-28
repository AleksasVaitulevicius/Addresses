
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
   
    private Map<Integer, AddressModel> DB = new HashMap<>();
    
    public AddressService()
    {
        DB.put(0, new AddressModel(0, "Lietuva", "Vilnius", "Vilniaus g.", "5", "5", "55555"));
        DB.put(1, new AddressModel(1, "Lietuva", "Vilnius", "Vilniaus g.", "5", "6", "555555"));
        DB.put(2, new AddressModel(2, "Lietuva", "Vilnius", "Didlaukio g.", "5", "7", "555555"));
        DB.put(3, new AddressModel(2, "Lietuva", "Vilnius", "Vilniaus g.", "2", "", "555555"));
        DB.put(4, new AddressModel(2, "Lietuva", "Vilnius", "Savanoriu pr.", "13", "", "555555"));
        DB.put(5, new AddressModel(2, "Lietuva", "Kaunas", "Saltuvos g.", "37", "", "555555"));
        DB.put(6, new AddressModel(2, "Lietuva", "Klaipeda", "Vytauto g.", "2", "", "555555"));
    }
    
    @Override
    public int add(AddressModel model){
        int ID = 0;
        while(DB.containsKey(ID))
            ID++;
        model.ID = ID;
        DB.put(ID, model);
        return ID;
    }
    
    @Override
    public void update(int id, AddressModel model) {
        model.ID = id;
        DB.put(id, model);
    }
    
    @Override
    public void patch(int id, AddressModel model){
        AddressModel oldModel = getSingle(id);
        if(model.country != null)
            oldModel.country = model.country;
        if(model.city != null)
            oldModel.city = model.city;
        if(model.street != null)
            oldModel.street = model.street;
        if(model.buildingNr != null)
            oldModel.buildingNr = model.buildingNr;
        if(model.flatNr != null)
            oldModel.flatNr = model.flatNr;
        if(model.ZIPCode != null)
            oldModel.ZIPCode = model.ZIPCode;
        DB.put(id, oldModel);
    }
    
    @Override
    public void delete(int id){
        DB.remove(id);
    }
    
    @Override
    public AddressModel getSingle(int id){
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
