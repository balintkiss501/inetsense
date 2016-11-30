package hu.elte.inetsense.server.web.service.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.elte.inetsense.server.data.UserRepository;
import hu.elte.inetsense.server.data.RoleRepository;
import hu.elte.inetsense.server.data.UserRoleRepository;
import hu.elte.inetsense.server.data.entities.User;
import hu.elte.inetsense.server.data.entities.Role;
import hu.elte.inetsense.server.data.entities.UserRole;

/**
 * @author Zsolt Istvanfi
 */
@Service
public class InetsenseUserDetailsService implements UserDetailsService {

    private final UserRepository        userRepository;
    private final RoleRepository        roleRepository;
    private final UserRoleRepository    userRoleRepository;

    @Autowired
    public InetsenseUserDetailsService(final UserRepository userRepository, final RoleRepository roleRepository, final UserRoleRepository userRoleRepository) {
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

        String roleName = "USER";
        UserRole userRole = userRoleRepository.findByUserId(user.getId());
        Long roleId = userRole.getRoleId();
        if (roleId != null) {
            Role role = roleRepository.findById(roleId);
            if (role != null) {
                roleName = role.getName();
            }
        }

        return new InetsenseUserDetails(user, Arrays.asList(roleName));
    }

}
