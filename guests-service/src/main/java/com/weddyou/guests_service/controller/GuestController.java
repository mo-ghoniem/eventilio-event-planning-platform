package com.weddyou.guests_service.controller;

import com.weddyou.guests_service.dto.GuestRequest;
import com.weddyou.guests_service.dto.GuestResponse;
import com.weddyou.guests_service.service.GuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/guests")
public class GuestController {

    private final GuestService guestService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public  List<GuestResponse>getGuestList(){

        return guestService.getGuests();
    }

    @PostMapping("/add-guest")
    @ResponseStatus(HttpStatus.CREATED)
    public  void addGuest(@RequestBody GuestRequest guestRequest){
        guestService.addGuest(guestRequest);
    }

    @DeleteMapping("/{guest-name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGuest(@PathVariable("guest-name")String guestName){
        guestService.deleteGuest(guestName);
    }

    @PutMapping("/{guest-name}")
    @ResponseStatus(HttpStatus.OK)
    public void updateGuest(@PathVariable("guest-name")String guestName
                            ,@RequestBody GuestRequest guestRequest){


        guestService.updateGuest(guestName,guestRequest);


    }

    @GetMapping("/guest-list/{user-id}")
    @ResponseStatus(HttpStatus.OK)
    public  List<GuestResponse>getUserGuestList(@PathVariable(value = "user-id",required = false) String userId){

        return guestService.getUserGuestList(userId);
    }

    @PostMapping("/guest-list/{user-id}")
    @ResponseStatus(HttpStatus.CREATED)
    public  void addGuestForUser(@Valid @PathVariable(value = "user-id",required = false) String userId,
                                 @RequestBody GuestRequest guestRequest){
        guestService.addGuestForUser(guestRequest,userId);
    }

    @PostMapping("/guest-list/add-list/{user-id}")
    @ResponseStatus(HttpStatus.CREATED)
    public  void addGuestListForUser(@Valid @PathVariable(value = "user-id",required = false) String userId,
                                 @RequestBody List<GuestRequest> guestRequests){
        guestService.addGuestsForUser(guestRequests,userId);
    }

    @DeleteMapping("/delete-guest/{user-id}/{guest-name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGuestByUserId(@PathVariable(value = "user-id",required = false) String userId,
                                    @PathVariable("guest-name")String guestName){
        guestService.deleteGuestByUserId(userId,guestName);
    }
    @DeleteMapping("/delete-guest-list/{user-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGuestListByUserId(@PathVariable(value = "user-id",required = false) String userId){
        guestService.deleteGuestListByUserId(userId);
    }

    @PutMapping("/update-guest/{user-id}/{guest-name}")
    @ResponseStatus(HttpStatus.OK)
    public void updateGuestForUser(@PathVariable(value = "user-id",required = false) String userId,
                                    @PathVariable("guest-name")String guestName,
                                    @RequestBody GuestRequest guestRequest){


        guestService.updateGuestForUser(guestName,userId,guestRequest);


    }



}
