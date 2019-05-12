package com.example.tabletwebdb.repos;

import com.example.tabletwebdb.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepo extends JpaRepository<City, Long>{
    City findByCity(String city);
    List<City> findAllByOrderByCityAsc();
}
