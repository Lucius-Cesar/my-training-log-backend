package com.my_training_log.utils;

import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class UrlUtils {
    public static UUID extractIdFromLocationHeader(ResponseEntity<?> response) {
        String location = response.getHeaders().getLocation().toString();
        return UUID.fromString(location.substring(location.lastIndexOf('/') + 1));
    }
}
