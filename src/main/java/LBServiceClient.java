import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;


/**
 * @author Aleksas
 */
public class LBServiceClient{
    private String url = "http://192.168.99.100:4321/people/name//";

    public boolean checkIfRegistered(String name, String surname)throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        url += name;
        
        HttpGet getRequest = new HttpGet(url);
        HttpResponse response = client.execute(getRequest);
        
        if(response.getStatusLine().getStatusCode() == 404)
            return false;
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try (InputStream instream = entity.getContent()) {
                byte[] data = new byte[500];
                instream.read(data);
                String dataJson = new String(data, "UTF-8");
                int position = dataJson.indexOf("]");
                dataJson = dataJson.substring(0, position + 1);
                PersonModel[] models = new Gson().fromJson(dataJson, PersonModel[].class);
                instream.close();
                for(PersonModel model: models){
                    if(model.surname.equals(surname))
                        return true;
                }
            }
        }
        return false;
    }
}
