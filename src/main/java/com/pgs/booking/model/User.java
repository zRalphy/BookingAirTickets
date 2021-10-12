package com.pgs.booking.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    @Column(name = "accountNonExpired")
    private boolean accountNonExpired;
    @Column(name = "accountNonLocked")
    private boolean accountNonLocked;
    @Column(name = "credentialsNonExpired")
    private boolean credentialsNonExpired;
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }
}
