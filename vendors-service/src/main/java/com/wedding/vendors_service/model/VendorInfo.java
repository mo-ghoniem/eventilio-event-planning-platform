package com.wedding.vendors_service.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "vendor_info")
public class VendorInfo {

    @Id
    private String id;
    private String vendorName;
    private String vendorLink;
    private String vendorDescription;
    private String vendorCategory;
    private String vendorLocations;
    private String vendorPrice;
    private String vendorPhoneNumbers;
    private String vendorFollowersNum;
    private String vendorFollowingNum;
    private String dataSource = "Spring";
}
