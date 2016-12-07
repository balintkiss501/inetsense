package hu.elte.inetsense.server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.elte.inetsense.server.data.entities.user.User;

/**
 * @author Zsolt Istvanfi
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
