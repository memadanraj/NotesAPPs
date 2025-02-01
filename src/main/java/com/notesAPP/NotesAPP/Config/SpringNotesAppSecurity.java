package com.notesAPP.NotesAPP.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringNotesAppSecurity {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http ) throws Exception {

        http
                .authorizeHttpRequests(HttpSecurity -> HttpSecurity
//                        .requestMatchers("/test").permitAll()
                        .anyRequest().permitAll())

                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(Session ->Session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());

//                .formLogin(Customizer.withDefaults());


            return http.build();
        }

}
