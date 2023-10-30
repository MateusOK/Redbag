package br.com.redbag.api.repository;

import br.com.redbag.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    UserDetails findByUsernameOrEmail(String username, String email);
    UserDetails findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
