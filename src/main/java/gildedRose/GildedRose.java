package gildedRose;

public class GildedRose {
    protected Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    private ItemUpdateStrategy getUpdateStrategy(Item item) {
        switch(item.name) {
            case "Sulfuras, Hand of Ragnaros":
                return new SulfurasUpdateStrategy();
            case "Aged Brie":
                return new AgedBrieUpdateStrategy();
            case "Backstage passes to a TAFKAL80ETC concert":
                return new BackstagePassUpdateStrategy();
            case "Conjured Item":
                return new ConjuredItemUpdateStrategy();
            default:
                return new DefaultItemUpdateStrategy();
        }
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];
            item.setUpdateStrategy(getUpdateStrategy(item));
            item.update();
        }
    }
}
