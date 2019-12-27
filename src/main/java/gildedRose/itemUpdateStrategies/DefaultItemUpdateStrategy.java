package gildedRose.itemUpdateStrategies;

import gildedRose.itemUpdateExecution.Item;

public class DefaultItemUpdateStrategy implements ItemUpdateStrategy {

    public void update(Item item) {
        if (item.sellIn > 0) {
            item.quality = Math.max(item.quality - 1, 0);
        } else {
            item.quality = Math.max(item.quality - 2, 0);
        }
        item.sellIn = item.sellIn - 1;
    }

}
