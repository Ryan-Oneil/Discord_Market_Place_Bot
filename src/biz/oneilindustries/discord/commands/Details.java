package biz.oneilindustries.discord.commands;

import biz.oneilindustries.hibrenate.entity.User;
import biz.oneilindustries.service.MarketUserService;

public class Details extends Command{

    public Details() {
        this.name = "!details";
        this.help = "Displays details of a user given a steamid";
        this.args = "!details steamid";
        this.requiredRole = "officer";
        this.argsAmount = 1;
        this.requiresSteamID = true;
        this.steamArgIndex = 1;
    }

    @Override
    public String executeCommand(String[] args, String[] userNameDetails) {

        String steamID = args[steamArgIndex];

        MarketUserService marketUserService = new MarketUserService();

        User user = marketUserService.getUserBySteamID(steamID);

        if (user == null) {
            return "This steamID isn't registered to a user";
        }

        return "Name: " + user.getFirstName() + " " + user.getLastName()
            + "\nBank Account: " + user.getBankNumber()
            + "\nPhone Number: " + user.getPhoneNumber()
            + "\nEnabled: " + user.isEnabled()
            + "\nSteamID: " + user.getSteamID();
    }
}
