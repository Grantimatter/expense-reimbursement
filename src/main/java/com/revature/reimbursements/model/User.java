package com.revature.reimbursements.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.revature.reimbursements.model.enumerator.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "author")
    @JsonManagedReference(value = "author")
    private List<Reimbursement> authoredReimbursementList;

    @OneToMany(mappedBy = "resolver")
    @JsonManagedReference(value = "resolver")
    private List<Reimbursement> resolvedReimbursementList;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
