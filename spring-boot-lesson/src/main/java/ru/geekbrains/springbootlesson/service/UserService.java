package ru.geekbrains.springbootlesson.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.springbootlesson.persist.entity.User;
import ru.geekbrains.springbootlesson.persist.repo.UserRepository;

import java.util.List;
import java.util.Optional;

// для описания бизнес логики
@Service
public class UserService {
    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    //нужны транзакции
    public List<User> findAll() {
        return repository.findAll();
    }

    //нужны транзакции
    @Transactional
    public void save(User user) {
        repository.save(user);
    }

    @Transactional(readOnly = true)
    //нужны транзакции
    public Optional<User> findById(long id) {
        return repository.findById(id);
    }
}
