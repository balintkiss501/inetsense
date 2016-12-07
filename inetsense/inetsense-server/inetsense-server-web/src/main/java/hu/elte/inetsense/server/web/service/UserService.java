package hu.elte.inetsense.server.web.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.elte.inetsense.common.dtos.user.UserDTO;
import hu.elte.inetsense.server.data.entities.user.Role;
import hu.elte.inetsense.server.data.entities.user.User;
import hu.elte.inetsense.server.data.entities.user.UserRole;
import hu.elte.inetsense.server.data.repository.RoleRepository;
import hu.elte.inetsense.server.data.repository.UserRepository;
import hu.elte.inetsense.server.data.repository.UserRoleRepository;

/**
 * @author Zsolt Istvanfi
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private UserRepository      userRepository;
    @Autowired
    private RoleRepository      roleRepository;
    @Autowired
    private UserRoleRepository  userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = false)
    public User addUser(final UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("userDTO cannot be null");
        }

        User user = new User();

        user.setCreatedOn(new Date());
        user.setEmail(userDTO.getEmail().toLowerCase());

        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());

        user.setPassword(hashedPassword);

        User savedUser = userRepository.save(user);

        if (savedUser != null) {
            Role role = roleRepository.findByName("USER");
            if (role != null) {
                UserRole userRole = new UserRole();
                userRole.setUserId(savedUser.getId());
                userRole.setRoleId(role.getId());
                userRoleRepository.save(userRole);
            }
        }

        return savedUser;
    }

    public User findUserByEmail(final String email) {
        if (email == null) {
            throw new IllegalArgumentException("email cannot be null");
        }

        return userRepository.findByEmail(email.toLowerCase());
    }
}
