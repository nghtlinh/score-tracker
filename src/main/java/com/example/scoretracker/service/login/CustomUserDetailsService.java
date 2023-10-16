package com.example.scoretracker.service.login;

import com.example.scoretracker.model.entity.EmployeeEntity;
import com.example.scoretracker.model.entity.EmployeeRoleEntity;
import com.example.scoretracker.model.entity.RoleEntity;
import com.example.scoretracker.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EmployeeEntity user = employeeRepository.findFirstByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Not found user: %s", username));
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (EmployeeRoleEntity userRole : user.getEmpRoles()) {
            RoleEntity roleEntity = userRole.getRole();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleEntity.getRoleName()));
        }
        return new CustomUserDetails(user, authorities);
    }

}

