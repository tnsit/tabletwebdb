package com.example.tabletwebdb.controller;

import com.example.tabletwebdb.entity.User;
import com.example.tabletwebdb.repos.CityRepo;
import com.example.tabletwebdb.repos.ContractorRepo;
import com.example.tabletwebdb.repos.StatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class ShipmentController {

    @Autowired
    private ContractorRepo contractorRepo;
    @Autowired
    private CityRepo cityRepo;

    @Autowired
    private StatusRepo statusRepo;

    @GetMapping("/shipment")
    public String addDevice(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user",user);
        model.addAttribute("status",statusRepo.findAll());
        model.addAttribute("role", GetRole.getRoleRu(user));
        model.addAttribute("contractors", contractorRepo.findAllByOrderByCodeAsc());
        model.addAttribute("city", cityRepo.findAllByOrderByCityAsc());
        return "admin/shipment";
    }
}
