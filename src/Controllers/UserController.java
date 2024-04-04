package Controllers;

import Models.Data.Database;
import Views.AccountView;

public abstract class UserController {

    public void logout()
    {
        Database.getInstance().setLogedInUser(null);
    }
    public String accountInfo()
    {
        return Database.getInstance().getLogedInUser().toString();
    }
}
