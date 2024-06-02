package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDTO;
import com.capgemini.wsb.fitnesstracker.user.api.UserEmailDTO;
import com.capgemini.wsb.fitnesstracker.user.api.UserInfoDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDto(User user) {
        return new UserDTO(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    User toEntity(UserDTO userDto) {
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email());
    }

   public User saveEntity(UserDTO userDto) {
        return new User(
                userDto.Id(),
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email());
    }

    UserEmailDTO toUserEmailDTO(User user) {
        return new UserEmailDTO(
                user.getId(),
                user.getEmail()
        );
    }

    UserInfoDTO convertToUserInfoDTO(User user) {
        return new UserInfoDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName()
        );
    }

}
