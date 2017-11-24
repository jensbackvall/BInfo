package dk.binfo.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;


	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth
				.userDetailsService(userDetailsService)
				.passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.
			authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/registration").permitAll()
				.antMatchers("/error").hasAuthority("ADMIN")
				.antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
				.authenticated().and().csrf().disable().formLogin()
				.loginPage("/login").failureUrl("/login?error=true")
				.defaultSuccessUrl("/admin/home")
				.usernameParameter("email")
				.passwordParameter("password")
				.and().rememberMe().key("rem-me-key").rememberMeParameter("remember-me").rememberMeCookieName("remember-me") // TODO Check om der er sikkershedsproblemer
				.and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/").and().exceptionHandling()
				.accessDeniedPage("/access-denied");
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/resources/**", "/static/**", "/fonts/**", "/bootstrap/**","/css/**", "/js/**", "/img/**");
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

}

/*
This class is where the security logic is implemented, let´s analyze the code.
Line 21 → password encoder reference implemented in WebMvcConfig.java
Line 24 → data source implemented out of the box by Spring Boot. We only need to provide the database information in the application.properties file (please see the reference below).
Lines 27 and 30 → Reference to user and role queries stored in application.properties file (please see the reference below).
Lines from 33 to 41 → AuthenticationManagerBuilder provides a mechanism to get a user based on the password encoder, data source, user query and role query.
Lines from 44 to 61 → Here we define the antMatchers to provide access based on the role(s) (lines 48 to 51), the parameters for the login process (lines 55 to 56), the success login page(line 53), the failure login page(line 53), and the logout page (line 58).
Lines from 64 to 68 → Due we have implemented Spring Security we need to let Spring knows that our resources folder can be served skipping the antMatchers defined.
 */



/* The WebSecurityConfig class is annotated with @EnableWebSecurity to enable Spring Security’s web security support and provide the Spring MVC integration. It also extends WebSecurityConfigurerAdapter and overrides a couple of its methods to set some specifics of the web security configuration.

The configure(HttpSecurity) method defines which URL paths should be secured and which should not. Specifically, the "/" and "/home" paths are configured to not require any authentication. All other paths must be authenticated.

When a user successfully logs in, they will be redirected to the previously requested page that required authentication. There is a custom "/login" page specified by loginPage(), and everyone is allowed to view it.

As for the configureGlobal(AuthenticationManagerBuilder) method, it sets up an in-memory user store with a single user. That user is given a username of "user", a password of "password", and a role of "USER". */