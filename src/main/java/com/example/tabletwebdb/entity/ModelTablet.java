package com.example.tabletwebdb.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class ModelTablet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String modelTablet;

    public ModelTablet(String modelTablet){
        this.modelTablet = modelTablet;
    }
}