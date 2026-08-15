package com.example.events.service;

import org.springframework.stereotype.Service;
import com.example.events.dto.RegisterUserRequest;
import com.example.events.model.User;
import com.example.events.exceptions.DuplicateEmailException;
import com.example.events.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(RegisterUserRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase();

        if (userRepository.findByEmail(normalizedEmail).isPresent()) {
            throw new DuplicateEmailException(normalizedEmail);
        }

        User user = new User();
        user.setEmail(normalizedEmail);
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setFirstName(request.firstName().trim());
        user.setLastName(request.lastName().trim());

        try{
            return userRepository.save(user);
        }catch(DataIntegrityViolationException ex){
            throw new DuplicateEmailException(normalizedEmail);
        }
    }

}
