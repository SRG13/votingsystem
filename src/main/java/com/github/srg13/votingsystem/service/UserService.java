package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.AuthorizedUser;
import com.github.srg13.votingsystem.dao.UserDao;
import com.github.srg13.votingsystem.util.exception.NotFoundException;
import com.github.srg13.votingsystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.github.srg13.votingsystem.util.UserUtil.prepareToSave;

@Service
public class UserService implements UserDetailsService {

    private final UserDao repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDao repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
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
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(prepareToSave(user, passwordEncoder));
    }
}
