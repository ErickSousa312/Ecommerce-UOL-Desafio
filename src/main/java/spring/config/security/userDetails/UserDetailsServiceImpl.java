package spring.config.security.userDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.domain.entities.user.model.Customer;
import spring.domain.repositories.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user details not found user: " + username));
        System.out.println(customer);
        List<GrantedAuthority> authorities = customer.getAuthority().stream()
                .map(authority -> new SimpleGrantedAuthority((authority.getName()))).collect(Collectors.toList());
        UserDetails user = new UserDetailsImpl(customer.getEmail(), customer.getPws(),customer.getName(),authorities);
        return user;
    }
}
