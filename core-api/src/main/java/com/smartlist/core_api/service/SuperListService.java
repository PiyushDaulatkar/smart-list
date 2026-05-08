package com.smartlist.core_api.service;

import com.smartlist.core_api.dto.listItem.ListItemRequest;
import com.smartlist.core_api.dto.superlist.CreateSuperListRequest;
import com.smartlist.core_api.dto.superlist.SuperListDetailResponse;
import com.smartlist.core_api.dto.superlist.SuperlistResponse;
import com.smartlist.core_api.entity.ListItem;
import com.smartlist.core_api.entity.Superlist;
import com.smartlist.core_api.entity.User;
import com.smartlist.core_api.exception.ApiException;
import com.smartlist.core_api.exception.ErrorType;
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
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException("User not found.", ErrorType.USER_NOT_FOUND));
        List<Superlist> superlists = user.getSuperlists();        
        return superlistMapper.toResponseList(superlists);
    }

    public SuperListDetailResponse getSuperList(Long superListId) {
        Superlist superlist = superListRepository.findById(superListId).orElseThrow(() -> new ApiException("Superlist not found.", ErrorType.SUPERLIST_NOT_FOUND));
        return superlistMapper.toDetailResponse(superlist);
    }

    public SuperlistResponse createSuperList(Long userId, CreateSuperListRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException("User not found.", ErrorType.USER_NOT_FOUND));
        Superlist superlist = new Superlist();
        superlist.setName(request.name());
        user.addSuperList(superlist);
        superListRepository.save(superlist);
        return superlistMapper.toResponse(superlist);
    }

    public void deleteSuperList(Long superListId) {
        Superlist superlist = superListRepository.findById(superListId).orElseThrow(() -> new ApiException("Superlist not found.", ErrorType.SUPERLIST_NOT_FOUND));
        superListRepository.delete(superlist);
    }

    public SuperlistResponse addItemToSuperList(Long superListId, ListItemRequest addListItemRequest) {
        Superlist superlist = superListRepository.findById(superListId).orElseThrow(() -> new ApiException("Superlist not found.", ErrorType.SUPERLIST_NOT_FOUND));
        ListItem item = new ListItem();
        item.setContent(addListItemRequest.content());
        superlist.addItem(item);
        superListRepository.save(superlist);
        return superlistMapper.toResponse(superlist);
    }

    @Transactional
    public SuperListDetailResponse updateItem(Long superListId, Long itemId, ListItemRequest listItemRequest) {
        Superlist superlist = superListRepository.findById(superListId).orElseThrow(() -> new ApiException("Superlist not found.", ErrorType.SUPERLIST_NOT_FOUND));
        ListItem item = listItemRepository.findById(itemId).orElseThrow(() -> new ApiException("List item not found.", ErrorType.LIST_ITEM_NOT_FOUND));
        if (!item.getSuperlist().getId().equals(superlist.getId())) {
            throw new ApiException("Item does not belong to this superlist.", ErrorType.ITEM_NOT_BELONG_TO_SUPERLIST);
        }
        item.setContent(listItemRequest.content());
        item.setCompleted(listItemRequest.completed());
        listItemRepository.save(item);
        return superlistMapper.toDetailResponse(superlist);
    }

    public void deleteListItem(Long superListId, long itemId) {
        Superlist superlist = superListRepository.findById(superListId).orElseThrow(() -> new ApiException("Superlist not found.", ErrorType.SUPERLIST_NOT_FOUND));
        ListItem item = listItemRepository.findById(itemId).orElseThrow(() -> new ApiException("List item not found.", ErrorType.LIST_ITEM_NOT_FOUND));
        if (!item.getSuperlist().getId().equals(superlist.getId())) {
            throw new ApiException("Item does not belong to this superlist.", ErrorType.ITEM_NOT_BELONG_TO_SUPERLIST);
        }
        superlist.removeItem(item);
        superListRepository.save(superlist);
    }
}
