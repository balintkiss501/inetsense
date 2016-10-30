package hu.elte.inetsense.server.web.service.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.elte.inetsense.server.data.UserRepository;
import hu.elte.inetsense.server.data.entities.User;

/**
 * @author Zsolt Istvanfi
 */
@Service
public class InetsenseUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public InetsenseUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        return new InetsenseUserDetails(user, Arrays.asList("ROLE_USER"));
    }

}
