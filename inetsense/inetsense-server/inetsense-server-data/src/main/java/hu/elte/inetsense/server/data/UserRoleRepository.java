package hu.elte.inetsense.server.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.elte.inetsense.server.data.entities.UserRole;

/**
 * @author Zoltan Torok
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
