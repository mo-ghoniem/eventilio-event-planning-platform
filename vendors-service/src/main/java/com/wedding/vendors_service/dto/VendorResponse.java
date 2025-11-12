package com.wedding.vendors_service.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorResponse {


    @JsonProperty("vendor_name")
    private String vendorName;

    @JsonProperty("vendor_link")
    private String vendorLink;

    @JsonProperty("vendor_description")
    private String vendorDescription;

    @JsonProperty("vendor_category")
    private String vendorCategory;

    @JsonProperty("vendor_locations")
    private String vendorLocations;

    @JsonProperty("vendor_price")
    private String vendorPrice;

    @JsonProperty("vendor_phone_numbers")
    private String vendorPhoneNumbers;

    @JsonProperty("vendor_followers_num")
    private String vendorFollowersNum;

    @JsonProperty("vendor_following_num")
    private String vendorFollowingNum;

    private String dataSource;
}
