package com.example.customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
public class CustomerController {

    @Value("${preferences.api.url:http://preferences:8080}")    
    private String remoteURL;

    private static final String RESPONSE_STRING_FORMAT = "C100 * %s *\n";

    private final RestTemplate restTemplate;

    public CustomerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/")
    public ResponseEntity<String> getCustomer() {
        try {            
            String response = restTemplate.getForObject(remoteURL, String.class);            
            return ResponseEntity.ok(String.format(RESPONSE_STRING_FORMAT, response));
        } catch (RestClientException ex) {
            System.err.println(ex);
            return ResponseEntity.ok(String.format(RESPONSE_STRING_FORMAT, ex.getMessage()));
        }
    }

}
