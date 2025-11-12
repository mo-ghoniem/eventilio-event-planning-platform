package com.wedding.rsvp_service.controller;

import com.wedding.rsvp_service.dto.RsvpRequest;
import com.wedding.rsvp_service.dto.RsvpResponse;
import com.wedding.rsvp_service.service.RsvpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/rsvp")
public class RsvpController {

    private final RsvpService rsvpService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RsvpResponse> getAllRsvps(){

        return rsvpService.getAllRsvps();
    }

    @PostMapping("/add-rsvp")
    @ResponseStatus(HttpStatus.CREATED)
    public void addRsvp(@RequestBody RsvpRequest request){

        rsvpService.addRsvp(request);
    }

    @DeleteMapping("/delete-rsvp/{rsvp-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRsvp(@PathVariable("rsvp-id") Long rsvpId){

        rsvpService.deleteRsvpById(rsvpId);

    }

    @PutMapping("/update-rsvp/{rsvp-id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateRsvp(
            @PathVariable("rsvp-id") Long rsvpId
            ,@RequestBody RsvpRequest request) {

        rsvpService.updateRsvpById(rsvpId,request);

    }

    @GetMapping("/user-rsvp/{user-id}")
    @ResponseStatus(HttpStatus.OK)
    public RsvpResponse getUserRsvp(
            @PathVariable(value = "user-id"
            ,required = false) String userId){

        return  rsvpService.getUserRsvp(userId);
    }

    @PostMapping("/add-user-rsvp/{user-id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUserRsvp(
            @PathVariable(value = "user-id"
            ,required = false) String userId,
            @RequestBody RsvpRequest request){

        rsvpService.addRsvpForUser(userId,request);
    }

    @DeleteMapping("/delete-user-rsvp/{user-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserRsvp(
            @PathVariable(value = "user-id"
                    ,required = false) String userId){

        rsvpService.deleteRsvpByUserId(userId);
    }

    @PutMapping("/update-user-rsvp/{user-id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserRsvp(
            @PathVariable(value = "user-id"
                    ,required = false) String userId,
            @RequestBody RsvpRequest request){

        rsvpService.updateRsvpForUser(userId,request);
    }

}
