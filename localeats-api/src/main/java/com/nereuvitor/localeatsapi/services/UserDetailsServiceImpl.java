package com.nereuvitor.localeatsapi.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nereuvitor.localeatsapi.models.User;
import com.nereuvitor.localeatsapi.repositories.UserRepository;
import com.nereuvitor.localeatsapi.security.UserSpringSecurity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email));
        return new UserSpringSecurity(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getProfiles());
    }

}
