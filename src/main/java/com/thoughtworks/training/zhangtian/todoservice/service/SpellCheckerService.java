package com.thoughtworks.training.zhangtian.todoservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.thoughtworks.training.zhangtian.todoservice.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SpellCheckerService {
    @Autowired
    private SpellChecker spellChecke;

    //@Retryable(maxAttempts = 2,backoff = @Backoff(10))
    @HystrixCommand(fallbackMethod = "checkFallBack")
    public List<User> check(List<User> users) {
        spellChecke.check(users, User::getName, User::setSuggestion);
        return users;
    }

// @Recover
// public List<User> onFailure(RuntimeException e,List<User> users)
// {
//     log.info(e.getMessage());
//     return users;
// }

    public List<User> checkFallBack(List<User> users) {
        log.info("checkFallBack");
        return users;
    }


}
