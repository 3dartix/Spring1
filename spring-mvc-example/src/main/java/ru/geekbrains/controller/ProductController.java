package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.repo.ProductRepository;
import ru.geekbrains.service.ProductService;

import java.math.BigDecimal;

@RequestMapping("/product")
@Controller
public class ProductController {
    //разобрать логер
    private static final Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    //private ProductRepository productRepository;
    //лучше не использовать напрямую репозитории в контроллере
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /*
      @RequestParam("mixPrice")BigDecimal minPrice,
      @RequestParam("maxPrice")BigDecimal maxPrice
      сортировка minPrice maxPrice получаем из get запроса (products.html)
      / defaultValue = "0"
      //пагинация
      @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    */
    @GetMapping
    public String productList(Model model,
                              @RequestParam(value = "minPrice", required = false)BigDecimal minPrice,
                              @RequestParam(value = "maxPrice", required = false)BigDecimal maxPrice) {
        logger.info("Product list with minPrice={} and maxPrice={}", minPrice, maxPrice);

        model.addAttribute("products", productService.filterByPrice(minPrice, maxPrice));
//        model.addAttribute("products", productService.findAll());
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

        productService.save(product);
        return "redirect:/product";
    }
}
