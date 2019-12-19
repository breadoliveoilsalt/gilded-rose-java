package gildedRose;

public class GildedRose {
    protected Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];

            if (item.name.equals("Sulfuras, Hand of Ragnaros")) {
                continue;
            }

            if (item.name.equals("Aged Brie")) {
                if (item.sellIn > 0)  {
                    item.quality = Math.min(item.quality + 1, 50);
                } else {
                    item.quality = Math.min(item.quality + 2, 50);
                }

                items[i].sellIn = items[i].sellIn - 1;
                continue;
            }

            if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (item.sellIn > 10) {
                    item.quality = Math.min(item.quality + 1, 50);
                } else if (item.sellIn > 5) {
                    item.quality = Math.min(item.quality + 2, 50);
                } else if (item.sellIn > 0) {
                    item.quality = Math.min(item.quality + 3, 50);
                } else {
                    item.quality = 0;
                }
                item.sellIn = items[i].sellIn - 1;
                continue;
            }

            if (item.quality > 0) {
                if (item.sellIn <= 0) {
                    item.quality = Math.max(item.quality - 2, 0);
                } else {
                    item.quality = Math.max(item.quality - 1, 0);
                }

            }
            item.sellIn = item.sellIn - 1;
        }
    }
}
