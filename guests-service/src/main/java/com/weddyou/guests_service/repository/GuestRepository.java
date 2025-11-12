package com.weddyou.guests_service.repository;

import com.weddyou.guests_service.model.GuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<GuestEntity,Long> {
    GuestEntity findByGuestName(String guestName);
    GuestEntity findByUserIdAndGuestName(String userId,String guestName);
    List<GuestEntity> findByUserId(String userId);

}
