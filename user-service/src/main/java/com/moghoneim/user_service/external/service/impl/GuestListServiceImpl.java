package com.moghoneim.user_service.external.service.impl;

import com.moghoneim.user_service.external.client.GuestFeignClient;
import com.moghoneim.user_service.external.dto.GuestRequest;
import com.moghoneim.user_service.external.dto.GuestResponse;
import com.moghoneim.user_service.external.service.GuestListService;
import com.moghoneim.user_service.keycloak.KeycloakUserManager;
import com.moghoneim.user_service.util.AuthHelper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GuestListServiceImpl  implements GuestListService {


    private final GuestFeignClient guestFeignClient;
    private final AuthHelper authHelper;
    private static final String CIRCUIT_BREAKER_NAME = "guestListService";

    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "getUserGuestListFallback")
    public List<GuestResponse> getUserGuestList() {

        return guestFeignClient.getUserGuestList(authHelper.getCurrentUserId());
    }

    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "addGuestForUserFallback")
    public void addGuestForUser(GuestRequest request) {

        guestFeignClient.addGuestForUser(authHelper.getCurrentUserId(), request);
    }

    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "addGuestsForUserFallback")
    public void addGuestsForUser(List<GuestRequest> requests) {
        guestFeignClient.addGuestListForUser(authHelper.getCurrentUserId(), requests);
    }

    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "deleteGuestByUserIdFallback")
    public void deleteGuestByUserId(String guestName) {

        guestFeignClient.deleteGuestByUserId(authHelper.getCurrentUserId(), guestName);

    }

    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "deleteGuestListByUserIdFallback")
    public void deleteGuestListByUserId() {

        guestFeignClient.deleteGuestListByUserId(authHelper.getCurrentUserId());

    }

    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "updateGuestForUserFallback")
    public void updateGuestForUser(String guestName, GuestRequest request) {

        guestFeignClient.updateGuestForUser(authHelper.getCurrentUserId(), guestName,request);

    }

    // -------------------------------
    // 🔁 Fallback Methods with Logging
    // -------------------------------
    public List<GuestResponse> getUserGuestListFallback(Throwable t) {
        log.error("Fallback for getUserGuestList triggered due to: {}", t.getMessage(), t);
        return List.of(); // return empty list or cached data
    }

    public void addGuestForUserFallback(GuestRequest request, Throwable t) {
        log.error("Fallback for addGuestForUser. Request: {}. Error: {}", request, t.getMessage(), t);
    }

    public void addGuestsForUserFallback(List<GuestRequest> requests, Throwable t) {
        log.error("Fallback for addGuestsForUser. Requests count: {}. Error: {}", requests.size(), t.getMessage(), t);
    }

    public void deleteGuestByUserIdFallback(String guestName, Throwable t) {
        log.error("Fallback for deleteGuestByUserId. Guest name: {}. Error: {}", guestName, t.getMessage(), t);
    }

    public void deleteGuestListByUserIdFallback(Throwable t) {
        log.error("Fallback for deleteGuestListByUserId. Error: {}", t.getMessage(), t);
    }

    public void updateGuestForUserFallback(String guestName, GuestRequest request, Throwable t) {
        log.error("Fallback for updateGuestForUser. Guest: {}, Request: {}. Error: {}",
                guestName, request, t.getMessage(), t);
    }


}
