package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public interface UserDetailServiceImpl implements UserDetailService {
	
	private final UserRepository usersRepository;
	
	@Override 
	pubilc UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		
		User user=usersRepository.findById(username);
		.orElseThrow() -> new UsernameNotFoundException(#"usernot found with username: " +username);
		
		Collection <GrantedAuthority> authorities =new ArrayList<>();
		
		if(user.isUserAdminFlg()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		return new User(user.getUserId(), user.getUserPassword(), authorities);
		
		
	}

}
