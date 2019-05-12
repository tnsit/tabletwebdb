package com.example.tabletwebdb.repos;

import com.example.tabletwebdb.entity.City;
import com.example.tabletwebdb.entity.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractorRepo extends JpaRepository<Contractor, Long>{
    Contractor findByCode(String contractor);
    List<Contractor> findAllByOrderByCodeAsc();
}
