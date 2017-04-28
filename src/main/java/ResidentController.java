
import spark.Request;
import spark.Response;

/**
 * @author Aleksas
 */
public class ResidentController {
    
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_NOT_FOUND = 404;
    
    private static String validateNumerical(String value){
        if(value == null || value.equals(""))
            return "laukas negali buti tuscias";
        try{
            Integer.parseInt(value);
        }
        catch(Exception exp){
            return "laukas privalo buti skaicius";
        }
        return null;
    }
    
    private static String validateModel(ResidentModel model){
        if(model.name == null || model.name.equals(""))
            return "Name laukas turi buti uzpildytas";
        if(model.surname == null || model.surname.equals(""))
            return "Surname laukas turi buti uzpildytas";
        String result = validateNumerical(model.IDCode);
        if(result != null)
            return "IDCode " + result;
        result = validateNumerical(model.addressID);
        if(result != null)
            return "AddressID " + result;
        return null;
    }
    
    public static Object AddModel(Request request, Response response, IResidentService service){
        ResidentModel model = JsonTransformer.fromJson(request.body(), ResidentModel.class);
        String error = validateModel(model);
        if(error == null)
        {
            return service.add(model);     
        }
        else
        {
            response.status(HTTP_BAD_REQUEST);
            return new ErrorMessage(error);
        }
    }
    
    public static Object UpdateModel(Request request, Response response, IResidentService service){
        ResidentModel model = JsonTransformer.fromJson(request.body(), ResidentModel.class);
        String error = validateModel(model);
        if(error != null){
            response.status(HTTP_BAD_REQUEST);
            return new ErrorMessage(error);
        }
        try {
            String id = request.params("id");
            service.update(Integer.parseInt(id), model);
            return "OK";
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti gyventojo su id: " + request.params("id"));
        }
    }
    
    public static Object PatchModel(Request request, Response response, IResidentService service){
        ResidentModel model = JsonTransformer.fromJson(request.body(), ResidentModel.class);
        try {
            String id = request.params("id");
            service.patch(Integer.parseInt(id), model);
            return "OK";
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti adreso su id: " + request.params("id"));
        }
    }
    
    public static Object DeleteModel(Request request, Response response, IResidentService service){
        try {
            String id = request.params("id");
            service.delete(Integer.parseInt(id));
            return "OK";
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti adreso su id: " + request.params("id"));
        }
    }
    
    public static Object GetModel(Request request, Response response, IResidentService service){
        try {
            String id = request.params("id");
            ResidentModel model = service.getSingle(Integer.parseInt(id));
            if (model == null) {
                throw new Exception("Nepavyko rasti gyventojo su id: " + request.params("id"));
            }
            return model;
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti gyventojo su id: " + request.params("id"));
        }
    }
    
    public static Object GetAll(Request request, Response response, IResidentService service){
        return service.getAll();
    }
    
    public static Object Error(Request request, Response response, IResidentService service){
        response.status(HTTP_BAD_REQUEST);
        return new ErrorMessage("ID laukas negali buti tuscias");
    }
    
    //adress by resident, residents by city
}
