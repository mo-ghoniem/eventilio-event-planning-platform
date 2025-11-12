package com.wedding.rsvp_service.service.impl;

import com.wedding.rsvp_service.dto.RsvpRequest;
import com.wedding.rsvp_service.dto.RsvpResponse;
import com.wedding.rsvp_service.dto.mapper.RsvpMapper;
import com.wedding.rsvp_service.model.Rsvp;
import com.wedding.rsvp_service.repository.RsvpRepository;
import com.wedding.rsvp_service.service.RsvpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class RsvpServiceImpl implements RsvpService {

    private final RsvpRepository rsvpRepo;
    private final RsvpMapper rsvpMapper;


    @Override
    public List<RsvpResponse> getAllRsvps() {
        return rsvpRepo.findAll()
                .stream().map(rsvpMapper::rsvpMapToRsvpResponse).toList();
    }

    @Override
    public void addRsvp(RsvpRequest request) {

        rsvpRepo.save(rsvpMapper.rsvpRequestMapToRsvp(request));

    }

    @Override
    public void deleteRsvpById(Long rsvpId) {
        Optional<Rsvp> rsvp=rsvpRepo.findById(rsvpId);
        rsvp.ifPresent(rsvpRepo::delete);

    }

    @Override
    public void updateRsvpById(Long rsvpId, RsvpRequest request) {
        Optional<Rsvp> rsvp=rsvpRepo.findById(rsvpId);
        rsvp.ifPresent(value -> {
            value.setQuestions(request.getQuestions());
            value.setAnswers(request.getAnswers());

        });
        rsvp.ifPresent(rsvpRepo::save);
    }

    @Override
    public RsvpResponse getUserRsvp(String userId) {
       Optional<Rsvp>rsvp= rsvpRepo.findByUserId(userId);
        return rsvp.map(rsvpMapper::rsvpMapToRsvpResponse).orElse(null);
    }

    @Override
    public void addRsvpForUser(String userId, RsvpRequest request) {

        //each user can create one rsvp only
        //So, it is better to check if the user rsvp created or not

        Optional<Rsvp> existingRsvp = rsvpRepo.findByUserId(userId);

        if(existingRsvp.isPresent()){
            existingRsvp.get().setQuestions(request.getQuestions());
            existingRsvp.get().setAnswers(request.getAnswers());
            rsvpRepo.save(existingRsvp.get());
        }
        else{
        request.setUserId(userId);
        Rsvp rsvp= rsvpMapper.rsvpRequestMapToRsvp(request);
        rsvpRepo.save(rsvp);}
    }

    @Override
    public void deleteRsvpByUserId(String userId) {
        Optional<Rsvp> rsvp=rsvpRepo.findByUserId(userId);
        rsvp.ifPresent(rsvpRepo::delete);

    }

    @Override
    public void updateRsvpForUser(String userId, RsvpRequest request) {
        Optional<Rsvp> rsvp=rsvpRepo.findByUserId(userId);
        rsvp.ifPresent(value -> {
            value.setQuestions(request.getQuestions());
            value.setAnswers(request.getAnswers());
        });

        rsvp.ifPresent(rsvpRepo::save);


    }
}
