package com.smartlist.core_api.service;

import com.smartlist.core_api.entity.ListItem;
import com.smartlist.core_api.repository.ListItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListItemService {
    private final ListItemRepository listItemRepository;

    public ListItemService(ListItemRepository listItemRepository) {
        this.listItemRepository = listItemRepository;
    }

    public List<ListItem> getAll() {
        return listItemRepository.findAll();
    }
}
