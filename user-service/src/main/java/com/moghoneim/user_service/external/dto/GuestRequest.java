package com.moghoneim.user_service.external.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestRequest {
    private String guestName;
    private String guestAddress;
    private String guestEmail;
    private String guestPhoneNum;
    private String userId;
}
