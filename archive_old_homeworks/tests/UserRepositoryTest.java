package com.davidsafta.tests;

import com.davidsafta.homework.InvalidUserDataException;
import com.davidsafta.homework.User;
import com.davidsafta.homework.UserRepository;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserRepositoryTest {

    @Test
    public void addValidUser_increasesSize() throws InvalidUserDataException {
        UserRepository repo = new UserRepository();
        repo.addUser(new User("testuser", 25));
        Assert.assertEquals(repo.getUsers().size(), 1);
    }

    @Test(expectedExceptions = InvalidUserDataException.class)
    public void addUser_withTooShortUsername_throws() throws InvalidUserDataException {
        new UserRepository().addUser(new User("ab", 22));
    }

    @Test(expectedExceptions = InvalidUserDataException.class)
    public void addUser_withNegativeAge_throws() throws InvalidUserDataException {
        new UserRepository().addUser(new User("baduser", -5));
    }
}
