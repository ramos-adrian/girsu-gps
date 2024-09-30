package ar.ramos.girsugps.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/ws/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/trucks/**", "/positionRecords/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/trucks/**", "/positionRecords/**")
                        .hasRole("ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .csrf(csfr -> csfr.disable())
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    Just for testing purposes
//    @Bean
//    UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder) {
//        User.UserBuilder users = User.builder();
//        UserDetails sarah = users
//                .username("sarah")
//                .password(passwordEncoder.encode("password"))
//                .roles("USER")
//                .build();
//        UserDetails john = users
//                .username("john")
//                .password(passwordEncoder.encode("password"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(sarah, john);
//    }
}
