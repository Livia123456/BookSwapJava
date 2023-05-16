package controller.server;

import database.user.DatabaseUser;
import model.AccountToDelete;
import model.Email;
import model.UserInfo;
import model.UserInfoUpdate;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
            oos.writeObject(userInfo);
            oos.flush();
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
        dbUser.removeUserFromDatabase(userId);
    }

    public UserInfo getCurrentUser() {
        return currentUser;
    }


    public void savingImage(ImageIcon message) {


        BufferedImage image = new BufferedImage(
                message.getIconWidth(),
                message.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        message.paintIcon(null, image.getGraphics(), 0, 0);

        File outputFile = new File(String.format("files/profile_images/%d_image", currentUser.getUserId()));

        try {
            // Use the ImageIO.write() method to write the BufferedImage to the file
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
