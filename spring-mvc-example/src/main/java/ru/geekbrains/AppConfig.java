package ru.geekbrains;

import org.apache.log4j.BasicConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

@EnableWebMvc
@Configuration
@ComponentScan("ru.geekbrains")
public class AppConfig implements WebMvcConfigurer {

    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    //настройка логирования
    @Bean
    public void initLog4j(){
        BasicConfigurator.configure();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //** - уровень вложенности до бесконечности /resources/css/...../style.css
        //* - уровень вложенности до 1 папки /resources/style.css
        registry.addResourceHandler("/resources/**") // /resources/css/style.css
                .addResourceLocations(
                        "/resources/", // WEB-INF/resources
                        "classpath:/css" // /resources/css
                );
    }

    //Для определения контроллером конкретного запроса
    //http://localhost:8080/mvc-app/welcome
    @Bean
    public ViewResolver htmlViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        //хотим обрабатывать страницы с помощью шаблонизатора
        resolver.setTemplateEngine(templateEngine(htmlTemplateResolver()));
        resolver.setContentType("text/html"); //хотим использовать html в качестве представления
        resolver.setCharacterEncoding("UTF-8");
        resolver.setViewNames(new String[] {"*"});
        return resolver;
    }

    //настройки шаблонизатора
    private ITemplateResolver htmlTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(context);
        //все шаблоны находятся в этой папке
        resolver.setPrefix("/WEB-INF/view/");
        //они должны иметь расширение html
        resolver.setSuffix(".html");
        resolver.setCacheable(false);
        resolver.setTemplateMode(TemplateMode.HTML);
        return resolver;
    }

    //настройки шаблонизатора
    private ISpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver);
        return engine;
    }
}
