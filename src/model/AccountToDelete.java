package model;

import java.io.Serializable;

/**
 * Class to store a UserInfo-object to be deleted
 * @author Sebastian Zulj
 */

public class AccountToDelete implements Serializable {

    static final long serialVersionUID = 52345L;

    private UserInfo userToDelete;

    public AccountToDelete(UserInfo userToDelete) {
        this.userToDelete = userToDelete;
    }

    public UserInfo getUserToDelete() { return userToDelete; }
}
