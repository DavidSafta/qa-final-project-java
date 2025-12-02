package com.davidsafta.tests;

import com.davidsafta.homework.PostValidator;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class PostValidatorTest {

    @Test
    public void validPost_isAccepted() {
        assertTrue(PostValidator.isValid("Hello World", "This is a valid content for the post."));
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = ".*Title.*(empty|least 5).*")
    public void titleTooShort_throws() {
        PostValidator.isValid("abc", "This is a valid content for the post.");
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = ".*Content.*")
    public void contentEmpty_throws() {
        PostValidator.isValid("Good title", "   ");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void contentTooShort_throws() {
        PostValidator.isValid("Good title", "too short");
    }
}
