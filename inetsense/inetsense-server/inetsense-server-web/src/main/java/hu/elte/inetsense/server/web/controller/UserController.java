package hu.elte.inetsense.server.web.controller;

import java.security.Principal;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zsolt Istvanfi
 */
@RestController
@Transactional(readOnly = true)
public class UserController {

    /**
     * If the “/user” resource is reachable then it will return the currently authenticated user (an <code>Authentication</code>),
     * and otherwise Spring Security will intercept the request and send a 401 response through an
     * <code>AuthenticationEntryPoint</code>.
     */
    @RequestMapping("/user")
    public Principal user(final Principal user) {
        return user;
    }

}
