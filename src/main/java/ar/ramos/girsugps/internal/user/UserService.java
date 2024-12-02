package ar.ramos.girsugps.internal.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Profile("prod")
@Service
public class UserService implements UserDetailsService {

    private final IUserRepository userRepository;
    private final IUserHomeRepository userHomeRepository;

    public UserDetails addUser(String username, String encodedPassword, String roles) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Transactional
    public UserHome setHome(String username, UserHome home) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserHome currentUserHome = user.getHome();
        if (currentUserHome != null) {
            userHomeRepository.delete(currentUserHome);
            user.setHome(null);
            userRepository.save(user);
        }
        UserHome savedHome = userHomeRepository.save(home);
        user.setHome(savedHome);
        userRepository.save(user);
        return savedHome;
    }
}
