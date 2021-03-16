package com.lookfor.iwannatravel.repositories;

import com.lookfor.iwannatravel.models.Country;
import com.lookfor.iwannatravel.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByTelegramUserId(Integer telegramUserId);
}
