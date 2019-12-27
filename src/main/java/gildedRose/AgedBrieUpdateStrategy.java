package gildedRose;

public class AgedBrieUpdateStrategy implements ItemUpdateStrategy {

    public void update(Item item) {
        if (item.sellIn > 0)  {
            item.quality = Math.min(item.quality + 1, 50);
        } else {
            item.quality = Math.min(item.quality + 2, 50);
        }
        item.sellIn = item.sellIn - 1;
    }
}
