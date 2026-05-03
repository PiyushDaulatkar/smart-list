package com.smartlist.core_api.service;

import com.smartlist.core_api.dto.listItem.ListItemRequest;
import com.smartlist.core_api.dto.superlist.CreateSuperListRequest;
import com.smartlist.core_api.dto.superlist.SuperListDetailResponse;
import com.smartlist.core_api.dto.superlist.SuperlistResponse;
import com.smartlist.core_api.entity.ListItem;
import com.smartlist.core_api.entity.Superlist;
import com.smartlist.core_api.entity.User;
import com.smartlist.core_api.mapper.SuperlistMapper;
import com.smartlist.core_api.repository.ListItemRepository;
import com.smartlist.core_api.repository.SuperListRepository;
import com.smartlist.core_api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SuperListService {
    private final SuperListRepository superListRepository;
    private final UserRepository userRepository;
    private final SuperlistMapper superlistMapper;
    private final ListItemRepository listItemRepository;

    public SuperListService(SuperListRepository superListRepository, UserRepository userRepository, SuperlistMapper superlistMapper, ListItemRepository listItemRepository) {
        this.superListRepository = superListRepository;
        this.userRepository = userRepository;
        this.superlistMapper = superlistMapper;
        this.listItemRepository = listItemRepository;
    }

    public List<SuperlistResponse> listSuperListsForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found."));
        List<Superlist> superlists = user.getSuperlists();        
        return superlistMapper.toResponseList(superlists);
    }

    public SuperListDetailResponse getSuperList(Long superListId) {
        Superlist superlist = superListRepository.findById(superListId).orElseThrow(() -> new RuntimeException("Superlist not found."));
        return superlistMapper.toDetailResponse(superlist);
    }

    public SuperlistResponse createSuperList(Long userId, CreateSuperListRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found."));
        Superlist superlist = new Superlist();
        superlist.setName(request.name());
        user.addSuperList(superlist);
        superListRepository.save(superlist);
        return superlistMapper.toResponse(superlist);
    }

    public void deleteSuperList(Long superListId) {
        Superlist superlist = superListRepository.findById(superListId).orElseThrow(() -> new RuntimeException("Superlist not found."));
        superListRepository.delete(superlist);
    }

    public SuperlistResponse addItemToSuperList(Long superListId, ListItemRequest addListItemRequest) {
        Superlist superlist = superListRepository.findById(superListId).orElseThrow(() -> new RuntimeException("Superlist not found."));
        ListItem item = new ListItem();
        item.setContent(addListItemRequest.content());
        superlist.addItem(item);
        superListRepository.save(superlist);
        return superlistMapper.toResponse(superlist);
    }

    @Transactional
    public SuperListDetailResponse updateItem(Long superListId, Long itemId, ListItemRequest listItemRequest) {
        Superlist superlist = superListRepository.findById(superListId).orElseThrow(() -> new RuntimeException("Superlist not found."));
        ListItem item = listItemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("List item not found."));
        if (!item.getSuperlist().getId().equals(superlist.getId())) {
            throw new RuntimeException("Item does not belong to this superlist.");
        }
        item.setContent(listItemRequest.content());
        item.setCompleted(listItemRequest.completed());
        listItemRepository.save(item);
        return superlistMapper.toDetailResponse(superlist);
    }

    public void deleteListItem(Long superListId, long itemId) {
        Superlist superlist = superListRepository.findById(superListId).orElseThrow(() -> new RuntimeException("Superlist not found."));
        ListItem item = listItemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("List item not found."));
        if (!item.getSuperlist().getId().equals(superlist.getId())) {
            throw new RuntimeException("Item does not belong to this superlist.");
        }
        superlist.removeItem(item);
        superListRepository.save(superlist);
    }
}
