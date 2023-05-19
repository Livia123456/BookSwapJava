package controller.server;

import database.chat.DatabaseChat;
import database.user.DatabaseUser;
import model.AccountToDelete;
import model.Email;
import model.UserInfo;
import model.UserInfoUpdate;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

/**
 * This class is responsible for all the user-related communications with the database
 * @author Livia Tengelin, Sebastian Zulj
 */

public class UserController {
    private DatabaseUser dbUser;
    private UserInfo currentUser;
    private ClientHandler clientHandler;
    private ObjectOutputStream oos;

    public UserController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.oos = clientHandler.getOOS();
        dbUser = new DatabaseUser();
    }

    private void logIn(UserInfo userInfo) {

        userInfo = dbUser.checkUserInfo(userInfo);
        try {
            if (userInfo.isCorrectInfo()) {
                currentUser = userInfo;
                currentUser.setCurrentUsersUploadedBooks(
                        clientHandler.getBookController().loadCurrentUsersUploadedBooks());
                currentUser.setProfileImage(getProfileImage(currentUser.getUserId()));
                //System.out.println(getProfileImage(currentUser.getUserId()));
                oos.writeObject(currentUser);
                oos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            oos.writeObject(dbUser.checkUserInfo(userInfo));
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void createNewUser(UserInfo userInfo) {
        dbUser.newUser(userInfo);
        userInfo.setCorrectInfo(true);

        try {
            if(userInfo.getUserId() != 0) {
                currentUser = userInfo;
                oos.writeObject(currentUser);
                System.out.println(currentUser.getName() + " " + currentUser.getUserId() + " sent back to client");
                oos.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkEmail(Email email) {

        email.setRegistered(dbUser.checkEmail(email.getEmailAddress()));

        try {
            oos.writeObject(email);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void userInfoReceived(UserInfo userInfo) {
        if (userInfo.getName() == null || userInfo.getName().isEmpty()) {
            logIn(userInfo);
        } else {
            createNewUser(userInfo);
        }
    }

    public void updateUserInfo(UserInfoUpdate userInfo) {
        dbUser.updateUserInfo(currentUser.getUserId(), userInfo);
    }


    public void deleteAccount(AccountToDelete accountToDelete) {
        int userId = accountToDelete.getUserToDelete().getUserId();
        DatabaseChat dbChat = new DatabaseChat();
        dbChat.deleteMessagesFromId(userId);
        dbUser.removeUserFromDatabase(userId);
    }

    public UserInfo getCurrentUser() {
        return currentUser;
    }


    public void savingImage(ImageIcon image) {

        File directory = new File("profile_images");
        if (!directory.exists()) {
            directory.mkdir();
        }
        String newFilePath = String.format("profile_images/%d.jpg", currentUser.getUserId());

        File newFile = new File(newFilePath);
        try {
            imageToFile(image, newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void imageToFile(ImageIcon image, File file) throws IOException {

        BufferedImage bufferedImage = new BufferedImage(
                image.getIconWidth(),
                image.getIconHeight(),
                BufferedImage.TYPE_INT_RGB
        );
        Graphics graphics = bufferedImage.createGraphics();

        image.paintIcon(null, graphics, 0, 0);
        graphics.dispose();

        ImageIO.write(bufferedImage, "jpg", file);
    }

    public ImageIcon getProfileImage(int userId) {

        File folder = new File("profile_images");
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    String[] fileNameParts = fileName.split("\\.");
                    String userIdString = fileNameParts[0];

                    try {
                        int fileUserId = Integer.parseInt(userIdString);
                        if (fileUserId == userId) {
                            return new ImageIcon(file.getAbsolutePath());
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            }
        }
       return new ImageIcon("profile_images/user.jpg");
    }
}
