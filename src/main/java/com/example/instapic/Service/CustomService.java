package com.example.instapic.Service;

import com.example.instapic.Entity.Users;
import com.example.instapic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findUsersByEmail(username)
                .orElseThrow(() ->new UsernameNotFoundException("Username not found with" +username));
        return build(user);
    }
    public static Users build(Users user){
        List<GrantedAuthority> authority = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
        return new Users(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authority);
    }
    public  Users loadUserById(Long id) {
        return userRepository.findUsersById(id).orElse(null);

    }

}
