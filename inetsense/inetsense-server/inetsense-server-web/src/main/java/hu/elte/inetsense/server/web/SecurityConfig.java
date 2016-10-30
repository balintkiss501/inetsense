package hu.elte.inetsense.server.web;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "/register", "/index.html", "/app/app.js", "/app/login/**").permitAll()
                .anyRequest().authenticated().and()
                // login config
                .httpBasic().and().formLogin().loginPage("/#/login").usernameParameter("email").passwordParameter("password")
                .and()
                // logout config
                .logout().logoutSuccessUrl("/#/login").and()
                // CSRF protection
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

}
