package com.smartlist.core_api.controller;

import com.smartlist.core_api.dto.listItem.ListItemRequest;
import com.smartlist.core_api.dto.superlist.CreateSuperListRequest;
import com.smartlist.core_api.dto.superlist.SuperListDetailResponse;
import com.smartlist.core_api.dto.superlist.SuperlistResponse;
import com.smartlist.core_api.response.ApiResponse;
import com.smartlist.core_api.service.SuperListService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/superlists")
public class SuperListController {

    private final SuperListService superListService;

    public SuperListController(SuperListService superListService) {
        this.superListService = superListService;
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<SuperlistResponse>> listSuperListsForUser(@PathVariable Long userId) {
        return ApiResponse.success(superListService.listSuperListsForUser(userId));
    }

    @GetMapping("/{superListId}")
    public ApiResponse<SuperListDetailResponse> getSuperList(@PathVariable Long superListId) {
        return ApiResponse.success(superListService.getSuperList(superListId));
    }

    @PostMapping("/user/{userId}")
    public ApiResponse<SuperlistResponse> createSuperList(@PathVariable Long userId, @Valid @RequestBody CreateSuperListRequest superlist) {
        return ApiResponse.success(superListService.createSuperList(userId, superlist));
    }

    @DeleteMapping("/{superListId}")
    // INSIGHT: @ResponseStatus: To define the HTTP status code that should be returned.
    // @ResponseStatus(HttpStatus.NO_CONTENT)
    // ⬆️ 204 No Content is not valid anymore, because there is a body in the response.
    public ApiResponse<Void> deleteSuperList(@PathVariable Long superListId) {
        superListService.deleteSuperList(superListId);
        return ApiResponse.success(null);
    }

    @PostMapping("/{superListId}/items")
    public ApiResponse<SuperlistResponse> addItem (@PathVariable Long superListId, @RequestBody ListItemRequest listItemRequest) {
        return ApiResponse.success(superListService.addItemToSuperList(superListId, listItemRequest));
    }

    // INSIGHT: @PatchMapping is used here because you are doing a partial update, not replacing the entire resource.
    @PatchMapping("/{superListId}/items/{itemId}")
    public ApiResponse<SuperListDetailResponse> patchItem(@PathVariable Long superListId, @PathVariable Long itemId, @RequestBody ListItemRequest listItemRequest) {
        return ApiResponse.success(superListService.updateItem(superListId, itemId, listItemRequest));
    }

    @DeleteMapping("/{superListId}/items/{itemId}")
    public ApiResponse<Void> deleteListItem(@PathVariable Long superListId, @PathVariable Long itemId) {
        superListService.deleteListItem(superListId, itemId);
        return ApiResponse.success(null);
    }
}
