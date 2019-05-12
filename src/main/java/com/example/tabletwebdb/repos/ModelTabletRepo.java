package com.example.tabletwebdb.repos;

import com.example.tabletwebdb.entity.ModelTablet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelTabletRepo extends JpaRepository<ModelTablet, Long>{
    ModelTablet findByModelTablet(String modelTablet);
}
