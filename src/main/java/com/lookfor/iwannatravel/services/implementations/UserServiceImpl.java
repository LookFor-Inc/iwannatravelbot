package com.lookfor.iwannatravel.services.implementations;

import com.lookfor.iwannatravel.models.User;
import com.lookfor.iwannatravel.repositories.UserRepository;
import com.lookfor.iwannatravel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Collections;

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

    @Override
    public void saveUpdates(Message message) {
        String username = message.getFrom().getUserName();
        if (username == null) {
            username = message.getFrom().getFirstName();
        }

        Integer userId = message.getFrom().getId();
        User user = fetchByTelegramUserId(userId);

        if (user == null) {
            user = User.builder()
                    .telegramUserId(userId)
                    .username(username)
                    .countries(Collections.emptySet())
                    .build();
            save(user);
        }
    }
}
