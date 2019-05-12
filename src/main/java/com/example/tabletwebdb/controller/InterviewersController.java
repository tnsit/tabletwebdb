package com.example.tabletwebdb.controller;

import com.example.tabletwebdb.entity.Interviewer;
import com.example.tabletwebdb.entity.User;
import com.example.tabletwebdb.repos.InterviewerRepo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.DefaultProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")

public class InterviewersController {
    @Autowired
    private InterviewerRepo interviewerRepo;

    private String errorMessage;

    @GetMapping("/interviewers")
    public String interviewers(@AuthenticationPrincipal User user, Model model){
        List<Interviewer> interviewersList = interviewerRepo.findAllByOrderByFullNameAsc();
        model.addAttribute("user", user);
        model.addAttribute("role", GetRole.getRoleRu(user));
        model.addAttribute("interviewers", interviewersList);
        return returnLink(user);
    }

    @GetMapping("/createInterviewers")
    public String createInterviewers(){
        return "redirect:/interviewers";
    }

    @PostMapping("/createInterviewers")
    public String createInterviewers(@AuthenticationPrincipal User user,
                                     @RequestParam("firstName") List<String> firstName,
                                     @RequestParam("secondName") List<String> secondName,
                                     @RequestParam("thirdName") List<String> thirdName,
                                     @RequestParam("hidden-checkbox") List<Boolean> brig,
                                     Model model){
        errorMessage="";
        List<String> created = new ArrayList<>();
        List<String> exists = new ArrayList<>();
        for (int i=0; i<firstName.size(); i++){
            String fullName = firstName.get(i)+" "+secondName.get(i)+" "+thirdName.get(i);
            if (interviewerRepo.findByFullName(fullName)==null){
                String shortName = firstName.get(i)+" "+secondName.get(i).substring(0, 1)+"."+thirdName.get(i).substring(0, 1)+".";
                Interviewer interviewer = new Interviewer(firstName.get(i), secondName.get(i), thirdName.get(i), fullName, shortName, brig.get(i));
                interviewerRepo.save(interviewer);
                created.add(fullName);
            }
            else {
                exists.add(fullName);
            }
        }


        String createdMessage = String.join("</br>", created);
        String existsMessage = String.join("</br>", exists);

        if (exists.isEmpty()){
            errorMessage = "Интервьюеры добавлены в базу";
        }
        else {
            errorMessage = "Добавлены:</br>"+createdMessage+"</br></br>Уже Существуют:</br>"+ existsMessage;
        }
        List<Interviewer> interviewersList = interviewerRepo.findAllByOrderByFullNameAsc();
        model.addAttribute("message", errorMessage);
        model.addAttribute("user", user);
        model.addAttribute("role", GetRole.getRoleRu(user));
        model.addAttribute("interviewers", interviewersList);
        return returnLink(user);
    }

    @PostMapping("/editInterviewers")
    public String editInterviewers(@AuthenticationPrincipal User user,
                                   @RequestParam("fn-edit") String firstName,
                                   @RequestParam("sn-edit") String secondName,
                                   @RequestParam("tn-edit") String thirdName,
                                   @RequestParam("inter-id") String id,
                                   @RequestParam(name="brig", defaultValue="false") Boolean brig,
                                   Model model){

        String fullName = firstName+" "+secondName+" "+thirdName;
        Optional<Interviewer> optional = interviewerRepo.findById(Long.parseLong(id));
        Interviewer interviewer = new Interviewer();
        if (optional.isPresent()){
            interviewer = optional.get();
        }
        if (!fullName.equals(interviewer.getFullName())&&interviewerRepo.findByFullName(fullName)!=null){
            errorMessage = "Интервьюер</br>'"+fullName+"'</br>уже существует";
        }
        else{
            String shortName = firstName+" "+secondName.substring(0, 1)+"."+thirdName.substring(0, 1)+".";
            interviewer.setFirstName(firstName);
            interviewer.setSecondName(secondName);
            interviewer.setThirdName(thirdName);
            interviewer.setFullName(fullName);
            interviewer.setShortName(shortName);
            interviewer.setBrig(brig);
            interviewerRepo.save(interviewer);
            errorMessage = "Данные интервьюера</br>'"+fullName+"'</br>изменены";
        }

        List<Interviewer> interviewersList = interviewerRepo.findAllByOrderByFullNameAsc();
        model.addAttribute("message", errorMessage);
        model.addAttribute("user", user);
        model.addAttribute("role", GetRole.getRoleRu(user));
        model.addAttribute("interviewers", interviewersList);
        return returnLink(user);
    }

    @GetMapping("/editInterviewers")
    public String editInterviewers(){
        return "redirect:/interviewers";
    }

    private String returnLink(User user){
        if (user.getRoles().stream().findFirst().isPresent()){
            if (user.getRoles().stream().findFirst().get().toString().equals("ADMIN")){
                return "admin/interviewers";
            }
            if (user.getRoles().stream().findFirst().get().toString().equals("MANAGER")){
                return "manager/interviewers";
            }
        }
        return null;
    }


}


