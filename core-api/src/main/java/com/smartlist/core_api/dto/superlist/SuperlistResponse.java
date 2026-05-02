package com.smartlist.core_api.dto.superlist;

// NOTE: Client needs id for: (for response DTO only, not for request DTO).
//fetching later (GET /superlists/{id})
//updating (PUT)
//deleting (DELETE).
public record SuperlistResponse(Long id, String name) {}
