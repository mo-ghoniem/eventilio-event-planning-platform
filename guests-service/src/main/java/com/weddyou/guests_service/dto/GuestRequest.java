package com.weddyou.guests_service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuestRequest {
    private String guestName;
    private String guestAddress;
    private String guestEmail;
    private String guestPhoneNum;
    private String userId;
}
