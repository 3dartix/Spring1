package server.beanFactory.player;

import org.springframework.stereotype.Component;

@Component
@UnproducableClassicMusic(usingMusicClass = RockMusic.class)
public class ClassicMusic implements Music{
    private String name = "Classic music play...";
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public void Play() {
        System.out.printf(name);
    }
}
