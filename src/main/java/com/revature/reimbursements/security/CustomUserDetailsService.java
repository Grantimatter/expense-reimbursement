package com.revature.reimbursements.security;

import com.revature.reimbursements.controller.UserController;
import com.revature.reimbursements.model.User;
import com.revature.reimbursements.repo.UserRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;
    private static final Logger log = LogManager.getLogger(CustomUserDetailsService.class);

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.getUserByUsernameIgnoreCase(username);
        log.debug("Checking user by username");

        CustomUserDetails userDetails = null;
        if(user != null) {
            userDetails = new CustomUserDetails();
            userDetails.setUser(user);
            log.info("User: " + userDetails.getUsername());
        } else {
            throw new UsernameNotFoundException("User \"" + username + "\" does not exist");
        }
        return userDetails;
    }
}
