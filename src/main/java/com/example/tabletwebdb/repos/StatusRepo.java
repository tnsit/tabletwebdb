package com.example.tabletwebdb.repos;

import com.example.tabletwebdb.entity.Status;
import com.example.tabletwebdb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepo extends JpaRepository<Status, Long>{
    Status findByStatus(String status);
}
