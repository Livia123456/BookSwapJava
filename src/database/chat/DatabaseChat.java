package database.chat;

import database.Database;
import model.chat.ChatObject;
import model.chat.ChatsWith;
import model.chat.MessageObject;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Handles the queries associated with all the chat functions of the system.
 * @author Kasper Lindberg
 */
public class DatabaseChat {

    private Database db;

    public DatabaseChat(){
        db = new Database();
    }


    /**
     * Adds a message to a specific chat.
     */
    public void addMessage(MessageObject messageObject) {

        int chatId = getChatId(messageObject);

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

    /**
     * Adds a new chat.
     */
    public void addChat(MessageObject messageObject) {

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
    }


    /**
     * Fetches and returns a chatId from a message object.
     */
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


    /**
     * Fetches and returns the ten latest messages in a specific chat as an ArrayList.
     */
    public ArrayList<MessageObject> getChatHistory(int chat_id) {

        ArrayList<MessageObject> chatHistory = new ArrayList<>();

        Connection con = db.getDatabaseConnection();
        String QUERY = String.format("SELECT m.message, m.sender, m.message_date, c.user_1_id, c.user_2_id " +
                "FROM messages m " +
                "JOIN chat c ON m.chat_id = c.chat_id " +
                "WHERE m.chat_id = %d " +
                "ORDER BY m.message_date DESC " +
                "LIMIT 10", chat_id);

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);

            while (rs.next()) {
                int sender = rs.getInt("sender");
                int user1 = rs.getInt("user_1_id");
                int user2 = rs.getInt("user_2_id");

                if(sender == user1){
                    chatHistory.add(new MessageObject(sender, user2, rs.getString("message")));
                } else{
                    chatHistory.add(new MessageObject(sender, user1, rs.getString("message")));
                }
            }

            stmt.close();
            con.close();
            db.terminateIdle();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return chatHistory;
    }


    public void deleteChat(ChatObject chatObject){

        int chatId = getChatId(new MessageObject(chatObject.getUser1(), chatObject.getUser2(), ""));

        Connection con = db.getDatabaseConnection();
        String QUERY = String.format("DELETE FROM messages WHERE chat_id = %d", chatId);

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


    /**
     * Returns a list of ChatsWith objects -> parameters: chatObject (currentUser + Status.populate)
     */
    public Object getActiveChats(ChatObject chatObject) {

        ArrayList<ChatsWith> chatsWith = new ArrayList<>();

        Connection con = db.getDatabaseConnection();
        String QUERY = String.format("SELECT chat.chat_id, " +
                "CASE " +
                "WHEN chat.user_1_id = %d " +
                "THEN users1.user_name " +
                "ELSE users2.user_name " +
                "END AS other_user_name " +
                "FROM chat " +
                "INNER JOIN users AS users1 ON chat.user_2_id = users1.user_id " +
                "INNER JOIN users AS users2 ON chat.user_1_id = users2.user_id " +
                "WHERE " +
                "chat.user_1_id = %d OR chat.user_2_id = %d;",
                chatObject.getUser1(), chatObject.getUser1(), chatObject.getUser1());

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);

            while (rs.next()) {
                int chatId = rs.getInt("chat_id");
                String name = rs.getString("other_user_name");
                chatsWith.add(new ChatsWith(chatId, name));
            }

            stmt.close();
            con.close();
            db.terminateIdle();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return chatsWith;
    }
}
