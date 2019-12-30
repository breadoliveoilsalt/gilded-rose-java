package gildedRoseTests.itemUpdateExecutionTests;

import gildedRose.itemUpdateExecution.GildedRose;
import gildedRose.itemUpdateExecution.Item;

public class TestableGildedRose extends GildedRose {

    public TestableGildedRose(Item[] items) {
        super(items);
    }

    public Item[] getItems() {
        return items;
    }
}
