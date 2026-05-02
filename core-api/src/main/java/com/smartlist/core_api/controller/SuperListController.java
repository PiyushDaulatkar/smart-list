package com.smartlist.core_api.controller;

import com.smartlist.core_api.dto.listItem.AddListItemRequest;
import com.smartlist.core_api.dto.superlist.CreateSuperListRequest;
import com.smartlist.core_api.dto.superlist.SuperlistResponse;
import com.smartlist.core_api.service.SuperListService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/superlists")
public class SuperListController {

    private final SuperListService superListService;

    public SuperListController(SuperListService superListService) {
        this.superListService = superListService;
    }

    @PostMapping("/user/{userId}")
    public SuperlistResponse createSuperList(@PathVariable Long userId, @RequestBody CreateSuperListRequest superlist) {
        return superListService.createSuperList(userId, superlist);
    }

    @PostMapping("/{superListId}/items")
    public SuperlistResponse addItem(@PathVariable Long superListId, @RequestBody AddListItemRequest listItemRequest) {
        return superListService.addItemToSuperList(superListId, listItemRequest);
    }
}
