package com.example.tabletwebdb.controller;

import com.example.tabletwebdb.entity.User;
import com.example.tabletwebdb.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String errorMessage;

    @GetMapping("/users")
    public String users(@AuthenticationPrincipal User user, Model model){
        errorMessage="";
        List<User> userList = userRepo.findAllByOrderByNameAsc();
        model.addAttribute("users", userList);
        model.addAttribute("message", errorMessage);
        model.addAttribute("user",user);
        model.addAttribute("role", GetRole.getRoleRu(user));

        return "admin/users";
    }

    @PostMapping("/users")
    public String registration(@RequestParam("regname") String regname,
                              @RequestParam("reglogin") String reglogin,
                              @RequestParam("regpass") String regpass,
                              @RequestParam(name="regactive", defaultValue="false") Boolean regactive,
                              @RequestParam("regrole") String regroles,
                               @AuthenticationPrincipal User user,
                               Model model){


        errorMessage ="";
        if (userRepo.findByLogin(reglogin)!=null){
            errorMessage = "Пользователь с логином '"+reglogin+"' уже существует";
        }
        else if(userRepo.findByName(regname)!=null){
            errorMessage = "Пользователь с именем '"+regname+"' уже существует";
        }
        else {
            User saveuser = new User(regname,reglogin,passwordEncoder.encode(regpass),regactive,GetRole.getRoleEn(regroles));
            userRepo.save(saveuser);
            errorMessage = "Пользователь '"+regname+"' создан";
        }
        List<User> userList = userRepo.findAllByOrderByNameAsc();

        model.addAttribute("role", GetRole.getRoleRu(user));
        model.addAttribute("user", user);
        model.addAttribute("message", errorMessage);
        model.addAttribute("users", userList);
        return "/admin/users";
    }

    @GetMapping("/userChange")
    public String userChange(){
        return "redirect:/users";
    }

    @PostMapping("/userChange")
    public String userChange(@RequestParam("changename") String changename,
                             @RequestParam("changelogin") String changelogin,
                             @RequestParam("changepass") String changepass,
                             @RequestParam("user-id") String userid,
                             @RequestParam("changerole") String changerole,
                             @RequestParam(name="changeactive", defaultValue="false") Boolean regactive,
                             @AuthenticationPrincipal User user,
                             Model model
                             ){
        errorMessage ="";
        Optional<User> userFromDb = userRepo.findById(Long.parseLong(userid));
        User getUser = null;
        if (userFromDb.isPresent()){
            getUser = userFromDb.get();
        }

        if (!changelogin.equals(getUser.getLogin()) && userRepo.findByLogin(changelogin)!=null){
            errorMessage = "Пользователь с логином '"+changelogin+"' уже существует";
        }
        else if(!changename.equals(getUser.getName()) && userRepo.findByName(changename)!=null){
            errorMessage = "Пользователь с именем '"+changename+"' уже существует";
        }
        else {
            if (!changename.equals(getUser.getName())){
                getUser.setName(changename);
            }

            if (!changelogin.equals(getUser.getLogin())){
                getUser.setLogin(changelogin);
            }

            if (changepass!=null && !(changepass.isEmpty())){
                getUser.setPassword(passwordEncoder.encode(changepass));
            }

            if (!(changerole.toLowerCase()).equals(GetRole.getRoleRu(getUser).toLowerCase())){
                getUser.getRoles().clear();
                getUser.getRoles().add(GetRole.getRoleEn(changerole).stream().findFirst().get());
            }
            if (regactive!=getUser.isActive()){
                getUser.setActive(regactive);
            }
            userRepo.save(getUser);
            errorMessage = "Данные пользователя '"+changename+"' изменены";
        }
        List<User> userList = userRepo.findAllByOrderByNameAsc();

        model.addAttribute("role", GetRole.getRoleRu(user));
        model.addAttribute("user", user);
        model.addAttribute("message", errorMessage);
        model.addAttribute("users", userList);
        return "/admin/users";
    }
}
