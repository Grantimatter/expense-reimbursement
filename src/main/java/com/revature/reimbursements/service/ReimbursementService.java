package com.revature.reimbursements.service;

import com.revature.reimbursements.model.Reimbursement;
import com.revature.reimbursements.model.User;
import com.revature.reimbursements.model.enumerator.Status;
import com.revature.reimbursements.repo.ReimbursementRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ReimbursementService {
    private final ReimbursementRepo reimbursementRepo;

    public ReimbursementService(ReimbursementRepo reimbursementRepo) {
        this.reimbursementRepo = reimbursementRepo;
    }

    /**
     * Creates a new reimbursement request
     * @param reimbursement The reimbursement to be created
     * @return Returns the reimbursement if it was created successfully
     */
    public Reimbursement createReimbursement(Reimbursement reimbursement) {
        try {
            reimbursement.setStatus(Status.PENDING);
            return reimbursementRepo.save(reimbursement);
        } catch (RuntimeException e) {
            return null;
        }
    }

    /**
     * Updates an existing reimbursement request
     * @param reimbursement The reimbursement request to be updated
     * @return Returns the updated reimbursement if it was updated successfully
     */
    public Reimbursement updateReimbursement(Reimbursement reimbursement, User sessionUser) {
        try {
            reimbursement.setResolved(Date.from(Instant.now()));
            reimbursement.setResolver(sessionUser);
            return reimbursementRepo.save(reimbursement);
        } catch (RuntimeException e) {
            return null;
        }
    }

    public Reimbursement getReimbursementFromId(int id) {
        return reimbursementRepo.getOne(id);
    }

    /**
     * Retrieves all reimbursement requests from the given author
     * @param author The author to retrieve reimbursement requests from
     * @return Returns List of all reimbursements created by specified author
     */
    public List<Reimbursement> getReimbursementsFromAuthor(User author) {
        try {
            return reimbursementRepo.findAllByAuthor(author);
        } catch (RuntimeException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Retrieves all reimbursement requests from the given resolver
     * @param resolver The resolver to retrieve reimbursement requests from
     * @return Returns List of all reimbursements resolved by specified author
     */
    public List<Reimbursement> getReimbursementsFromResolver(User resolver) {
        try {
            return reimbursementRepo.findAllByResolver(resolver);
        } catch (RuntimeException e) {
            return Collections.emptyList();
        }
    }

    public List<Reimbursement> getAllReimbursements() {
        return reimbursementRepo.findAll(Sort.by("submitted"));
    }
}
