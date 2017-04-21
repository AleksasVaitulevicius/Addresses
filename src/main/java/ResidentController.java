
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
        service.add(model);
        return "OK";
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
                throw new Exception("Nepavyko rasti");
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
}
