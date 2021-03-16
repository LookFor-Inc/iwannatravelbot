package com.lookfor.iwannatravel.services.implementations;

import com.lookfor.iwannatravel.exceptions.CountryNotFoundException;
import com.lookfor.iwannatravel.exceptions.IncorrectRequestException;
import com.lookfor.iwannatravel.exceptions.UserNotFoundException;
import com.lookfor.iwannatravel.models.Country;
import com.lookfor.iwannatravel.models.User;
import com.lookfor.iwannatravel.repositories.UserRepository;
import com.lookfor.iwannatravel.services.CountryService;
import com.lookfor.iwannatravel.services.TrajectoryService;
import com.lookfor.iwannatravel.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CountryService countryService;
    private final TrajectoryService trajectoryService;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByTelegramUserId(Integer telegramUserId) {
        return userRepository.findByTelegramUserId(telegramUserId);
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

        Optional<User> userOptional = findByTelegramUserId(userId);
        if (userOptional.isEmpty()) {
            User user = User.builder()
                    .telegramUserId(userId)
                    .username(username)
                    .trajectories(Collections.emptySet())
                    .build();
            save(user);
        }
    }

    @Override
    @Transactional
    public void saveUserDepartureCountry(
            Integer userId,
            String countryName
    ) throws CountryNotFoundException, UserNotFoundException {
        Optional<User> userOptional = findByTelegramUserId(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        User user = userOptional.get();
        trajectoryService.removeUser(user);

        Optional<Country> countryOptional = countryService.findCountryByName(countryName);
        if (countryOptional.isEmpty()) {
            throw new CountryNotFoundException(countryName);
        }
        user.setCountry(countryOptional.get());
    }

    @Override
    @Transactional
    public void saveUserArrivalCountry(
            Integer userId,
            String countryName
    ) throws CountryNotFoundException, UserNotFoundException, IncorrectRequestException {
        Optional<Country> countryOptional = countryService.findCountryByName(countryName);
        if (countryOptional.isEmpty()) {
            throw new CountryNotFoundException(countryName);
        }

        Optional<User> userOptional = findByTelegramUserId(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        Country country = countryOptional.get();
        User user = userOptional.get();
        if (country.getId() == user.getCountry().getId()) {
            throw new IncorrectRequestException(
                    String.format(
                            "Country %s cannot be both destination and arrival",
                            country.getEn())
            );
        }
        trajectoryService.saveByUserAndCountries(user, user.getCountry(), country);
    }

    @Override
    @Transactional
    public List<String> fetchUserArrivalCountries(Integer userId) throws UserNotFoundException {
        Optional<User> userOptional = findByTelegramUserId(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        User user = userOptional.get();
        return user.getTrajectories().stream()
                .map(trajectory -> trajectory.getArrivalCountry().getEn())
                .collect(Collectors.toList());
    }
}
