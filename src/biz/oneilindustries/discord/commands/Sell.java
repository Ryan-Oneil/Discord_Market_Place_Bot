package biz.oneilindustries.discord.commands;

import biz.oneilindustries.discord.DiscordManager;
import biz.oneilindustries.service.MarketItemService;

public class Sell extends Command {

    private static final String ADMIN_NOTIFICATION_CHANNEL_ID = "598478774819880981";

    public Sell() {
        this.name = "!sell";
        this.help = "Adds an item to be sold on the market place";
        this.args = "!sell itemName price imageLink description";
        this.requiredRole = "user";
        this.argsAmount = 4;
        this.requiresSteamID = false;
        this.steamArgIndex = 1;
    }

    @Override
    public String executeCommand(String[] args, String[] userNameDetails) {

        String itemName = args[1];
        String itemPrice = args[2];
        String imageLink = args[3];
        StringBuilder itemDescription = new StringBuilder();

        for (int i = 4; i < args.length; i++) {
            itemDescription.append(args[i]);
        }

        try {
            MarketItemService marketItemService = new MarketItemService();

            marketItemService.createMarketItem(itemName, Integer.valueOf(itemPrice), imageLink, itemDescription.toString(), userNameDetails[1]);
        }catch (NumberFormatException e) {
            return "The price entered wasn't a number. Value entered was " + itemPrice;
        }

        DiscordManager discordManager = new DiscordManager();

        discordManager.sendChannelMessage(ADMIN_NOTIFICATION_CHANNEL_ID, "A new listing "
            + "has been added view inactive listings with !inactive");

        return "Your listing has been posted and needs to be manually accepted by an admin before it is live";
    }
}
