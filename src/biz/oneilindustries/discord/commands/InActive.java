package biz.oneilindustries.discord.commands;

import biz.oneilindustries.discord.DiscordManager;
import biz.oneilindustries.hibrenate.entity.MarketItem;
import biz.oneilindustries.service.MarketItemService;
import java.util.List;

public class InActive extends Command {

    public InActive() {
        this.name = "!inactive";
        this.help = "Displays all inactive listings that need to be approved";
        this.args = "!inactive";
        this.requiredRole = "officer";
        this.argsAmount = 0;
        this.requiresSteamID = false;
        this.steamArgIndex = 0;
    }

    @Override
    public String executeCommand(String[] args, String[] invokerDetails) {

        DiscordManager discordManager = new DiscordManager();
        MarketItemService marketItemService = new MarketItemService();

        List<MarketItem> items = marketItemService.getItemsByStatus(false);

        for (MarketItem item: items) {

            String itemDetails = "Item ID\tItem Name\t Owner SteamID"
                + "\n" + item.getId() + "\t" + item.getItem() +  "\t" + item.getOwnerID();

            discordManager.sendChannelMessage(invokerDetails[2], itemDetails);
        }

        return null;
    }
}
