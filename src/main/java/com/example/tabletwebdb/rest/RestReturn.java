package com.example.tabletwebdb.rest;

import com.example.tabletwebdb.Enum.Place;
import com.example.tabletwebdb.Enum.TransType;
import com.example.tabletwebdb.entity.*;
import com.example.tabletwebdb.repos.*;
import com.example.tabletwebdb.responce.Response;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class RestReturn {

    @Autowired
    private TabletRepo tabletRepo;

    @Autowired
    private AkbRepo akbRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private CityRepo cityRepo;

    @PostMapping("/return")
    public Response getDevices(@RequestBody Map<String, String> devices) {
        Map<String, String> response = new HashMap<>();
        LinkedHashSet<List<String>> tablets = new LinkedHashSet<>();
        LinkedHashSet<List<String>> akb = new LinkedHashSet<>();
        List<Status> statusFromDb = statusRepo.findAll();
        LinkedHashSet<String> status = new LinkedHashSet<>();

        for (Status s: statusFromDb){
            status.add(s.getStatus());
        }



        String[] list = devices.get("numbers").split(",");

        if (!devices.get("numbers").equals("")){
            for (String s : list) {
                if (tabletRepo.findByNumber(s) != null) {
                    Tablet t = tabletRepo.findByNumber(s);
                    ArrayList<String> tabletList = new ArrayList<>();
                    tabletList.add(t.getNumber());
                    tabletList.add(t.getStatus().getStatus());
                    if (t.getUser()!=null){
                        tabletList.add(t.getUser().getName());
                    }

                    if (t.getContractor()!=null){
                        tabletList.add(t.getContractor().getCode());
                    }

                    if (t.getPlaceTablet().stream().findFirst().get().getName().equals("склад")){
                        tabletList.add("склад");
                    }

                    if (t.getStatusComment()!=null){
                        tabletList.add(t.getStatusComment());
                    }
                    else {
                        tabletList.add("");
                    }

                    if (t.getManagerCommentForWarehouse()!=null){
                        tabletList.add(t.getManagerCommentForWarehouse());
                    }
                    else {
                        tabletList.add("");
                    }
                    tablets.add(tabletList);
                }

                if (akbRepo.findByNumber(s) != null) {
                    Akb a = akbRepo.findByNumber(s);
                    ArrayList<String> akbList = new ArrayList<>();
                    akbList.add(a.getNumber());
                    akbList.add(a.getStatus().getStatus());
                    if (a.getUser()!=null){
                        akbList.add(a.getUser().getName());
                    }

                    if (a.getContractor()!=null){
                        akbList.add(a.getContractor().getCode());
                    }

                    if (a.getInterviewer()!=null){
                        akbList.add(a.getInterviewer().getShortName());
                    }

                    if (a.getPlaceAkb().stream().findFirst().get().getName().equals("склад")){
                        akbList.add("склад");
                    }

                    akb.add(akbList);
                }
            }
        }


        ObjectMapper mapper = new ObjectMapper();
        mapper.getFactory().configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        String tabletsJson = "";
        String akbJson = "";
        try {
            if (!tablets.isEmpty()){
                tabletsJson = mapper.writeValueAsString(tablets);
            }
            if (!akb.isEmpty()){
                akbJson = mapper.writeValueAsString(akb);
            }
            if (tabletsJson.equals("")&&akbJson.equals("")){
                return new Response("Null", "");
            }
            else {
                response.put("tablets", tabletsJson);
                response.put("akb", akbJson);
                response.put("status",mapper.writeValueAsString(status));
                return new Response("Done", response);
            }

        } catch (Exception e) {
            return new Response("Error", "");
        }
    }

    @PostMapping("saveStatus")
    public Response saveStatus(@RequestBody Map<String, String> status) {
        Map<String, String> response = new HashMap<>();
        if (tabletRepo.findByNumber(status.get("number"))!=null){
            Tablet tablet = tabletRepo.findByNumber(status.get("number"));
            tablet.setStatus(statusRepo.findByStatus(status.get("status")));
            tablet.setStatusComment(status.get("comment"));
            tabletRepo.save(tablet);
        }

        if (akbRepo.findByNumber(status.get("number"))!=null){
            Akb akb = akbRepo.findByNumber(status.get("number"));
            System.out.println(status.get("status"));
            akb.setStatus(statusRepo.findByStatus(status.get("status")));
            akbRepo.save(akb);
        }

        response.put("number", status.get("number"));
        return new Response("Done", response);
    }




    @PostMapping("/saveReturn")
    public Response saveIssue(@RequestBody Map<String, String> issue,
                              @AuthenticationPrincipal User user){
        String message="";
        Map<String, String> response = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String tabletMessage ="";
        String akbMessage ="";

        if (!issue.get("tablets").equals("")){
            Set<TransType> transTablet = Collections.singleton(TransType.RETURN);
            Set<Place> placeTablet = Collections.singleton(Place.WAREHOUSE);
            String trim = issue.get("tablets").trim();
            String[] tablets = trim.split(" ");
            List<String> tabletsList = new ArrayList<String>(Arrays.asList(tablets));
            tabletsList.remove(0);
            for(String s: tabletsList){
                Tablet tablet = tabletRepo.findByNumber(s);
                if (tablet.getUser()!=null){
                    Transaction transaction = new Transaction(tablet,
                            transTablet,
                            tablet.getUser(),
                            null,
                            null,
                            userRepo.findByName("склад"),
                            null,
                            null,
                            sdf.format(date),
                            user);

                    transactionRepo.save(transaction);
                    tablet.getPlaceTablet().clear();
                    tablet.getPlaceTablet().add(placeTablet.stream().findFirst().get());
                    tablet.setUser(null);
                    tablet.setManagerComment(null);
                    tablet.setManagerCommentForWarehouse(null);
                    tablet.setAkb(null);
                    tablet.setInterviewer(null);
                    tablet.setPurpose(null);
                    tablet.setToOtherUser(null);
                    tablet.setTransaction(transactionRepo.findFirstByTabletOrderByIdDesc(tabletRepo.findByNumber(s)));

                    tabletRepo.save(tablet);
                }

                if (tablet.getContractor()!=null){
                    Transaction transaction = new Transaction(tablet,
                            transTablet,
                            null,
                            tablet.getContractor(),
                            null,
                            userRepo.findByName("склад"),
                            null,
                            null,
                            sdf.format(date),
                            user);

                    transactionRepo.save(transaction);
                    tablet.getPlaceTablet().clear();
                    tablet.getPlaceTablet().add(placeTablet.stream().findFirst().get());
                    tablet.setContractor(null);
                    tablet.setCity(cityRepo.findByCity("Москва"));
                    tablet.setTransaction(transactionRepo.findFirstByTabletOrderByIdDesc(tabletRepo.findByNumber(s)));

                    tabletRepo.save(tablet);
                }




            }
            tabletMessage = "Планшеты: "+tabletsList.size()+"</br>";

        }

        if (!issue.get("akb").equals("")){
            Set<Place> placeAkb = Collections.singleton(Place.WAREHOUSE);
            String trim = issue.get("akb").trim();
            String[] akbs = trim.split(" ");
            List<String> akbList = new ArrayList<String>(Arrays.asList(akbs));
            akbList.remove(0);
            for(String s: akbList){
                Akb akb = akbRepo.findByNumber(s);
                akb.getPlaceAkb().clear();
                akb.getPlaceAkb().add(placeAkb.stream().findFirst().get());
                akb.setUser(null);
                akb.setContractor(null);
                akb.setInterviewer(null);
                akb.setCity(cityRepo.findByCity("Москва"));
                akb.setDate(sdf.format(date));
                akb.setRespUser(user);
                akbRepo.save(akb);

            }
            akbMessage = "Акб: "+akbList.size()+"</br>";

        }
        message = "Возвращены на склад:</br>"+tabletMessage+akbMessage;

        response.put("message", message);
        return new Response("Done",response);

    }

}
