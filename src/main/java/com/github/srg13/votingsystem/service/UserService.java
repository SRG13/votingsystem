package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.AuthorizedUser;
import com.github.srg13.votingsystem.dao.UserDao;
import com.github.srg13.votingsystem.exception.NotFoundException;
import com.github.srg13.votingsystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private UserDao repository;

    @Autowired
    public UserService(UserDao repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getByEmail(email);

        return new AuthorizedUser(user);
    }

    public User get(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id=" + id + " not found."));
    }

    public User getByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + "not found."));
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    @Transactional
    public void update(User user) {
        repository.save(prepareToSave(user));
    }

    @Transactional
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(prepareToSave(user));
    }

    public User getWithVotes(int id) {
        return repository.getWithVotes(id)
                .orElseThrow(() -> new NotFoundException("User with id=" + id + " not found."));
    }

    public static User prepareToSave(User user) {
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
