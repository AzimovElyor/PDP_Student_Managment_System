package com.example.pdp_student_managment_system.entity;

import com.example.pdp_student_managment_system.enums.CourseName;
import com.example.pdp_student_managment_system.enums.Permissions;
import com.example.pdp_student_managment_system.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity extends BaseEntity implements UserDetails {
    @Column(nullable = false)
    private String name;
    private String surname;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Permissions> permissions;
    @Enumerated(EnumType.STRING)
    private Set<CourseName> languages;
    private String password;
    private Boolean isVerification = false;
    private Boolean isBlocked =false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet<SimpleGrantedAuthority> roles= new HashSet<>(Set.of(new SimpleGrantedAuthority("ROLE_" + this.role)));
       roles.addAll(permissions
               .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet())
       );
       return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
