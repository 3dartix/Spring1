package ru.geekbrains.springbootlesson.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.springbootlesson.persist.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
