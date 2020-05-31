package server.beanFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import server.beanFactory.player.RockMusic;

@Component
public class TestBeanPostProcessor implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // Находим бин класса фотокамеры
        if (bean instanceof RockMusic) {
            System.out.println("Меняем значение поля!");
            // Делаем пробное фото
            ((RockMusic) bean).setName("some music...");
        }
        return bean;
    }
}
