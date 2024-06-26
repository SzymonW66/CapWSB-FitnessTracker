package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */

    @Query("SELECT user FROM User user WHERE LOWER(user.email) LIKE LOWER(concat('%', :emailFragment, '%'))")
    List<User> findUserIdsAndEmailsByEmail(@Param("email") String email);

    @Query("SELECT user FROM User user WHERE LOWER(user.email) LIKE LOWER(concat('%', ?1, '%'))")
    List<User> findByEmail(final String email);

    @Query("SELECT user FROM User user WHERE user.birthdate < :maxBirthdate")
    List<User> findUsersOlderThan(@Param("maxBirthdate") LocalDate maxBirthdate);
}
