package com.example.tabletwebdb.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Interviewer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String secondName;
    private String thirdName;

    private String shortName;
    private String fullName;

    private boolean brig;
    private String tablets;
    private String manager;

    public Interviewer(String firstName, String secondName, String thirdName, String fullName, String shortName, boolean brig){
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.fullName = fullName;
        this.shortName = shortName;
        this.brig = brig;
    }


}
