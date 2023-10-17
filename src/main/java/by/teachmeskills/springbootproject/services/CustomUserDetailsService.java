package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.Role;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails;
        User user = userRepository.findByEmail(username);
        if (user != null) {
            userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                    user.getRoles().stream().map(Role::getName).map(SimpleGrantedAuthority::new).toList());
        } else {
            throw new UsernameNotFoundException("User wasn't found");
        }
        return userDetails;
    }
}
