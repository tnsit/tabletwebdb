package com.example.tabletwebdb.repos;

import com.example.tabletwebdb.entity.Akb;
import com.example.tabletwebdb.entity.Tablet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AkbRepo extends JpaRepository<Akb, Long>{
    Akb findByNumber(String number);
    List<Akb> findAllByOrderByNumberAsc();
}
