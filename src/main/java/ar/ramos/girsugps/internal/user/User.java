package ar.ramos.girsugps.internal.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Profile("prod")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "\"USER\"")
public class User implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String roles; // Comma-separated roles

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    private String password;

    private String home_latitude;
    private String home_longitude;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
