package com.davidsafta.tests;

import com.davidsafta.homework.PostValidator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PostValidatorTest {
    PostValidator validator = new PostValidator();

    @DataProvider(name = "postDataProvider")
    public Object[][] provideData() {
        return new Object[][] {
                { null, "ERROR_EMPTY" },
                { "", "ERROR_EMPTY" },
                { "Acesta este un text valid.", "POST_VALID" },
                { "Text care contine politică este interzis.", "ERROR_FORBIDDEN" },
                // caz > 250 caractere:
                { "x".repeat(251), "ERROR_TOO_LONG" }
        };
    }

    @Test(dataProvider = "postDataProvider")
    public void testGetPostStatus(String inputBody, String expectedStatus) {
        Assert.assertEquals(validator.getPostStatus(inputBody), expectedStatus);
    }
}

