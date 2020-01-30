import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.api.StoreApi;
import org.openapitools.client.auth.*;
import org.openapitools.client.model.*;
import org.openapitools.client.api.PetApi;

import java.util.Map;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:8080/api/v3");

        StoreApi apiInstance = new StoreApi(defaultClient);
        try {
            Map<String, Integer> result = apiInstance.getInventory();
            System.out.println(result.get("approved"));
        } catch (ApiException e) {
            System.err.println("Exception when calling PetApi#addPet");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}