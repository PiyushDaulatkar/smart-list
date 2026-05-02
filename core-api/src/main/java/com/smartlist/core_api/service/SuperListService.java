package com.smartlist.core_api.service;

import com.smartlist.core_api.dto.superlist.CreateSuperListRequest;
import com.smartlist.core_api.dto.superlist.SuperlistResponse;
import com.smartlist.core_api.entity.ListItem;
import com.smartlist.core_api.entity.Superlist;
import com.smartlist.core_api.entity.User;
import com.smartlist.core_api.mapper.SuperlistMapper;
import com.smartlist.core_api.repository.SuperListRepository;
import com.smartlist.core_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperListService {
    private final SuperListRepository superListRepository;
    private final UserRepository userRepository;
    private final SuperlistMapper superlistMapper;

    public SuperListService(SuperListRepository superListRepository, UserRepository userRepository, SuperlistMapper superlistMapper) {
        this.superListRepository = superListRepository;
        this.userRepository = userRepository;
        this.superlistMapper = superlistMapper;
    }

    public List<Superlist> getAll() {
        return superListRepository.findAll();
    }

    public SuperlistResponse createSuperList(Long userId, CreateSuperListRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found."));
        Superlist superlist = new Superlist();
        superlist.setName(request.name());
        user.addSuperList(superlist);
        superListRepository.save(superlist);
        return superlistMapper.toResponse(superlist);
    }

    public Superlist addItemToSuperList(Long superListId, String content){
        Superlist superlist = superListRepository.findById(superListId).orElseThrow(() -> new RuntimeException("Superlist not found."));
        ListItem item = new ListItem();
        item.setContent(content);
        superlist.addItem(item);
        return superListRepository.save(superlist);
    }
}
