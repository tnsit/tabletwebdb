package com.example.tabletwebdb.repos;

import com.example.tabletwebdb.entity.City;
import com.example.tabletwebdb.entity.Tablet;
import com.example.tabletwebdb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TabletRepo extends JpaRepository<Tablet, Long>{
    Tablet findByNumber(String number);
    Tablet findByImei(String imei);
    Tablet findByPhone(String phone);
    List<Tablet> findByUserOrderByNumberAsc(User user);
    List<Tablet> findByToOtherUser(User user);
    List<Tablet> findAllByOrderByNumberAsc();
}
