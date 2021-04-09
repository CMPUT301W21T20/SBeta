package com.example.sbeta;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class UserTest {
    private User mockUser() {
        return new User("mnjk", "233@ualberta.ca");
    }

    @Test
    public void testSetUserName() {
        User user = mockUser();
        assertEquals(user.getUserName(), null);
        user.setUserName("Riki");
        assertEquals(user.getUserName(), "Riki");
    }

    @Test
    public void testGetUserName() {
        User user = mockUser();
        user.setUserName("Riki");
        assertEquals(user.getUserName(), "Riki");
    }

    @Test
    public void testGetUserID() {
        User user = mockUser();
        assertEquals(user.getUserID(), "mnjk");
    }

    @Test
    public void testGetContactIndo() {
        User user = mockUser();
        assertEquals(user.getContactInfo(), "233@ualberta.ca");
    }
}
