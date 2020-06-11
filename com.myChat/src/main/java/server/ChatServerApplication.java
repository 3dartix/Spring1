package server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.ApplicationContext;

public class ChatServerApplication {
    public static void main(String[] args) {
//        AuthServiceJDBC authServiceJDBC = new AuthServiceJDBC(
//                "jdbc:mysql://localhost:3306/bus_stops_db?useUnicode=true&serverTimezone=UTC",
//                "root",
//                "root"
//        );
//        ChatServer chatServer = new ChatServer(authServiceJDBC);
//        chatServer.start(8189);

//        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringContext.class);
        context.getBean("chatServer", ChatServer.class).start(8189);
    }
}
