package com.example.tabletwebdb.controller;

import com.example.tabletwebdb.Enum.Role;
import com.example.tabletwebdb.entity.User;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Set;

@Configuration
public class GetRole {
    public static String getRoleRu(User user){
        String role = "";
        String firstElement="";
        if (user.getRoles().stream().findFirst().isPresent()){
            firstElement = user.getRoles().stream().findFirst().get().toString();
        }
        if (firstElement.equals("ADMIN")){
            role="АДМИНИСТРАТОР";
        }
        if (firstElement.equals("MANAGER")){
            role="МЕНЕДЖЕР";
        }
        if (firstElement.equals("SUPER")){
            role="СУПЕРВАЙЗЕР";
        }
        return role;
    }

    public static Set<Role> getRoleEn(String roleEn){
        Set<Role> role = null;
        if (roleEn.equals("Администратор")){
            role=Collections.singleton(Role.ADMIN);
        }
        if (roleEn.equals("Менеджер")){
            role=Collections.singleton(Role.MANAGER);
        }
        if (roleEn.equals("Супервайзер")){
            role=Collections.singleton(Role.SUPER);
        }
        return role;
    }
}
