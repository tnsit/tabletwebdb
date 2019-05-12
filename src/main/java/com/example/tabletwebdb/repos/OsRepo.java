package com.example.tabletwebdb.repos;

import com.example.tabletwebdb.entity.Os;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OsRepo extends JpaRepository<Os, Long>{
    Os findByOs(String os);
}
