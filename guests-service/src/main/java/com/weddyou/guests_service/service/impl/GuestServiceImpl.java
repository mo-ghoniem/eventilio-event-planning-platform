package com.weddyou.guests_service.service.impl;

import com.weddyou.guests_service.dto.GuestRequest;
import com.weddyou.guests_service.dto.GuestResponse;
import com.weddyou.guests_service.dto.mapper.GuestMapper;
import com.weddyou.guests_service.model.GuestEntity;
import com.weddyou.guests_service.repository.GuestRepository;
import com.weddyou.guests_service.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class GuestServiceImpl implements GuestService {
    private final GuestRepository guestRepo;
    private final GuestMapper guestMapper;

    @Override
    public void addGuest(GuestRequest request) {


        GuestEntity guest=guestMapper.mapToGuest(request);
        guestRepo.save(guest);

    }

    @Override
    public void deleteGuest(String guestName) {

        GuestEntity guest=guestRepo.findByGuestName(guestName);

        if(guest!=null){

            guestRepo.delete(guest);
        }


    }

    @Override
    public List<GuestResponse> getGuests() {
        List<GuestEntity>guests=guestRepo.findAll();
        return guests.stream().map(guestMapper::mapToResponse).toList();
    }

    @Override
    public void updateGuest(String guestName,GuestRequest guestRequest) {
        GuestEntity guest=guestRepo.findByGuestName(guestName);

        if(guest!=null){

        guest.setGuestName(guestRequest.getGuestName());
        guest.setGuestAddress(guestRequest.getGuestAddress());
        guest.setGuestEmail(guestRequest.getGuestEmail());
        guest.setGuestPhoneNum(guestRequest.getGuestPhoneNum());
        guestRepo.save(guest);


        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<GuestResponse> getUserGuestList(String userId) {
        List<GuestEntity> userGuestList=guestRepo.findByUserId(userId);
        return userGuestList.stream().map(guestMapper::mapToResponse).toList();
    }

    @Override
    public void deleteGuestListByUserId(String userId) {
        List<GuestEntity> guestsToDelete = guestRepo.findByUserId(userId);

        for (GuestEntity guest : guestsToDelete) {
            guestRepo.delete(guest);
        }
    }

    @Override
    public void deleteGuestByUserId(String userId, String guestName) {

        GuestEntity guest=guestRepo.findByUserIdAndGuestName(userId,guestName);

        if(guest!=null){

            guestRepo.delete(guest);
        }

    }

    @Override
    public void addGuestForUser(GuestRequest request, String userId) {
        request.setUserId(userId);
        GuestEntity guest=guestMapper.mapToGuest(request);
        guestRepo.save(guest);

    }

    @Override
    public void addGuestsForUser(List<GuestRequest> requests, String userId) {
        List<GuestEntity> guests = requests.stream()
                .map(request -> {
                    request.setUserId(userId);
                    return guestMapper.mapToGuest(request);
                })
                .collect(Collectors.toList());

        guestRepo.saveAll(guests);
    }

    @Override
    public void updateGuestForUser(String guestName,String userId, GuestRequest guestRequest) {
        GuestEntity guest=guestRepo.findByUserIdAndGuestName(userId,guestName);
        if(guest!=null){

            guest.setGuestName(guestRequest.getGuestName());
            guest.setGuestAddress(guestRequest.getGuestAddress());
            guest.setGuestEmail(guestRequest.getGuestEmail());
            guest.setGuestPhoneNum(guestRequest.getGuestPhoneNum());
            guestRepo.save(guest);

        }


    }



}
