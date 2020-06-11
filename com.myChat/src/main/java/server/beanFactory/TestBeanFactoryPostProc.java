package server.beanFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import server.beanFactory.player.UnproducableClassicMusic;

@Component
public class TestBeanFactoryPostProc implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for(String name: beanDefinitionNames) {
            // Получение BeanDefinition по имени
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
            // Вывод информации о BeanDefinition
            System.out.println(name);

            /*Получаем имя класса создаваемого бина, чтобы проверить,
             * содержит ли он аннотацию UnproducableClassicMusic
             */
            String className = beanDefinition.getBeanClassName();
// на классе testBeanFactoryPostProc className почему-то равен null
            if(className != null) {
                try {
                    // Получаем класс по имени
                    Class<?> beanClass = Class.forName(className);

                    /*Пытаемся получить объект аннотации и ее значение,
                     * если  класс не содержит данную аннотацию, то  метод вернет null
                     */
                    UnproducableClassicMusic annotation = beanClass.getAnnotation(UnproducableClassicMusic.class);
                    // Проверяем, содержал ли класс эту аннотацию
                    if(annotation != null) {
                        // Получаем значение, указанное в параметрах аннотации (класс пленки, которую необходимо использовать)
                        Class usingMusicClass = annotation.usingMusicClass();
                        // Меняем класс будущего бина
                        beanDefinition.setBeanClassName(usingMusicClass.getName());
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }






        }
    }
}
