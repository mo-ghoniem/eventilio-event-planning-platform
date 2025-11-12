package com.moghoneim.user_service.external.service;

import com.moghoneim.user_service.external.dto.GuestRequest;
import com.moghoneim.user_service.external.dto.GuestResponse;

import java.util.List;

public interface GuestListService {
    public List<GuestResponse> getUserGuestList();
    public void addGuestForUser(GuestRequest request);
    public void addGuestsForUser(List<GuestRequest> requests);
    public void deleteGuestByUserId(String guestName);
    public void deleteGuestListByUserId();
    public void updateGuestForUser(String guestName,GuestRequest request);
}
