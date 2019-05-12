package com.example.tabletwebdb.controller;

import com.example.tabletwebdb.Enum.Place;
import com.example.tabletwebdb.entity.Contractor;
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
import java.util.Optional;
import java.util.Set;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class TabletController {

    @Autowired
    private ModelTabletRepo modelTabletRepo;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private OsRepo osRepo;

    @Autowired
    private TabletRepo tabletRepo;

    @GetMapping("/tablets")
    public String tablets(@AuthenticationPrincipal User user,
                            Model model){
        model.addAttribute("modelTablet", modelTabletRepo.findAll());
        model.addAttribute("status", statusRepo.findAll());
        model.addAttribute("os", osRepo.findAll());
        model.addAttribute("user",user);
        model.addAttribute("role", GetRole.getRoleRu(user));
        model.addAttribute("place", GetRole.getRoleRu(user));
        return "admin/tablets";
    }

    @PostMapping("/tablets")
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
        return "admin/tablets";
    }

    @PostMapping("/editTablet")
    public String editDevice(@AuthenticationPrincipal User user,
                             @RequestParam("tablet-number") String tabletNumber,
                             @RequestParam("tablet-model") String tabletModel,
                             @RequestParam("tablet-status") String tabletStatus,
                             @RequestParam("tablet-status-comment") String tabletStatusComment,
                             @RequestParam("tablet-imei") String tabletImei,
                             @RequestParam("tablet-os") String tabletOs,
                             @RequestParam("tablet-phone") String tabletPhone,
                             @RequestParam("tablet-pin") String tabletPin,
                             @RequestParam("tablet-comment") String tabletComment,
                             @RequestParam("tablet-id") String tabletId,
                             Model model){
        String errorMessage="";
        Optional<Tablet> optional = tabletRepo.findById(Long.parseLong(tabletId));
        Tablet tablet = new Tablet();
        if (optional.isPresent()){
            tablet = optional.get();
        }

        if (!tabletNumber.equals(tablet.getNumber())&&tabletRepo.findByNumber(tabletNumber)!=null){
            errorMessage = "Планшет с номером '"+tabletNumber+"' уже существует";
        } else if(!tabletImei.equals(tablet.getImei())&&tabletRepo.findByImei(tabletImei)!=null){
            errorMessage = "Планшет с IMEI '"+tabletImei+"' уже существует";
        }else if(!tabletPhone.equals(tablet.getPhone())&&tabletRepo.findByPhone(tabletPhone)!=null){
            errorMessage = "Планшет с телефоном '"+tabletPhone+"' уже существует";
        }else {
            tablet.setNumber(tabletNumber);
            tablet.setModelTablet(modelTabletRepo.findByModelTablet(tabletModel));
            tablet.setStatus(statusRepo.findByStatus(tabletStatus));
            tablet.setStatusComment(tabletStatusComment);
            tablet.setImei(tabletImei);
            tablet.setOs(osRepo.findByOs(tabletOs));
            tablet.setPin(tabletPin);
            tablet.setTabletComment(tabletComment);
            tabletRepo.save(tablet);
            List<Tablet> tabletEdited = new ArrayList<>();
            tabletEdited.add(tabletRepo.findByNumber(tablet.getNumber()));
            model.addAttribute("tablets", tabletEdited);
            errorMessage = "Данные планшета '"+tabletNumber+"' изменены";
        }

        model.addAttribute("modelTablet", modelTabletRepo.findAll());
        model.addAttribute("status", statusRepo.findAll());
        model.addAttribute("os", osRepo.findAll());

        model.addAttribute("message", errorMessage);
        model.addAttribute("user",user);
        model.addAttribute("role", GetRole.getRoleRu(user));
        return "admin/tablets";
    }


}
