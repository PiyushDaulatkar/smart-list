package com.smartlist.core_api.exception;

import java.time.Instant;
import java.util.Map;

public record ApiErrorResponse(
        Instant timeStamp,
        int status,
        String error,
        // INSIGHT: Frontend logic uses code, not message.
        String code,
        String message,
        String path,
        Map<String, String> validationErrors
) {
}
