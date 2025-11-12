package com.wedding.vendors_service.service.impl;


import com.wedding.vendors_service.api.VendorsApiClient;
import com.wedding.vendors_service.dto.VendorRequest;
import com.wedding.vendors_service.dto.VendorResponse;
import com.wedding.vendors_service.dto.mapper.VendorMapper;
import com.wedding.vendors_service.model.VendorInfo;
import com.wedding.vendors_service.repository.VendorRepository;
import com.wedding.vendors_service.service.VendorService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Service
@Slf4j
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;
    private final VendorsApiClient vendorsApiClient;

    private static final String CIRCUIT_BREAKER_NAME = "vendorService";

    @Override
    public List<VendorResponse> getAllVendors() {
        if (vendorRepository.count() == 0) {
            List<VendorResponse> fetchedVendors = vendorsApiClient.getVendors();
            if (fetchedVendors != null && !fetchedVendors.isEmpty()) {
                // Save fetched vendors to the database.  Convert to entities first.
                List<VendorInfo> vendorEntities = fetchedVendors.stream()
                        .map(vendorResponse -> vendorMapper.toVendorInfo(vendorResponse, "Django"))
                        .toList();
                vendorRepository.saveAll(vendorEntities);

                // Return the fetched vendors
                return fetchedVendors;
            }else {
                // Handle empty or null API response (e.g., log, return empty list, or throw exception)
                return new ArrayList<>(); // or throw new CustomException("No vendors found")
            }
        } else {
            return vendorRepository.findAll().stream()
                    .map(vendorMapper::toVendorResponse)
                    .toList();
        }
    }

    @Override
    public List<VendorResponse> getVendorsByCategory(String category) {
        List<VendorInfo> vendors = vendorRepository.findByVendorCategory(category);
        if (vendors != null && !vendors.isEmpty()) {
            return vendors.stream().map(vendorMapper::toVendorResponse).toList();
        }
        List<VendorResponse> fetchedVendors = vendorsApiClient.getVendorByCategory(category);
        if (fetchedVendors != null && !fetchedVendors.isEmpty()) {

            List<VendorInfo> vendorEntities = fetchedVendors.stream()
                    .map(vendorResponse -> vendorMapper.toVendorInfo(vendorResponse, "Django"))
                    .toList();
            vendorRepository.saveAll(vendorEntities);
            return fetchedVendors;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No vendors found for category: " + category);
    }

    @Override
    public VendorResponse createVendor(VendorRequest vendorRequest) {
        VendorInfo vendorInfo = vendorMapper.toVendorInfo(vendorRequest);
        VendorInfo savedVendor = vendorRepository.save(vendorInfo);
        return vendorMapper.toVendorResponse(savedVendor);
    }

    @Override
    public void updateVendor(String vendorName, VendorRequest vendorRequest) {
        VendorInfo existingVendor = vendorRepository.findByVendorName(vendorName);
        if (existingVendor!=null) {
            VendorInfo vendorInfo = vendorMapper.toVendorInfo(vendorRequest);
            vendorInfo.setId(existingVendor.getId()); // Keep the original ID for the update
            vendorRepository.save(vendorInfo);
        }
    }

    @Override
    public void deleteVendorByName(String vendorName) {
        VendorInfo vendor = vendorRepository.findByVendorName(vendorName);
        if (vendor!=null) {
            vendorRepository.delete(vendor);
        }
    }

    // for testing the required fields and fixing any problem related to Model fields or Response-object class attributes
    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "getAllScrapedVendorsFallback")
    public List<VendorResponse> getAllScrapedVendors() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3000);
        requestFactory.setReadTimeout(3000);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        ResponseEntity<VendorResponse[]> responseEntity = restTemplate.exchange(
                "http://localhost:8000/vendors/all-vendor-info/",
                HttpMethod.GET, null, VendorResponse[].class);

        return Arrays.stream(Objects.requireNonNull(responseEntity.getBody()))
                .toList();
    }

    // -------------------------------
    // 🔁 Fallback Methods with Logging
    // -------------------------------
    public List<VendorResponse> getAllScrapedVendorsFallback(Throwable t) {
        log.error("Fallback triggered for getAllScrapedVendors: {}", t.getMessage(), t);
        return List.of();
    }

}
