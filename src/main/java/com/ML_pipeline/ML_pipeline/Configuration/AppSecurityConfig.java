package com.ML_pipeline.ML_pipeline.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(Customizer.withDefaults());//form -> form.disable()

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
}