package com.example.tabletwebdb.repos;

import com.example.tabletwebdb.entity.Tablet;
import com.example.tabletwebdb.entity.Transaction;
import com.example.tabletwebdb.entity.User;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long>{
Transaction findFirstByTabletOrderByIdDesc(Tablet tablet);
List<Transaction> findTop500ByOrderByIdDesc();
List<Transaction> findAllByFromUserOrToUser(User fromUser, User toUser);

}
