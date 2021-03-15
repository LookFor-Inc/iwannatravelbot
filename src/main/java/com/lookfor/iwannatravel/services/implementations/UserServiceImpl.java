package com.lookfor.iwannatravel.services.implementations;

import com.lookfor.iwannatravel.models.User;
import com.lookfor.iwannatravel.repositories.UserRepository;
import com.lookfor.iwannatravel.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
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
        var userFrom = message.getFrom();
        Integer userId = userFrom.getId();
        String username = userFrom.getUserName();
        if (username == null) {
            username = userFrom.getFirstName();
        }
        log.info(String.format(
                "From @%s (%s): '%s'", username, userId, message.getText())
        );

        User user = fetchByTelegramUserId(userId);
        // Save new user if doesn't exist
        if (user == null) {
            user = User.builder()
                    .telegramUserId(userId)
                    .username(username)
                    // .countries(Collections.emptySet())
                    .build();
            save(user);
        }
    }
}
