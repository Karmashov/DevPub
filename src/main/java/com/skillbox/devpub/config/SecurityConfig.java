package com.skillbox.devpub.config;

import com.skillbox.devpub.controller.ApiAuthController;
import com.skillbox.devpub.dto.universal.ResponseFactory;
import com.skillbox.devpub.service.AuthService;
import com.skillbox.devpub.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**"/*, "/api/auth/restore"*/).permitAll()
//                .antMatchers("/api/post").hasAuthority(Permission.USER.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().disable()
                .httpBasic()
//                .and()
//                .logout()
//                .logoutUrl("/api/auth/logout")
////                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
////                .logoutSuccessUrl("/**")
////                .permitAll()
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .logoutSuccessHandler(logoutSuccessHandler())
                ;

//        http
////                .antMatcher("/api/**")
//                .httpBasic().disable()
//                .csrf().disable()
//                .cors()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers(
//                        "/",
//                        "/api/auth/login"
////                        "/api/v1/auth/*",
////                        "/api/v1/account/register",
////                        "/api/v1/account/password/recovery",
////                        "/api/v1/account/password/set",
////                        "/api/v1/platform/languages",
////                        "/api/v1/storage/*",
////                        "/api/v1/support/message"
//                ).permitAll()
////                .antMatchers("/api/v1/**").hasRole("USER")
//                .and()
//                .apply(new JwtConfigurer(jwtTokenProvider));
////                .and().exceptionHandling().authenticationEntryPoint(Http401EntryPoint.unauthorizedHandler());
    }

//    @Bean
//    public LogoutSuccessHandler logoutSuccessHandler() {
//        ApiAuthController controller = null;
//        controller.logout();
//        return new CustomLogoutSuccessHandler();
//    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //    private final JwtTokenProvider jwtTokenProvider;

//    @Autowired
//    public SecurityConfig(UserDetailsService userDetailsService, JwtTokenProvider jwtTokenProvider) {
//        this.userDetailsService = userDetailsService;
//        this.jwtTokenProvider = jwtTokenProvider;
//    }

//    private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
}
