package com.wedding.rsvp_service.repository;
import com.wedding.rsvp_service.model.Rsvp;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface RsvpRepository extends MongoRepository<Rsvp,Long> {
    Optional<Rsvp> findByUserId(String userId);

}
