package com.furkan.client.controller;

import com.amazonaws.util.json.JSONObject;
import com.furkan.client.dao.UserDao;
import com.furkan.client.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.furkan.client.util.Util.encrypt;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UserController.class)
public class UserControllerTest {

    @Test
    public void testCreate() throws Exception {
        // given
        UserController userController = new UserController();
        UserDao userDao = mock(UserDao.class);
        setInternalState(userController, "userDao", userDao);
        User user = mock(User.class);
        doReturn(1L).when(user).getId();
        whenNew(User.class).withAnyArguments().thenReturn(user);
        String expected = "User succesfully created! (id = 1)";

        // when
        String result = userController.create("testuser", "testpassword");

        // then
        assertEquals(expected, result);
    }

    @Test
    public void testDelete() throws Exception {
        // given
        UserController userController = new UserController();
        UserDao userDao = mock(UserDao.class);
        setInternalState(userController, "userDao", userDao);
        User user = mock(User.class);
        whenNew(User.class).withAnyArguments().thenReturn(user);
        String expected = "User succesfully deleted!";

        // when
        String result = userController.delete(1);

        // then
        assertEquals(expected, result);
    }

    @Test
    public void testUpdateUser() throws Exception {
        // given
        UserController userController = new UserController();
        UserDao userDao = mock(UserDao.class);
        User user = mock(User.class);
        doReturn(user).when(userDao).findOne(1L);
        setInternalState(userController, "userDao", userDao);
        String expected = "User succesfully updated!";

        // when
        String result = userController.updateUser(1L, "testuser", "testpassword");

        // then
        assertEquals(expected, result);
    }

    @Test
    public void testCheckUser() throws Exception {
        // given
        UserController userController = new UserController();
        UserDao userDao = mock(UserDao.class);
        User user = new User("testUsername", "testPassword");
        doReturn(user).when(userDao).findByUsernameAndPassword(user.getUsername(), encrypt(user.getPassword()));
        setInternalState(userController, "userDao", userDao);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result", "true");
        String expected = jsonObj.toString();

        // when
        String result = userController.checkUser(user);

        // then
        assertEquals(expected, result);
    }
}
