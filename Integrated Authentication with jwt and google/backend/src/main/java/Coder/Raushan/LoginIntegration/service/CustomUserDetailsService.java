package Coder.Raushan.LoginIntegration.service;

import Coder.Raushan.LoginIntegration.entity.CustomUserDetails;
import Coder.Raushan.LoginIntegration.entity.User;
import Coder.Raushan.LoginIntegration.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /** "username" here is the user's email — that's what LoginController authenticates with. */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (user.getPassword() == null) {
            // Account only ever registered via Google - no local password to check against.
            throw new UsernameNotFoundException("This email is registered via Google. Please use Login with Google.");
        }

        return new CustomUserDetails(user);
    }
}
