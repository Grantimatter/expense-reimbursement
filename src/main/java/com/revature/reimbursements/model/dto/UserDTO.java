package com.revature.reimbursements.model.dto;

import com.revature.reimbursements.model.Reimbursement;
import com.revature.reimbursements.model.enumerator.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserDTO {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private List<Reimbursement> authoredReimbursementList;
    private List<Reimbursement> resolvedReimbursementList;
    private Role role;
}
