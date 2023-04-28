package database.chat;

import database.Database;
import model.MessageObject;


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

        if(chatId == 0){
            chatId = addChat(messageObject);
        }

        String message = messageObject.getMessage();
        message = message.replaceAll("'", "''"); // Escape apostrophes

        Connection con = db.getDatabaseConnection();
        String QUERY = String.format("INSERT INTO messages(sender, " +
                        "message, message_date, chat_id) VALUES " +
                        "('%s', '%s', '%s', '%d')", messageObject.getSender(),
                        message, "NOW()", chatId);

        try {

            Statement stmt = con.createStatement();
            stmt.executeUpdate(QUERY);

            stmt.close();
            con.close();
            db.terminateIdle();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private int addChat(MessageObject messageObject) {

        int chatId = 0;

        Connection con = db.getDatabaseConnection();

        String QUERY = String.format("INSERT INTO chat " +
                "(user_1_id, user_2_id) VALUES (%d, %d)", messageObject.getSender(), messageObject.getReciever());

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(QUERY);

            chatId = getChatId(messageObject);

            stmt.close();
            con.close();
            db.terminateIdle();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return chatId;
    }

    public int getChatId(MessageObject messageObject){

        int chatId = 0;

        Connection con = db.getDatabaseConnection();
        String QUERY = String.format("SELECT chat_id FROM chat WHERE " +
                        "user_1_id = %d OR " +
                        "user_2_id = %d AND " +
                        "user_1_id = %d OR " +
                        "user_2_id = %d", messageObject.getSender(), messageObject.getSender(),
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

    public ArrayList<MessageObject> getChatHistory(int chat_id) {

        //TODO: göra om?

        ArrayList<MessageObject> chatHistory = new ArrayList<>();



        Connection con = db.getDatabaseConnection();
        String QUERY = String.format("SELECT message FROM messages WHERE " +
                        "chat_id = %d", chat_id);

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);

            while (rs.next()) {
             //  chatId = rs.getInt("chat_id");
            }

            stmt.close();
            con.close();
            db.terminateIdle();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        return chatHistory;
    }
}
