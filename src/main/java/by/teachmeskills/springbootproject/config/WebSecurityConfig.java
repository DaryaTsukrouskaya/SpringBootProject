package by.teachmeskills.springbootproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@Configuration
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable).
                authorizeHttpRequests((authz) ->
                        authz.requestMatchers(new AntPathRequestMatcher("/profile/**"),
                                new AntPathRequestMatcher("/cart/checkout"), new AntPathRequestMatcher("/cart/createOrder")).
                        authenticated().requestMatchers(new AntPathRequestMatcher("/**/loadFromFile/**"), new AntPathRequestMatcher("/**/loadCsvFile/**"))
                        .hasAuthority("ADMIN").anyRequest().permitAll()).formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/home").failureUrl("/login?error=true")
                        .usernameParameter("email").passwordParameter("password")).logout(logout -> logout.logoutUrl("/logout").invalidateHttpSession(true).clearAuthentication(true)
                        .permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
