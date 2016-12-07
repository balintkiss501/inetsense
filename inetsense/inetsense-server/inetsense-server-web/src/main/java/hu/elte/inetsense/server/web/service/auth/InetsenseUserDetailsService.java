package hu.elte.inetsense.server.web.service.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.elte.inetsense.server.data.entities.user.Role;
import hu.elte.inetsense.server.data.entities.user.User;
import hu.elte.inetsense.server.data.repository.UserRepository;

/**
 * @author Zsolt Istvanfi
 */
@Service
public class InetsenseUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

    @Autowired
    public InetsenseUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        List<Role> userRoles = user.getRoles();
        if (userRoles.isEmpty()) {
        	Role userRole = new Role();
        	userRole.setName("USER");
        	userRoles.add(userRole); // default role
        }

        return new InetsenseUserDetails(user);
    }

}
