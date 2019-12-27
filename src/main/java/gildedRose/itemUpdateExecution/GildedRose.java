package gildedRose.itemUpdateExecution;

import gildedRose.itemUpdateStrategies.*;
import java.util.HashMap;
import java.util.concurrent.Callable;

public class GildedRose {
    protected Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
        setSpecializedStrategyMap();
    }

    private HashMap<String, Callable<ItemUpdateStrategy>> specializedStrategyMap;

    private void setSpecializedStrategyMap() {
        specializedStrategyMap = new HashMap();
        specializedStrategyMap.put("Sulfuras, Hand of Ragnaros", SulfurasUpdateStrategy::new);
        specializedStrategyMap.put("Aged Brie", AgedBrieUpdateStrategy::new);
        specializedStrategyMap.put("Backstage passes to a TAFKAL80ETC concert", BackstagePassUpdateStrategy::new);
        specializedStrategyMap.put("Conjured Item", ConjuredItemUpdateStrategy::new);
    }

    private Callable<ItemUpdateStrategy> defaultStrategy() {
        return DefaultItemUpdateStrategy::new;
    }

    public void updateQuality() {
        try {
            for (int i = 0; i < items.length; i++) {
                Item item = items[i];
                Callable<ItemUpdateStrategy> strategy = specializedStrategyMap.get(item.name);
                if (strategy == null) {
                    strategy = defaultStrategy();
                }
                item.setUpdateStrategy(strategy.call());
                item.update();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
