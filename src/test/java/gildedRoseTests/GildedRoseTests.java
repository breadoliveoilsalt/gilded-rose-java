package gildedRoseTests;

import gildedRose.GildedRose;
import gildedRose.Item;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GildedRoseTests {

    @Test
    public void testSampleTestPasses() {
        assertTrue(true);
    }

    @Test
    public void foo() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("fixme", app.items[0].name);
    }
}
