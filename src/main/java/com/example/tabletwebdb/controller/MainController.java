package com.example.tabletwebdb.controller;

import com.example.tabletwebdb.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String greeting(@AuthenticationPrincipal User user) {

        if (user.getRoles().stream().findFirst().isPresent()){
            if (user.getRoles().stream().findFirst().get().toString().equals("ADMIN")){
                return "redirect:/issue";
            }
            if (user.getRoles().stream().findFirst().get().toString().equals("SUPER")){
                return "redirect:/tabletsSV";
            }
            if (user.getRoles().stream().findFirst().get().toString().equals("MANAGER")){
                return "redirect:/myTablets";
            }
        }


        return null;

    }

}
