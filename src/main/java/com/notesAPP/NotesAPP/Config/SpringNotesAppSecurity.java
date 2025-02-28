package com.notesAPP.NotesAPP.Config;

import com.notesAPP.NotesAPP.Filters.JWTSecurityFilter;
import com.notesAPP.NotesAPP.Impl.UserdetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SpringNotesAppSecurity {

@Autowired
private UserdetailsService userdetailsService;
@Autowired
private JWTSecurityFilter jwtSecurityFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http ) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for APIs
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No sessions
                .authorizeHttpRequests(auth -> auth
                        // Only ADMIN can access
                        .requestMatchers("/api/userinfo/admin/**"
                                ,"/api/mapping/admin/**"
                                ,"/api/notes/admin/**"
                                ,"/api/qn/admin/**"
                                , "/api/notice/admin/**",
                                "/api/results/admin/**",
                                "/api/solution/admin/**").hasRole("ADMIN")
                        // USER & ADMIN can access
                        .requestMatchers("/api/userinfo/user/**",
                                "/api/notes/user/**"
                                ,"/api/qn/user/**"
                                , "/api/notice/user/**",
                                "/api/results/user/**",
                                "/api/solution/user/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/userinfo/public/**",
                                "/v3/api-docs", "/swagger-ui/**", "/swagger-ui.html").permitAll()  // Anyone can access
                        .anyRequest().authenticated()  // All other endpoints need authentication
                )
                .addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService((userdetailsService) ); // Inject UserAuthImpl here
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
         return config.getAuthenticationManager();
    }


}
