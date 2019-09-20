package biz.oneilindustries.service;

import biz.oneilindustries.dao.MarketItemDAO;
import biz.oneilindustries.exceptions.MarketItemException;
import biz.oneilindustries.hibrenate.entity.MarketItem;
import biz.oneilindustries.hibrenate.entity.User;

public class MarketItemService {

    private MarketItemDAO marketItemDAO;
    private MarketUserService marketUserService;

    public MarketItemService() {
        this.marketItemDAO = new MarketItemDAO();
        this.marketUserService = new MarketUserService();
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
}