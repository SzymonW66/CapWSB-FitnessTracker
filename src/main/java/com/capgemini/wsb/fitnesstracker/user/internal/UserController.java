package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDTO;
import com.capgemini.wsb.fitnesstracker.user.api.UserEmailDTO;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @GetMapping("/simple")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList()));
    }


    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsersDetails() {
        return ResponseEntity.ok(userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList()));
    }


    @GetMapping("/email")
    public ResponseEntity<List<UserEmailDTO>> getByEmail(@RequestParam String email) {
        final List<User> userByEmail = userService.getUserByEmail(email);
        if (userByEmail.isEmpty()) {
            throw new UserNotFoundException("User Not Found");
        }
        return ResponseEntity.ok(userByEmail.stream().map(userMapper::toUserEmailDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
        final Optional<User> optionalUser = userService.getUser(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(userId);
        }
        return ResponseEntity.ok(userMapper.toDto(optionalUser.get()));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody UserDTO userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userMapper.toEntity(userDto)));
    }

    @GetMapping("/older-than/{age}")
    final ResponseEntity<List<UserDTO>> getUserOlderThan(@PathVariable LocalDate time) {
        final List<User> ageUsers = userRepository.findUsersOlderThan(time);
        if (ageUsers.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return ResponseEntity.ok(ageUsers.stream().map(userMapper::toDto).collect(Collectors.toList()));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UserDTO changedUser) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUser(userMapper.saveEntity(changedUser), userId));
    }

    @GetMapping("/older/{time}")
    public ResponseEntity<List<UserDTO>> getUsersOlderThanGivenAge(@PathVariable LocalDate time) {
        final List<User> olderUsers = userService.getUsersOlderThanProvided(time);
        if (olderUsers.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return ResponseEntity.ok(olderUsers.stream().map(userMapper::toDto).collect(Collectors.toList()));
    }
}