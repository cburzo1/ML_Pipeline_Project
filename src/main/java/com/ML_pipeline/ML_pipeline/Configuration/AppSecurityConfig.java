package com.ML_pipeline.ML_pipeline.Configuration;

import com.ML_pipeline.ML_pipeline.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/*🔐 http.authorizeHttpRequests(...)
This tells Spring Security:

        “Hey, let’s define rules for which requests are allowed without authentication and which are not.”

You're providing a lambda that customizes the AuthorizationFilter.

        🧱 Inside the Lambda: auth -> auth.requestMatchers(...)...
Line	Explanation
requestMatchers("signup", "login")	This says: “If the HTTP request path matches /signup or /login, allow it.”
These are public endpoints (no login required).
        .permitAll()	Explicitly allows everyone to access those endpoints — even unauthenticated users.
.anyRequest().authenticated()	All other requests must come from an authenticated user. If not, Spring Security will block the request or redirect to login (depending on setup).
*/

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("signup", "login")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                //.formLogin(Customizer.withDefaults())
                .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));//form -> form.disable()

        System.out.println("🔐 Security config applied");

        return http.build();
    }

 //   @Bean
 //   public UserDetailsService userDetailsService(){

//        UserDetails user1 = User
//                .withDefaultPasswordEncoder()
//                .username("")

   //     return new InMemoryUserDetailsManager();
    //}

    @Bean
    public AuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);

        return provider;
        //PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        /*
        *🔁 Why the Instructor Said "The Problem is When Getting the User"
            Because the error usually occurs when Spring Security retrieves the user from the DB and attempts to match the password during login — not during signup.

            If the password in the DB:

            Lacks a prefix ({bcrypt} or {noop})

            Is not hashed or malformed

            Then Spring throws:

            python
            Copy
            Edit
            There is no PasswordEncoder mapped for the id "null"
            ✅ Summary
            createDelegatingPasswordEncoder() expects passwords to be prefixed with {bcrypt}, {noop}, etc.

            You must manually call encode() on passwords before saving.

            If you skip encode(), add {noop} yourself for testing.

            Always test your login, not just the signup.
        * */
    }

    //talks to authentication provider ^
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}