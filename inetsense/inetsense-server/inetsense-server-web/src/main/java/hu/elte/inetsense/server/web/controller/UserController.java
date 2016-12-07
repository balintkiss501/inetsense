package hu.elte.inetsense.server.web.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.inetsense.common.dtos.user.UserDTO;
import hu.elte.inetsense.server.data.entities.user.User;
import hu.elte.inetsense.server.web.service.UserService;

/**
 * @author Zsolt Istvanfi
 */
@RestController
public class UserController {

    private enum RegisterResponse {
        OK,
        USER_ALREADY_EXISTS;
    }

    @Autowired
    private UserService userService;

    /**
     * If the “/user” resource is reachable then it will return the currently authenticated user (an <code>Authentication</code>),
     * and otherwise Spring Security will intercept the request and send a 401 response through an
     * <code>AuthenticationEntryPoint</code>.
     */
    @RequestMapping("/user")
    public Principal user(final Principal user) {
        return user;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RegisterResponse register(@Valid @RequestBody final UserDTO user) {
        User userEntity = userService.findUserByEmail(user.getEmail());
        if (userEntity != null) {
            return RegisterResponse.USER_ALREADY_EXISTS;
        }

        userService.addUser(user);

        return RegisterResponse.OK;
    }

}
