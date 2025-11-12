package com.moghoneim.user_service.external.service;

import com.moghoneim.user_service.external.dto.RsvpRequest;
import com.moghoneim.user_service.external.dto.RsvpResponse;

public interface UserRsvpService {
    public RsvpResponse getUserRsvp();
    public void addRsvpForUser(RsvpRequest request);
    public void deleteRsvpByUserId();
    public void updateRsvpForUser(RsvpRequest request);
}
