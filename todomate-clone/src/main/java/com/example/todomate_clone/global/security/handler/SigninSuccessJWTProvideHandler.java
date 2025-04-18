package com.example.todomate_clone.global.security.handler;

import com.example.todomate_clone.auth.service.AuthService;
import com.example.todomate_clone.global.security.jwt.service.JwtService;
import com.example.todomate_clone.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class SigninSuccessJWTProvideHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email = extractEmail(authentication);
        String accessToken = jwtService.createAccessToken(email);
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        authService.saveRefreshToken(email, refreshToken);

        log.info("로그인 성공. email: {}", email);
        log.info("AccessToken 발급. AccessToken: {}", accessToken);
        log.info("RefreshToken 발급. RefreshToken: {}", refreshToken);

        response.getWriter().write("success");
    }

    private String extractEmail(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
