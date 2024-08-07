package spring.Infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.repositories.UserRepository;
import spring.services.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var teste =  userService.getUserByUsername(username)
                .map(UserAuthenticated::new)
                .orElseThrow(()->new UsernameNotFoundException("user not Found"));

        System.out.println(teste.getUsername());
        System.out.println(teste.getPassword());
        return teste;
    }
}
