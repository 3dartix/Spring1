package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.repo.ProductRepository;
import ru.geekbrains.service.ProductService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

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
      @RequestParam(value = "minPrice", required = false)BigDecimal minPrice,
    */
    @GetMapping
    public String productList(Model model,
                              @RequestParam(value = "minPrice") Optional<BigDecimal> minPrice,
                              @RequestParam(value = "maxPrice") Optional<BigDecimal> maxPrice,
                              @RequestParam(name = "productName") Optional<String> productName,
                              @RequestParam(name = "page") Optional<Integer> page,
                              @RequestParam(name = "pageSize") Optional<Integer> pageSize) {
        logger.info("Product list with minPrice={} and maxPrice={}", minPrice, maxPrice);

        model.addAttribute("productsPage", productService.filterByPrice(minPrice.orElse(null), maxPrice.orElse(null), productName.orElse(""),
                PageRequest.of(page.orElse(1) - 1, pageSize.orElse(5))
        ));
        model.addAttribute("minPrice", minPrice.orElse(null));
        model.addAttribute("maxPrice", maxPrice.orElse(null));
        model.addAttribute("productName", productName.orElse(""));
        model.addAttribute("currentPage", page.orElse(1));
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

    @GetMapping("edit")
    public String createProduct(Model model,
                                @RequestParam(value = "idProduct") Optional<Long> idProduct) {
        logger.info("Edit product form");
        //передаем в шаблонизатор html страницу user из view
        model.addAttribute("product", productService.findById(idProduct.orElse(-1L)));
        return "product";
    }

    @GetMapping("delete")
    public String createProduct(@RequestParam(value = "idProduct") Optional<Long> idProduct) {
        logger.info("Delete product");
        productService.deleteProductByIe(idProduct.orElse(-1L));
        return "redirect:/product";
    }

    @PostMapping
    public String saveProduct(@Valid Product product, BindingResult bindingResult) {
        logger.info("Save product method");

        if(bindingResult.hasErrors()){
            return "product";
        }

        productService.save(product);
        return "redirect:/product";
    }
}
