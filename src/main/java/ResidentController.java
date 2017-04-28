
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
    
    private static String validateModel(ResidentModel model, IAddressService addressService){
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
        if(!validateAddress(model.addressID, addressService))
            return "Nera adreso su nurodytu ID:" + model.addressID;
        return null;
    }
    
    public static Object AddModel(Request request, Response response, IResidentService service, IAddressService addressService){
        ResidentModel model = JsonTransformer.fromJson(request.body(), ResidentModel.class);
        String error = validateModel(model, addressService);
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
    
    public static Object UpdateModel(Request request, Response response, IResidentService service, IAddressService addressService){
        ResidentModel model = JsonTransformer.fromJson(request.body(), ResidentModel.class);
        String error = validateModel(model, addressService);
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
    
    public static Object PatchModel(Request request, Response response, IResidentService service, IAddressService addressService){
        ResidentModel model = JsonTransformer.fromJson(request.body(), ResidentModel.class);
        if(!validateAddress(model.addressID, addressService)){
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nera adreso su nurodytu ID:" + model.addressID);
        }
        if(model.IDCode != null && validateNumerical(model.IDCode) != null)
            return new ErrorMessage("IDCode " + validateNumerical(model.IDCode));
        try {
            String id = request.params("id");
            service.patch(Integer.parseInt(id), model);
            return "OK";
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti gyventojo su id: " + request.params("id"));
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
    
    public static Object GetModelAddress(Request request, Response response, IResidentService residents, IAddressService addresses)
    {
        ResidentModel resident;
        try {
            String id = request.params("id");
            resident = residents.getSingle(Integer.parseInt(id));
            if (resident == null) {
                throw new Exception("Nepavyko rasti gyventojo su id: " + request.params("id"));
            }
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti gyventojo su id: " + request.params("id"));
        }
        
        try {
            String id = resident.addressID;
            AddressModel model = addresses.getSingle(Integer.parseInt(id));
            if (model == null) {
                throw new Exception();
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
    
    public static Object Error(Request request, Response response, IResidentService service){
        response.status(HTTP_BAD_REQUEST);
        return new ErrorMessage("ID laukas negali buti tuscias");
    }
    
    private static boolean validateAddress(String address, IAddressService service){
        try{
            if(service.getSingle(Integer.parseInt(address)) == null)
                return false;
        }
        catch(NumberFormatException exp){
            return false;
        }
        return true;
    }
}
