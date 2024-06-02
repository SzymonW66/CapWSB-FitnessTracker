package com.capgemini.wsb.fitnesstracker.user.api;

import io.micrometer.common.lang.Nullable;

public record UserEmailDTO(@Nullable Long Id, String email) {
}
