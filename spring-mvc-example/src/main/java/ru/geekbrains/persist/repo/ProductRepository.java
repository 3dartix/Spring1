package ru.geekbrains.persist.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.entity.User;

import java.math.BigDecimal;
import java.util.List;

//А если нужна собственная реализация метода, то как ее написать правильно?
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findProductsByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    List<Product> findProductsByOrderByPriceDesc();
}
