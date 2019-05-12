package com.example.tabletwebdb.controller;

import com.example.tabletwebdb.entity.Tablet;
import com.example.tabletwebdb.entity.User;
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
@PreAuthorize("hasAuthority('SUPER')")
public class SuperController {
    @Autowired
    private ModelTabletRepo modelTabletRepo;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private OsRepo osRepo;

    @Autowired
    private TabletRepo tabletRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @GetMapping("/tabletsSV")
    public String addDevice(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("modelTablet", modelTabletRepo.findAll());
        model.addAttribute("status", statusRepo.findAll());
        model.addAttribute("os", osRepo.findAll());
        model.addAttribute("user",user);
        model.addAttribute("role", GetRole.getRoleRu(user));
        model.addAttribute("place", GetRole.getRoleRu(user));
        return "super/tablets";
    }

    @PostMapping("/tabletsSV")
    public String searchTablets(@AuthenticationPrincipal User user,
                                @RequestParam("tablet-search") String search,
                                Model model){

        List<Tablet> allTablet = tabletRepo.findAll();
        List<Tablet> searchList = new ArrayList<>();

        for (Tablet t: allTablet){
            if ((t.getInterviewer()!=null&&t.getInterviewer().getFullName().toLowerCase().contains(search))||
                    (t.getContractor()!=null&&t.getContractor().getCode().toLowerCase().contains(search))||
                    (t.getUser()!=null&&t.getUser().getName().toLowerCase().contains(search))||
                    t.getNumber().toLowerCase().contains(search)||
                    t.getModelTablet().getModelTablet().toLowerCase().contains(search)||
                    t.getStatus().getStatus().toLowerCase().contains(search)||
                    (t.getStatusComment()!=null&&t.getStatusComment().toLowerCase().contains(search))||
                    t.getImei().toLowerCase().contains(search)||
                    t.getPhone().toLowerCase().contains(search)||
                    t.getPin().toLowerCase().contains(search)||
                    t.getOs().getOs().toLowerCase().contains(search)||
                    (t.getTabletComment()!=null&&t.getTabletComment().toLowerCase().contains(search))||
                    t.getCity().getCity().toLowerCase().contains(search)||
                    (t.getPurpose()!=null&&t.getPurpose().getPurpose().toLowerCase().contains(search))||
                    t.getPlaceTablet().iterator().next().getName().toLowerCase().contains(search)){
                searchList.add(t);
            }
        }
        if (searchList.isEmpty()){
            model.addAttribute("message", "Поиск не дал результатов");
        }

        model.addAttribute("modelTablet", modelTabletRepo.findAll());
        model.addAttribute("status", statusRepo.findAll());
        model.addAttribute("os", osRepo.findAll());
        model.addAttribute("tablets", searchList);
        model.addAttribute("user",user);
        model.addAttribute("role", GetRole.getRoleRu(user));
        model.addAttribute("place", GetRole.getRoleRu(user));
        return "super/tablets";
    }


    @GetMapping("/transactionSV")
    public String transaction(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user",user);
        model.addAttribute("role", GetRole.getRoleRu(user));
        model.addAttribute("place", GetRole.getRoleRu(user));
        model.addAttribute("transactions", transactionRepo.findTop500ByOrderByIdDesc());
        return "super/transaction";
    }


}
