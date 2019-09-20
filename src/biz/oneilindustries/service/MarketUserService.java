package biz.oneilindustries.service;

import biz.oneilindustries.dao.UserDAO;
import biz.oneilindustries.exceptions.UserRegistrationError;
import biz.oneilindustries.hibrenate.entity.User;
import java.util.List;

public class MarketUserService {

    private UserDAO userDAO;

    public MarketUserService() {
        userDAO = new UserDAO();
    }

    public User getUserBySteamID(String steamID) {

        User user = userDAO.getUserBySteamID(steamID);

        userDAO.close();

        return user;
    }

    public User getUserByDiscordUUID(String uuid) {

        User user = userDAO.getUserByDiscordID(uuid);

        userDAO.close();

        return user;
    }

    public List<User> getUsers() {

        List<User> users = userDAO.getUsers();

        userDAO.close();

        return users;
    }

    public void registerUser(User user) {
        User marketUser = userDAO.getUserBySteamID(user.getSteamID());
        User discordID = userDAO.getUserByDiscordID(user.getDiscordID());

        if (marketUser != null) {
            if (marketUser.isEnabled()) {
                throw new UserRegistrationError("Already Registered");
            }
            throw new UserRegistrationError("Please wait till an admin approves your account");
        }

        if (discordID != null) {
            throw new UserRegistrationError("This discord account is already registered with another steamID");
        }

        userDAO.saveUser(user);
    }

    public User verifyUser(String steamID) {
        User user = userDAO.getUserBySteamID(steamID);

        if (user == null) {
            throw new UserRegistrationError("SteamID not found");
        }
        user.setEnabled(true);

        userDAO.saveUser(user);

        return user;
    }

    public User unVerifyUser(String steamID) {
        User user = userDAO.getUserBySteamID(steamID);

        if (user == null) {
            throw new UserRegistrationError("SteamID not found");
        }
        user.setEnabled(false);

        userDAO.saveUser(user);

        return user;
    }
}
