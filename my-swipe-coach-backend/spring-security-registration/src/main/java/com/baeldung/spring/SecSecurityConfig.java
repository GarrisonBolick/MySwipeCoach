package com.baeldung.spring;

import java.io.File;
import java.io.IOException;

import com.baeldung.javainuse.config.JwtAuthenticationEntryPoint;
import com.baeldung.javainuse.config.JwtRequestFilter;
import com.baeldung.persistence.dao.UserRepository;
import com.baeldung.security.CustomRememberMeServices;
import com.baeldung.security.google2fa.CustomAuthenticationProvider;
import com.baeldung.security.google2fa.CustomWebAuthenticationDetailsSource;
import com.baeldung.security.location.DifferentLocationChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;

@ComponentScan(basePackages = { "src.main.java.com.baeldung.security" })
// @ImportResource({ "classpath:webSecurityConfig.xml" })
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private LogoutSuccessHandler myLogoutSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private CustomWebAuthenticationDetailsSource authenticationDetailsSource;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DifferentLocationChecker differentLocationChecker;

    public SecSecurityConfig() {
        super();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/resources/**")
            .antMatchers("/h2/**");
    }
    


	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF for this example
		httpSecurity.csrf().disable()
				// dont authenticate this particular request
				.authorizeRequests().antMatchers("/authenticate", "/user/login", "/user/registration", "/registrationConfirm").permitAll().
				// all other requests need to be authenticated
				anyRequest().authenticated().and().
				// make sure we use stateless session; session won't be used to
				// store user's state.
				exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		// Enable CORS
		httpSecurity.cors();

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

//    @Override
//    protected void configure(final HttpSecurity http) throws Exception {
//        // @formatter:off
//
//   http.csrf().disable();
//   // http
////            .csrf().disable()
////            .authorizeRequests()
////                .antMatchers( "client/update", "/user/login","/logout*", "/signin/**", "/signup/**", "/customLogin",
////                        "/user/registration*", "/registrationConfirm*", "/expiredAccount*", "/registration*",
////                        "/badUser*","/register*", "/user/resendRegistrationToken*" ,"/forgetPassword*", "/user/resetPassword*","/user/savePassword*","/updatePassword*",
////                        "/user/changePassword*", "/emailError*", "/resources/**","/old/user/registration*","/successRegister*","/qrcode*","/user/enableNewLoc*").permitAll()
////                .antMatchers("/invalidSession*").anonymous()
////                .antMatchers("/user/updatePassword*").hasAuthority("CHANGE_PASSWORD_PRIVILEGE")
////               // .antMatchers(HttpMethod.POST, "/user/login").permitAll()
////                .anyRequest().hasAuthority("WRITE_PRIVILEGE")
////               
////                .and()
////            .formLogin()
////            	.loginPage("/user/login")
////                .failureUrl("/login?error=true")
////                .successHandler(myAuthenticationSuccessHandler)
////                .failureHandler(authenticationFailureHandler)
////                .authenticationDetailsSource(authenticationDetailsSource)
////            .permitAll()
////                .and()
////            .sessionManagement()
////                .invalidSessionUrl("/invalidSession.html")
////                .maximumSessions(1).sessionRegistry(sessionRegistry()).and()
////                .sessionFixation().none()
////            .and()
////            .logout()
////                .logoutSuccessHandler(myLogoutSuccessHandler)
////                .invalidateHttpSession(false)
////                .logoutSuccessUrl("/logout.html?logSucc=true")
////                .deleteCookies("JSESSIONID")
////                .permitAll()
////             .and()
////                .rememberMe().rememberMeServices(rememberMeServices()).key("theKey");
//
//    // @formatter:on
//    }

    // beans

    @Bean
    public DaoAuthenticationProvider authProvider() {
        final CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        authProvider.setPostAuthenticationChecks(differentLocationChecker);
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        CustomRememberMeServices rememberMeServices = new CustomRememberMeServices("theKey", userDetailsService, new InMemoryTokenRepositoryImpl());
        return rememberMeServices;
    }

    @Bean(name="GeoIPCountry")
    public DatabaseReader databaseReader() throws IOException, GeoIp2Exception {
        final File resource = new File("src/main/resources/maxmind/GeoLite2-Country.mmdb");
        return new DatabaseReader.Builder(resource).build();
    }

}
