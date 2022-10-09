package com.example.instapic.Entity;

import com.example.instapic.Entity.Enum.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Table(name = "users")
public class Users implements UserDetails {
    public Users(Long id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, updatable = false)
    private String username;

    @Column(columnDefinition ="text")
    private String  bio;

    @Column(unique = false)
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(length = 3000)
    private String password;

    @ElementCollection(targetClass = Role.class)
    @CollectionTable(name="users_role", joinColumns = @JoinColumn(name = "users_id"))
   private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "users", orphanRemoval = true)
   private List<Post> posts = new ArrayList<>();
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    @PrePersist
    protected void created(){
        this.createdDate = LocalDateTime.now();
    }
    public Users() {

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
