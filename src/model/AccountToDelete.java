package model;

public class AccountToDelete {

    private UserInfo userToDelete;

    public AccountToDelete(UserInfo userToDelete) {
        this.userToDelete = userToDelete;
    }

    public UserInfo getUserToDelete() { return userToDelete; }
}
