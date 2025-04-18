package com.example.todomate_clone.global.config;

import com.example.todomate_clone.auth.service.AuthService;
import com.example.todomate_clone.global.security.filter.JsonUsernamePasswordAuthenticationFilter;
import com.example.todomate_clone.global.security.filter.JwtAuthenticationProcessingFilter;
import com.example.todomate_clone.global.security.handler.SigninFailureHandler;
import com.example.todomate_clone.global.security.handler.SigninSuccessJWTProvideHandler;
import com.example.todomate_clone.global.security.jwt.service.JwtService;
import com.example.todomate_clone.global.security.service.UserDetailsServiceImpl;
import com.example.todomate_clone.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthService authService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordSigninFilter) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/signup/**", "/signin", "/api/v1/auth/signup/**", "/api/v1/auth/signin").permitAll()
                        .anyRequest().authenticated())
                .logout((logout) -> logout
                        .logoutSuccessHandler((req, res, auth) -> {
                            res.setStatus(HttpServletResponse.SC_OK);
                        }))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterAfter(jsonUsernamePasswordSigninFilter, LogoutFilter.class)
                .addFilterBefore(jwtAuthenticationProcessingFilter(), JsonUsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        DaoAuthenticationProvider provider = daoAuthenticationProvider();
        return new ProviderManager(provider);
    }

    @Bean
    public SigninSuccessJWTProvideHandler signinSuccessJWTProvideHandler() {
        return new SigninSuccessJWTProvideHandler(jwtService, authService);
    }

    @Bean
    public SigninFailureHandler signinFailureHandler() {
        return new SigninFailureHandler();
    }

    @Bean
    public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordSigninFilter() throws Exception {
        JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordSigninFilter = new JsonUsernamePasswordAuthenticationFilter(objectMapper);
        jsonUsernamePasswordSigninFilter.setAuthenticationManager(authenticationManager());
        jsonUsernamePasswordSigninFilter.setAuthenticationSuccessHandler(signinSuccessJWTProvideHandler());
        jsonUsernamePasswordSigninFilter.setAuthenticationFailureHandler(signinFailureHandler());
        return jsonUsernamePasswordSigninFilter;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter(){
        JwtAuthenticationProcessingFilter jsonUsernamePasswordSigninFilter = new JwtAuthenticationProcessingFilter(jwtService, userRepository);

        return jsonUsernamePasswordSigninFilter;
    }

}
