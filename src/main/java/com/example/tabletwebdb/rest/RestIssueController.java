package com.example.tabletwebdb.rest;

import com.example.tabletwebdb.Enum.Place;
import com.example.tabletwebdb.Enum.Role;
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

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class RestIssueController {
    @Autowired
    private TabletRepo tabletRepo;

    @Autowired
    private AkbRepo akbRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private PurposeRepo purposeRepo;

    @PostMapping("/pins")
    public Response getPins(@RequestBody Map<String, String> tablet) {
        Map<String, String> response = new HashMap<>();
        Map<String, String> pins = new HashMap<>();
        String[] list = tablet.get("numbers").split(",");

        for (String s : list) {
            if (tabletRepo.findByNumber(s) != null) {
                pins.put(s, tabletRepo.findByNumber(s).getPin());
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
        String pinsString = "";
        try {
            pinsString = mapper.writeValueAsString(pins);
            if(pinsString.equals("{}")){
                return new Response("Null", "");
            }
            else {
                response.put("pins", pinsString);
                return new Response("Done", response);
            }

        } catch (Exception e) {
            return new Response("Error", "");
        }
    }

    @PostMapping("/devices")
    public Response getDevices(@RequestBody Map<String, String> devices) {
        Map<String, String> response = new HashMap<>();
        LinkedHashSet<List<String>> tablets = new LinkedHashSet<>();
        LinkedHashSet<List<String>> akb = new LinkedHashSet<>();
        String[] list = devices.get("numbers").split(",");

        if (!devices.get("numbers").equals("")){
            for (String s : list) {
                if (tabletRepo.findByNumber(s) != null) {
                    Tablet t = tabletRepo.findByNumber(s);
                    ArrayList<String> tabletList = new ArrayList<>();
                    tabletList.add(t.getNumber());
                    tabletList.add(t.getStatus().getStatus());
                    tabletList.add(t.getPlaceTablet().stream().findFirst().get().getName());
                    tablets.add(tabletList);
                }

                if (akbRepo.findByNumber(s) != null) {
                    Akb a = akbRepo.findByNumber(s);
                    ArrayList<String> akbList = new ArrayList<>();
                    akbList.add(a.getNumber());
                    akbList.add(a.getStatus().getStatus());
                    akbList.add(a.getPlaceAkb().stream().findFirst().get().getName());
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
                return new Response("Done", response);
            }

        } catch (Exception e) {
            return new Response("Error", "");
        }
    }

    @PostMapping("/managers")
    public Response getUsers(@RequestBody Map<String, String> manager){
        Map<String, String> response = new HashMap<>();
        ArrayList<String> find = new ArrayList<>();
        List <User> list = userRepo.findAll();

        for (User u: list) {
            if (u.isActive()&&u.getName().toLowerCase().contains(manager.get("manager").toLowerCase())){
                find.add(u.getName());
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.getFactory().configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        String findString = "";
        try {
            findString = mapper.writeValueAsString(find);
            response.put("managers", findString);
            if (findString.equals("[]")){
                return new Response("Null", "");
            }
            else {
                return new Response("Done", response);
            }

        } catch (Exception e) {
            return new Response("Error", "");
        }
    }


    @PostMapping("/saveIssue")
    public Response saveIssue(@RequestBody Map<String, String> issue,
                              @AuthenticationPrincipal User user){
        String message="";
        Map<String, String> response = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String tabletMessage ="";
        String akbMessage ="";

        if (!issue.get("tablets").equals("")){
            Set<TransType> transTablet = Collections.singleton(TransType.MANAGER);
            Set<Place> placeTablet = Collections.singleton(Place.MANAGER);
            String trim = issue.get("tablets").trim();
            String[] tablets = trim.split(" ");
            List<String> tabletsList = new ArrayList<String>(Arrays.asList(tablets));
            tabletsList.remove(0);
            for(String s: tabletsList){
                Transaction transaction = new Transaction(tabletRepo.findByNumber(s),
                        transTablet,
                        userRepo.findByName("склад"),
                        null,
                        null,
                        userRepo.findByName(issue.get("manager")),
                        null,
                        null,
                        sdf.format(date),
                        user);

                transactionRepo.save(transaction);

                Tablet tablet = tabletRepo.findByNumber(s);
                tablet.getPlaceTablet().clear();
                tablet.getPlaceTablet().add(placeTablet.stream().findFirst().get());
                tablet.setUser(userRepo.findByName(issue.get("manager")));
                tablet.setPurpose(purposeRepo.findByPurpose("свободен"));
                tablet.setTransaction(transactionRepo.findFirstByTabletOrderByIdDesc(tabletRepo.findByNumber(s)));

                tabletRepo.save(tablet);


            }
            tabletMessage = "Планшеты: "+tabletsList.size()+"</br>";

        }

        if (!issue.get("akb").equals("")){
            Set<Place> placeAkb = Collections.singleton(Place.MANAGER);
            String trim = issue.get("akb").trim();
            String[] akbs = trim.split(" ");
            List<String> akbList = new ArrayList<String>(Arrays.asList(akbs));
            akbList.remove(0);
            for(String s: akbList){
                Akb akb = akbRepo.findByNumber(s);
                akb.getPlaceAkb().clear();
                akb.getPlaceAkb().add(placeAkb.stream().findFirst().get());
                akb.setUser(userRepo.findByName(issue.get("manager")));
                akb.setDate(sdf.format(date));
                akb.setRespUser(user);
                akbRepo.save(akb);

            }
            akbMessage = "Акб: "+akbList.size()+"</br>";

        }
        message = "Выданы устройства</br>"+tabletMessage+akbMessage+"Менеджер: "+issue.get("manager");

        response.put("message", message);
        return new Response("Done",response);

    }
}
