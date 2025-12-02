package com.davidsafta.tests;

import com.davidsafta.homework.PostValidator;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class PostValidatorTest {

    // ------------ Sanity (valid case) ------------
    @Test
    public void validPost_isAccepted() {
        assertTrue(PostValidator.isValid("Hello World", "This is a valid content for the post."));
    }

    // ------------ Title rules ------------
    @Test(
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = ".*Title.*least 5.*"
    )
    public void titleTooShort_throws() {
        PostValidator.isValid("abc", "This is a valid content");  // 3 caractere -> aruncă
    }

    @Test
    public void titleLengthExactly5_isAccepted() {
        String title = "abcde";           // exact 5
        String content = "0123456789";    // 10 -> ok
        assertTrue(PostValidator.isValid(title, content));
    }

    // ------------ Content rules ------------
    @Test(
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = ".*Content.*least 10.*"
    )
    public void contentTooShort_throws() {
        PostValidator.isValid("validTitle", "short"); // 5 -> aruncă
    }

    // Boundary: după trim rămân 10 -> ACCEPTAT
    @Test
    public void contentTrimTo10_isAccepted() {
        String title = "validTitle";      // >= 5
        String content = "  0123456789  "; // după trim -> 10
        assertTrue(PostValidator.isValid(title, content));
    }

    // Boundary: după trim rămân 9 -> ARUNCĂ
    @Test(
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = ".*Content.*least 10.*"
    )
    public void contentTrimTo9_throws() {
        String title = "validTitle";       // >= 5
        String content = "  012345678  ";  // după trim -> 9
        PostValidator.isValid(title, content);
    }
}
