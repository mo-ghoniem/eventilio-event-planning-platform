package com.moghoneim.user_service.controller;

import com.moghoneim.user_service.external.dto.RsvpRequest;
import com.moghoneim.user_service.external.dto.RsvpResponse;
import com.moghoneim.user_service.external.service.UserRsvpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users/rsvp")
public class UserRsvpController {

    private final UserRsvpService userRsvpService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public RsvpResponse getUserRsvp(){return userRsvpService.getUserRsvp();}

    @PostMapping("/add-user-rsvp")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUserRsvp(@RequestBody RsvpRequest request){userRsvpService.addRsvpForUser(request);}

    @DeleteMapping("/delete-user-rsvp")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserRsvp(){userRsvpService.deleteRsvpByUserId();}

    @PutMapping("/update-user-rsvp")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserRsvp(@RequestBody RsvpRequest request){userRsvpService.updateRsvpForUser(request);}

}
