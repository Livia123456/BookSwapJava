package database.chat;

import database.Database;
import model.UserInfo;
import model.chat.ChatObject;
import model.chat.ChatsWith;
import model.chat.MessageObject;
import model.chat.StartChat;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Semaphore;

/**
 * Handles the queries associated with all the chat functions of the system.
 * @author Kasper Lindberg
 */
public class DatabaseChat {

    private Database db;
    private Semaphore semaphore;
    public DatabaseChat(){
        db = Database.getInstance();
        semaphore = db.getDbSemaphore();
    }


    /**
     * Adds a message to a specific chat.
     */
    public void addMessage(MessageObject messageObject) {

        int chatId = getChatId(messageObject);

        String message = messageObject.getMessage();
        message = message.replaceAll("'", "''"); // Escape apostrophes

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
        semaphore.release();
    }

    /**
     * Adds a new chat.
     */
    public void addChat(MessageObject messageObject) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Connection con = db.getDatabaseConnection();
        String QUERY = String.format("INSERT INTO chat " +
                "(user_1_id, user_2_id) VALUES (%d, %d)", messageObject.getSender(), messageObject.getReceiver());

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(QUERY);

            stmt.close();
            con.close();
            db.terminateIdle();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        semaphore.release();
    }


    public void deleteMessagesFromId(int userId) {
        ArrayList<Integer> list = getChat(userId);

        Connection con = db.getDatabaseConnection();

        for (int i = 0; i < list.size(); i++) {

            String QUERY = String.format("DELETE FROM messages WHERE chat_id = %d", i);
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
    }


    public ArrayList<Integer> getChat(int id) {
        ArrayList<Integer> list = new ArrayList<>();
        Connection con = db.getDatabaseConnection();
        String QUERY = String.format("SELECT chat_id FROM chat WHERE " +
                        "user_1_id = %d AND " +
                        "user_2_id = %d",
                         id, id);

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);

            while (rs.next()) {
                list.add(rs.getInt("chat_id"));
            }

            stmt.close();
            con.close();
            db.terminateIdle();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public int getChatId(MessageObject messageObject){

        int chatId = 0;

            Connection con = db.getDatabaseConnection();
            String QUERY = String.format("SELECT chat_id FROM chat WHERE " +
                            "user_1_id = %d AND " +
                            "user_2_id = %d OR " +
                            "user_1_id = %d AND " +
                            "user_2_id = %d", messageObject.getSender(), messageObject.getReceiver(),
                    messageObject.getReceiver(), messageObject.getSender());

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
        try {
            semaphore.acquire();

        Connection con = db.getDatabaseConnection();
        String QUERY = String.format("SELECT m.message, m.sender, m.message_date, c.user_1_id, c.user_2_id " +
                "FROM messages m " +
                "JOIN chat c ON m.chat_id = c.chat_id " +
                "WHERE m.chat_id = %d " +
                "ORDER BY m.message_date DESC " +
                "LIMIT 20", chat_id);

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);

            while (rs.next()) {
                int sender = rs.getInt("sender");
                int user1 = rs.getInt("user_1_id");
                int user2 = rs.getInt("user_2_id");

                if(sender == user1){
                    chatHistory.add(new MessageObject(user1, user2, rs.getString("message")));
                } else{
                    chatHistory.add(new MessageObject(user2, user1, rs.getString("message")));
                }
            }

            stmt.close();
            con.close();
            db.terminateIdle();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        semaphore.release();
        return chatHistory;
    }

    /**
     * Deletes a chat and it's messages -> Trigger on messages that deletes the chat.
     */
    public void deleteChat(ChatObject chatObject){

        int chatId = getChatId(new MessageObject(chatObject.getCurrentUser(), chatObject.getUser2(), ""));
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
        semaphore.release();
    }


    /**
     * Returns a list of ChatsWith objects -> parameters: chatObject (currentUser + Status.populate)
     */
    public ArrayList<ChatsWith> getActiveChats(ChatObject chatObject) {

        ArrayList<ChatsWith> chatsWith = new ArrayList<>();
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Connection con = db.getDatabaseConnection();
        String QUERY = String.format("SELECT chat.chat_id, " +
                        "CASE " +
                        "WHEN chat.user_1_id = %d THEN users1.user_name " +
                        "ELSE users2.user_name " +
                        "END AS other_user_name, " +
                        "CASE " +
                        "WHEN chat.user_1_id = %d THEN chat.user_2_id " +
                        "ELSE chat.user_1_id " +
                        "END AS other_user_id " +
                        "FROM chat " +
                        "INNER JOIN users AS users1 " +
                        "ON chat.user_2_id = users1.user_id " +
                        "INNER JOIN users AS users2 " +
                        "ON chat.user_1_id = users2.user_id " +
                        "WHERE  " +
                        "chat.user_1_id = %d OR chat.user_2_id = %d;",
                chatObject.getCurrentUser(), chatObject.getCurrentUser(), chatObject.getCurrentUser(), chatObject.getCurrentUser());

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);

            while (rs.next()) {
                int chatId = rs.getInt("chat_id");
                String name = rs.getString("other_user_name");
                int userId = rs.getInt("other_user_id");
                chatsWith.add(new ChatsWith(chatId, new UserInfo(name, userId)));
            }

            stmt.close();
            con.close();
            db.terminateIdle();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        semaphore.release();
        return chatsWith;
    }

    public StartChat startChatFromSearch(ChatObject chatObject) {
        StartChat startChat = new StartChat();
        int chatId = getChatId(new MessageObject(chatObject.getCurrentUser(), chatObject.getUser2(), ""));
        if (chatId == 0){
            addChat(new MessageObject(chatObject.getCurrentUser(), chatObject.getUser2(), ""));
        }
        startChat.setContacts(getActiveChats(chatObject));
        startChat.setMessages(getChatHistory(chatId));
        for (ChatsWith cw : startChat.getContacts()) {
            if (cw.getUserId() == chatObject.getUser2()) {
                startChat.setChatsWith(cw);
                break;
            }
        }

        return startChat;
    }
}
