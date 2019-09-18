package biz.oneilindustries.discord.commands;

import biz.oneilindustries.dao.UserDAO;
import biz.oneilindustries.discord.DiscordManager;
import biz.oneilindustries.hibrenate.entity.User;

public class UnVerify extends Command {

    public UnVerify() {
        this.name = "!unverify";
        this.help = "Unverifies a user account";
        this.args = "!unverify steamid";
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

        user.setEnabled(false);

        userDAO.saveUser(user);

        userDAO.close();

        DiscordManager discordManager = new DiscordManager();

        discordManager.removeRoles(discordManager.getMember(user.getDiscordID()));

        return "User has been disabled";
    }
}
