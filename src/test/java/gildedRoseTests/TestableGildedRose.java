package gildedRoseTests;

import gildedRose.GildedRose;
import gildedRose.Item;

public class TestableGildedRose extends GildedRose {

    public TestableGildedRose(Item[] items) {
        super(items);
    }

    public Item[] getItems() {
        return items;
    }
}
