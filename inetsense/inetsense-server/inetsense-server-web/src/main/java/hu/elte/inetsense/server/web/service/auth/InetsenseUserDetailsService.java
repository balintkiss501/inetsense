package hu.elte.inetsense.server.web.service.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.elte.inetsense.server.data.RoleRepository;
import hu.elte.inetsense.server.data.UserRepository;
import hu.elte.inetsense.server.data.UserRoleRepository;
import hu.elte.inetsense.server.data.entities.Role;
import hu.elte.inetsense.server.data.entities.User;
import hu.elte.inetsense.server.data.entities.UserRole;

/**
 * @author Zsolt Istvanfi
 */
@Service
public class InetsenseUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final UserRoleRepository userRoleRepository;

    @Autowired
    public InetsenseUserDetailsService(final UserRepository userRepository, final RoleRepository roleRepository,
            final UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        List<String> roleNames = new ArrayList<>();

        List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
        if (userRoles.isEmpty()) {
            roleNames.add("USER"); // default role
        } else {
            for (UserRole userRole : userRoles) {
                Role role = roleRepository.findById(userRole.getRoleId());
                roleNames.add(role.getName());
            }
        }

        return new InetsenseUserDetails(user, roleNames);
    }

}
