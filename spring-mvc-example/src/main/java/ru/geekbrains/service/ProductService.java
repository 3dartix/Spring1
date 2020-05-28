package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.repo.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

// для описания бизнес логики
@Service
public class ProductService {
    private ProductRepository repository;

    private int maxNumberOfEntries = 5;

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
    public List<Product> filterByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        if(null == minPrice && null != maxPrice) {
            //если первое значение нулл, а второе число
            minPrice = new BigDecimal(0);
            return repository.findProductsByPriceBetween(minPrice, maxPrice, PageRequest.of(0,maxNumberOfEntries)).getContent();
        } else if (null != minPrice && null == maxPrice) {
            //если перво значение число, а второе null
            maxPrice = repository.findProductsByOrderByPriceDesc().get(0).getPrice();
            return repository.findProductsByPriceBetween(minPrice, maxPrice, PageRequest.of(0,maxNumberOfEntries)).getContent();
        } else if (null == minPrice && null == maxPrice) {
            //если оба значения null
//            return repository.findAll();
            Page<Product> page = repository.findProductsByPriceBetween(new BigDecimal(0),
                    repository.findProductsByOrderByPriceDesc().get(0).getPrice(),
                    PageRequest.of(0,maxNumberOfEntries));

            return repository.findProductsByPriceBetween(new BigDecimal(0),
                    repository.findProductsByOrderByPriceDesc().get(0).getPrice(),
                    PageRequest.of(0,maxNumberOfEntries)).getContent();
        } else {
            //если оба значения не null
            return repository.findProductsByPriceBetween(minPrice, maxPrice, PageRequest.of(0,maxNumberOfEntries)).getContent();
        }
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
}
