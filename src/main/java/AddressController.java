
import java.io.*;
import java.net.*;
import java.util.*;
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

    private static String validateNumerical(String value) {
        if (value == null || value.equals("")) {
            return "laukas negali buti tuscias";
        }
        try {
            Integer.parseInt(value);
        } catch (Exception exp) {
            return "laukas privalo buti skaicius";
        }
        return null;
    }

    private static String validateModel(AddressModel model) {
        if (model.country == null || model.country.equals("")) {
            return "country laukas turi buti uzpildytas";
        }
        if (model.city == null || model.city.equals("")) {
            return "city laukas turi buti uzpildytas";
        }
        if (model.street == null || model.street.equals("")) {
            return "street laukas negali buti tuscias";
        }
        String result = validateNumerical(model.buildingNr);
        if (result != null) {
            return "buildingNr " + result;
        }
        result = validateNumerical(model.flatNr);
        if (result != null) {
            return "flatNr " + result;
        }
        result = validateNumerical(model.ZIPCode);
        if (result != null) {
            return "ZIPCode " + result;
        }
        return null;
    }

    public static Object AddModel(Request request, Response response, IAddressService service) {
        AddressModel model = JsonTransformer.fromJson(request.body(), AddressModel.class);
        String error = validateModel(model);
        if (error != null) {
            response.status(HTTP_BAD_REQUEST);
            return new ErrorMessage(error);
        }
        return service.add(model);
    }

    public static Object UpdateModel(Request request, Response response, IAddressService service) {
        AddressModel model;
        try{
            model = JsonTransformer.fromJson(request.body(), AddressModel.class);
        }
        catch(Exception ex)
        {
            response.status(HTTP_BAD_REQUEST);
            return new ErrorMessage(ex.getMessage());
        }
        String error = validateModel(model);
        if (error != null) {
            response.status(HTTP_BAD_REQUEST);
            return new ErrorMessage(error);
        }
        try {
            String id = request.params("id");
            service.update(Integer.parseInt(id), model);
            return "OK";
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti adreso su ID: " + request.params("id"));
        }
    }

    public static Object PatchModel(Request request, Response response, IAddressService service) {
        AddressModel model = JsonTransformer.fromJson(request.body(), AddressModel.class);
        
        if(model.ZIPCode != null && validateNumerical(model.ZIPCode) != null)
            return new ErrorMessage("ZIPCode " + validateNumerical(model.ZIPCode));
        if(model.flatNr != null && validateNumerical(model.flatNr) != null)
            return new ErrorMessage("FlatNr " + validateNumerical(model.flatNr));
        if(model.buildingNr != null && validateNumerical(model.buildingNr) != null)
            return new ErrorMessage("BuildingNr " + validateNumerical(model.buildingNr));
        
        try {
            String id = request.params("id");
            service.patch(Integer.parseInt(id), model);
            return "OK";
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti adreso su ID: " + request.params("id"));
        }
    }

    public static Object DeleteModel(Request request, Response response, IAddressService service) {
        try {
            String id = request.params("id");
            service.delete(Integer.parseInt(id));
            return "OK";
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti adreso su ID: " + request.params("id"));
        }
    }

    public static Object GetModel(Request request, Response response, IAddressService service) {
        try {
            String id = request.params("id");
            AddressModel model = service.getSingle(Integer.parseInt(id));
            if (model == null) {
                throw new Exception();
            }
            return model;
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti adreso su ID: " + request.params("id"));
        }
    }

    public static Object GetCompaniesByAddress(Request request, Response response, IAddressService service, CompaniesService compService) {
        AddressModel model;
        List<Company> allCompanies;
        List<Company> companies = new ArrayList();
        try {
            String id = request.params("id");
            model = service.getSingle(Integer.parseInt(id));
            if (model == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti adreso su ID: " + request.params("id"));
        }
        try {
            allCompanies = compService.getAll();
            if (allCompanies == null) {
                throw new Exception();
            }
        } catch (Exception ex) {
            return new ErrorMessage("Klaida naudojant paslauga \"companies\"");
        }
        for(Company company:allCompanies)
        {
            if(model.city.equals(company.city) &&
               model.street.equals(company.address.substring(0, company.address.lastIndexOf(' '))) &&
               model.buildingNr.equals(company.address.substring(company.address.lastIndexOf(' ') + 1)))
            {
                companies.add(company);
            }
        }
        
        return companies;
    }
    
    public static Object GetCompaniesByCity(Request request, Response response, IAddressService service, CompaniesService compService) {

        List<Company> companies = new ArrayList();
        try {
            companies = compService.getAll(request.params("city"));
        }catch(RuntimeException ex)
        {
            return companies;
        }
        catch (Exception ex) {
            return new ErrorMessage("Klaida naudojant paslauga \"companies\"");
        }
        
        return companies;
    }
    
    public static Object GetResidentsByCity(Request request, Response response, IAddressService addressService, IResidentService residentService) {
        
        String city = request.params("city");
        List<ResidentModel> residents = new ArrayList();
        for(ResidentModel res: residentService.getAll())
        {
            AddressModel address = addressService.getSingle(Integer.parseInt(res.addressID));
            if (address.city.equals(city))
            {
                residents.add(res);
            }
        }
        return residents;
        
    }
    
    public static Object GetResidents(Request request, Response response, IAddressService addressService, IResidentService residentService) {
        AddressModel address;
        try {
            String id = request.params("id");
            address = addressService.getSingle(Integer.parseInt(id));
            if (address == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti adreso su ID: " + request.params("id"));
        }
        List<ResidentModel> residents = new ArrayList();
        for(ResidentModel res: residentService.getAll())
        {
            if (Integer.parseInt(res.addressID) == address.ID)
            {
                residents.add(res);
            }
        }
        return residents;
    }
    
    public static Object GetAll(Request request, Response response, IAddressService service) {
        return service.getAll();
    }

    public static Object Error(Request request, Response response, IAddressService service) {
        response.status(HTTP_BAD_REQUEST);
        return new ErrorMessage("Truksta ID lauko");
    }
}
