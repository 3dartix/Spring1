package ru.geekbrains.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.persist.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
//JpaRepository<User - основной класс , Long - тип ключа >

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

//@Repository
//public class UserRepository {
//
//    @PersistenceContext //внедряет энтити менеджер
//    private EntityManager em;
//
//    //нужны транзакции
//    public List<User> findAll() {
//        return em.createQuery("from User", User.class)
//                .getResultList();
//    }
//
//    //нужны транзакции
//    public void save(User user) {
//        em.persist(user);
//    }
//
//    //нужны транзакции
//    public User findById(long id) {
//        return em.find(User.class, id);
//    }
//}
