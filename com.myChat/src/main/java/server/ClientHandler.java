package server;

import server.auth.SqlQueryHelper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ClientHandler {
    private Socket socket;
    private DataInputStream in; //входящий поток
    private DataOutputStream out; //исходящий поток
    private ChatServer server;
    private SqlQueryHelper sqlQueryHelper;
    public String nick;
    public String id;
    public List<String> blackList;

    public ClientHandler(ChatServer server, Socket socket, SqlQueryHelper sqlQueryHelper) {
        try {
            this.sqlQueryHelper = sqlQueryHelper;
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            blackList = new ArrayList<>();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/auth")) { //начинается ли строка с символа

                                LogSave.addLog("client try auth: " + socket.getRemoteSocketAddress());

                                String[] tokens = str.split(" ");
                                String currentNick = sqlQueryHelper.getNickByLoginAndPass(tokens[1], tokens[2]);
                                System.out.println(server.isNickOnline(tokens[1]));
                                if (currentNick != null && !server.isNickOnline(currentNick)) {
                                    sendMessage("/authok" + " " + currentNick);

                                    LogSave.addLog("client auth ok: " + socket.getRemoteSocketAddress());

                                    nick = currentNick;

                                    id = sqlQueryHelper.getIdByNick(nick);
                                    LogSave.addLog("client received a nick: " + socket.getRemoteSocketAddress());
                                    blackList = sqlQueryHelper.getArrBlackList(id);

                                    server.subscribe(ClientHandler.this);

                                    // история
                                    ArrayList<String> history = sqlQueryHelper.getHistoryFromBD();
                                    history.forEach(el -> {
                                        server.sendMessageToUser(nick, el);
                                    });

                                    break;
                                } else {
                                    LogSave.addLog("entered an invalid login or password: " + socket.getRemoteSocketAddress());
                                    sendMessage("неверный логин/пароль");
                                }
                            }
                        }

                        while (true) {
                            String str = in.readUTF();
                            LogSave.addLog("client sent a message: " + socket.getRemoteSocketAddress());
                            if (str.startsWith("/")){
                                if (str.equalsIgnoreCase("/end")) {
                                    sendMessage("/clientClose");

                                    LogSave.addLog("received a symbol /end: " + socket.getRemoteSocketAddress());

                                    break;
                                } else if (str.startsWith("/w")){
                                    String[] tokens = str.split(" ", 3); //сплитаем на 3 части

                                    LogSave.addLog("received a symbol /w: " + socket.getRemoteSocketAddress());

                                    server.sendMessageToUser(nick, tokens[1], tokens[2]);
                                }  else if (str.startsWith("/blacklist")){
                                    String[] tokens = str.split(" "); //сплитаем на 3 части
                                    out.writeUTF("Пользователь " + tokens[1] + " заблокирован!");

                                    LogSave.addLog("received a symbol /blacklist: user " + tokens[1] +" blocked" + socket.getRemoteSocketAddress());

                                    addUserToBlackList(tokens[1]); //блокируем пользователя
                                }
                            } else {
                                //server.BroadcastMessage(str);
                                server.broadcastMessage(ClientHandler.this, nick + ": " + str);

                                //отправляем сообщение в историю
                                sqlQueryHelper.addMessageToHistory(nick, str);

                                System.out.println(nick + ": " + str);

                            }
                        }

                    } catch (SQLException e) {
                        LogSave.addLog(e.getMessage() + socket.getRemoteSocketAddress(), Level.SEVERE);
                        e.printStackTrace();
                    } catch (IOException e) {
                        LogSave.addLog(e.getMessage() + socket.getRemoteSocketAddress(), Level.SEVERE);
                        e.printStackTrace();
                    } finally {
                        try {
                            LogSave.addLog("Closed DataOutputStream: " + socket.getRemoteSocketAddress());
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            LogSave.addLog("Closed DataInputStream: " + socket.getRemoteSocketAddress());
                            in.close();
                        } catch (IOException e) {
                            LogSave.addLog("closing DataInputStream fail " + e.getMessage()+ " " +  socket.getRemoteSocketAddress(), Level.SEVERE);
                            e.printStackTrace();
                        }
                        try {
                            LogSave.addLog("Closed DataInputStream: " + socket.getRemoteSocketAddress());
                            socket.close();
                        } catch (IOException e) {
                            LogSave.addLog("closing socket fail: " + e.getMessage()+ " " + socket.getRemoteSocketAddress(), Level.SEVERE);
                            e.printStackTrace();
                        }
                        server.unsubscribe(ClientHandler.this);
                    }
                }
            }).start();
        } catch (IOException e) {
            LogSave.addLog("Other error: " + e.getMessage()+ " " + socket.getRemoteSocketAddress(), Level.SEVERE);
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg){
        try{
            out.writeUTF(msg + "\n");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean cheeckBlackList(String name){
        System.out.println(blackList.size());
        return blackList.contains(name);
    }
    
    public void addUserToBlackList(String user) throws SQLException {
        if(!cheeckBlackList(user)){
            blackList.add(user);
            sqlQueryHelper.addUserToBlackList(id, user);
        }

    }

}
