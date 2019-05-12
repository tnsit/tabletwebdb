package com.example.tabletwebdb.rest;

import com.example.tabletwebdb.entity.Akb;
import com.example.tabletwebdb.Enum.Place;
import com.example.tabletwebdb.entity.Tablet;
import com.example.tabletwebdb.repos.*;
import com.example.tabletwebdb.responce.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class RestAddDeviceController {

    @Autowired
    private TabletRepo tabletRepo;

    @Autowired
    private AkbRepo akbRepo;

    @Autowired
    private ModelTabletRepo modelTabletRepo;

    @Autowired
    private ModelAkbRepo modelAkbRepo;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private CityRepo cityRepo;

    @Autowired
    private OsRepo osRepo;

    private String errorMessage = "";

    @PostMapping("/addTablet")
    public Response addTablet(@RequestBody Map<String, String> tablet){
        Map<String, String> response = new HashMap<>();
        if (tabletRepo.findByNumber(tablet.get("number"))!=null){
            errorMessage = "Планшет с номером '"+tablet.get("number")+"' уже существует";
            response.put("message", errorMessage);
            return new Response("alreadyExists", response);
        } else if(tabletRepo.findByImei(tablet.get("imei"))!=null){
            errorMessage = "Планшет с IMEI '"+tablet.get("imei")+"' уже существует";
            response.put("message", errorMessage);
            return new Response("alreadyExists", response);
        } else if(tabletRepo.findByPhone(tablet.get("phone"))!=null){
            errorMessage = "Планшет с телефоном '"+tablet.get("phone")+"' уже существует";
            response.put("message", errorMessage);
            return new Response("alreadyExists", response);
        } else {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            String statusComment = null;
            Set<Place> placeTablet = Collections.singleton(Place.WAREHOUSE);

            if (!tablet.get("statusComment").isEmpty()){
                statusComment = tablet.get("statusComment");
            }
            String tabletComment = null;
            if (!tablet.get("tabletComment").isEmpty()){
                tabletComment = tablet.get("tabletComment");
            }

            Tablet newTablet = new Tablet(tablet.get("number"),
                    modelTabletRepo.findByModelTablet(tablet.get("model")),
                    statusRepo.findByStatus(tablet.get("status")),
                    statusComment,
                    tablet.get("imei"),
                    osRepo.findByOs(tablet.get("os")),
                    tablet.get("phone"),
                    tablet.get("pin"),
                    placeTablet,
                    cityRepo.findByCity("Москва"),
                    sdf.format(date),
                    "добавление в базу",
                    tabletComment
                    );

            tabletRepo.save(newTablet);
            errorMessage = "Планшет с номером '"+tablet.get("number")+"' добавлен в базу";
            response.put("message", errorMessage);
            return new Response("Done", response);
        }
    }

    @PostMapping("/addAkb")
    public Response addAkb(@RequestBody Map<String, String> akb){
        Map<String, String> response = new HashMap<>();
        if (akbRepo.findByNumber(akb.get("number"))!=null){
            errorMessage = "Акб с номером '"+akb.get("number")+"' уже существует";
            response.put("message", errorMessage);
            return new Response("alreadyExists", response);
        }
        else {
            Set<Place> placeAkb = Collections.singleton(Place.WAREHOUSE);
            Akb newAkb = new Akb(akb.get("number"),
                    modelAkbRepo.findByModelAkb(akb.get("model")),
                    statusRepo.findByStatus(akb.get("status")),
                    placeAkb,
                    cityRepo.findByCity("Москва"));
            akbRepo.save(newAkb);
            errorMessage = "Акб с номером '"+akb.get("number")+"' добавлен в базу";
            response.put("message", errorMessage);
            return new Response("Done", response);
        }
    }
}
