package com.revature.reimbursements.service;

import com.revature.reimbursements.model.User;
import com.revature.reimbursements.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User createUser(User user) {
        try {
            return userRepo.save(user);
        } catch (Exception e) {
            return null;
        }
    }

    public User loginUser(String usernameOrEmail, String password) {
        return userRepo.getUserByUsernameIgnoreCaseOrEmailIgnoreCaseAndPassword(usernameOrEmail, usernameOrEmail, password);
    }

    public User updateUser(User user) {
        return userRepo.save(user);
    }

    public User getUserById(int userId) {
        return userRepo.getOne(userId);
    }

    public User getUserByUsername(String username) { return userRepo.getUserByUsernameIgnoreCase(username); }
}
