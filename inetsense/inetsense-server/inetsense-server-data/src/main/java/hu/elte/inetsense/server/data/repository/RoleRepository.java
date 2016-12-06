package hu.elte.inetsense.server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.elte.inetsense.server.data.entities.Role;

/**
 * @author Zoltan Torok
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findById(Long id);
    Role findByName(String name);

}
