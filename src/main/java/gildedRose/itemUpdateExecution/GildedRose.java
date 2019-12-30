package gildedRose.itemUpdateExecution;

import gildedRose.itemUpdateStrategies.*;
import java.util.HashMap;
import java.util.concurrent.Callable;

public class GildedRose {

    protected Item[] items;
    private HashMap<String, Callable<ItemUpdateStrategy>> specializedStrategyMap;
    private Callable<ItemUpdateStrategy> defaultStrategy;

    public GildedRose(Item[] items) {
        this.items = items;
        setSpecializedStrategyMap();
        setDefaultStrategy();
    }

    private void setSpecializedStrategyMap() {
        specializedStrategyMap = new HashMap();
        specializedStrategyMap.put("Sulfuras, Hand of Ragnaros", SulfurasUpdateStrategy::new);
        specializedStrategyMap.put("Aged Brie", AgedBrieUpdateStrategy::new);
        specializedStrategyMap.put("Backstage passes to a TAFKAL80ETC concert", BackstagePassUpdateStrategy::new);
        specializedStrategyMap.put("Conjured Item", ConjuredItemUpdateStrategy::new);
    }

    private void setDefaultStrategy() {
        defaultStrategy = DefaultItemUpdateStrategy::new;
    }

    public void updateQuality() {
        try {
            for (Item item: items) {
                Callable<ItemUpdateStrategy> strategy = specializedStrategyMap.getOrDefault(item.name, defaultStrategy);
                item.setUpdateStrategy(strategy.call());
                item.update();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
