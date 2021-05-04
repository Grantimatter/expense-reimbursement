package com.revature.reimbursements.repo;

import com.revature.reimbursements.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository @Transactional
public interface UserRepo extends JpaRepository<User, Integer> {
    public User getUserByUsernameIgnoreCase(String username);
    public User getUserByUsernameIgnoreCaseOrEmailIgnoreCaseAndPassword(String username, String email, String password);
}
