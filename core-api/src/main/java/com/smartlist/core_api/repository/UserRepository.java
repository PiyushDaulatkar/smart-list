package com.smartlist.core_api.repository;

import com.smartlist.core_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
