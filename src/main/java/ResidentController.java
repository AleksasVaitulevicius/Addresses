
import java.util.ArrayList;
import java.util.List;
import spark.Request;
import spark.Response;

/**
 * @author Aleksas
 */
public class ResidentController {
    
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_NOT_FOUND = 404;

    public static Object AddModel(Request request, Response response, IResidentService service){
        ResidentModel model = JsonTransformer.fromJson(request.body(), ResidentModel.class);
        String errors = validateResident(model);
        if(errors.isEmpty() || errors == null)
        {
            service.add(model);
            return "OK";            
        }
        else
        {
            response.status(HTTP_BAD_REQUEST);
            return new ErrorMessage(errors);
        }
    }
    
    public static Object DeleteModel(Request request, Response response, IResidentService service){
        try {
            String id = request.params("id");
            service.delete(id);
            return "OK";
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti adreso su id: " + request.params("id"));
        }
    }
    
    public static Object GetModel(Request request, Response response, IResidentService service){
        try {
            String id = request.params("id");
            ResidentModel model = service.getSingle(id);
            if (model == null) {
                throw new Exception("Nepavyko rasti gyventojo su tokiu id");
            }
            return model;
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti adreso su id: " + request.params("id"));
        }
    }
    
    public static Object GetAll(Request request, Response response, IResidentService service){
        return service.getAll();
    }
    
    private static String validateResident(ResidentModel model)
    {
        String message = "";
        if (model.ID.isEmpty() || model.ID == null)
        {
            message += "ID negali buti tuscias\n";
        }
        else
        {
            if(!isNumeric(model.ID))
            {
                message += "ID privalo buti sudarytas is skaitmenu\n";
            }
        }
        if (model.name.isEmpty() || model.name == null)
        {
            message += "Vardas negali buti tuscias\n";
        }
        if (model.surname.isEmpty() || model.surname == null)
        {
            message += "Pavarde negali buti tuscia\n";
        }
        
        if (model.IDCode.isEmpty() || model.IDCode == null)
        {
            message += "Asmens kodas negali buti tuscias\n";
        }
        else
        {
            if(!isNumeric(model.IDCode))
            {
                message += "Asmens kodas privalo buti sudarytas is skaitmenu\n";
            }
        }
        
        if (model.AddressID.isEmpty() || model.IDCode == null)
        {
            message += "Adresas negali buti tuscias\n";
        }
        else
        {
            if(!isNumeric(model.AddressID))
            {
                message += "AdresoID privalo buti sudarytas is skaitmenu\n";
            }
        }
        return message;
    }
    
    public static Object Error(Request request, Response response, IResidentService service){
        response.status(HTTP_BAD_REQUEST);
        return new ErrorMessage("ID laukas negali buti tuscias");
    }
    
    public static boolean isNumeric(String str)  
    {  
      try  
      {  
        int i = Integer.parseInt(str);  
      }  
      catch(NumberFormatException nfe)  
      {  
        return false;  
      }  
      return true;  
    }
}
