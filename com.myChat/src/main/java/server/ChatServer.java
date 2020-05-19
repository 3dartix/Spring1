package server;

import org.springframework.stereotype.Component;
import server.auth.IAuthService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;

@Component
public class ChatServer {
    private Vector<ClientHandler> clients;
    private IAuthService authService;

    public ChatServer(IAuthService authService) {
        this.authService = authService;
    }

    public void start(int port) {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;

        Scanner console = new Scanner(System.in);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String str = console.nextLine();
                    broadcastMessage("Server: "+ str);
                    LogSave.addLog("Message from console: " + str);
                }
            }
        }).start();

        try {
            server = new ServerSocket(port);
            LogSave.addLog("server started");

            while (true) {
                socket = server.accept();
                LogSave.addLog("client connected");
                new ClientHandler(this, socket, authService.getSqlQueryHelper());
                LogSave.addLog("new client: " + socket.getRemoteSocketAddress(), Level.CONFIG);
            }

        } catch (IOException e) {
            LogSave.addLog(e.getMessage(), Level.SEVERE);
            e.printStackTrace();
        } finally {
            try {
                LogSave.addLog("client disable: " + socket.getRemoteSocketAddress());
                socket.close();
            } catch (IOException e) {
                LogSave.addLog("client disable fail: " + e.getMessage(), Level.SEVERE);
                e.printStackTrace();
            }
            try {
                LogSave.addLog("server disable: " + socket.getRemoteSocketAddress());
                server.close();
            } catch (IOException e) {
                LogSave.addLog("server disable fail: " + e.getMessage(), Level.SEVERE);
                e.printStackTrace();
            }
        }
        authService.disconnect();
    }

    public void broadcastMessage(String msg){
        for (ClientHandler el: clients) {
            el.sendMessage(msg);
        }
    }

    public void broadcastMessage(ClientHandler client, String msg){
        for (ClientHandler el: clients) {
            if (!el.cheeckBlackList(client.nick)) {
                el.sendMessage(msg);
            }
        }
    }

    public void broadcastClientsList() {
        StringBuilder sb = new StringBuilder(); //позволяет изменять строку, а не создает новую(для многоп. прилож)
        sb.append("/clientList ");
        for (ClientHandler o : clients) {
            sb.append(o.nick + " ");
        }
        String out = sb.toString();
        for (ClientHandler o : clients) {
            o.sendMessage(out);
        }
    }

    public void sendMessageToUser(String to, String msg){
        for (ClientHandler client: clients) {
            if(to.equalsIgnoreCase(client.nick)){
                client.sendMessage(msg);
            }
        }
    }

    public void sendMessageToUser(String from, String to, String msg){
        for (ClientHandler client: clients) {
            if(to.equalsIgnoreCase(client.nick)){
                client.sendMessage("From " + from + ": " + msg);
            }
        }
    }

    public void subscribe(ClientHandler client) {
        clients.add(client);
        broadcastClientsList();
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
        broadcastClientsList();
    }

    public boolean isNickOnline (String nick) {
        boolean result = false;
        for (ClientHandler client: clients) {
            System.out.println(client.nick + "   " + nick);
            if (nick.equalsIgnoreCase(client.nick)){
                result = true;
                break;
            }
        }
        return result;
    }

}
