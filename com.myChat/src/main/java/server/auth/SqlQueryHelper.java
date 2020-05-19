package server.auth;

import server.ClientsHistory;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

public class SqlQueryHelper {
    private Connection connection;
    private Statement stmt;
    private PreparedStatement pstmt;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public SqlQueryHelper(Connection connection) throws SQLException {
        this.connection = connection;
        this.stmt = connection.createStatement();
    }

    public String getNickByLoginAndPass(String login, String pass) throws SQLException {
        String sql = String.format("SELECT nickname FROM main where login = '%s' and password = '%s'", login, pass);
        ResultSet rs = stmt.executeQuery(sql);
        if(rs.next()) {
            return rs.getString(1); //индексанция начинается с 1 (столбик)
        }
        return null;
    }
    public String getIdByNick(String nick) throws SQLException {
        String sql = String.format("SELECT id FROM main where nickname = '%s'", nick);
        ResultSet rs = stmt.executeQuery(sql);
        if(rs.next()) {
            return rs.getString(1); //индексанция начинается с 1 (столбик)
        }
        return null;
    }
    public List<String> getArrBlackList(String id) throws SQLException {
        List<String> blackList = new ArrayList<>();
        String sql = String.format("SELECT * FROM blackList where id = '%s'", id);
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            blackList.add(rs.getString(2));
        }
        return blackList;
    }
    public void addUserToBlackList (String id, String nick) throws SQLException {
        String sql = String.format("INSERT INTO blackList (id, users) VALUES ('%s','%s')", id,nick);
        stmt.execute(sql);
    }
    public void createHistoryTable() throws SQLException {
        stmt.executeUpdate("CREATE TABLE IF not EXISTS history (" +
                "datetime TEXT," +
                "name TEXT," +
                "message TEXT)");
    }
    public void addMessageToHistory(String name, String message) throws SQLException {
        String dateString = format.format(new Date());
        System.out.println(dateString);

        pstmt = connection.prepareStatement("INSERT INTO history (datetime, name, message)" +
                " VALUES (?,?,?)");
        pstmt.setString(1, dateString);
        pstmt.setString(2, name);
        pstmt.setString(3, message);

        pstmt.execute();
    }
    public ArrayList<String> getHistoryFromBD() throws SQLException {
        ArrayList<String> listMessage = new ArrayList<>();

        TreeSet<ClientsHistory> history = new TreeSet<ClientsHistory>();
        ResultSet rs = stmt.executeQuery("Select * From history;");

        while (rs.next()){
            try {
                Date date = format.parse(rs.getString(1));
                ClientsHistory el = new ClientsHistory(date, rs.getString(2), rs.getString(3));
                history.add(el);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        history.forEach(el -> listMessage.add(new String(el.name + ": " + el.message)));

        //history.forEach(awd -> System.out.println(awd.date + " " + awd.name));
        return listMessage;
    }
    public void deleteAllMessageInHistoryByNick(String name) throws SQLException {
        pstmt = connection.prepareStatement("delete from history where name=?");
        pstmt.setString(1, name);
        pstmt.executeUpdate();
    }
    public void changeRecordInHistoryByDatetime(String datetime) throws SQLException {
        pstmt = connection.prepareStatement("update history set message=? where datetime=?");
        pstmt.setString(1, "что-то новое");
        pstmt.setString(2, datetime);
        pstmt.executeUpdate();
    }
}
