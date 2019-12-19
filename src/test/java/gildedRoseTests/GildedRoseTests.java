package gildedRoseTests; import gildedRose.GildedRose;
import gildedRose.Item;
import org.junit.Test;
import static org.junit.Assert.*;

public class GildedRoseTests {

    // General Applicability

    @Test
    public void updateStatusDoesNoteChangeTheName() {
        Item[] items = new Item[]{new Item("foo", 0, 0)};
        TestableGildedRose app = new TestableGildedRose(items);

        app.updateQuality();

        assertEquals("foo", app.getItems()[0].name);
    }

    @Test
    public void updateStatusDoesNotLowerQualityBelowZero() {
        Item item1 = new Item("Ordinary Item 1", 10, 0);
        Item item2 = new Item("Ordinary Item 2", -10, 0);
        Item[] items = new Item[]{item1, item2};

        new GildedRose(items).updateQuality();

        assertEquals(0, item1.quality);
        assertEquals(0, item2.quality);

    }

    @Test
    public void updateStatusDoesNotIncreaseQualityAboveFifty() {
        Item brie = new Item("Aged Brie", 10, 49);
        Item backstagePass = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49);
        Item[] items = new Item[]{brie, backstagePass};

        new GildedRose(items).updateQuality();

        assertEquals(50, brie.quality);
        assertEquals(50, backstagePass.quality);
    }

    @Test
    public void updateStatusLowersExpirationDateByOneExceptForSulfurasItem() {
        Item brie = new Item("Aged Brie", 10, 1);
        Item backstagePass = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 1);
        Item ordinaryItem = new Item("Ordinary Item", 10, 1);
        Item sulfuras = new Item("Sulfuras, Hand of Ragnaros", 10, 1);

        Item[] items = new Item[]{brie, backstagePass, ordinaryItem, sulfuras};

        new GildedRose(items).updateQuality();

        assertEquals(9, brie.sellIn);
        assertEquals(9, backstagePass.sellIn);
        assertEquals(9, ordinaryItem.sellIn);
        assertEquals(10, sulfuras.sellIn);
    }

    // Non-Specialized Items

    @Test
    public void updateStatusForNonSpecializedItemsLowersQualityByOneIfItemNotExpired() {
        Item item1 = new Item("Ordinary Item 1", 10, 10);
        Item item2 = new Item("Ordinary Item 2", 1, 10);
        Item[] items = new Item[]{item1, item2};

        new GildedRose(items).updateQuality();

        assertEquals(9, item1.quality);
        assertEquals(9, item2.quality);
    }

    @Test
    public void updateStatusForNonSpecializedItemsLowersQualityByTwoIfItemExpired() {
        Item item1 = new Item("Ordinary Item 1", 0, 10);
        Item item2 = new Item("Ordinary Item 2", -10, 10);
        Item[] items = new Item[]{item1, item2};

        new GildedRose(items).updateQuality();

        assertEquals(8, item1.quality);
        assertEquals(8, item2.quality);
    }

    @Test
    public void updateStatusForNonSpecializedItemsLowersExpirationDateByOne() {
        Item item1 = new Item("Ordinary Item 1", 10, 10);
        Item item2 = new Item("Ordinary Item 2", 1, 10);
        Item item3 = new Item("Ordinary Item 3", -10, -10);
        Item[] items = new Item[]{item1, item2, item3};

        new GildedRose(items).updateQuality();

        assertEquals(9, item1.sellIn);
        assertEquals(0, item2.sellIn);
        assertEquals(-11, item3.sellIn);
    }

    // Aged Brie

    @Test
    public void updateStatusForAgedBrieIncreasesQualityByOneIfNotExpired() {
        Item brie1 = new Item("Aged Brie", 10, 10);
        Item brie2 = new Item("Aged Brie", 1, 10);
        Item[] items = new Item[]{brie1, brie2};

        new GildedRose(items).updateQuality();

        assertEquals(11, brie1.quality);
        assertEquals(11, brie2.quality);
    }

    @Test
    public void updateStatusForAgedBrieIncreasesQualityByTwoIfExpired() {
        Item brie1 = new Item("Aged Brie", 0, 10);
        Item brie2 = new Item("Aged Brie", -10, 10);
        Item[] items = new Item[]{brie1, brie2};

        new GildedRose(items).updateQuality();

        assertEquals(12, brie1.quality);
        assertEquals(12, brie2.quality);
    }

    // Backstage Passes

    @Test
    public void updateStatusForBackstagePassesIncreasesQualityByOneIfMoreThanTenDaysToExpiration() {
        Item backstagePass1 = new Item("Backstage passes to a TAFKAL80ETC concert", 11, 0   );
        Item backstagePass2 = new Item("Backstage passes to a TAFKAL80ETC concert", 20, 0   );
        Item[] items = new Item[]{backstagePass1, backstagePass2};

        new GildedRose(items).updateQuality();

        assertEquals(1, backstagePass1.quality);
        assertEquals(1, backstagePass2.quality);
    }

    @Test
    public void updateStatusForBackstagePassesIncreasesQualityByTwoIfSixToTenDaysToExpiration() {
        Item backstagePass1 = new Item("Backstage passes to a TAFKAL80ETC concert", 6, 0   );
        Item backstagePass2 = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 0   );
        Item[] items = new Item[]{backstagePass1, backstagePass2};

        new GildedRose(items).updateQuality();

        assertEquals(2, backstagePass1.quality);
        assertEquals(2, backstagePass2.quality);
    }

    @Test
    public void updateStatusForBackstagePassesIncreasesQualityByThreeIfOneToFiveDaysToExpiration() {
        Item backstagePass1 = new Item("Backstage passes to a TAFKAL80ETC concert", 1, 0   );
        Item backstagePass2 = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 0   );
        Item[] items = new Item[]{backstagePass1, backstagePass2};

        new GildedRose(items).updateQuality();

        assertEquals(3, backstagePass1.quality);
        assertEquals(3, backstagePass2.quality);
    }

    @Test
    public void updateStatusForBackstagePassesDecreasesQualityToZeroUponExpiration() {
        Item backstagePass1 = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 10   );
        Item backstagePass2 = new Item("Backstage passes to a TAFKAL80ETC concert", -10, 10   );
        Item[] items = new Item[]{backstagePass1, backstagePass2};

        new GildedRose(items).updateQuality();

        assertEquals(0, backstagePass1.quality);
        assertEquals(0, backstagePass2.quality);
    }

    // Sulfuras

    @Test
    public void updateStatusForSulfurasDoesNotChangeTheQualityOrExpirationDate() {
        Item sulfuras1 = new Item("Sulfuras, Hand of Ragnaros", 10, 80);
        Item sulfuras2 = new Item("Sulfuras, Hand of Ragnaros", -10, -80);
        Item[] items = new Item[]{sulfuras1, sulfuras2};

        new GildedRose(items).updateQuality();

        assertEquals(80, sulfuras1.quality);
        assertEquals(10, sulfuras1.sellIn);
        assertEquals(-80, sulfuras2.quality);
        assertEquals(-10, sulfuras2.sellIn);
    }
}


/*

    context "for Conjured items" do

      it "lowers the quality by 2 if the sell_in date is greater than 0" do
        item_1 = Item.new("Conjured Item", 10, 5)
        item_2 = Item.new("Conjured Item", 1, 5)
        items = [item_1, item_2]

        GildedRose.new(items).update_quality()

        expect(item_1.quality).to eq 3
        expect(item_2.quality).to eq 3
      end

      it "lowers the quality by 4 if the sell_in date is 0 or less" do
        item_1 = Item.new("Conjured Item", 0, 5)
        item_2 = Item.new("Conjured Item", -10, 5)
        items = [item_1, item_2]

        GildedRose.new(items).update_quality()

        expect(item_1.quality).to eq 1
        expect(item_2.quality).to eq 1
      end

      it "lowers the sell_in date by 1" do
        item = Item.new("Ordinary Item", 10, 5)
        items = [item]

        GildedRose.new(items).update_quality()

        expect(item.sell_in).to eq 9
      end

    end

  end

end



 */
