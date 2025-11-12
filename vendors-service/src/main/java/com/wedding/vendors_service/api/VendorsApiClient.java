package com.wedding.vendors_service.api;

import com.wedding.vendors_service.config.RestTemplateConfig;
import com.wedding.vendors_service.dto.VendorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VendorsApiClient {

    private final RestTemplate restTemplate;

    private final Environment env;

    public List<VendorResponse> getVendors(){
        String vendorsBaseUrl = env.getProperty("vendors.api.base-url");
        return restTemplate.exchange(
                vendorsBaseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<VendorResponse>>() {}
        ).getBody();
    }

    public List<VendorResponse> getVendorByCategory(String category){
        String vendorsBaseUrl = env.getProperty("vendors.api.base-url");
        String vendorsByCategoryUrl = vendorsBaseUrl + env.getProperty("vendors.api.category-url") + category;
        return restTemplate.exchange(
                vendorsByCategoryUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<VendorResponse>>() {}
        ).getBody();
    }
}
