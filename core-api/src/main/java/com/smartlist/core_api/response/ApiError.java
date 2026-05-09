package com.smartlist.core_api.response;

import java.util.Map;

public record ApiError(
        String code,
        String message,
        Map<String, String> details
) {
}
