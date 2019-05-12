package com.example.tabletwebdb.controller;

import com.example.tabletwebdb.entity.*;
import com.example.tabletwebdb.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class OtherTableController {

    @Autowired
    private CityRepo cityRepo;
    @Autowired
    private ModelAkbRepo modelAkbRepo;
    @Autowired
    private ModelTabletRepo modelTabletRepo;
    @Autowired
    private OsRepo osRepo;
    @Autowired
    private PurposeRepo purposeRepo;
    @Autowired
    private StatusRepo statusRepo;
    @Autowired
    private ContractorRepo contractorRepo;

    @GetMapping("/otherTable")
    public String otherTable(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("city", cityRepo.findAllByOrderByCityAsc());
        model.addAttribute("modelTablet", modelTabletRepo.findAll());
        model.addAttribute("modelAkb", modelAkbRepo.findAll());
        model.addAttribute("os", osRepo.findAll());
        model.addAttribute("purpose", purposeRepo.findAll());
        model.addAttribute("status", statusRepo.findAll());
        model.addAttribute("contractor", contractorRepo.findAll());
        model.addAttribute("user",user);
        model.addAttribute("role", GetRole.getRoleRu(user));
        return "admin/otherTable";
    }

    @PostMapping("/create")
    public String otherTableCreate(@AuthenticationPrincipal User user,
                                   @RequestParam("hidden-option-select") String select,
                                   @RequestParam("input-create") String input,
                                   Model model){
        String errorMessage = "";
        if (select.equals("cityOption")){
            if (cityRepo.findByCity(input)!=null){
                errorMessage = "Город '"+input+"' уже существует";
            }
            else {
                City city = new City(input);
                cityRepo.save(city);
                errorMessage = "Город '"+input+"' добавлен в базу";
            }
        }

        if (select.equals("modelTabletOption")){
            if (modelTabletRepo.findByModelTablet(input)!=null){
                errorMessage = "Модель планшета '"+input+"' уже существует";
            }
            else {
                ModelTablet modelTablet = new ModelTablet(input);
                modelTabletRepo.save(modelTablet);
                errorMessage = "Модель планшета '"+input+"' добавлена в базу";
            }
        }

        if (select.equals("modelAkbOption")){
            if (modelAkbRepo.findByModelAkb(input)!=null){
                errorMessage = "Модель аккумулятора '"+input+"' уже существует";
            }
            else {
                ModelAkb modelAkb = new ModelAkb(input);
                modelAkbRepo.save(modelAkb);
                errorMessage = "Модель аккумулятора '"+input+"' добавлена в базу";
            }
        }

        if (select.equals("osOption")){
            if (osRepo.findByOs(input)!=null){
                errorMessage = "Версия OS '"+input+"' уже существует";
            }
            else {
                Os os = new Os(input);
                osRepo.save(os);
                errorMessage = "Версия OS '"+input+"' добавлена в базу";
            }
        }

        if (select.equals("purposeOption")){
            if (purposeRepo.findByPurpose(input)!=null){
                errorMessage = "Цель '"+input+"' уже существует";
            }
            else {
                Purpose purpose = new Purpose(input);
                purposeRepo.save(purpose);
                errorMessage = "Цель '"+input+"' добавлена в базу";
            }
        }

        if (select.equals("statusOption")){
            if (statusRepo.findByStatus(input)!=null){
                errorMessage = "Статус '"+input+"' уже существует";
            }
            else {
                Status status = new Status(input);
                statusRepo.save(status);
                errorMessage = "Статус '"+input+"' добавлен в базу";
            }
        }

        if (select.equals("contractorOption")){
            if (contractorRepo.findByCode(input)!=null){
                errorMessage = "Подрядчик '"+input+"' уже существует";
            }
            else {
                Contractor contractor = new Contractor(input);
                contractorRepo.save(contractor);
                errorMessage = "Подрядчик '"+input+"' добавлен в базу";
            }
        }


        model.addAttribute("message", errorMessage);
        model.addAttribute("user",user);
        model.addAttribute("role", GetRole.getRoleRu(user));
        model.addAttribute("city", cityRepo.findAll());
        model.addAttribute("modelTablet", modelTabletRepo.findAll());
        model.addAttribute("modelAkb", modelAkbRepo.findAll());
        model.addAttribute("os", osRepo.findAll());
        model.addAttribute("purpose", purposeRepo.findAll());
        model.addAttribute("status", statusRepo.findAll());
        model.addAttribute("contractor", contractorRepo.findAll());
        return "admin/otherTable";
    }


}
