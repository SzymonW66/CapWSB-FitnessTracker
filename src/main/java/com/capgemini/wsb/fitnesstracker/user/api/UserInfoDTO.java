package com.capgemini.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;
import lombok.NoArgsConstructor;

public record UserInfoDTO(@Nullable Long id, String firstName, String lastName) {
}
