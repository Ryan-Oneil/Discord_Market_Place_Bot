package biz.oneilindustries.discord.commands;

import biz.oneilindustries.discord.DiscordManager;
import biz.oneilindustries.hibrenate.entity.User;
import biz.oneilindustries.service.MarketUserService;

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
        MarketUserService marketUserService = new MarketUserService();

        User user = marketUserService.unVerifyUser(args[steamArgIndex]);

        DiscordManager discordManager = new DiscordManager();

        discordManager.removeRoles(discordManager.getMember(user.getDiscordID()));

        return "User has been disabled";
    }
}
