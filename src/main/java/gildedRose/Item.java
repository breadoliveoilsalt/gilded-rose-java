package gildedRose;

public class Item {

    public String name;
    public int sellIn;
    public int quality;
    private ItemUpdateStrategy updateStrategy;

    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public void setUpdateStrategy(ItemUpdateStrategy updateStrategy) {
        this.updateStrategy = updateStrategy;
    }

    public void update() {
        updateStrategy.update(this);
    }

}
