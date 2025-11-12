package com.wedding.vendors_service.dto.mapper;

import com.wedding.vendors_service.dto.VendorRequest;
import com.wedding.vendors_service.dto.VendorResponse;
import com.wedding.vendors_service.model.VendorInfo;
import org.springframework.stereotype.Component;

@Component
public class VendorMapper {

    public VendorInfo toVendorInfo(VendorResponse vendorResponse, String dataSource) {
        if (vendorResponse == null) {
            return null;
        }

        return VendorInfo.builder()
                .vendorName(vendorResponse.getVendorName())
                .vendorLink(vendorResponse.getVendorLink())
                .vendorDescription(vendorResponse.getVendorDescription())
                .vendorCategory(vendorResponse.getVendorCategory())
                .vendorLocations(vendorResponse.getVendorLocations())
                .vendorPrice(vendorResponse.getVendorPrice())
                .vendorPhoneNumbers(vendorResponse.getVendorPhoneNumbers())
                .vendorFollowersNum(vendorResponse.getVendorFollowersNum())
                .vendorFollowingNum(vendorResponse.getVendorFollowingNum())
                .dataSource(dataSource)
                .build();
    }

    public VendorInfo toVendorInfo(VendorRequest vendorRequest) {
        if (vendorRequest == null) {
            return null;
        }
        return VendorInfo.builder()
                .vendorName(vendorRequest.getVendorName())
                .vendorLink(vendorRequest.getVendorLink())
                .vendorDescription(vendorRequest.getVendorDescription())
                .vendorCategory(vendorRequest.getVendorCategory())
                .vendorLocations(vendorRequest.getVendorLocations())
                .vendorPrice(vendorRequest.getVendorPrice())
                .vendorPhoneNumbers(vendorRequest.getVendorPhoneNumbers())
                .vendorFollowersNum(vendorRequest.getVendorFollowersNum())
                .vendorFollowingNum(vendorRequest.getVendorFollowingNum())
                .build();
    }

    // Method to convert VendorInfo to VendorResponse
    public VendorResponse toVendorResponse(VendorInfo vendorInfo) {
        if (vendorInfo == null) {
            return null;
        }

        return VendorResponse.builder()
                .vendorName(vendorInfo.getVendorName())
                .vendorLink(vendorInfo.getVendorLink())
                .vendorDescription(vendorInfo.getVendorDescription())
                .vendorCategory(vendorInfo.getVendorCategory())
                .vendorLocations(vendorInfo.getVendorLocations())
                .vendorPrice(vendorInfo.getVendorPrice())
                .vendorPhoneNumbers(vendorInfo.getVendorPhoneNumbers())
                .vendorFollowersNum(vendorInfo.getVendorFollowersNum())
                .vendorFollowingNum(vendorInfo.getVendorFollowingNum())
                .dataSource(vendorInfo.getDataSource())
                .build();
    }
}
