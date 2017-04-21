import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static spark.Spark.*;

public class Comments {

    public static void main(String[] args) {
        IAddressService address = new AddressService();
        IResidentService resident = new ResidentService();

        port(1234);

        path("/address", () -> {
            get("", (req, res) -> {
                    return AddressController.GetAll(req, res, address);
                } , new JsonTransformer());

            get("/:id", (req, res) -> {
                return AddressController.GetModel(req, res, address);
            } , new JsonTransformer());

            post("", (req, res) -> {
                return AddressController.AddModel(req, res, address);
            } , new JsonTransformer());

            put("/:id", (req, res) -> {
                return AddressController.AddModel(req, res, address);
            } , new JsonTransformer());

            delete("/:id", (req, res) -> {
                return AddressController.DeleteModel(req, res, address);
            } , new JsonTransformer());

        });
        
        path("/resident", () -> {
            get("", (req, res) -> {
                    return ResidentController.GetAll(req, res, resident);
                } , new JsonTransformer());

            get("/:id", (req, res) -> {
                return ResidentController.GetModel(req, res, resident);
            } , new JsonTransformer());

            post("", (req, res) -> {
                return ResidentController.AddModel(req, res, resident);
            } , new JsonTransformer());

            put("/:id", (req, res) -> {
                return ResidentController.AddModel(req, res, resident);
            } , new JsonTransformer());

            delete("/:id", (req, res) -> {
                return ResidentController.DeleteModel(req, res, resident);
            } , new JsonTransformer());

        });

        exception(Exception.class, (e, req, res) -> {
            res.status(HTTP_BAD_REQUEST);
            JsonTransformer jsonTransformer = new JsonTransformer();
            res.body(jsonTransformer.render( new ErrorMessage(e) ));
        });

        after((req, rep) -> rep.type("application/json"));

    }
}
