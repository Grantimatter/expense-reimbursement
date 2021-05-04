package com.revature.reimbursements.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.revature.reimbursements.model.enumerator.Status;
import com.revature.reimbursements.model.enumerator.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Reimbursement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private BigDecimal amount;
    private String description;

    @CreationTimestamp @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date submitted;
    @Temporal(TemporalType.TIMESTAMP)
    private Date resolved;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @ManyToOne(fetch=FetchType.EAGER)
    @JsonBackReference(value = "author")
    private User author;

    @ManyToOne(fetch=FetchType.LAZY)
    @JsonBackReference(value = "resolver")
    private User resolver;

    public boolean resolve() {
        if(resolved != null) return false;
        resolved = Timestamp.from(Instant.EPOCH);
        return true;
    }
}
