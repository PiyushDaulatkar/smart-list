package com.smartlist.core_api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

    // INSIGHT : Below is equivalent to:
    //    public static final ErrorCode SUPERLIST_NOT_FOUND =
    //    new ErrorCode(HttpStatus.NOT_FOUND, "SUPERLIST_NOT_FOUND");
    // Business errors
    // NOT_FOUND
    SUPERLIST_NOT_FOUND(HttpStatus.NOT_FOUND, "SUPERLIST_NOT_FOUND"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND"),
    LIST_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "LIST_ITEM_NOT_FOUND"),
    // BAD_REQUEST
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "INVALID_REQUEST"),
    ITEM_NOT_BELONG_TO_SUPERLIST(HttpStatus.BAD_REQUEST, "ITEM_NOT_BELONG_TO_SUPERLIST"),

    // Validation errors
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR"),

    // System errors
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR");

    private final HttpStatus status;
    private final String code;

    ErrorType(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }
}
