package com.smartlist.core_api.repository;

import com.smartlist.core_api.entity.ListItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListItemRepository extends JpaRepository<ListItem, Long> {
}
