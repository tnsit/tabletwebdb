package com.example.tabletwebdb.controller;

import com.example.tabletwebdb.Enum.Role;
import com.example.tabletwebdb.entity.Tablet;
import com.example.tabletwebdb.entity.User;
import com.example.tabletwebdb.repos.InterviewerRepo;
import com.example.tabletwebdb.repos.PurposeRepo;
import com.example.tabletwebdb.repos.TabletRepo;
import com.example.tabletwebdb.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@PreAuthorize("hasAuthority('MANAGER')")
public class ManagerController {

    @Autowired
    TabletRepo tabletRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    PurposeRepo purposeRepo;

    @Autowired
    InterviewerRepo interviewerRepo;

    @GetMapping("/myTablets")
    public String tablets(@AuthenticationPrincipal User user, Model model){

        List<Tablet> allTablets = tabletRepo.findByUserOrderByNumberAsc(user);
        Set<Role> role = new HashSet<>();
        role.add(Role.MANAGER);
        List<User> userList = userRepo.findAllByRolesAndActiveOrderByNameAsc(role, true);

        List<Tablet> mainTablets = new ArrayList<>();
        List<Tablet> toOtherUser = new ArrayList<>();
        List<Tablet> toWarehouse = new ArrayList<>();

        List<Tablet> fromOtherUser = tabletRepo.findByToOtherUser(user);

        for (Tablet t: allTablets){
            if (t.getToOtherUser()==null){
                mainTablets.add(t);
            }
            else{
                if (t.getToOtherUser().getName().equals("склад")){
                    toWarehouse.add(t);
                }
                else {
                    toOtherUser.add(t);
                }
            }
        }

        int toSize = 0;
        int fromSize = 0;

        if (toOtherUser.size()!=0){
            toSize = toOtherUser.size();
        }

        if (fromOtherUser.size()!=0){
            fromSize = fromOtherUser.size();
        }

        String fromAndToSize = String.valueOf(toSize+fromSize);

        model.addAttribute("size", fromAndToSize);
        model.addAttribute("managers", userList);
        model.addAttribute("tablets", mainTablets);
        model.addAttribute("toOtherUser", toOtherUser);
        model.addAttribute("fromOtherUser", fromOtherUser);
        model.addAttribute("toWarehouse", toWarehouse);
        model.addAttribute("purpose", purposeRepo.findAll());
        model.addAttribute("interviewers", interviewerRepo.findAllByOrderByFullNameAsc());
        model.addAttribute("user",user);
        model.addAttribute("role", GetRole.getRoleRu(user));
        model.addAttribute("place", GetRole.getRoleRu(user));
        return "manager/tablets";
    }
}
