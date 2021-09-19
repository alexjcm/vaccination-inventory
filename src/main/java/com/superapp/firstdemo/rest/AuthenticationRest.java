package com.superapp.firstdemo.rest;

import com.superapp.firstdemo.dao.UserDao;
import com.superapp.firstdemo.security.JWTToken;
import com.superapp.firstdemo.entities.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("api")
@Api(tags="Auth Rest", description = "Authentication")
public class AuthenticationRest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JWTToken jwtToken;

    private static final Logger logger = Logger.getLogger(AuthenticationRest.class.getName());

    @ApiOperation(value = "Checks that the user exists and returns the user id, role and JWT token")
    @PostMapping(value = "/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody User user) {
        JSONObject jsonObject = new JSONObject();
        try {
            User userSaved = userDao.authenticate(user).get(0);
            String token = jwtToken.createToken(userSaved.getId().toString(), userSaved.getUsername());
            jsonObject.put("user_id", userSaved.getId());
            jsonObject.put("role", userSaved.getRole().getName());
            jsonObject.put("token", token);
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
