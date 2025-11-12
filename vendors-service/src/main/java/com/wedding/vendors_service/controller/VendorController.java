package com.wedding.vendors_service.controller;

import com.wedding.vendors_service.dto.VendorRequest;
import com.wedding.vendors_service.dto.VendorResponse;
import com.wedding.vendors_service.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/vendors")
public class VendorController {

    private final VendorService vendorService;

    @PostMapping("/create-vendor")
    public ResponseEntity<String> createVendor(@RequestBody VendorRequest vendorRequest) {
        VendorResponse response = vendorService.createVendor(vendorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Vendor created successfully: " + response);
    }

    @GetMapping("/get-by-category/{category}")
    public ResponseEntity<List<VendorResponse>> getVendorsByCategory(@PathVariable("category") String category) {
        List<VendorResponse> vendors = vendorService.getVendorsByCategory(category);
        return ResponseEntity.ok(vendors);
    }

    @GetMapping("/view-all-vendors")
    public ResponseEntity<List<VendorResponse>> getAllVendors() {
        List<VendorResponse> vendors = vendorService.getAllVendors();
        return ResponseEntity.ok(vendors);
    }

    @PutMapping("/update-vendor/{vendor-name}")
    public ResponseEntity<String> updateVendor(
            @PathVariable("vendor-name") String vendorName,
            @RequestBody VendorRequest vendorRequest) {
        vendorService.updateVendor(vendorName, vendorRequest);
        return ResponseEntity.ok("Vendor updated successfully");
    }

    @DeleteMapping("/delete-vendor/{vendor-name}")
    public ResponseEntity<String> deleteVendor(@PathVariable("vendor-name") String vendorName) {
        vendorService.deleteVendorByName(vendorName);
        return ResponseEntity.ok("Vendor deleted successfully");
    }


    // for testing the required fields and fixing any problem related to Model fields or Response-object class attributes
    @GetMapping("/get-all-scraped-vendors")
    public ResponseEntity<List<VendorResponse>> getAllScrapedVendors() {
        List<VendorResponse> vendors = vendorService.getAllScrapedVendors();
        return ResponseEntity.ok(vendors);
    }
}
