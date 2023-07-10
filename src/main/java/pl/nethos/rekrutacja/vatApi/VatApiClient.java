package pl.nethos.rekrutacja.vatApi;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class VatApiClient {

    private final RestTemplate restTemplate;

    public VatApiClient() {
        this.restTemplate = new RestTemplate();
    }

    public VatApiResponse getVatData(String nip, String bankAccount) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        String url = "https://wl-test.mf.gov.pl/" + nip + "/bank-account/" + bankAccount;
        VatApiResponse response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, VatApiResponse.class).getBody();
        return response;
    }
}
