package com.moghoneim.user_service.keycloak;

import com.moghoneim.user_service.config.KeycloakConfig;
import com.moghoneim.user_service.dto.UserDto;
import com.moghoneim.user_service.dto.UserRecord;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;


@Component
public class KeycloakUserManager {

    private UsersResource getUsersResource() {
        return KeycloakConfig.getInstance()
                .realm(KeycloakConfig.getRealm())
                .users();
    }

    public String registerUserWithRole(UserDto userDto, String roleName) {
        try {
            CredentialRepresentation credential = Credentials.createPasswordCredentials(userDto.getPassword());
            UserRepresentation user = getUserRepresentation(userDto, credential);

            Response response = getUsersResource().create(user);
            if (response.getStatus() != 201) {
                throw new RuntimeException("Failed to create user. Status: " + response.getStatus()
                        + ", Error: " + response.readEntity(String.class));
            }

            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

            RoleRepresentation realmRole = KeycloakConfig.getInstance()
                    .realm(KeycloakConfig.getRealm())
                    .roles().get(roleName).toRepresentation();

            getUsersResource().get(userId).roles().realmLevel().add(List.of(realmRole));

            return "User created successfully!";
        } catch (Exception ex) {
            throw new RuntimeException("Error during user registration: " + ex.getMessage(), ex);
        }
    }

    public UserRecord getUserByUsername(String userName) {
        try {
            // Fetch users from Keycloak
            UserRepresentation user = getUsersResource().search(userName, true).get(0);

            // Map UserRepresentation to UserRecord
            return new UserRecord(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.isEmailVerified(),
                    user.getCreatedTimestamp()
            );
        } catch (Exception ex) {
            throw new RuntimeException("Failed to fetch user by username: " + ex.getMessage(), ex);
        }
    }

    public void updateUser(String userId, UserDto userDto) {
        try {
            UserRepresentation existingUser = getUsersResource().get(userId).toRepresentation();

            if (userDto.getFirstName() != null && !userDto.getFirstName().isEmpty()) {
                existingUser.setFirstName(userDto.getFirstName());
            }
            if (userDto.getLastName() != null && !userDto.getLastName().isEmpty()) {
                existingUser.setLastName(userDto.getLastName());
            }
            if (userDto.getEmail() != null && !userDto.getEmail().isEmpty()) {
                existingUser.setEmail(userDto.getEmail());
            }

            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                CredentialRepresentation credential = Credentials.createPasswordCredentials(userDto.getPassword());
                existingUser.setCredentials(List.of(credential));
            }

            getUsersResource().get(userId).update(existingUser);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to update user: " + ex.getMessage(), ex);
        }
    }

    public void deleteUser(String userId) {
        try {
            getUsersResource().get(userId).remove();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to delete user: " + ex.getMessage(), ex);
        }
    }

    public void sendVerificationLink(String userId) {
        try {
            getUsersResource().get(userId).sendVerifyEmail();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to send verification email: " + ex.getMessage(), ex);
        }
    }

    public void sendResetPasswordLink(String userId) {
        try {
            getUsersResource().get(userId)
                    .executeActionsEmail(Collections.singletonList("UPDATE_PASSWORD"));
        } catch (Exception ex) {
            throw new RuntimeException("Failed to send reset password link: " + ex.getMessage(), ex);
        }
    }

    private static UserRepresentation getUserRepresentation(UserDto userDto, CredentialRepresentation credential) {


        String username = userDto.getEmail().toLowerCase();
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setEmailVerified(true);
        user.setCredentials(List.of(credential));
        user.setEnabled(true);
        return user;
    }
}

