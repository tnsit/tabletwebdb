package com.example.tabletwebdb.repos;

import com.example.tabletwebdb.entity.ModelAkb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelAkbRepo extends JpaRepository<ModelAkb, Long>{
    ModelAkb findByModelAkb(String modelAkb);
}
