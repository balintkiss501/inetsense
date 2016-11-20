package hu.elte.inetsense.server.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @author Zsolt Istvanfi
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "/register", "/index.html", "/app/app.js", "/app/login/**", "/fonts/**")
                .permitAll().anyRequest().authenticated().and()
                // login config
                .formLogin().loginPage("/#/login").usernameParameter("email").passwordParameter("password")
                .loginProcessingUrl("/login").successHandler(new NoRedirectAuthenticationSuccessHandler())
                .failureHandler(new NoRedirectAuthenticationFailuresHandler()).and()
                // logout config
                .logout().logoutSuccessUrl("/").and()
                // CSRF protection
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public class NoRedirectAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                final Authentication authentication) throws IOException, ServletException {
            // It is sent to avoid redirects.
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    public class NoRedirectAuthenticationFailuresHandler extends SimpleUrlAuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
                final AuthenticationException exception) throws IOException, ServletException {
            // It is sent to avoid redirects.
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

}
