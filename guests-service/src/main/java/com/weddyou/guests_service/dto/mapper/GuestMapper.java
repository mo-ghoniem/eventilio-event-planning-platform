package com.weddyou.guests_service.dto.mapper;

import com.weddyou.guests_service.dto.GuestRequest;
import com.weddyou.guests_service.dto.GuestResponse;
import com.weddyou.guests_service.model.GuestEntity;
import org.springframework.stereotype.Component;

@Component
public class GuestMapper {


    public GuestResponse mapToResponse(GuestEntity guest){
        return GuestResponse.builder()
                .guestName(guest.getGuestName())
                .guestAddress(guest.getGuestAddress())
                .guestEmail(guest.getGuestEmail())
                .guestPhoneNum(guest.getGuestPhoneNum())
                .userId(guest.getUserId())
                .build();


    }

    public GuestEntity mapToGuest(GuestRequest guest) {

        return GuestEntity.builder()
                .guestName(guest.getGuestName())
                .guestAddress(guest.getGuestAddress())
                .guestEmail(guest.getGuestEmail())
                .guestPhoneNum(guest.getGuestPhoneNum())
                .userId(guest.getUserId())
                .build();


    }
}
