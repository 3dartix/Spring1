package server.beanFactory.player;

import org.springframework.stereotype.Component;

@Component
public class RockMusic implements Music {
    private String name = "Rock music play...";
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public void Play() {
        System.out.printf(name);
    }
}
