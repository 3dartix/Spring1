package server.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import server.LogSave;

import java.sql.*;
//@Component
public class AuthServiceJDBCImpl implements IAuthService {
    private Connection connection;
    private SqlQueryHelper sqlQueryHelper;
//    @Value("Соединение1")
    private String nameConnect;

    public void setNameConnect(String nameConnect) {
        this.nameConnect = nameConnect;
    }

    public String getNameConnect() {
        return nameConnect;
    }

    public AuthServiceJDBCImpl(String url, String username, String pass) {
        try {
            connection = DriverManager.getConnection(url, username, pass);
            sqlQueryHelper = new SqlQueryHelper(connection);
            LogSave.addLog("connected to db");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public SqlQueryHelper getSqlQueryHelper() {
        return sqlQueryHelper;
    }
    public void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
