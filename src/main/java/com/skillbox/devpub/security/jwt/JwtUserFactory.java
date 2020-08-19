package com.skillbox.devpub.security.jwt;

import com.skillbox.devpub.model.Role;
import com.skillbox.devpub.model.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class JwtUserFactory {

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getIsModerator(),
                user.getRegTime(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getCode(),
                user.getPhoto(),
                getAuthorities(user.getRoles())
        );
    }

    private static List<GrantedAuthority> getAuthorities(List<Role> userRoles){
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName().toString())
                ).collect(Collectors.toList());
    }
}
