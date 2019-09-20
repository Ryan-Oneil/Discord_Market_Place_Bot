package biz.oneilindustries.discord.commands;

import biz.oneilindustries.discord.DiscordManager;
import biz.oneilindustries.discord.Rank;
import biz.oneilindustries.exceptions.UserRegistrationError;
import biz.oneilindustries.hibrenate.entity.User;
import biz.oneilindustries.service.MarketUserService;

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
        String userSteamID = userNameDetails[steamArgIndex];

        MarketUserService marketUserService = new MarketUserService();

        try {
            User user = new User(userSteamID,userID, args[2], args[3], Integer.valueOf(args[4]), Integer.valueOf(args[5]),false);

            try {
                marketUserService.registerUser(user);
            }catch (UserRegistrationError e) {
                return e.getMessage();
            }
        }catch (NumberFormatException e) {
            return "Bank account and phone number must be numbers only. Please try again";
        }

        DiscordManager discordManager = new DiscordManager();

        discordManager.sendChannelMessage("598478774819880981", Rank.getRequiredDiscordRole("ceo").getAsMention() + " " + userNameDetails[0] + " has registered and needs to be manually verified."
            + "\nUse !details " + userSteamID + " for more information");

        return ("Your account has been registered. An admin will need to manually verify it before you can post listings");
    }
}