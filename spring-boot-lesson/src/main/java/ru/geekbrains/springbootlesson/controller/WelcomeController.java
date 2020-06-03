package ru.geekbrains.springbootlesson.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    //обработка запросов
    @GetMapping("/welcome")
    public String welcome() {
        //в качестве представления возвращаем имя
        //.html задаем в настройках AppConfig
        return "welcome";
    }
}
