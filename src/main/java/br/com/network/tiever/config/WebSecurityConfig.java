package br.com.network.tiever.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.network.tiever.security.JWTAuthenticationFilter;
import br.com.network.tiever.security.JWTLoginFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // disable caching
        http.headers().cacheControl();
        http.csrf().disable();

        http.csrf().disable() // disable csrf for our requests.
    	.authorizeRequests()
    	.antMatchers("/register**").permitAll()
    	.antMatchers(HttpMethod.POST, "/usuario").permitAll()
    	.antMatchers("/css/**", "/js/**", "/img/**", "/bower_components/**", "/app.js", "/register/**", "/assets/**", "/index**").permitAll()
    	.anyRequest().authenticated()
    	.and()
    	// We filter the api/login requests
    	.addFilterBefore(new JWTLoginFilter("/register", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
    	// And filter other requests to check the presence of JWT in header
    	.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    
//    http.csrf().disable() // disable csrf for our requests.
//	.authorizeRequests()
//	.antMatchers("/register**").permitAll()
//	.anyRequest().authenticated()
//	.and()
//	.formLogin()
//	.and()
//	// We filter the api/login requests
//	.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
//	// And filter other requests to check the presence of JWT in header
//	.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    
    // Esse aqui deu certin ein =D
//    http.csrf().disable() // disable csrf for our requests.
//	.authorizeRequests()
//	.antMatchers("/register**").permitAll()
//	.antMatchers("/css/**", "/js/**", "/img/**", "/bower_components/**", "/app.js", "/register/**", "/assets/**", "/index**")
//    .permitAll()
//	.anyRequest().authenticated()
//	.and()
//	// We filter the api/login requests
//	.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
//	// And filter other requests to check the presence of JWT in header
//	.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    
//    http
//	.authorizeRequests()
//		.anyRequest().authenticated()
//		.and()
//	.formLogin()
//		.loginPage("/register/register.html")
//		.permitAll()
//		.and()
//	.logout();

}
