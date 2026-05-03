package com.smartlist.core_api.dto.superlist;

import com.smartlist.core_api.dto.listItem.ListItemResponse;

import java.util.List;

public record SuperListDetailResponse(Long id, String name, List<ListItemResponse> items) {
}
