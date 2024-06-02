package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDTO;
import com.capgemini.wsb.fitnesstracker.user.api.UserEmailDTO;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    @GetMapping("/simple")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ok(userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList());
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsersDetails() {
        return ok(userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList());
    }

    @GetMapping("/email")
    public List<UserEmailDTO> getByEmail(@RequestParam String email) {
        final List<User> userByEmail = userService.getUserByEmail(email);
        if(userByEmail.isEmpty()) {
            throw new UserNotFoundException("User Not Found");
        }
        return userByEmail.stream().map(userMapper::toUserEmailDTO).collect(toList());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
        final Optional<User> optionalUser = userService.getUser(userId);
        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException(userId);
        }
        return ok(userMapper.toDto(optionalUser.get()));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(userId, NO_CONTENT);
    }


    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody UserDTO userDto) {
        return new ResponseEntity<>(userService.createUser(userMapper.toEntity(userDto)), CREATED);
    }


    @GetMapping("/older/{time}")
    public ResponseEntity<List<UserDTO>> getUsersOlderThanGivenAge(@PathVariable LocalDate time) {
        final List<User> olderUsers = userService.getUsersOlderThanProvided(time);
        if(olderUsers.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return ok(olderUsers.stream().map(userMapper::toDto).collect(toList()));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UserDTO changedUser) {
        return new ResponseEntity<>(userService.updateUser(userMapper.saveEntity(changedUser), userId), ACCEPTED);
    }

}