package com.example.tabletwebdb.rest;

import com.example.tabletwebdb.entity.*;
import com.example.tabletwebdb.repos.*;
import com.example.tabletwebdb.responce.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class RestOtherTableController {

    @Autowired
    private CityRepo cityRepo;
    @Autowired
    private ModelAkbRepo modelAkbRepo;
    @Autowired
    private ModelTabletRepo modelTabletRepo;
    @Autowired
    private OsRepo osRepo;
    @Autowired
    private PurposeRepo purposeRepo;
    @Autowired
    private StatusRepo statusRepo;
    @Autowired
    private ContractorRepo contractorRepo;

    @PostMapping("/saveElement")
    public Response saveElement(@RequestBody Map<String, String> element){
        String[] idString = element.get("id").split("-");
        Long id = Long.parseLong(idString[1]);
        System.out.println(id);
        String errorMessage = "";
        Map<String, String> response = new HashMap<>();
        if (element.get("table").equals("cityOption")){
            if (cityRepo.findByCity(element.get("element"))!=null){
                errorMessage = "Город '"+element.get("element")+"' уже существует";
                response.put("message", errorMessage);
                return new Response("alreadyExists", response);
            }
            else {
                Optional<City> fromDb = cityRepo.findById(id);

                if (fromDb.isPresent()){
                    City city = fromDb.get();
                    city.setCity(element.get("element"));
                    cityRepo.save(city);
                }
                response.put("element", element.get("element"));
                response.put("test1", "test1");
                return new Response("Done", response);
            }
        }

        if (element.get("table").equals("modelTabletOption")){
            if (modelTabletRepo.findByModelTablet(element.get("element"))!=null){
                errorMessage = "Модель планшета '"+element.get("element")+"' уже существует";
                response.put("message", errorMessage);
                return new Response("alreadyExists", response);
            }
            else {
                Optional<ModelTablet> fromDb = modelTabletRepo.findById(id);

                if (fromDb.isPresent()){
                    ModelTablet modelTablet = fromDb.get();
                    modelTablet.setModelTablet(element.get("element"));
                    modelTabletRepo.save(modelTablet);
                }
                response.put("element", element.get("element"));
                response.put("test1", "test1");
                return new Response("Done", response);
            }
        }

        if (element.get("table").equals("modelAkbOption")){
            if (modelAkbRepo.findByModelAkb(element.get("element"))!=null){
                errorMessage = "Модель аккумулятора '"+element.get("element")+"' уже существует";
                response.put("message", errorMessage);
                return new Response("alreadyExists", response);
            }
            else {
                Optional<ModelAkb> fromDb = modelAkbRepo.findById(id);

                if (fromDb.isPresent()){
                    ModelAkb modelAkb = fromDb.get();
                    modelAkb.setModelAkb(element.get("element"));
                    modelAkbRepo.save(modelAkb);
                }
                response.put("element", element.get("element"));
                response.put("test1", "test1");
                return new Response("Done", response);
            }
        }

        if (element.get("table").equals("osOption")){
            if (osRepo.findByOs(element.get("element"))!=null){
                errorMessage = "Версия OS '"+element.get("element")+"' уже существует";
                response.put("message", errorMessage);
                return new Response("alreadyExists", response);
            }
            else {
                Optional<Os> fromDb = osRepo.findById(id);

                if (fromDb.isPresent()){
                    Os os = fromDb.get();
                    os.setOs(element.get("element"));
                    osRepo.save(os);
                }
                response.put("element", element.get("element"));
                response.put("test1", "test1");
                return new Response("Done", response);
            }
        }

        if (element.get("table").equals("purposeOption")){
            if (purposeRepo.findByPurpose(element.get("element"))!=null){
                errorMessage = "Цель '"+element.get("element")+"' уже существует";
                response.put("message", errorMessage);
                return new Response("alreadyExists", response);
            }
            else {
                Optional<Purpose> fromDb = purposeRepo.findById(id);

                if (fromDb.isPresent()){
                    Purpose purpose = fromDb.get();
                    purpose.setPurpose(element.get("element"));
                    purposeRepo.save(purpose);
                }
                response.put("element", element.get("element"));
                response.put("test1", "test1");
                return new Response("Done", response);
            }
        }

        if (element.get("table").equals("statusOption")){
            if (statusRepo.findByStatus(element.get("element"))!=null){
                errorMessage = "Статус '"+element.get("element")+"' уже существует";
                response.put("message", errorMessage);
                return new Response("alreadyExists", response);
            }
            else {
                Optional<Status> fromDb = statusRepo.findById(id);

                if (fromDb.isPresent()){
                    Status status = fromDb.get();
                    status.setStatus(element.get("element"));
                    statusRepo.save(status);
                }
                response.put("element", element.get("element"));
                response.put("test1", "test1");
                return new Response("Done", response);
            }
        }

        if (element.get("table").equals("contractorOption")){
            if (contractorRepo.findByCode(element.get("element"))!=null){
                errorMessage = "Подрядчик '"+element.get("element")+"' уже существует";
                response.put("message", errorMessage);
                return new Response("alreadyExists", response);
            }
            else {
                Optional<Contractor> fromDb = contractorRepo.findById(id);

                if (fromDb.isPresent()){
                    Contractor contractor = fromDb.get();
                    contractor.setCode(element.get("element"));
                    contractorRepo.save(contractor);
                }
                response.put("element", element.get("element"));
                response.put("test1", "test1");
                return new Response("Done", response);
            }
        }

        return null;
    }
}
