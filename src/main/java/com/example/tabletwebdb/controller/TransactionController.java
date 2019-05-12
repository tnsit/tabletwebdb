package com.example.tabletwebdb.controller;

import com.example.tabletwebdb.entity.User;
import com.example.tabletwebdb.repos.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class TransactionController {
    @Autowired
    private TransactionRepo transactionRepo;


    @GetMapping("/transaction")
    public String addDevice(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user",user);
        model.addAttribute("role", GetRole.getRoleRu(user));
        model.addAttribute("place", GetRole.getRoleRu(user));
        model.addAttribute("transactions", transactionRepo.findTop500ByOrderByIdDesc());
        return "admin/transaction";
    }
}
