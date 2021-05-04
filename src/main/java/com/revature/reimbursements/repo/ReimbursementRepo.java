package com.revature.reimbursements.repo;

import com.revature.reimbursements.model.Reimbursement;
import com.revature.reimbursements.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository @Transactional
public interface ReimbursementRepo extends JpaRepository<Reimbursement, Integer> {
    public List<Reimbursement> findAllByAuthor(User author);
    public List<Reimbursement> findAllByResolver(User resolver);
}
