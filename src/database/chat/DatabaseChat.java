package database.chat;

import database.Database;
import model.Email;
import model.MessageObject;
import model.SearchAble;
import model.UserInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseChat {

    private Database db;

    public DatabaseChat(){
        db = new Database();
    }

    public void addMessage(MessageObject messageObject) {


        int chatId = getChatId(messageObject);
        



    }

    public int getChatId(MessageObject messageObject){

        Connection con = db.getDatabaseConnection();
        int chatId = 0;

        String QUERY = String.format("SELECT chat_id FROM chat WHERE" +
                        "user_1_id LIKE '%s' OR" +
                        "user_2_id LIKE '%s' AND " +
                        "user_1_id LIKE '%s' OR " +
                        "user_2_id LIKE '%s'", messageObject.getSender(), messageObject.getSender(),
                messageObject.getReciever(), messageObject.getReciever());

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);

            while (rs.next()) {
                chatId = rs.getInt("chat_id");
            }

            stmt.close();
            con.close();
            db.terminateIdle();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return chatId;
    }
}
