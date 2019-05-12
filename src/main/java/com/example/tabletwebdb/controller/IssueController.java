package com.example.tabletwebdb.controller;

import com.example.tabletwebdb.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class IssueController {
    @GetMapping("/issue")
    public String addDevice(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user",user);
        model.addAttribute("role", GetRole.getRoleRu(user));
        return "admin/issue";
    }
}
