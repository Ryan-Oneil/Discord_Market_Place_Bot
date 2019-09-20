package biz.oneilindustries.discord.commands;

import biz.oneilindustries.discord.DiscordManager;
import biz.oneilindustries.hibrenate.entity.User;
import biz.oneilindustries.service.MarketUserService;

public class Verify extends Command {

    private static final String SELLER_ROLE = "seller";
    private static final String MARKET_STORE_NAME = "Market Place";

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
        MarketUserService marketUserService = new MarketUserService();

        User user = marketUserService.verifyUser(args[steamArgIndex]);

        DiscordManager discordManager = new DiscordManager();

        discordManager.addUserRole(discordManager.getMember(user.getDiscordID()), SELLER_ROLE);
        discordManager.sendUserMessage(user.getDiscordID(), "Your account has been approved to sell items on " + MARKET_STORE_NAME);

        return "User has been added as an authorised user";
    }

}
