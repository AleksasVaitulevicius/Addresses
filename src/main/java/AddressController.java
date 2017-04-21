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

    public static Object AddModel(Request request, Response response, IAddressService service){
        AddressModel model = JsonTransformer.fromJson(request.body(), AddressModel.class);
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
}
