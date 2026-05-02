package com.smartlist.core_api.repository;

import com.smartlist.core_api.entity.Superlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperListRepository extends JpaRepository<Superlist, Long> {
}
