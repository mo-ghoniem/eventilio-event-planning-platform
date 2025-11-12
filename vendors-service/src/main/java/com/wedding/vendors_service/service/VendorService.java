package com.wedding.vendors_service.service;

import com.wedding.vendors_service.dto.VendorRequest;
import com.wedding.vendors_service.dto.VendorResponse;

import java.util.List;

public interface VendorService {

    List<VendorResponse> getAllVendors();

    List<VendorResponse> getVendorsByCategory(String category);

    VendorResponse createVendor(VendorRequest vendorRequest);

    void updateVendor(String vendorName, VendorRequest vendorRequest);

    void deleteVendorByName(String vendorName);

    // for testing the required fields and fixing any problem related to Model fields or Response-object class attributes
    List<VendorResponse> getAllScrapedVendors();
}
