package com.wedding.rsvp_service.service;
import com.wedding.rsvp_service.dto.RsvpRequest;
import com.wedding.rsvp_service.dto.RsvpResponse;

import java.util.List;

public interface RsvpService {
    public List<RsvpResponse> getAllRsvps();
    public void addRsvp(RsvpRequest request);
    public void deleteRsvpById(Long rsvpId);
    public void updateRsvpById(Long rsvpId,RsvpRequest request);

    public RsvpResponse getUserRsvp(String userId);
    public void addRsvpForUser(String userId,RsvpRequest request);
    public void deleteRsvpByUserId(String userId);
    public void updateRsvpForUser(String userId,RsvpRequest request);
}
