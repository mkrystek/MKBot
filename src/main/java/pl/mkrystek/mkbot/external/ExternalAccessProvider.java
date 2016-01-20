package pl.mkrystek.mkbot.external;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

public class ExternalAccessProvider {

    private final RestOperations restOperations;

    public ExternalAccessProvider(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    public <T> ResponseEntity<T> performGetRequest(String url, Class<T> responseType) {
        return restOperations.getForEntity(url, responseType);
    }

    public <T> ResponseEntity<T> performPostRequest(String url, Object requestObject, Class<T> responseType) {
        return restOperations.postForEntity(url, requestObject, responseType);
    }
}
