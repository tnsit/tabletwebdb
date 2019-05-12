package com.example.tabletwebdb.entity;

import com.example.tabletwebdb.Enum.TransType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tablet_id")
    private Tablet tablet;

    @NonNull
    @ElementCollection(targetClass = TransType.class, fetch = FetchType.EAGER)
    @CollectionTable(name="transType", joinColumns = @JoinColumn(name = "transaction_id"))
    @Enumerated(EnumType.STRING)
    private Set<TransType> transType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_contractor_id")
    private Contractor fromContractor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_interviewer_id")
    private Interviewer fromInterviewer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_user_id")
    private User toUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_contractor_id")
    private Contractor toContractor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_interviewer_id")
    private Interviewer toInterviewer;

    @NonNull
    private String date;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resp_user_id")
    private User respUser;

    public Transaction(@NonNull Tablet tablet,
                       @NonNull Set<TransType> transType,
                       User fromUser,
                       Contractor fromContractor,
                       Interviewer fromInterviewer,
                       User toUser,
                       Contractor toContractor,
                       Interviewer toInterviewer,
                       @NonNull String date,
                       @NonNull User respUser) {
        this.tablet = tablet;
        this.transType = transType;
        this.fromUser = fromUser;
        this.fromContractor = fromContractor;
        this.fromInterviewer = fromInterviewer;
        this.toUser = toUser;
        this.toContractor = toContractor;
        this.toInterviewer = toInterviewer;
        this.date = date;
        this.respUser = respUser;
    }
}
