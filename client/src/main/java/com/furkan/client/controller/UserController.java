package com.furkan.client.controller;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.furkan.client.dao.UserDao;
import com.furkan.client.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.NoSuchAlgorithmException;

import static com.furkan.client.util.Util.encrypt;

/**
 * Controller for crud operations of user entity
 */
@Controller("userController")
public class UserController {

    @Autowired
    private UserDao userDao;

    public String create(String username, String password) {
        // Create new user with username and ecrypted password
        User user = null;
        try {
            user = new User(username, encrypt(password));
            userDao.save(user);
        } catch (Exception ex) {
            return "Error creating the user: " + ex.toString();
        }
        return "User succesfully created! (id = " + user.getId() + ")";
    }

    public String delete(long id) {
        // Delete user with id
        try {
            User user = new User(id);
            userDao.delete(user);
        } catch (Exception ex) {
            return "Error deleting the user:" + ex.toString();
        }
        return "User succesfully deleted!";
    }

    public String updateUser(long id, String username, String password) {
        // Update user with given id
        try {
            User user = userDao.findOne(id);
            user.setUsername(username);
            user.setPassword(encrypt(password));
            userDao.save(user);
        } catch (Exception ex) {
            return "Error updating the user: " + ex.toString();
        }
        return "User succesfully updated!";
    }

    @RequestMapping(value= "/check", method = RequestMethod.POST)
    public @ResponseBody String checkUser(@RequestBody User user) throws JSONException, NoSuchAlgorithmException {
        JSONObject jsonObj = new JSONObject();

        // Check if the username and password matching exists
        try {
            User foundUser = userDao.findByUsernameAndPassword(user.getUsername(), encrypt(user.getPassword()));
            if(foundUser != null && foundUser.getUsername() != null) {
                jsonObj.put("result", "true");
                return jsonObj.toString();
            }
        } catch (Exception ex) {
            jsonObj.put("result", "false");
            return jsonObj.toString();
        }
        jsonObj.put("result", "false");
        return jsonObj.toString();
    }
}
