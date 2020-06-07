package ru.geekbrains.springbootlesson.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.springbootlesson.persist.entity.Product;
import ru.geekbrains.springbootlesson.persist.repo.ProductRepository;
import ru.geekbrains.springbootlesson.persist.repo.ProductSpecification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

// для описания бизнес логики
@Service
public class ProductService {
    private ProductRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

//    @Transactional(readOnly = true)
//    //нужны транзакции
//    public List<Product> findAll() {
//        return repository.findAll();
//    }

    public void nextPage(){
    }

    @Transactional(readOnly = true)
    //нужны транзакции
    public Page<Product> filterByPrice(BigDecimal minPrice, BigDecimal maxPrice, String productName, Pageable pageable) {
        Specification<Product> specification = ProductSpecification.trueLiteral();

        if(minPrice != null) {
            specification = specification.and(ProductSpecification.priceGreaterThanEqual(minPrice));
        }

        if(maxPrice != null){
            specification = specification.and(ProductSpecification.priceLessThanEqual(maxPrice));
        }

        if(productName != null && !productName.isEmpty()){
            logger.info("productName:" + productName);
            specification = specification.and(ProductSpecification.productName(productName));
        }

        return repository.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll (){
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Product findProductById(Long id){
        return repository.findProductsById(id);
    }

    //нужны транзакции
    @Transactional
    public void save(Product user) {
        repository.save(user);
    }

    @Transactional(readOnly = true)
    //нужны транзакции
    public Optional<Product> findById(long id) {
        return repository.findById(id);
    }

    @Transactional
    public void deleteProductByIe(Long id){
        repository.deleteProductById(id);
    }
}
