package server;

import java.util.Date;

public class ClientsHistory implements Comparable<ClientsHistory> {
    public Date date;
    public String name;
    public String message;

    public ClientsHistory(Date date, String name, String message) {
        this.date = date;
        this.name = name;
        this.message = message;
    }

    public int compareTo(ClientsHistory h){
        return date.compareTo(h.date);
    }

    public String GetNickAndMessage(){
        return name + ": " + message;
    }
}
