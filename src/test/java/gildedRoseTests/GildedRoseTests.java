package gildedRoseTests; import gildedRose.GildedRose;
import gildedRose.Item;
import org.junit.Test;
import static org.junit.Assert.*;

public class GildedRoseTests {

    @Test
    public void updateStatusDoesNoteChangeTheName() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        TestableGildedRose app = new TestableGildedRose(items);

        app.updateQuality();

        assertEquals("foo", app.getItems()[0].name);
    }

    @Test
    public void updateStatusDoesNotLowerQualityBelowZero() {
        Item item1 = new Item("Ordinary Item 1", 10, 0);
        Item item2 = new Item("Ordinary Item 2", -10, 0);
        Item[] items = new Item[] {item1, item2};

        new GildedRose(items).updateQuality();

        assertEquals(0, item1.quality);
        assertEquals(0, item2.quality);

    }

    @Test
    public void updateStatusForNonSpecializedItemsLowersQualityByOneIfItemNotExpired() {
        Item item1 = new Item("Ordinary Item 1", 10, 10);
        Item item2 = new Item("Ordinary Item 2", 1, 10);
        Item[] items = new Item[] {item1, item2};

        new GildedRose(items).updateQuality();

        assertEquals(9, item1.quality);
        assertEquals(9, item2.quality);
    }

    @Test
    public void updateStatusForNonSpecializedItemsLowersQualityByTwoIfItemExpired() {
        Item item1 = new Item("Ordinary Item 1", 0, 10);
        Item item2 = new Item("Ordinary Item 2", -10, 10);
        Item[] items = new Item[] {item1, item2};

        new GildedRose(items).updateQuality();

        assertEquals(8, item1.quality);
        assertEquals(8, item2.quality);
    }

    @Test
    public void updateStatusForNonSpecializedItemsLowersExpirationDateByOne() {
        Item item1 = new Item("Ordinary Item 1", 10, 10);
        Item item2 = new Item("Ordinary Item 2", 1, 10);
        Item item3 = new Item("Ordinary Item 1", -10, -10);
        Item[] items = new Item[] {item1, item2, item3};

        new GildedRose(items).updateQuality();

        assertEquals(9, item1.sellIn);
        assertEquals(0, item2.sellIn);
        assertEquals(-11, item3.sellIn);
    }

    @Test
    public void updateStatusForAgedBrieDoesNotIncreaseQualityBeyondFifty() {
        Item item1 = new Item("Aged Brie", 10, 50);
        Item item2 = new Item("Aged Brie", 10, 49);
        Item item3 = new Item("Aged Brie", -10, 49);
        Item[] items = new Item[] {item1, item2, item3};

        new GildedRose(items).updateQuality();

        assertEquals(50, item1.quality);
        assertEquals(50, item2.quality);
        assertEquals(50, item3.quality);
    }

}


/*

    context "for Aged Brie" do

      it "does not increase the quality of Aged Brie beyond 50" do
        item_1 = Item.new("Aged Brie", 10, 50)
        item_2 = Item.new("Aged Brie", 10, 49)
        items = [item_1, item_2]

        GildedRose.new(items).update_quality()

        expect(item_1.quality).to eq 50
        expect(item_2.quality).to eq 50
      end

      it "increases the quality of Aged Brie by 1 if the sell_in date is greater than 0" do
        item = Item.new("Aged Brie", 1, 45)
        items =[item]

        GildedRose.new(items).update_quality()

        expect(item.quality).to eq 46
      end

      it "increases the quality of Aged Brie by 2 if the sell_in date is 0 or less" do
        item_1 = Item.new("Aged Brie", 0, 0)
        item_2 = Item.new("Aged Brie", -1, 0)
        items =[item_1, item_2]

        GildedRose.new(items).update_quality()

        expect(item_1.quality).to eq 2
        expect(item_2.quality).to eq 2
      end

      it "lowers the sell_in date by 1" do
        item = Item.new("Aged Brie", 10, 45)
        items = [item]

        GildedRose.new(items).update_quality()

        expect(item.sell_in).to eq 9
      end

    end

    context "for Backstage passes" do

      it "does not increase the quality of a backstage pass beyond 50" do
        item_1 = Item.new("Backstage passes to a TAFKAL80ETC concert", 10, 50)
        item_2 = Item.new("Backstage passes to a TAFKAL80ETC concert", 3, 49)
        items = [item_1, item_2]

        GildedRose.new(items).update_quality()

        expect(item_1.quality).to eq 50
        expect(item_2.quality).to eq 50
      end

      it "increases the quality of 1 if the sell_in date is greater than 10" do
        item_1 = Item.new("Backstage passes to a TAFKAL80ETC concert", 20, 0)
        item_2 = Item.new("Backstage passes to a TAFKAL80ETC concert", 30, 0)
        items = [item_1, item_2]

        GildedRose.new(items).update_quality()

        expect(item_1.quality).to eq 1
        expect(item_2.quality).to eq 1
      end

      it "increases the quality by 2 if the sell_in date is from 6 to 10" do
        item_1 = Item.new("Backstage passes to a TAFKAL80ETC concert", 10, 0)
        item_2 = Item.new("Backstage passes to a TAFKAL80ETC concert", 6, 0)
        items = [item_1, item_2]

        GildedRose.new(items).update_quality()

        expect(item_1.quality).to eq 2
        expect(item_2.quality).to eq 2
      end

      it "increases the quality by 3 if the sell_in date is from 1 to 5" do
        item_1 = Item.new("Backstage passes to a TAFKAL80ETC concert", 1, 0)
        item_2 = Item.new("Backstage passes to a TAFKAL80ETC concert", 5, 0)
        items = [item_1, item_2]

        GildedRose.new(items).update_quality()

        expect(item_1.quality).to eq 3
        expect(item_2.quality).to eq 3
      end

      it "lowers the sell_in date by 1" do
        item = Item.new("Backstage passes to a TAFKAL80ETC concert", 10, 45)
        items = [item]

        GildedRose.new(items).update_quality()

        expect(item.sell_in).to eq 9
      end

      it "lowers the quality to 0 if the sell_in date is 0 or less" do
        item_1 = Item.new("Backstage passes to a TAFKAL80ETC concert", 0, 45)
        item_2 = Item.new("Backstage passes to a TAFKAL80ETC concert", -1, 45)
        items = [item_1, item_2]

        GildedRose.new(items).update_quality()

        expect(item_1.quality).to eq 0
        expect(item_2.quality).to eq 0
      end

    end

    context "for Sulfuras" do

      it "does not change the quality of Sulfuras" do
        item = Item.new("Sulfuras, Hand of Ragnaros", 10, 80)
        items = [item]

        GildedRose.new(items).update_quality()

        expect(item.quality).to eq 80
      end

      it "does not lower the sell_in date for Sulfuras" do
        item = Item.new("Sulfuras, Hand of Ragnaros", 10, 80)
        items = [item]

        GildedRose.new(items).update_quality()

        expect(item.sell_in).to eq 10
      end

    end

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
