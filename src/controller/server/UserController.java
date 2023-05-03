package controller.server;

import database.user.DatabaseUser;
import model.AccountToDelete;
import model.Email;
import model.UserInfo;
import model.UserInfoUpdate;

import java.io.IOException;
import java.io.ObjectOutputStream;

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
        currentUser = userInfo;
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
}
