package com.wedding.rsvp_service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RsvpRequest {
    private Long rsvpId;
    private List<String> questions;
    private List<String> answers;
    private String userId;
}
