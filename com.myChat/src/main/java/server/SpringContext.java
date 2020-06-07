package server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import server.auth.AuthServiceJDBCImpl;
import server.auth.IAuthService;

@Configuration
@ComponentScan(basePackages = "server")
public class SpringContext {
    @Bean
    public IAuthService authServiceJDBC(){
        return new AuthServiceJDBCImpl(
                "jdbc:mysql://localhost:3306/bus_stops_db?useUnicode=true&serverTimezone=UTC",
                "root",
                "root"
        );
    }
    @Bean
    public ChatServer chatServer(){
        return new ChatServer(authServiceJDBC());
    }
}
