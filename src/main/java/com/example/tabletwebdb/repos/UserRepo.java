package com.example.tabletwebdb.repos;

import com.example.tabletwebdb.Enum.Role;
import com.example.tabletwebdb.entity.City;
import com.example.tabletwebdb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserRepo extends JpaRepository<User, Long>{
    User findByLogin(String login);
    User findByName(String name);
    List<User> findAllByOrderByNameAsc();
    List<User> findAllByRolesAndActiveOrderByNameAsc(Set<Role> role, boolean active);
}
