package gildedRoseTests;

import gildedRose.Item;
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
        TestableGildedRose app = new TestableGildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.getItems()[0].name);
    }
}
