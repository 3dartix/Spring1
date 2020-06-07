package ru.geekbrains.springbootlesson.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.springbootlesson.persist.entity.User;
import ru.geekbrains.springbootlesson.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    //private UserRepository userRepository;
    //лучше не использовать напрямую репозитории в контроллере
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userList(Model model) {
        logger.info("User list");

        model.addAttribute("users", userService.findAll());
        return "users";
    }
    //добавляет страницу /user/new
    @GetMapping("new")
    public String createUser(Model model) {
        logger.info("Create user form");
        //передаем в шаблонизатор html страницу user из view
        model.addAttribute("user", new User());
        return "user";
    }

    @PostMapping
    public String saveUser(User user) {
        logger.info("Save user method");

        userService.save(user);
        return "redirect:/user";
    }
}
