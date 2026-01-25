package com.davidsafta.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SanityTest {

    @Test
    public void sanityWorks() {
        int sum = 2 + 3;
        Assert.assertEquals(sum, 5, "Sanity test failed");
    }
}
