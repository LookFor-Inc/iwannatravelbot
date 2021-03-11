package com.lookfor.iwannatravel.services.implementations;

import com.lookfor.iwannatravel.models.User;
import com.lookfor.iwannatravel.repositories.UserRepository;
import com.lookfor.iwannatravel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User fetchByTelegramUserId(Integer telegramUserId) {
        return userRepository.findByTelegramUserId(telegramUserId);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
