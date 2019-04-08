package com.sapbasemodule.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }
    
/*    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	System.out.println("Adding CORS Filter");
        http.cors();
        System.out.println("Added CORS Filter");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        
        List<String> allowedOriginList = new ArrayList<String>();
        allowedOriginList.add("*");
        configuration.setAllowedOrigins(allowedOriginList);
        
        List<String> allowedMethodsList = new ArrayList<String>();
        allowedMethodsList.add("HEAD");
        allowedMethodsList.add("OPTIONS");
        allowedMethodsList.add("GET");
        allowedMethodsList.add("POST");
        allowedMethodsList.add("PUT");
        allowedMethodsList.add("DELETE");
        allowedMethodsList.add("PATCH");
        configuration.setAllowedMethods(allowedMethodsList);
        
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        List<String> allowedHeadersList = new ArrayList<String>();
        allowedHeadersList.add("Authorization");
        allowedHeadersList.add("Cache-Control");
        allowedHeadersList.add("Content-Type");
        configuration.setAllowedHeaders(allowedHeadersList);
        
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        System.out.println("Added CORS Configuration");
        
        return source;
    }*/
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
           .userDetailsService(userDetailsService)
           .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
           .ignoring()
           .antMatchers("/v1/test")
           .antMatchers("/v1/ext/**");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}

