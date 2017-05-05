
import com.google.gson.internal.LinkedTreeMap;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * @author Laimonas
 */
public class CompaniesService {

    public URL url;

    CompaniesService() {
        try {
            url = new URL("http://localhost:8888");
        } catch (Exception e) {
        }
    }

    public List<Company> getAll() throws Exception {

        URL url = new URL(this.url, "/companies");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        List<Company> companies = new ArrayList (Arrays.asList(JsonTransformer.fromJson(new InputStreamReader(conn.getInputStream()), Company[].class)));
        return companies;
    }
    
    public List<Company> getAll(String city) throws Exception
    {
        
        URL url = new URL(this.url, "/companies/city/" + city);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        List<Company> companies = new ArrayList (Arrays.asList(JsonTransformer.fromJson(new InputStreamReader(conn.getInputStream()), Company[].class)));
        return companies;
    }
}
