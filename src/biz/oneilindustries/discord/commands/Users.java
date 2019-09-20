package biz.oneilindustries.discord.commands;

import biz.oneilindustries.hibrenate.entity.User;
import biz.oneilindustries.service.MarketUserService;
import java.util.List;

public class Users extends Command {

    public Users() {
        this.name = "!users";
        this.help = "Displays registered users";
        this.args = "!users";
        this.requiredRole = "officer";
        this.argsAmount = 0;
        this.requiresSteamID = false;
        this.steamArgIndex = 0;
    }

    @Override
    public String executeCommand(String[] args, String[] userNameDetails) {

        MarketUserService marketUserService = new MarketUserService();

        List<User> users = marketUserService.getUsers();

        StringBuilder message = new StringBuilder("Name\t\tEnabled\tSteamID");

        for (User user : users) {
            message.append("\n").append(user.getFirstName()).append(" ").append(user.getLastName()).append("\t\t").append(user.isEnabled())
                .append("\t").append(user.getSteamID());
        }

        return message.toString();
    }
}
