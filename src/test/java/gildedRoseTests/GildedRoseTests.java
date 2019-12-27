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
    public void updateStatusForNonSpecializedItemsDoesNotLowerQualityBelowZero() {
        Item item1 = new Item("Ordinary Item 1", 10, 0);
        Item item2 = new Item("Ordinary Item 2", -10, 0);
        Item[] items = new Item[]{item1, item2};

        new GildedRose(items).updateQuality();

        assertEquals(0, item1.quality);
        assertEquals(0, item2.quality);
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

    @Test
    public void updateStatusForAgedBrieDoesNotIncreaseQualityAboveFifty() {
        Item brie1 = new Item("Aged Brie", 10, 49);
        Item brie2 = new Item("Aged Brie", -10, 49);
        Item[] items = new Item[]{brie1, brie2};

        new GildedRose(items).updateQuality();

        assertEquals(50, brie1.quality);
        assertEquals(50, brie2.quality);
    }

    @Test
    public void updateStatusForAgedBrieLowersExpirationDateByOne() {
        Item brie1 = new Item("Aged Brie", 10, 10);
        Item brie2 = new Item("Aged Brie", 1, 10);
        Item brie3 = new Item("Aged Brie", -10, -10);
        Item[] items = new Item[]{brie1, brie2, brie3};

        new GildedRose(items).updateQuality();

        assertEquals(9, brie1.sellIn);
        assertEquals(0, brie2.sellIn);
        assertEquals(-11, brie3.sellIn);
    }

    // Backstage Passes

    @Test
    public void updateStatusForBackstagePassesIncreasesQualityByOneIfMoreThanTenDaysToExpiration() {
        Item backstagePass1 = new Item("Backstage passes to a TAFKAL80ETC concert", 11, 0);
        Item backstagePass2 = new Item("Backstage passes to a TAFKAL80ETC concert", 20, 0);
        Item[] items = new Item[]{backstagePass1, backstagePass2};

        new GildedRose(items).updateQuality();

        assertEquals(1, backstagePass1.quality);
        assertEquals(1, backstagePass2.quality);
    }

    @Test
    public void updateStatusForBackstagePassesIncreasesQualityByTwoIfSixToTenDaysToExpiration() {
        Item backstagePass1 = new Item("Backstage passes to a TAFKAL80ETC concert", 6, 0);
        Item backstagePass2 = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 0);
        Item[] items = new Item[]{backstagePass1, backstagePass2};

        new GildedRose(items).updateQuality();

        assertEquals(2, backstagePass1.quality);
        assertEquals(2, backstagePass2.quality);
    }

    @Test
    public void updateStatusForBackstagePassesIncreasesQualityByThreeIfOneToFiveDaysToExpiration() {
        Item backstagePass1 = new Item("Backstage passes to a TAFKAL80ETC concert", 1, 0);
        Item backstagePass2 = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 0);
        Item[] items = new Item[]{backstagePass1, backstagePass2};

        new GildedRose(items).updateQuality();

        assertEquals(3, backstagePass1.quality);
        assertEquals(3, backstagePass2.quality);
    }

    @Test
    public void updateStatusForBackstagePassesDecreasesQualityToZeroUponExpiration() {
        Item backstagePass1 = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 10);
        Item backstagePass2 = new Item("Backstage passes to a TAFKAL80ETC concert", -10, 10);
        Item[] items = new Item[]{backstagePass1, backstagePass2};

        new GildedRose(items).updateQuality();

        assertEquals(0, backstagePass1.quality);
        assertEquals(0, backstagePass2.quality);
    }

    @Test
    public void updateStatusForBackstagePassesDoesNotIncreaseQualityAboveFifty() {
        Item backstagePass1 = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 50);
        Item backstagePass2 = new Item("Backstage passes to a TAFKAL80ETC concert", 1, 49);
        Item[] items = new Item[]{backstagePass1, backstagePass2};

        new GildedRose(items).updateQuality();

        assertEquals(50, backstagePass1.quality);
        assertEquals(50, backstagePass2.quality);
    }

    @Test
    public void updateStatusForBackstagePassesDoesNotDecreaseQualityBelowZero() {
        Item backstagePass1 = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 0);
        Item backstagePass2 = new Item("Backstage passes to a TAFKAL80ETC concert", -10, 0);
        Item[] items = new Item[]{backstagePass1, backstagePass2};

        new GildedRose(items).updateQuality();

        assertEquals(0, backstagePass1.quality);
        assertEquals(0, backstagePass2.quality);
    }

    @Test
    public void updateStatusForBackstagePassesLowersExpirationDateByOne() {
        Item backstagePass1 = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 10);
        Item backstagePass2 = new Item("Backstage passes to a TAFKAL80ETC concert", 1, 10);
        Item backstagePass3 = new Item("Backstage passes to a TAFKAL80ETC concert", -10, -10);
        Item[] items = new Item[]{backstagePass1, backstagePass2, backstagePass3};

        new GildedRose(items).updateQuality();

        assertEquals(9, backstagePass1.sellIn);
        assertEquals(0, backstagePass2.sellIn);
        assertEquals(-11, backstagePass3.sellIn);
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

    // Conjured Items

//    @Test
//    public void updateStatusForConjuredItemsLowersQualityByTwoIfItemNotExpired() {
//        Item conjured1 = new Item("Conjured Item", 10, 10);
//        Item conjured2 = new Item("Conjured Item", 1, 10);
//        Item[] items = new Item[]{conjured1, conjured2};
//
//        new GildedRose(items).updateQuality();
//
//        assertEquals(8, conjured1.quality);
//        assertEquals(8, conjured2.quality);
//    }
//
//    @Test
//    public void updateStatusForConjuredItemsLowersQualityByFourIfItemIsExpired() {
//        Item conjured1 = new Item("Conjured Item", 0, 10);
//        Item conjured2 = new Item("Conjured Item", -10, 10);
//        Item[] items = new Item[]{conjured1, conjured2};
//
//        new GildedRose(items).updateQuality();
//
//        assertEquals(6, conjured1.quality);
//        assertEquals(6, conjured2.quality);
//    }
//
//    @Test
//    public void updateStatusForConjuredItemsDoesNoteLowersQualityBelowZero() {
//        Item conjured1 = new Item("Conjured Item", 0, 0);
//        Item conjured2 = new Item("Conjured Item", -10, 0);
//        Item[] items = new Item[]{conjured1, conjured2};
//
//        new GildedRose(items).updateQuality();
//
//        assertEquals(0, conjured1.quality);
//        assertEquals(0, conjured2.quality);
//    }
//
//    @Test
//    public void updateStatusForConjuredItemsLowersExpirationDateByOne() {
//        Item conjuredItem1 = new Item("Conjured Item", 10, 10);
//        Item conjuredItem2 = new Item("Conjured Item", 1, 10);
//        Item conjuredItem3 = new Item("Conjured Item", -10, -10);
//        Item[] items = new Item[]{conjuredItem1, conjuredItem2, conjuredItem3};
//
//        new GildedRose(items).updateQuality();
//
//        assertEquals(9, conjuredItem1.sellIn);
//        assertEquals(0, conjuredItem2.sellIn);
//        assertEquals(-11, conjuredItem3.sellIn);
//    }
}

