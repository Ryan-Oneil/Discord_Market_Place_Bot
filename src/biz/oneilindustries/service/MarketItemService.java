package biz.oneilindustries.service;

import biz.oneilindustries.dao.MarketItemDAO;
import biz.oneilindustries.exceptions.MarketItemException;
import biz.oneilindustries.hibrenate.entity.MarketItem;
import biz.oneilindustries.hibrenate.entity.User;
import java.util.List;

public class MarketItemService {

    private MarketItemDAO marketItemDAO;
    private MarketUserService marketUserService;

    public MarketItemService() {
        this.marketItemDAO = new MarketItemDAO();
        this.marketUserService = new MarketUserService();
    }

    public List<MarketItem> getItemsByStatus(boolean status) {

        List<MarketItem> items = marketItemDAO.getItemsByStatus(status);

        marketItemDAO.close();

        return items;
    }

    public MarketItem getMarketItemByID(int id) {
        MarketItem marketItem = marketItemDAO.getItem(id);

        marketItemDAO.close();

        return marketItem;
    }

    public void createMarketItem(String itemName, int itemPrice, String itemImageLink,
        String itemDescription, String userUUID) {

        User user = marketUserService.getUserByDiscordUUID(userUUID);

        if (user == null) {
            throw new MarketItemException("You must register before you can sell items");
        }

        if (!user.isEnabled()) {
            throw new MarketItemException("Your account must be enabled to post sells");
        }

        MarketItem marketItem = new MarketItem(false, user.getSteamID(), itemName,
            itemPrice, itemDescription, itemImageLink, false);

        marketItemDAO.saveItem(marketItem);
    }

    public MarketItem purchaseMarketITem(int itemID) {
        MarketItem marketItem = marketItemDAO.getItem(itemID);


        if (marketItem == null) {
            throw new MarketItemException("This itemID doesn't exist");
        }

        if (marketItem.isEnabled()) {
            throw new MarketItemException("This item hasn't been approved yet");
        }

        if (marketItem.isSold()) {
            throw new MarketItemException("This item has already been sold");
        }

        marketItem.setSold(true);

        marketItemDAO.saveItem(marketItem);

        return marketItem;
    }
}