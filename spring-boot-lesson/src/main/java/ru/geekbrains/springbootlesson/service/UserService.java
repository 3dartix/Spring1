package ru.geekbrains.springbootlesson.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.springbootlesson.persist.entity.User;
import ru.geekbrains.springbootlesson.persist.repo.UserRepository;

import java.util.List;
import java.util.Optional;

// для описания бизнес логики
@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    //нужны транзакции
    public List<User> findAll() {
        return repository.findAll();
    }

    //нужны транзакции
    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    @Transactional(readOnly = true)
    //нужны транзакции
    public Optional<User> findById(long id) {
        return repository.findById(id);
    }
}
