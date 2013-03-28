package de.puzzles.core;

import de.puzzles.core.util.PuzzlesUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test-Class for the PuzzlesUtils unit tests.
 * @author Patrick Groß-Holtwick
 */
public class PuzzlesUtilsTest {

    /**
     * This test checks the MD5 Hash Method and compares the generated results with the expected results.
     */
    @Test
    public void testMd5() {
        Assert.assertEquals(PuzzlesUtils.md5(""), PuzzlesUtils.md5(null));
        Assert.assertEquals(PuzzlesUtils.md5(""),"d41d8cd98f00b204e9800998ecf8427e");
        Assert.assertEquals(PuzzlesUtils.md5("test"),"098f6bcd4621d373cade4e832627b4f6");
    }
}
