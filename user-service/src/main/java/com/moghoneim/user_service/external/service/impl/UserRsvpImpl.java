package com.moghoneim.user_service.external.service.impl;

import com.moghoneim.user_service.external.client.RsvpFeignClient;
import com.moghoneim.user_service.external.dto.RsvpRequest;
import com.moghoneim.user_service.external.dto.RsvpResponse;
import com.moghoneim.user_service.external.service.UserRsvpService;
import com.moghoneim.user_service.util.AuthHelper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserRsvpImpl implements UserRsvpService {

    private final RsvpFeignClient rsvpFeignClient;
    private final AuthHelper authHelper;
    private static final String CIRCUIT_BREAKER_NAME = "rsvpService";

    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "getUserRsvpFallback")
    public RsvpResponse getUserRsvp() {
        return rsvpFeignClient.getUserRsvp(authHelper.getCurrentUserId());
    }

    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "addRsvpForUserFallback")
    public void addRsvpForUser(RsvpRequest request) {

        rsvpFeignClient.addUserRsvp(authHelper.getCurrentUserId(), request);

    }

    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "deleteRsvpByUserIdFallback")
    public void deleteRsvpByUserId() {

        rsvpFeignClient.deleteUserRsvp(authHelper.getCurrentUserId());

    }

    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "updateRsvpForUserFallback")
    public void updateRsvpForUser(RsvpRequest request) {

        rsvpFeignClient.updateUserRsvp(authHelper.getCurrentUserId(), request);

    }

    // -------------------------------
    // 🔁 Fallback Methods with Logging
    // -------------------------------
    public RsvpResponse getUserRsvpFallback(Throwable throwable) {
        log.error("Fallback triggered for getUserRsvp due to: {}", throwable.getMessage(), throwable);
        return new RsvpResponse(); // or null or a default/fake response
    }

    public void addRsvpForUserFallback(RsvpRequest request, Throwable throwable) {
        log.error("Fallback triggered for addRsvpForUser. Request: {}. Error: {}", request, throwable.getMessage(), throwable);
    }

    public void deleteRsvpByUserIdFallback(Throwable throwable) {
        log.error("Fallback triggered for deleteRsvpByUserId due to: {}", throwable.getMessage(), throwable);
    }

    public void updateRsvpForUserFallback(RsvpRequest request, Throwable throwable) {
        log.error("Fallback triggered for updateRsvpForUser. Request: {}. Error: {}", request, throwable.getMessage(), throwable);
    }
}
