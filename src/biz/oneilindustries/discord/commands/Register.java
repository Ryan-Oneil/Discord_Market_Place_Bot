package biz.oneilindustries.discord.commands;

import biz.oneilindustries.dao.UserDAO;
import biz.oneilindustries.discord.DiscordManager;
import biz.oneilindustries.discord.Rank;
import biz.oneilindustries.hibrenate.entity.User;

public class Register extends Command {

    public Register() {
        this.name = "!register";
        this.help = "Registers as a new user to the Market Place";
        this.args = "!register steamid firstname secondname bankNumber phoneNumber";
        this.requiredRole = "user";
        this.argsAmount = 5;
        this.requiresSteamID = true;
        this.steamArgIndex = 1;
    }

    @Override
    public String executeCommand(String[] args, String[] userNameDetails) {
        String userID = userNameDetails[userNameDetails.length - 1];

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserBySteamID(args[this.steamArgIndex]);
        User discordID = userDAO.getUserByDiscordID(userID);

        //Checks if the user is already registered
        if (user != null) {
            if (user.isEnabled()) {
                return "Already Registered";
            }
            return "Please wait till an admin approves your account";
        }

        if (discordID != null) {
            return "This discord account is already registered with another steamID";
        }

        try {
            user = new User(args[1],userID, args[2], args[3], Integer.valueOf(args[4]), Integer.valueOf(args[5]),false);
        }catch (NumberFormatException e) {
            return "Bank account and phone number must be numbers only. Please try again";
        }

        userDAO.saveUser(user);

        userDAO.close();

        DiscordManager discordManager = new DiscordManager();

        discordManager.sendChannelMessage("598478774819880981", Rank.getRequiredDiscordRole("ceo").getAsMention() + " " + userNameDetails[0] + " has registered and needs to be manually verified."
            + "\nUse !details " + user.getSteamID() + " for more information");

        return ("Your account has been registered. An admin will need to manually verify it before you can post listings");
    }
}