package com.example.demo.service;
import java.util.ArrayList;
import java.util.Collection;

// ...(必要なimport文は、IDEに自動で追加させるのだ)...
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Users;
import com.example.demo.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findById(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found..."));

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (user.isUserAdminFlg()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(user.getUserId(), user.getUserPassword(), authorities);
    }
}