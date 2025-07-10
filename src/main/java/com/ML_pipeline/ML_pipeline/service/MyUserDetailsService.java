package com.ML_pipeline.ML_pipeline.service;

import com.ML_pipeline.ML_pipeline.model.User;
import com.ML_pipeline.ML_pipeline.model.UserPrincipal;
import com.ML_pipeline.ML_pipeline.repository.authDB_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/*3. UserDetailsService
This is the bridge between Spring Security and your database.

You implement loadUserByUsername(String username) to fetch your user from the DB and return
 a UserDetails object (usually your UserPrincipal).

java
Copy
Edit
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found");
        return new UserPrincipal(user);  // wrapped in your adapter
    }
};*/
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private authDB_repo adbr;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = adbr.findByUsername(username);
        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(user);
    }
}