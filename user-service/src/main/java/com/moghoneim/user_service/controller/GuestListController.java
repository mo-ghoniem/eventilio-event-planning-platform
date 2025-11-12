package com.moghoneim.user_service.controller;

import com.moghoneim.user_service.external.dto.GuestRequest;
import com.moghoneim.user_service.external.dto.GuestResponse;
import com.moghoneim.user_service.external.service.GuestListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/guest-list")
@RequiredArgsConstructor
public class GuestListController {
    private final GuestListService guestListService;
    @GetMapping
    public List<GuestResponse> getUserGuestList(){
        return guestListService.getUserGuestList();
    }

    @PostMapping("/add-guest")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addGuestForUser(@RequestBody GuestRequest request)
    {guestListService.addGuestForUser(request);}

    @PostMapping("/add-guest-list")
    @ResponseStatus(HttpStatus.CREATED)
    public void addGuestListForUser(@RequestBody List<GuestRequest> requests)
    {guestListService.addGuestsForUser(requests);}


    @DeleteMapping("/delete-guest/{guest-name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGuestByUserId(@PathVariable("guest-name")String guestName){
        guestListService.deleteGuestByUserId(guestName);
    }

    @DeleteMapping("/delete-guest-list")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGuestListByUserId(){
        guestListService.deleteGuestListByUserId();
    }

    @PutMapping("/update-guest/{guest-name}")
    @ResponseStatus(HttpStatus.OK)
    public void updateGuestForUser(@PathVariable("guest-name") String guestName,
                                   @RequestBody GuestRequest request) {

        guestListService.updateGuestForUser(guestName,request);

    }



}
