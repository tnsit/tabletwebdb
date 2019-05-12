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
public class Akb {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String number;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id")
    private ModelAkb modelAkb;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "inter_id")
    private Interviewer interviewer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contr_id")
    private Contractor contractor;

    @NonNull
    @ElementCollection(targetClass = Place.class, fetch = FetchType.EAGER)
    @CollectionTable(name="placeAkb", joinColumns = @JoinColumn(name = "akb_id"))
    @Enumerated(EnumType.STRING)
    private Set<Place> placeAkb;

    private String date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resp_user_id")
    private User respUser;

    public Akb(@NonNull String number,
               @NonNull ModelAkb modelAkb,
               @NonNull Status status,
               @NonNull Set<Place> placeEnum,
               @NonNull City city
               ) {
        this.number = number;
        this.modelAkb = modelAkb;
        this.status = status;
        this.placeAkb = placeEnum;
        this.city = city;
    }
}
