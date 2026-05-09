package com.smartlist.core_api.response;

public record ApiResponse<T>(
        boolean success,
        T data,
        ApiError error
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static <T> ApiResponse<T> failure(ApiError error) {
        // INSIGHT: Java can infer the type automatically from context, hence no T in below ApiResponse<>.
        return new ApiResponse<>(false, null, error);
    }
}
