package com.example.tabletwebdb.controller;

import com.example.tabletwebdb.entity.User;
import com.example.tabletwebdb.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AddDeviceController {

    @Autowired
    private ModelTabletRepo modelTabletRepo;

    @Autowired
    private ModelAkbRepo modelAkbRepo;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private OsRepo osRepo;


    @GetMapping("/addDevice")
    public String addDevice(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("modelTablet", modelTabletRepo.findAll());
        model.addAttribute("modelAkb", modelAkbRepo.findAll());
        model.addAttribute("status", statusRepo.findAll());
        model.addAttribute("os", osRepo.findAll());;
        model.addAttribute("user",user);
        model.addAttribute("role", GetRole.getRoleRu(user));
        return "admin/addDevice";
    }


}
