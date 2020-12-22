package com.github.srg13.votingsystem.web.user;

import com.github.srg13.votingsystem.model.User;
import com.github.srg13.votingsystem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.github.srg13.votingsystem.util.ValidationUtil.assureIdConsistent;
import static com.github.srg13.votingsystem.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected UserService service;

    public User get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return service.getByEmail(email);
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        service.create(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return service.create(user);
    }
}
