package biz.oneilindustries.discord.commands;

import biz.oneilindustries.hibrenate.entity.MarketItem;
import biz.oneilindustries.service.MarketItemService;

public class Item extends Command {

    public Item() {
        this.name = "!item";
        this.help = "Displays details of a market listing item";
        this.args = "!item itemID";
        this.requiredRole = "user";
        this.argsAmount = 1;
        this.requiresSteamID = false;
        this.steamArgIndex = 0;
    }

    @Override
    public String executeCommand(String[] args, String[] userNameDetails) {

        MarketItemService marketItemService = new MarketItemService();
        int itemID;
        try {
            itemID = Integer.valueOf(args[1]);
        }catch (NumberFormatException e) {
            return "Invalid id number. Please enter a number only. Id entered " + args[1];
        }

        MarketItem item = marketItemService.getMarketItemByID(itemID);

        String message = "ITem ID\t Item Name\tItem price\tItem Description\n" +
            item.getId() + "\t" + item.getItem() + "\t" + item.getPrice()
            + "\t" + item.getDescription();

        if (!item.getDescription().isEmpty()) {
            message+= "\nItem Image: " + item.getImage();
        }
        return message;
    }
}
