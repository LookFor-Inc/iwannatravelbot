package com.lookfor.iwannatravel.repositories;

import com.lookfor.iwannatravel.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByTelegramUserId(Integer telegramUserId);
}
