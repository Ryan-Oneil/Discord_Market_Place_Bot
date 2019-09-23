package biz.oneilindustries.discord.commands;

import biz.oneilindustries.discord.DiscordManager;
import biz.oneilindustries.hibrenate.entity.MarketItem;
import biz.oneilindustries.hibrenate.entity.User;
import biz.oneilindustries.service.MarketItemService;
import biz.oneilindustries.service.MarketUserService;

public class Buy extends Command {

    public Buy() {
        this.name = "!buy";
        this.help = "Purchases an item on the market";
        this.args = "!buy itemID";
        this.requiredRole = "user";
        this.argsAmount = 1;
        this.requiresSteamID = false;
        this.steamArgIndex = 0;
    }

    @Override
    public String executeCommand(String[] args, String[] userNameDetails) {

        MarketUserService marketUserService = new MarketUserService();
        MarketItemService marketItemService = new MarketItemService();
        DiscordManager discordManager = new DiscordManager();
        int itemID;

        User buyer = marketUserService.getUserByDiscordUUID(userNameDetails[1]);

        if (buyer == null || !buyer.isEnabled()) {
            return "You must be registered and approved to buy items";
        }

        try {
            itemID = Integer.valueOf(args[1]);
        }catch (NumberFormatException e) {
            return "Invalid id number. Please enter a number only. Id entered " + args[1];
        }
        MarketItem boughtItem = marketItemService.purchaseMarketITem(itemID);

        User seller = marketUserService.getUserBySteamID(boughtItem.getOwnerID());

        discordManager.sendUserMessage(seller.getDiscordID(), "A user has bought one of your listings"
            + "\n" + boughtItem.toString()
            + "\nBuyer Details:" + buyer.getFirstName() + " " + buyer.getLastName() +
            " Phone: " + buyer.getPhoneNumber());

        return "Listing " + boughtItem.getId() + " has been bought. The seller will contact you";
    }
}
