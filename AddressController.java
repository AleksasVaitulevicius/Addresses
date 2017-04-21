import spark.Request;
import spark.Response;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aleksas
 */
public class AddressController {
    
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
    
    private static String validateModel(AddressModel model){
        if(model.ID == null || model.ID.equals(""))
            return "ID laukas turi buti uzpildytas";
        if(model.country == null || model.country.equals(""))
            return "Country laukas turi buti uzpildytas";
        if(model.city == null || model.city.equals(""))
            return "City laukas turi buti uzpildytas";
        if(model.street == null || model.street.equals(""))
            return "Street laukas negali buti tuscias";
        String result = validateNumerical(model.buildingNr);
        if(result != null)
            return "BuildingNr " + result;
        result = validateNumerical(model.flatNr);
        if(result != null)
            return "FlatNr " + result;
        result = validateNumerical(model.ZIPCode);
        if(result != null)
            return "ZIPCode " + result;
        return null;
    }
    
    public static Object AddModel(Request request, Response response, IAddressService service){
        AddressModel model = JsonTransformer.fromJson(request.body(), AddressModel.class);
        String error = validateModel(model);
        if(error != null){
            response.status(HTTP_BAD_REQUEST);
            return new ErrorMessage(error);
        }
        service.add(model);
        return "OK";
    }
    
    public static Object DeleteModel(Request request, Response response, IAddressService service){
        try {
            String id = request.params("id");
            service.delete(id);
            return "OK";
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti adreso su id: " + request.params("id"));
        }
    }
    
    public static Object GetModel(Request request, Response response, IAddressService service){
        try {
            String id = request.params("id");
            AddressModel model = service.getSingle(id);
            if (model == null) {
                throw new Exception("Nepavyko rasti");
            }
            return model;
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti adreso su id: " + request.params("id"));
        }
    }
    
    public static Object GetAll(Request request, Response response, IAddressService service){
        return service.getAll();
    }
    
    public static Object Error(Request request, Response response, IAddressService service){
        response.status(HTTP_BAD_REQUEST);
        return new ErrorMessage("Truksta ID lauko");
    }
}
