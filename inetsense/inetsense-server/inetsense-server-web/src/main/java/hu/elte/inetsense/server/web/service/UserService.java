package hu.elte.inetsense.server.web.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.elte.inetsense.common.dtos.UserDTO;
import hu.elte.inetsense.server.data.UserRepository;
import hu.elte.inetsense.server.data.entities.User;

/**
 * @author Zsolt Istvanfi
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private UserRepository  userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = false)
    public User addUser(final UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("userDTO cannot be null");
        }

        User user = new User();

        user.setCreatedOn(new Date());
        user.setEmail(userDTO.getEmail());

        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());

        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

}
