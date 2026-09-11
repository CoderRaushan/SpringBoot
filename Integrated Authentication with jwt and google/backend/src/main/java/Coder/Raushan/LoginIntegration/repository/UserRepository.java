package Coder.Raushan.LoginIntegration.repository;

import Coder.Raushan.LoginIntegration.entity.AuthProvider;
import Coder.Raushan.LoginIntegration.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByProviderAndProviderSubject(AuthProvider provider, String providerSubject);
}
