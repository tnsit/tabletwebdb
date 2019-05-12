package com.example.tabletwebdb.controller;

import com.example.tabletwebdb.entity.Akb;
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

import java.util.Optional;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AkbController {
    @Autowired
    private ModelAkbRepo modelAkbRepo;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private AkbRepo akbRepo;

    @GetMapping("/akb")
    public String addDevice(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("modelAkb", modelAkbRepo.findAll());
        model.addAttribute("status", statusRepo.findAll());
        model.addAttribute("akbs", akbRepo.findAllByOrderByNumberAsc());
        model.addAttribute("user",user);
        model.addAttribute("role", GetRole.getRoleRu(user));
        return "admin/akb";
    }

    @PostMapping("/editAkb")
    public String editDevice(@AuthenticationPrincipal User user,
                             @RequestParam("akb-number") String akbNumber,
                             @RequestParam("akb-model") String akbModel,
                             @RequestParam("akb-status") String akbStatus,
                             @RequestParam("akb-id") String akbtId,
                             Model model){
        String errorMessage="";
        Optional<Akb> optional = akbRepo.findById(Long.parseLong(akbtId));
        Akb akb = new Akb();
        if (optional.isPresent()){
            akb = optional.get();
        }

        if (!akbNumber.equals(akb.getNumber())&&akbRepo.findByNumber(akbNumber)!=null){
            errorMessage = "Акб с номером '"+akbNumber+"' уже существует";
        } else {
            akb.setNumber(akbNumber);
            akb.setModelAkb(modelAkbRepo.findByModelAkb(akbModel));
            akb.setStatus(statusRepo.findByStatus(akbStatus));
            akbRepo.save(akb);
            errorMessage = "Данные планшета '"+akbNumber+"' изменены";
        }

        model.addAttribute("modelAkb", modelAkbRepo.findAll());
        model.addAttribute("status", statusRepo.findAll());
        model.addAttribute("akbs", akbRepo.findAllByOrderByNumberAsc());
        model.addAttribute("message", errorMessage);
        model.addAttribute("user",user);
        model.addAttribute("role", GetRole.getRoleRu(user));
        return "admin/akb";
    }
}
