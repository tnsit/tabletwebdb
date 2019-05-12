package com.example.tabletwebdb.entity;

import com.example.tabletwebdb.Enum.Place;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Tablet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String number;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id")
    private ModelTablet modelTablet;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "os_id")
    private Os os;

    @NonNull
    private String imei;

    @NonNull
    private String phone;

    @NonNull
    private String pin;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private Status status;

    @Column(length = 1000)
    private String statusComment;

    @NonNull
    @ElementCollection(targetClass = Place.class, fetch = FetchType.EAGER)
    @CollectionTable(name="placeTablet", joinColumns = @JoinColumn(name = "tablet_id"))
    @Enumerated(EnumType.STRING)
    private Set<Place> placeTablet;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @NonNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "toOtherUser_id")
    private User toOtherUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "inter_id")
    private Interviewer interviewer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contractor_id")
    private Contractor contractor;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "akb_id")
    private Akb akb;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "purpose_id")
    private Purpose purpose;

    private String inventoryDate;

    private String inventoryReason;

    @Column(length = 1000)
    private String tabletComment;

    @Column(length = 1000)
    private String managerComment;

    @Column(length = 1000)
    private String managerCommentForWarehouse;

    public Tablet(@NonNull String number,
                  @NonNull ModelTablet modelTablet,
                  @NonNull Status status,
                  String statusComment,
                  @NonNull String imei,
                  @NonNull Os os,
                  @NonNull String phone,
                  @NonNull String pin,
                  @NonNull Set<Place> placeTablet,
                  @NonNull City city,
                  String inventoryDate,
                  String inventoryReason,
                  String tabletComment) {
        this.number = number;
        this.modelTablet = modelTablet;
        this.os = os;
        this.imei = imei;
        this.phone = phone;
        this.pin = pin;
        this.status = status;
        this.statusComment = statusComment;
        this.placeTablet = placeTablet;
        this.inventoryDate = inventoryDate;
        this.inventoryReason = inventoryReason;
        this.tabletComment = tabletComment;
        this.city = city;

    }
}
