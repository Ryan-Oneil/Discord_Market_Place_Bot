package biz.oneilindustries.hibrenate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "market_item")
public class MarketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean enabled;

    @Column(name = "owner_id")
    private String ownerID;

    private String item;

    private int price;

    private String description;

    private String image;

    private boolean sold;

    public MarketItem() {
    }

    public MarketItem(boolean enabled, String ownerID, String item, int price, String description,
        String image, boolean sold) {
        this.enabled = enabled;
        this.ownerID = ownerID;
        this.item = item;
        this.price = price;
        this.description = description;
        this.image = image;
        this.sold = sold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "MarketItem{" +
            "id=" + id +
            ", item='" + item + '\'' +
            ", price=" + price +
            ", description='" + description + '\'' +
            '}';
    }
}
