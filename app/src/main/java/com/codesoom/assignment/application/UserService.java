package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.UserNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserData userData) {
        User user = User.builder()
                .name(userData.getName())
                .email(userData.getEmail())
                .password(userData.getPassword())
                .build();
        return userRepository.save(user);
    }

    public User updateUser(Long id, UserData userData) {
        User user = findUser(id);

        user.update(
                userData.getName(),
                userData.getEmail(),
                userData.getPassword()
        );

        return user;
    }

    public User deleteUser(Long id) {
        User product = findUser(id);

        userRepository.delete(product);

        return product;
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}