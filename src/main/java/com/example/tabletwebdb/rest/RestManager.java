package com.example.tabletwebdb.rest;

import com.example.tabletwebdb.Enum.Place;
import com.example.tabletwebdb.Enum.TransType;
import com.example.tabletwebdb.entity.*;
import com.example.tabletwebdb.repos.*;
import com.example.tabletwebdb.responce.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class RestManager {

    @Autowired
    private TabletRepo tabletRepo;

    @Autowired
    private InterviewerRepo interviewerRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private PurposeRepo purposeRepo;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/saveInter")
    public Response saveInter(@RequestBody Map<String, String> saveInter,
                              @AuthenticationPrincipal User user){
        String message="";
        Map<String, String> response = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String tabletMessage ="";

        if (!saveInter.get("tablets").equals("")){
            Set<TransType> transTablet = Collections.singleton(TransType.INTERVIEWER);
            Set<Place> placeTablet = Collections.singleton(Place.INTERVIEWER);
            String trim = saveInter.get("tablets").trim();
            String[] tablets = trim.split(" ");
            List<String> tabletsList = new ArrayList<String>(Arrays.asList(tablets));
            if (tabletsList.size()>1){
                tabletsList.remove(0);
            }

            for(String s: tabletsList){
                Transaction transaction = new Transaction(tabletRepo.findByNumber(s),
                        transTablet,
                        user,
                        null,
                        null,
                        null,
                        null,
                        interviewerRepo.findByFullName(saveInter.get("inter")),
                        sdf.format(date),
                        user);

                transactionRepo.save(transaction);

                Tablet tablet = tabletRepo.findByNumber(s);
                tablet.getPlaceTablet().clear();
                tablet.getPlaceTablet().add(placeTablet.stream().findFirst().get());
                tablet.setInterviewer(interviewerRepo.findByFullName(saveInter.get("inter")));
                tablet.setPurpose(purposeRepo.findByPurpose("выдан"));
                tablet.setTransaction(transactionRepo.findFirstByTabletOrderByIdDesc(tabletRepo.findByNumber(s)));

                tabletRepo.save(tablet);

                Interviewer interviewer = interviewerRepo.findByFullName(saveInter.get("inter"));
                if (interviewer.isBrig()){
                    if (interviewer.getTablets()!=null){
                        interviewer.setTablets(interviewer.getTablets()+" "+tablet.getNumber());
                    }

                    else {
                        interviewer.setTablets(tablet.getNumber());
                    }
                    interviewer.setManager(user.getName());
                }

                else {
                    interviewer.setTablets(tablet.getNumber());
                    interviewer.setManager(user.getName());
                }
                interviewerRepo.save(interviewer);


            }
            tabletMessage = tabletsList.size()+"</br>";

        }
        message = "Выданы планшеты шт.: "+tabletMessage+"Интевьюер: "+saveInter.get("inter");

        response.put("message", message);
        return new Response("Done",response);

    }

    @PostMapping("/managerEditTablet")
    public Response managerEditTablet(@RequestBody Map<String, String> editTablet,
                              @AuthenticationPrincipal User user){

        Map<String, String> response = new HashMap<>();

        Tablet tablet = tabletRepo.findByNumber(editTablet.get("number"));
        tablet.setPurpose(purposeRepo.findByPurpose(editTablet.get("status")));
        tablet.setManagerComment(editTablet.get("comment"));
        tabletRepo.save(tablet);
        String message="Данные планшета "+editTablet.get("number")+" изменены";
        response.put("message", message);
        return new Response("Done",response);

    }



    @PostMapping("/takeOffInter")
    public Response takeOffInter(@RequestBody Map<String, String> saveInter,
                              @AuthenticationPrincipal User user){
        String message="";
        Map<String, String> response = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String tabletMessage ="";

        if (!saveInter.get("tablets").equals("")){
            Set<TransType> transTablet = Collections.singleton(TransType.INTERVIEWER_RETURN);
            Set<Place> placeTablet = Collections.singleton(Place.MANAGER);
            String trim = saveInter.get("tablets").trim();
            String[] tablets = trim.split(" ");
            List<String> tabletsList = new ArrayList<String>(Arrays.asList(tablets));
            if (tabletsList.size()>1){
                tabletsList.remove(0);
            }

            for(String s: tabletsList){
                Interviewer interviewer = transactionRepo.findFirstByTabletOrderByIdDesc(tabletRepo.findByNumber(s)).getToInterviewer();
                Transaction transaction = new Transaction(tabletRepo.findByNumber(s),
                        transTablet,
                        null,
                        null,
                        interviewer,
                        user,
                        null,
                        null,
                        sdf.format(date),
                        user);

                transactionRepo.save(transaction);

                Tablet tablet = tabletRepo.findByNumber(s);
                tablet.getPlaceTablet().clear();
                tablet.getPlaceTablet().add(placeTablet.stream().findFirst().get());
                tablet.setInterviewer(null);
                tablet.setPurpose(purposeRepo.findByPurpose("свободен"));
                tablet.setTransaction(transactionRepo.findFirstByTabletOrderByIdDesc(tabletRepo.findByNumber(s)));

                tabletRepo.save(tablet);

                if (interviewer.isBrig()){
                    interviewer.setTablets(interviewer.getTablets().replace(s+" ",""));
                    interviewer.setManager(null);
                }

                else {
                    interviewer.setTablets(null);
                    interviewer.setManager(null);
                }
                interviewerRepo.save(interviewer);


            }
            tabletMessage = tabletsList.size()+"</br>";

        }
        message = "Планшеты возвращены шт.: "+tabletMessage;

        response.put("message", message);
        return new Response("Done",response);

    }



    @PostMapping("/saveBetween")
    public Response saveBetween(@RequestBody Map<String, String> saveInter,
                              @AuthenticationPrincipal User user){
        Map<String, String> response = new HashMap<>();

        if (!saveInter.get("tablets").equals("")){
            String trim = saveInter.get("tablets").trim();
            String[] tablets = trim.split(" ");
            List<String> tabletsList = new ArrayList<String>(Arrays.asList(tablets));
            if (tabletsList.size()>1){
                tabletsList.remove(0);
            }

            for(String s: tabletsList){
                Tablet tablet = tabletRepo.findByNumber(s);
                tablet.setToOtherUser(userRepo.findByName(saveInter.get("manager")));

                tabletRepo.save(tablet);
            }

        }
        String message = "Планшеты переданы пользователю: "+saveInter.get("manager")+"</br> и ожидают подтверждения";

        response.put("message", message);
        return new Response("Done",response);

    }


    @PostMapping("/saveCancelBetween")
    public Response saveCancelBetween(@RequestBody Map<String, String> saveInter,
                                @AuthenticationPrincipal User user){
        Map<String, String> response = new HashMap<>();
        List<String> tabletsList = null;
        boolean correct = true;
        if (!saveInter.get("tablets").equals("")){
            String trim = saveInter.get("tablets").trim();
            String[] tablets = trim.split(" ");
            tabletsList = new ArrayList<String>(Arrays.asList(tablets));
            if (tabletsList.size()>1){
                tabletsList.remove(0);
            }

        }

        if (tabletsList!=null){
            for(String s: tabletsList){

                if (tabletRepo.findByNumber(s).getUser()==null||!tabletRepo.findByNumber(s).getUser().equals(user)){
                    correct = false;
                }
            }
        }

        if (!correct){
            String message = "Один или несколько планшетов уже приняли, обновите страницу";

            response.put("message", message);
            return new Response("Done",response);
        }

        else {
            if (tabletsList!=null) {
                for (String s : tabletsList) {
                    Tablet tablet = tabletRepo.findByNumber(s);
                    tablet.setToOtherUser(null);

                    tabletRepo.save(tablet);
                }
                String message = "Передача планшетов отменена";

                response.put("message", message);
                return new Response("Done",response);
            }
            else {
                return null;
            }
        }
    }



    @PostMapping("/saveAcceptBetween")
    public Response saveAcceptBetween(@RequestBody Map<String, String> saveAccept,
                                      @AuthenticationPrincipal User user){
        Map<String, String> response = new HashMap<>();
        List<String> tabletsList = null;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        boolean correct = true;
        if (!saveAccept.get("tabletsAccept").equals("")){
            String trim = saveAccept.get("tabletsAccept").trim();
            String[] tablets = trim.split(" ");
            tabletsList = new ArrayList<String>(Arrays.asList(tablets));
            if (tabletsList.size()>1){
                tabletsList.remove(0);
            }

        }

        if (tabletsList!=null){
            for(String s: tabletsList){
                if (tabletRepo.findByNumber(s).getToOtherUser()==null){
                    correct = false;
                }
                else
                if (!tabletRepo.findByNumber(s).getToOtherUser().equals(user)){
                    correct = false;
                }
            }
        }

        if (!correct){
            String message = "Кто-то из пользователей отменил передачу планшетов. Обновите страницу";

            response.put("message", message);
            return new Response("Done",response);
        }

        else {
            if (tabletsList!=null) {
                Set<TransType> transTablet = Collections.singleton(TransType.BETWEEN);
                for (String s : tabletsList) {
                    User fromUser = transactionRepo.findFirstByTabletOrderByIdDesc(tabletRepo.findByNumber(s)).getToUser();
                    Transaction transaction = new Transaction(tabletRepo.findByNumber(s),
                            transTablet,
                            fromUser,
                            null,
                            null,
                            user,
                            null,
                            null,
                            sdf.format(date),
                            user);

                    transactionRepo.save(transaction);

                    Tablet tablet = tabletRepo.findByNumber(s);
                    tablet.setUser(user);
                    tablet.setToOtherUser(null);
                    tablet.setPurpose(purposeRepo.findByPurpose("свободен"));
                    tablet.setManagerComment(null);
                    tablet.setTransaction(transactionRepo.findFirstByTabletOrderByIdDesc(tabletRepo.findByNumber(s)));

                    tabletRepo.save(tablet);

                }
                String message = "Планшеты добавлены в ваш список";

                response.put("message", message);
                return new Response("Done",response);
            }
            else {
                return null;
            }
        }
    }



    @PostMapping("/saveToWarehouse")
    public Response saveToWarehouse(@RequestBody Map<String, String> tabletReturn,
                               @AuthenticationPrincipal User user){
        Map<String, String> response = new HashMap<>();

        if (!tabletReturn.get("tablets").equals("")){
            String trim = tabletReturn.get("tablets").trim();
            String[] tablets = trim.split(" ");
            List<String> tabletsList = new ArrayList<String>(Arrays.asList(tablets));
            if (tabletsList.size()>1){
                tabletsList.remove(0);
            }

            for(String s: tabletsList){
                Tablet tablet = tabletRepo.findByNumber(s);
                tablet.setToOtherUser(userRepo.findByName("склад"));

                tabletRepo.save(tablet);
            }

        }
        String message = "Планшеты возвращены на склад и ожидают подтверждения";

        response.put("message", message);
        return new Response("Done",response);

    }


}
