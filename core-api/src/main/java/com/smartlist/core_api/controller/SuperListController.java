package com.smartlist.core_api.controller;

import com.smartlist.core_api.dto.listItem.ListItemRequest;
import com.smartlist.core_api.dto.superlist.CreateSuperListRequest;
import com.smartlist.core_api.dto.superlist.SuperListDetailResponse;
import com.smartlist.core_api.dto.superlist.SuperlistResponse;
import com.smartlist.core_api.service.SuperListService;
import org.springframework.http.HttpStatus;
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
    public List<SuperlistResponse> listSuperListsForUser(@PathVariable Long userId) {
        return superListService.listSuperListsForUser(userId);
    }

    @GetMapping("/{superListId}")
    public SuperListDetailResponse getSuperList(@PathVariable Long superListId) {
        return superListService.getSuperList(superListId);
    }

    @PostMapping("/user/{userId}")
    public SuperlistResponse createSuperList(@PathVariable Long userId, @RequestBody CreateSuperListRequest superlist) {
        return superListService.createSuperList(userId, superlist);
    }

    @DeleteMapping("/{superListId}")
    // INSIGHT: @ResponseStatus: To define the HTTP status code that should be returned.
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSuperList(@PathVariable Long superListId) {
        superListService.deleteSuperList(superListId);
    }

    @PostMapping("/{superListId}/items")
    public SuperlistResponse addItem (@PathVariable Long superListId, @RequestBody ListItemRequest listItemRequest) {
        return superListService.addItemToSuperList(superListId, listItemRequest);
    }

    // INSIGHT: @PatchMapping is used here because you are doing a partial update, not replacing the entire resource.
    @PatchMapping("/{superListId}/items/{itemId}")
    public SuperListDetailResponse patchItem(@PathVariable Long superListId, @PathVariable Long itemId, @RequestBody ListItemRequest listItemRequest) {
        return superListService.updateItem(superListId, itemId, listItemRequest);
    }

    @DeleteMapping("/{superListId}/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteListItem(@PathVariable Long superListId, @PathVariable Long itemId) {
        superListService.deleteListItem(superListId, itemId);
    }
}
