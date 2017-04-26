
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Aleksas
 */
public class ResidentService implements IResidentService {
    
    private Map<Integer, ResidentModel> DB = new HashMap<>();
    
    
    public ResidentService()
    {
        DB.put(1, new ResidentModel(1, "Petras", "Petraitis", "11111111", "1"));
        DB.put(2, new ResidentModel(2, "Jonas", "Jonaitis", "22222222", "2"));
    }
    
    @Override
    public int add(ResidentModel model){
        int ID = 0;
        while(DB.containsKey(ID))
            ID++;
        model.ID = ID;
        DB.put(ID, model);
        return ID;
    }
    
    @Override
    public void update(int id, ResidentModel model){
        model.ID = id;
        DB.put(id, model);
    }
    
    @Override
    public void patch(int id, ResidentModel model){
        ResidentModel oldModel = getSingle(id);
        if(model.name != null)
            oldModel.name = model.name;
        if(model.surname != null)
            oldModel.surname = model.surname;
        if(model.IDCode != null)
            oldModel.IDCode = model.IDCode;
        if(model.addressID != null)
            oldModel.addressID = model.addressID;
        DB.put(id, model);
    }
    
    @Override
    public void delete(int id){
        DB.remove(id);
    }
    
    @Override
    public ResidentModel getSingle(int id){
        return DB.get(id);
    }

    @Override
    public List<ResidentModel> getAll(){
        Collection<ResidentModel> values = DB.values();
        List<ResidentModel> models = new ArrayList<>();
        for(ResidentModel model : values){
            models.add(model);
        }
        return models;
    }
}
