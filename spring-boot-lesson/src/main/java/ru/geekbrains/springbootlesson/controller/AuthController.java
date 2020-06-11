package ru.geekbrains.springbootlesson.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.springbootlesson.persist.entity.Product;
import ru.geekbrains.springbootlesson.persist.repo.ProductRepository;

@Controller
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    @GetMapping("/login")
    public String showMyLoginPage() {
        logger.info("login");
        return "login";
    }
}
