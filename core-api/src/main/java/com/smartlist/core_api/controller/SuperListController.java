package com.smartlist.core_api.controller;

import com.smartlist.core_api.dto.superlist.CreateSuperListRequest;
import com.smartlist.core_api.dto.superlist.SuperlistResponse;
import com.smartlist.core_api.entity.Superlist;
import com.smartlist.core_api.service.SuperListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/superlists")
public class SuperListController {

    private final SuperListService superListService;

    public SuperListController(SuperListService superListService) {
        this.superListService = superListService;
    }

    @GetMapping
    public List<Superlist> getALl() {
        return superListService.getAll();
    }

    @PostMapping("/user/{userId}")
    public SuperlistResponse createSuperList(@PathVariable Long userId, @RequestBody CreateSuperListRequest superlist) {
        return superListService.createSuperList(userId, superlist);
    }

    @PostMapping("/{superListId}/items")
    public Superlist addItem (@PathVariable Long superListId, @RequestBody String content) {
        return superListService.addItemToSuperList(superListId, content);
    }
}
