package com.example.tabletwebdb.repos;

import com.example.tabletwebdb.entity.Interviewer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewerRepo extends JpaRepository<Interviewer, Long>{
    Interviewer findByFullName(String fullName);
    Interviewer findByShortName(String shortName);
    List<Interviewer> findAllByOrderByFullNameAsc();
}
