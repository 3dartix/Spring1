package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.persist.enity.Product;
import ru.geekbrains.persist.repo.ProductRepository;

@RequestMapping("/product")
@Controller
public class ProductController {
    //разобрать логер
    private static final Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    private ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String productList(Model model) {
        logger.info("Product list");

        model.addAttribute("products", productRepository.findAll());
        return "products";
    }
    //добавляет страницу /user/new
    @GetMapping("new")
    public String createProduct(Model model) {
        logger.info("Create product form");
        //передаем в шаблонизатор html страницу user из view
        model.addAttribute("product", new Product());
        return "product";
    }

    @PostMapping
    public String saveProduct(Product product) {
        logger.info("Save product method");

        productRepository.save(product);
        return "redirect:/product";
    }
}
