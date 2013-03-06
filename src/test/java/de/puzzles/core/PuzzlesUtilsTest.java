package de.puzzles.core;

import de.puzzles.core.util.PuzzlesUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @author pgh
 *         Date: 05.03.13
 *         Time: 16:59
 *         To change this template use File | Settings | File Templates.
 */
public class PuzzlesUtilsTest {

    @Test
    public void testMd5() {
        Assert.assertEquals(PuzzlesUtils.md5(""), PuzzlesUtils.md5(null));
        Assert.assertEquals(PuzzlesUtils.md5(""),"d41d8cd98f00b204e9800998ecf8427e");
        Assert.assertEquals(PuzzlesUtils.md5("test"),"098f6bcd4621d373cade4e832627b4f6");
    }
}
