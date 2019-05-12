package com.example.tabletwebdb.repos;

import com.example.tabletwebdb.entity.Purpose;
import com.example.tabletwebdb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurposeRepo extends JpaRepository<Purpose, Long>{
    Purpose findByPurpose(String purpose);
}
