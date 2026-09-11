package Coder.Raushan.LoginIntegration.service;

import Coder.Raushan.LoginIntegration.entity.AuthProvider;
import Coder.Raushan.LoginIntegration.entity.Role;
import Coder.Raushan.LoginIntegration.entity.User;
import Coder.Raushan.LoginIntegration.repository.RoleRepository;
import Coder.Raushan.LoginIntegration.repository.UserRepository;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final OidcUserService delegate = new OidcUserService();
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public CustomOidcUserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        // Let Spring do the real OIDC handshake / token validation first.
        OidcUser oidcUser = delegate.loadUser(userRequest);

        String providerSubject = oidcUser.getSubject();
        String email = oidcUser.getClaimAsString("email");
        String name = oidcUser.getClaimAsString("name");

        registerOrUpdate(providerSubject, email, name);

        return oidcUser;
    }

    private void registerOrUpdate(String providerSubject, String email, String name) {
        Optional<User> byProviderSubject =
                userRepository.findByProviderAndProviderSubject(AuthProvider.GOOGLE, providerSubject);

        if (byProviderSubject.isPresent()) {
            User user = byProviderSubject.get();
            user.setName(name);
            user.setEmail(email);
            userRepository.save(user);
            return;
        }

        // Same email previously registered with a local password: link Google to that account.
        Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            user.setProviderSubject(providerSubject);
            if (user.getProvider() == AuthProvider.LOCAL && user.getPassword() != null) {
                // keep provider as LOCAL so the password login keeps working; Google sign-in
                // is matched via providerSubject regardless of the provider field.
            }
            userRepository.save(user);
            return;
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setProvider(AuthProvider.GOOGLE);
        newUser.setProviderSubject(providerSubject);
        newUser.setEnabled(true);

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("ROLE_USER not seeded"));
        newUser.getRoles().add(role);

        userRepository.save(newUser);
    }
}
