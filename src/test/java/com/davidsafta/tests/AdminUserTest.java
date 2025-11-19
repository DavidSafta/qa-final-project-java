package com.davidsafta.tests;

import com.davidsafta.homework.AdminUser;
import com.davidsafta.homework.UserRepository;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminUserTest {

    @Test
    public void adminUser_toString_containsPermissionLevel() {
        AdminUser admin = new AdminUser("admin", 30, "full_access");
        String s = admin.toString();
        Assert.assertTrue(s.contains("permissionLevel"));
        Assert.assertTrue(s.contains("full_access"));
    }

    @Test
    public void adminUser_addToRepository_isAccepted() throws Exception {
        UserRepository repo = new UserRepository();
        repo.addUser(new AdminUser("admin", 30, "full_access"));
        Assert.assertEquals(repo.getUsers().size(), 1);
    }
}
