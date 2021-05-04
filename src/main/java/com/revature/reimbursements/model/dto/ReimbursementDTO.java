package com.revature.reimbursements.model.dto;

import com.revature.reimbursements.model.User;
import com.revature.reimbursements.model.enumerator.Status;
import com.revature.reimbursements.model.enumerator.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data @NoArgsConstructor @AllArgsConstructor
public class ReimbursementDTO {
    private int id;
    private BigDecimal amount;
    private String description;
    private Timestamp submitted;
    private Timestamp resolved;
    private Status status;
    private Type type;
    private User author;
    private User resolver;
}
