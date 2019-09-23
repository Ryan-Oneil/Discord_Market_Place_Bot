package biz.oneilindustries.discord.commands;

import biz.oneilindustries.discord.DiscordManager;
import biz.oneilindustries.hibrenate.entity.MarketItem;
import biz.oneilindustries.service.MarketItemService;
import java.util.List;

public class Items extends Command {

    public Items() {
        this.name = "!items";
        this.help = "Lists all active items for sale on the market place";
        this.args = "!items";
        this.requiredRole = "user";
        this.argsAmount = 0;
        this.requiresSteamID = false;
        this.steamArgIndex = 0;
    }

    @Override
    public String executeCommand(String[] args, String[] userNameDetails) {
        MarketItemService marketItemService = new MarketItemService();
        DiscordManager discordManager = new DiscordManager();

        List<MarketItem> items = marketItemService.getItemsByStatus(true);

        for (MarketItem marketItem : items) {
            String message = "Item ID\tItem Name\tItem Price\tItem image link\tDescription\n" +
                marketItem.getId() + "\t" + marketItem.getItem() + "\t" + marketItem.getImage() +
                "\t\t" + marketItem.getDescription();

            discordManager.sendChannelMessage(userNameDetails[2], message);
        }
        return null;
    }
}
