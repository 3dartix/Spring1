package server.beanFactory.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Player {
    private Music music;

//    @Autowired
    public void setMusic(Music music) {
        this.music = music;
    }

    public void Play(){
        music.Play();
    }
}
