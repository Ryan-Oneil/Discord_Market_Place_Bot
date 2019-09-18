package biz.oneilindustries.discord.commands;

import biz.oneilindustries.dao.UserDAO;
import biz.oneilindustries.discord.DiscordManager;
import biz.oneilindustries.hibrenate.entity.User;

public class Verify extends Command {

    private static final String SELLER_ROLE = "seller";

    public Verify() {
        this.name = "!verify";
        this.help = "Verifies a user account that has registered with the !register";
        this.args = "!verify steamid";
        this.requiredRole = "officer";
        this.argsAmount = 1;
        this.requiresSteamID = true;
        this.steamArgIndex = 1;
    }

    @Override
    public String executeCommand(String[] args, String[] userNameDetails) {

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserBySteamID(args[this.steamArgIndex]);

        if (user == null) {
            return "SteamID not found";
        }

        user.setEnabled(true);

        userDAO.saveUser(user);

        userDAO.close();

        DiscordManager discordManager = new DiscordManager();

        discordManager.addUserRole(discordManager.getMember(user.getDiscordID()), SELLER_ROLE);

        return "User has been added as an authorised user";
    }

}
