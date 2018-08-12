package com.thoughtworks.training.zhangtian.todoservice.service;

import com.thoughtworks.training.zhangtian.todoservice.model.User;
import com.thoughtworks.training.zhangtian.todoservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private SpellCheckerService spellCheckerService;


    public Integer create(User user) {
        String password = user.getPassword();

        user.setPassword(encoder.encode(password));
        return userRepository.save(user).getId();
    }

    public User getOne(Integer id) {
        return userRepository.findOne(id);
    }

    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        spellCheckerService.check(users);
        return users;
    }

    public Boolean validate(User user) {
        Optional<User> optionalUser = userRepository.findOneByName(user.getName());
        return optionalUser.filter(u -> encoder.matches(user.getPassword(), u.getPassword())).isPresent();
    }

    public User validateLogin(User user) {
        Optional<User> optionalUser = userRepository.findOneByName(user.getName());
        if (optionalUser.filter(u -> encoder.matches(user.getPassword(), u.getPassword())).isPresent()) {
            return optionalUser.get();
        }

        return null;
    }


    public User getUserByName(String name) {

        Optional<User> optionalUser = userRepository.findOneByName(name);

        return optionalUser.orElse(null);

    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        int id = (int) authentication.getPrincipal();

        return userRepository.findOne(id);
    }
}
