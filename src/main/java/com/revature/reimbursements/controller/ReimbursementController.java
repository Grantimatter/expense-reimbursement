package com.revature.reimbursements.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimbursements.model.Reimbursement;
import com.revature.reimbursements.model.User;
import com.revature.reimbursements.model.dto.ReimbursementDTO;
import com.revature.reimbursements.model.enumerator.Role;
import com.revature.reimbursements.service.ReimbursementService;
import com.revature.reimbursements.util.SessionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/reimbursement")
@CrossOrigin("http://www.localhost:4200")
public class ReimbursementController {

    private final ReimbursementService reimbursementService;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public ReimbursementController(ReimbursementService reimbursementService) {
        this.reimbursementService = reimbursementService;
    }

    /**
     * Used to create new reimbursement requests
     * @param reimbursementDTO The reimbursement DTO to use in creating a local reimbursement
     * @param request The HttpServletRequest needed to validate sessions
     * @return Returns the newly created Reimbursement in a ResponseEntity if it was created successfully
     */
    @PostMapping
    public ResponseEntity<Reimbursement> createReimbursement(@RequestBody ReimbursementDTO reimbursementDTO, HttpServletRequest request) {
        User sessionUser = SessionUtil.getUserFromSession(request);
        if(sessionUser == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Reimbursement newReimbursement = objectMapper.convertValue(reimbursementDTO, Reimbursement.class);
        newReimbursement.setAuthor(sessionUser);
        Reimbursement reimbursement = reimbursementService.createReimbursement(newReimbursement);
        if(reimbursement != null) return ResponseEntity.status(HttpStatus.CREATED).body(reimbursement);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    /**
     * Used to retrieve all reimbursements for every user
     * @param request The HttpServletRequest needed to validate sessions
     * @return Returns a list of all reimbursement requests in a ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<Reimbursement>> getReimbursements(HttpServletRequest request) {
        User sessionUser = SessionUtil.getUserFromSession(request);
        if(sessionUser == null || !sessionUser.getRole().equals(Role.MANAGER)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        List<Reimbursement> reimbursementList = reimbursementService.getAllReimbursements();
        if (reimbursementList != null && !reimbursementList.isEmpty()) return ResponseEntity.status(HttpStatus.OK).body(reimbursementList);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Used to resovle an existing reimbursement request
     * @param reimbursementDTO The reimbursement DTO to use in creating a local reimbursement
     * @param request the HttpServletRequest needed to validate sessions
     * @return returns a response entity with a status and updated object
     */
    @PatchMapping
    public ResponseEntity<Reimbursement> patchReimbursement(@RequestBody ReimbursementDTO reimbursementDTO, HttpServletRequest request) {
        User sessionUser = SessionUtil.getUserFromSession(request);
        if(sessionUser == null || !sessionUser.getRole().equals(Role.MANAGER)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Reimbursement reimbursement = objectMapper.convertValue(reimbursementDTO, Reimbursement.class);
        Reimbursement dbReimbursement = reimbursementService.getReimbursementFromId(reimbursement.getId());
        dbReimbursement.setStatus(reimbursement.getStatus());
        dbReimbursement = reimbursementService.updateReimbursement(dbReimbursement, sessionUser);
        return ResponseEntity.status(HttpStatus.OK).body(dbReimbursement);
    }

}
