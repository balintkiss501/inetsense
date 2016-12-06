package hu.elte.inetsense.server.web.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import hu.elte.inetsense.server.data.entities.user.User;
import hu.elte.inetsense.server.web.service.auth.InetsenseUserDetails;

/**
 * @author Zsolt Istvanfi
 */
public class UserUtils {

    public static User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            InetsenseUserDetails userDetails = (InetsenseUserDetails) auth.getPrincipal();
            return userDetails.getUser();
        }

        return null;
    }

}
