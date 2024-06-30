package org.mylog.config;

import lombok.RequiredArgsConstructor;
import org.mylog.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityLoginFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/", "/users/register").permitAll()
                .anyRequest()
                .authenticated())

            .formLogin(form -> form
                    .loginPage("/loginForm")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/") // 첫 번째 파라미터는 기본 URL, 두 번째 파라미터 true는 항상 이 URL로 리디렉트
                    .permitAll())

            .logout(logout-> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/"))

            .userDetailsService(customUserDetailsService)

            .csrf(csrf -> csrf.disable())

            .sessionManagement(sessionManagement -> sessionManagement
                            .maximumSessions(1) // 동시 접속 허용 개수
                            .maxSessionsPreventsLogin(true) // 동시 로그인을 차단 default - false (먼저 로그인한 사용자 차단)
                                                            // true - 애초에 허용개수를 초과하는 사용자는 로그인이 안되도록 차단
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
