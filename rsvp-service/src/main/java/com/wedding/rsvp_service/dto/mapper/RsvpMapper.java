package com.wedding.rsvp_service.dto.mapper;

import com.wedding.rsvp_service.dto.RsvpRequest;
import com.wedding.rsvp_service.dto.RsvpResponse;
import com.wedding.rsvp_service.model.Rsvp;
import com.wedding.rsvp_service.service.impl.SequenceGeneratorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RsvpMapper {


    private final SequenceGeneratorServiceImpl sequenceGenerator;

    public RsvpResponse rsvpMapToRsvpResponse(Rsvp rsvp){

            return  RsvpResponse.builder()
                                .rsvpId(rsvp.getRsvpId())
                                .questions(rsvp.getQuestions())
                                .answers(rsvp.getAnswers())
                                .userId(rsvp.getUserId())
                                .build();
    }


    public Rsvp rsvpRequestMapToRsvp(RsvpRequest rsvpRequest){
        return  Rsvp.builder()
                .rsvpId(sequenceGenerator.getNextId("rsvp_sequence"))
                .questions(rsvpRequest.getQuestions())
                .answers(rsvpRequest.getAnswers())
                .userId(rsvpRequest.getUserId())
                .build();
    }



}
