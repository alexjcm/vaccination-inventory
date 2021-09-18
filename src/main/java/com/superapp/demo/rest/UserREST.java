package com.superapp.demo.rest;

import com.superapp.demo.util.ConstantUtils;
import com.superapp.demo.model.Role;
import com.superapp.demo.model.User;
import com.superapp.demo.service.UserService;
import com.superapp.demo.service.RoleService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Is a RestController which has request mapping methods for RESTful requests
 *
 * @author alexjcm
 */
@RestController
@RequestMapping("/users")
public class UserREST {

    @Autowired
    private UserService userService;

    /* @Autowired
    private AuthenticationManager authenticationManager;
     */
    @Autowired
    private RoleService roleService;

    private static final Logger logger = Logger.getLogger(UserREST.class.getName());

    @GetMapping
    private ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    //---NEW ADD-----
    @GetMapping("/{id}")
    private ResponseEntity<List<User>> getAllUsersByVaccine(@PathVariable("id") Long idVaccine) {
        return ResponseEntity.ok(userService.findAllByVaccine(idVaccine));
    }
    //---------------

    @PutMapping
    private ResponseEntity<User> updateUser(@RequestBody User user) {
        Optional<User> optionalUser = userService.findById(user.getId());
        if (optionalUser.isPresent()) {
            User updateUser = optionalUser.get();
            updateUser.setIdentCard(user.getIdentCard());
            updateUser.setFirstName(user.getFirstName());
            updateUser.setLastName(user.getLastName());
            updateUser.setEmail(user.getEmail());
            userService.save(updateUser);
            return ResponseEntity.ok(updateUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    private ResponseEntity<Boolean> deleteUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.deleteById(id));
    }

    /////////--------------LOGIN ----------   
    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticate(@RequestBody User user) {
        JSONObject jsonObject = new JSONObject();
        // try {
        Role auxRol = null;
        for (Role rol : user.getRoles()) {
            if (rol.getName().equalsIgnoreCase("admin")) {
                auxRol = rol;
                break;
            }
            auxRol = rol;
        }
        String email = user.getEmail();
        //jsonObject.put("token", tokenProvider.createToken(email, auxRol));
        return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);

        /* } catch (JSONException e) {
            try {
                jsonObject.put("exception", e.getMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.UNAUTHORIZED);
        }
        return null;*/
    }

    /*
     @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticate(@RequestBody User user) {
        JSONObject jsonObject = new JSONObject();
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
           
           if (authentication.isAuthenticated()) {
                Role auxRol = null;
                for (Role rol : user.getRoles()) {
                    if (rol.getName().equalsIgnoreCase("admin")) {
                        auxRol = rol;
                        break;
                    }
                    auxRol = rol;
                }
                String email = user.getEmail();
                jsonObject.put("name", authentication.getName());
                jsonObject.put("authorities", authentication.getAuthorities());
                //jsonObject.put("token", tokenProvider.createToken(email, auxRol));
                return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
            }
        } catch (JSONException e) {
            try {
                jsonObject.put("exception", e.getMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.UNAUTHORIZED);
        }
        return null;
    }
     */
    @PostMapping
    private ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User userSaved = userService.save(user);
            //return ResponseEntity.created(new URI("/users/" + userSaved.getId())).body(userSaved);
            return ResponseEntity.ok(userSaved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@RequestBody User user) {
        JSONObject jsonObject = new JSONObject();
        try {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            //user.setRole(roleService.findByName(ConstantUtils.USER.toString()));
            Boolean savedUser = userService.registerUser(user);
            jsonObject.put("message", user.getFirstName() + " saved is " + savedUser);
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (JSONException e) {
            try {
                jsonObject.put("exception", e.getMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.UNAUTHORIZED);
        }
    }
}
