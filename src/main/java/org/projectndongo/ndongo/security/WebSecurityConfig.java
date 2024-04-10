package org.projectndongo.ndongo.security;

import org.projectndongo.ndongo.security.handlers.DatabaseLoginFailureHandler;
import org.projectndongo.ndongo.security.handlers.DatabaseLoginSuccessHandler;
import org.projectndongo.ndongo.security.handlers.OAuthLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.authentication.logout.DelegatingServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.WebSessionServerLogoutHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    protected void configureAuth(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                request -> {
                    request.requestMatchers("/", "/login", "/register", "/static/**", "/makeRegister").permitAll();
              //      request.requestMatchers("/admin/**").hasRole("ADMIN");
                    request.anyRequest().authenticated();
                }
        ).formLogin(
                login -> {
                    login.loginPage("/login");
                    login.usernameParameter("username");
                    login.passwordParameter("password");
                    login.defaultSuccessUrl("/");
                    login.successForwardUrl("/");
                    login.successHandler(databaseLoginSuccessHandler);
                    login.failureHandler(databaseLoginFailureHandler);
                }
        ).oauth2Login(
                oauth2 -> {
                    oauth2.loginPage("/login");
                    oauth2.userInfoEndpoint(
                            userInfo -> userInfo.userService(oauth2UserService)
                    );
                    oauth2.successHandler(oauthLoginSuccessHandler);
                }
        ).logout(
                logout -> {
                    logout.logoutRequestMatcher(request -> request.getServletPath().equals("/logout"));
                    logout.deleteCookies("JSESSIONID");
                    logout.logoutSuccessUrl("/");
                }
        ).exceptionHandling(
                exception -> exception.accessDeniedPage("/403")
        );

        return http.build();
    }

    @Autowired
    private OAuth2UserService oauth2UserService;

    @Autowired
    private OAuthLoginSuccessHandler oauthLoginSuccessHandler;

    @Autowired
    private DatabaseLoginSuccessHandler databaseLoginSuccessHandler;

    @Autowired
    private DatabaseLoginFailureHandler databaseLoginFailureHandler;
}
