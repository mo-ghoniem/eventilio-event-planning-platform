package com.weddyou.guests_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="w_guest")
public class GuestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guestId;

    @NotBlank(message = "Guest name must not be blank")
    private String guestName;

    private String guestAddress;

    @NotBlank(message = "Guest email must not be blank")
    private String guestEmail;

    @NotBlank(message = "Guest phone number must not be blank")
    private String guestPhoneNum;

    @NotNull(message = "User ID must not be blank")
    private String userId;


}
