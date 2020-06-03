package server.beanFactory.player;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import server.SpringContext;

public class Main {
    public static void main(String[] args) {
//        Music classicMusic = new RockMusic();
//        Player player = new Player();
//        player.setMusic(classicMusic);
//        player.Play();
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringContext.class);
        Player player = context.getBean("player", Player.class);
        player.setMusic(context.getBean("classicMusic", Music.class));
        player.Play();
    }
}
