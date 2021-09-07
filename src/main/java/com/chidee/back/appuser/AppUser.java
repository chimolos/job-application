package com.chidee.back.appuser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Getter
@Setter

@NoArgsConstructor
@Entity
public class AppUser implements UserDetails, Serializable {


    @SequenceGenerator(name = "student_sequence",
                        sequenceName = "student_sequence",
                        allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "student_sequence")
    @Column(name = "id")
    private Long id;
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank
    private String surname;
    @NotBlank
    @Email(message = "Please provide a valid email")
    private String email;

    private String password;
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
    private Boolean locked;
    private Boolean enabled;
//    private Boolean loggedIn;

    public AppUser(String firstName, String surname, String email,
                   String password, AppUserRole appUserRole) {
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
        this.enabled = false;
    }

    public AppUser(String firstName, String surname, String email, AppUserRole appUserRole) {
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.appUserRole = appUserRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    public boolean hasRole(AppUserRole appUserRole) {
        return hasRole(appUserRole);
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String getUsername(){
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
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

    public String getFullName() {
        return getFirstName() + " " + getSurname();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser admin = (AppUser) o;
        return Objects.equals(email, admin.email) &&
                Objects.equals(password, admin.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, surname, email, password, appUserRole, locked, enabled);
    }
}
