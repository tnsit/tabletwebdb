package com.example.tabletwebdb.rest;

import com.example.tabletwebdb.Enum.Place;
import com.example.tabletwebdb.Enum.TransType;
import com.example.tabletwebdb.entity.*;
import com.example.tabletwebdb.repos.*;
import com.example.tabletwebdb.responce.Response;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class RestShipment {

    @Autowired
    private ContractorRepo contractorRepo;

    @Autowired
    private CityRepo cityRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private TabletRepo tabletRepo;

    @Autowired
    private AkbRepo akbRepo;

    @Autowired
    private StatusRepo statusRepo;

    @Value("${upload.path}")
    private String uploadpath;


    @PostMapping("/templateParsing")
    public Response addFromExcel (@RequestPart("upload-file") MultipartFile uploadFile){
        Map<String, String> responseNotFound = new HashMap<>();
        List<String> allGood = new ArrayList<>();
        Map<String, List<String>> responseAllGood = new HashMap<>();
        Set<String> contractorNotFound = new LinkedHashSet<>();
        Set<String> cityNotFound = new LinkedHashSet<>();


        XSSFWorkbook tabletsExcel = null;
        try{
            tabletsExcel = new XSSFWorkbook(uploadFile.getInputStream());
        }
        catch (Exception e){

        }
        XSSFSheet sheet = tabletsExcel.getSheetAt(0);



        for (int i=1; i<=sheet.getLastRowNum();i++) {
            XSSFRow row = sheet.getRow(i);
            if (row!=null){
                if (row.getCell(6)!=null&&row.getCell(5)!=null){
                    if (contractorRepo.findByCode(row.getCell(6).getStringCellValue())==null){
                        contractorNotFound.add(row.getCell(6).getStringCellValue());
                    }

                    if (cityRepo.findByCity(row.getCell(5).getStringCellValue())==null){
                        cityNotFound.add(row.getCell(5).getStringCellValue());
                    }
                }
            }

        }
        StringBuilder cnf = new StringBuilder("Данные подрядчики и города не найдены в базе:</br>");
        if (!contractorNotFound.isEmpty()||!cityNotFound.isEmpty()){

            if (!contractorNotFound.isEmpty()){
                for (String s: contractorNotFound){
                    cnf.append(s+"</br>");
                }
                cnf.append("</br>");
                responseNotFound.put("cnf", cnf.toString());
            }
            if (!cityNotFound.isEmpty()){

                for (String s: cityNotFound){
                    cnf.append(s+"</br>");
                }
                responseNotFound.put("cnf", cnf.toString());
                return new Response("notFound", responseNotFound);
            }

            return new Response("notFound", responseNotFound);
        }


        else {
            for (int i=1; i<=sheet.getLastRowNum();i++) {
                XSSFRow row = sheet.getRow(i);
                ArrayList<String> shipmentElement = new ArrayList<>();
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                if (row!=null){
                    shipmentElement.add(row.getCell(0).getStringCellValue());
                    shipmentElement.add(row.getCell(6).getStringCellValue());
                    shipmentElement.add(row.getCell(5).getStringCellValue());
                    shipmentElement.add(row.getCell(1).getStringCellValue());
                    shipmentElement.add(row.getCell(3).getStringCellValue());
                    shipmentElement.add(sdf.format(row.getCell(9).getDateCellValue()));
                    if (row.getCell(13)!=null){
                        shipmentElement.add(row.getCell(13).getStringCellValue());
                    }
                    else {
                        shipmentElement.add("");
                    }

                    row.getCell(11).setCellType(CellType.STRING);
                    shipmentElement.add(row.getCell(11).getStringCellValue());

                    ObjectMapper mapper = new ObjectMapper();
                    mapper.getFactory().configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
                    String elementString = "";
                    try{
                        elementString = mapper.writeValueAsString(shipmentElement);
                        allGood.add(elementString);

                    }
                    catch (Exception e){

                    }

                }
            }


        }

        List<Contractor> contractorAll =  contractorRepo.findAll();
        List<String> contractorString = new ArrayList<>();
        for (Contractor c:contractorAll){
            contractorString.add(c.getCode());
        }

        List<City> cityAll =  cityRepo.findAll();
        List<String> cityString = new ArrayList<>();
        for (City c:cityAll){
            cityString.add(c.getCity());
        }


        responseAllGood.put("list",allGood);
        responseAllGood.put("contractor",contractorString);
        responseAllGood.put("city",cityString);
        return new Response("Done", responseAllGood);

    }


    @GetMapping("addLine")
    public Response addLine(){
        Map<String, List<String>> response = new HashMap<>();
        List<Contractor> contractorAll =  contractorRepo.findAllByOrderByCodeAsc();
        List<String> contractorString = new ArrayList<>();
        for (Contractor c:contractorAll){
            contractorString.add(c.getCode());
        }

        List<City> cityAll =  cityRepo.findAllByOrderByCityAsc();
        List<String> cityString = new ArrayList<>();
        for (City c:cityAll){
            cityString.add(c.getCity());
        }

        List<String> dateList = new LinkedList<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        dateList.add(sdf.format(date));


        response.put("contractor",contractorString);
        response.put("city",cityString);
        response.put("date",dateList);
        return new Response("Done", response);
    }

    @PostMapping("resultExcel")
    public Response resultExcel(@RequestParam Map<String,String> elements){
        @SuppressWarnings("unchecked")
        List<Map<String,String>> list = new ArrayList<Map<String,String>>(new Gson().fromJson(elements.get("elements"),List.class));
        Map<String, String> response = new HashMap<>();

        File uploadDir = new File(uploadpath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String sdf = " dd.MM.yyyy_HH.mm.ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sdf);
        Date date = new Date();
        String resultFilename = "";
        resultFilename = "Otpravka_"+simpleDateFormat.format(date)+".xlsx";
        try {
            File fileShipment = new File(uploadpath + "/" + resultFilename);
            FileOutputStream file = new FileOutputStream(fileShipment);
            getWorkbook(list).write(file);

            file.close();
            response.put("url", "/files/"+fileShipment.getName());
            return new Response("Done", response);
        }
        catch (Exception e){
            response.put("error", e.toString());
            return new Response("Error", response);
        }
    }


    private Workbook getWorkbook(List<Map<String,String>> list) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Отправка");
        Row row = sheet.createRow(0);
        sheet.createFreezePane(0, 1);
        int rowInt = 1;

        row.createCell(0).setCellValue("Менеджер");
        row.createCell(1).setCellValue("Город");
        row.createCell(2).setCellValue("Подрядчик");
        row.createCell(3).setCellValue("Юр. Лицо");
        row.createCell(4).setCellValue("Проект");
        row.createCell(5).setCellValue("№ накладной");
        row.createCell(6).setCellValue("№ пакета");
        row.createCell(7).setCellValue("Дата отправки");
        row.createCell(8).setCellValue("Номер устройства");

        for (Map<String,String> map: list){
            List<String> tabletsList = deviceStringToList(map.get("tablet"));
            List<String> akbList = deviceStringToList(map.get("akb"));

            ArrayList<String> devicesList = new ArrayList<>();
            if (tabletsList!=null) {
                devicesList.addAll(tabletsList);
            }
            if (akbList!=null) {
                devicesList.addAll(akbList);
            }


            for (String s: devicesList) {
                Row r = sheet.createRow(rowInt);
                r.createCell(0).setCellValue(map.get("manager"));
                r.createCell(1).setCellValue(map.get("city"));
                r.createCell(2).setCellValue(map.get("contractor"));
                r.createCell(3).setCellValue(map.get("person"));
                r.createCell(4).setCellValue(map.get("project"));
                r.createCell(6).setCellValue(map.get("packet"));
                r.createCell(7).setCellValue(map.get("date"));
                r.createCell(8).setCellValue(s);
                rowInt++;
            }
            rowInt++;

        }
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);
        sheet.autoSizeColumn(8);

        return workbook;
    }



    @PostMapping("resultSave")
    public Response resultSave(@RequestParam Map<String,String> elements,
                               @AuthenticationPrincipal User user) {
        @SuppressWarnings("unchecked")
        List<Map<String, String>> list = new ArrayList<Map<String, String>>(new Gson().fromJson(elements.get("elements"), List.class));
        Map<String, String> response = new HashMap<>();

        for (Map<String,String> map: list){
            List<String> tabletsList = deviceStringToList(map.get("tablet"));
            List<String> akbList = deviceStringToList(map.get("akb"));

            if (tabletsList!=null) {
                for (String s: tabletsList){
                    Set<TransType> transTablet = Collections.singleton(TransType.SHIPMENT);
                    Set<Place> placeTablet = Collections.singleton(Place.CONTRACTOR);
                    Transaction transaction = new Transaction(tabletRepo.findByNumber(s),
                            transTablet,
                            userRepo.findByName("склад"),
                            null,
                            null,
                            null,
                            contractorRepo.findByCode(map.get("contractor")),
                            null,
                            map.get("date"),
                            user
                            );
                    transactionRepo.save(transaction);

                    Tablet tablet = tabletRepo.findByNumber(s);
                    tablet.getPlaceTablet().clear();
                    tablet.getPlaceTablet().add(placeTablet.stream().findFirst().get());
                    tablet.setContractor(contractorRepo.findByCode(map.get("contractor")));
                    tablet.setCity(cityRepo.findByCity(map.get("city")));
                    tablet.setTransaction(transactionRepo.findFirstByTabletOrderByIdDesc(tabletRepo.findByNumber(s)));

                    tabletRepo.save(tablet);
                }
            }
            if (akbList!=null) {
                for (String s: akbList){
                    Set<Place> placeAkb = Collections.singleton(Place.CONTRACTOR);
                    Akb akb = akbRepo.findByNumber(s);
                    akb.getPlaceAkb().clear();
                    akb.getPlaceAkb().add(placeAkb.stream().findFirst().get());
                    akb.setContractor(contractorRepo.findByCode(map.get("contractor")));
                    akb.setDate(map.get("date"));
                    akb.setRespUser(user);
                    akbRepo.save(akb);

                }
            }


        }
        return new Response("Done","");
    }

    private List<String> deviceStringToList(String deviceString){
        List<String> deviceList = null;
        if (!deviceString.equals("")) {
            String tabletString = deviceString.trim();
            String[] tablets = tabletString.split(" ");
            deviceList = new ArrayList<String>(Arrays.asList(tablets));
            deviceList.remove(0);
        }
        return deviceList;
    }


    @PostMapping("/saveShipmentSoft")
    public Response saveShipmentSoft(@RequestBody Map<String, String> soft,
                              @AuthenticationPrincipal User user){
        String message="";
        Map<String, String> response = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String tabletMessage ="";
        String akbMessage ="";

        if (!soft.get("tablets").equals("")){
            Set<TransType> transTablet = Collections.singleton(TransType.SHIPMENT);
            Set<Place> placeTablet = Collections.singleton(Place.CONTRACTOR);
            String trim = soft.get("tablets").trim();
            String[] tablets = trim.split(" ");
            List<String> tabletsList = new ArrayList<String>(Arrays.asList(tablets));
            tabletsList.remove(0);
            for(String s: tabletsList){
                Transaction transaction = new Transaction(tabletRepo.findByNumber(s),
                        transTablet,
                        userRepo.findByName("склад"),
                        null,
                        null,
                        null,
                        contractorRepo.findByCode(soft.get("manager")),
                        null,
                        sdf.format(date),
                        user);

                transactionRepo.save(transaction);

                Tablet tablet = tabletRepo.findByNumber(s);
                tablet.getPlaceTablet().clear();
                tablet.getPlaceTablet().add(placeTablet.stream().findFirst().get());
                tablet.setContractor(contractorRepo.findByCode(soft.get("manager")));
                tablet.setCity(cityRepo.findByCity(soft.get("city")));
                tablet.setTransaction(transactionRepo.findFirstByTabletOrderByIdDesc(tabletRepo.findByNumber(s)));

                tabletRepo.save(tablet);


            }
            tabletMessage = "Планшеты: "+tabletsList.size()+"</br>";

        }

        if (!soft.get("akb").equals("")){
            Set<Place> placeAkb = Collections.singleton(Place.CONTRACTOR);
            String trim = soft.get("akb").trim();
            String[] akbs = trim.split(" ");
            List<String> akbList = new ArrayList<String>(Arrays.asList(akbs));
            akbList.remove(0);
            for(String s: akbList){
                Akb akb = akbRepo.findByNumber(s);
                akb.getPlaceAkb().clear();
                akb.getPlaceAkb().add(placeAkb.stream().findFirst().get());
                akb.setContractor(contractorRepo.findByCode(soft.get("manager")));
                akb.setCity(cityRepo.findByCity(soft.get("city")));
                akb.setRespUser(user);
                akb.setDate(sdf.format(date));
                akbRepo.save(akb);

            }
            akbMessage = "Акб: "+akbList.size()+"</br>";

        }
        message = "Отправлены:</br>"+tabletMessage+akbMessage+"Подрядчик: "+soft.get("manager");

        response.put("message", message);
        return new Response("Done",response);

    }





    @PostMapping("/saveShipmentOther")
    public Response saveShipmentOther(@RequestBody Map<String, String> other,
                                     @AuthenticationPrincipal User user){
        String message="";
        Map<String, String> response = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String tabletMessage ="";
        String akbMessage ="";

        if (!other.get("tablets").equals("")){
            Set<TransType> transTablet = Collections.singleton(TransType.OTHER);
            Set<Place> placeTablet = Collections.singleton(Place.OTHER);
            String trim = other.get("tablets").trim();
            String[] tablets = trim.split(" ");
            List<String> tabletsList = new ArrayList<String>(Arrays.asList(tablets));
            tabletsList.remove(0);
            for(String s: tabletsList){
                Transaction transaction = new Transaction(tabletRepo.findByNumber(s),
                        transTablet,
                        userRepo.findByName("склад"),
                        null,
                        null,
                        userRepo.findByName("нестандартная отправка"),
                        null,
                        null,
                        sdf.format(date),
                        user);

                transactionRepo.save(transaction);

                Tablet tablet = tabletRepo.findByNumber(s);
                tablet.getPlaceTablet().clear();
                tablet.getPlaceTablet().add(placeTablet.stream().findFirst().get());
                tablet.setUser(userRepo.findByName("нестандартная отправка"));
                tablet.setCity(cityRepo.findByCity(other.get("city")));
                if (!other.get("status").equals("Выберите статус")){
                    tablet.setStatus(statusRepo.findByStatus(other.get("status")));
                }
                if (!other.get("comment").equals("")){
                    tablet.setStatusComment(other.get("comment"));
                }
                tablet.setTransaction(transactionRepo.findFirstByTabletOrderByIdDesc(tabletRepo.findByNumber(s)));

                tabletRepo.save(tablet);


            }
            tabletMessage = "Планшеты: "+tabletsList.size()+"</br>";

        }

        if (!other.get("akb").equals("")){
            Set<Place> placeAkb = Collections.singleton(Place.OTHER);
            String trim = other.get("akb").trim();
            String[] akbs = trim.split(" ");
            List<String> akbList = new ArrayList<String>(Arrays.asList(akbs));
            akbList.remove(0);
            for(String s: akbList){
                Akb akb = akbRepo.findByNumber(s);
                akb.getPlaceAkb().clear();
                akb.getPlaceAkb().add(placeAkb.stream().findFirst().get());
                akb.setCity(cityRepo.findByCity(other.get("city")));
                akb.setRespUser(user);
                akb.setDate(sdf.format(date));
                if (!other.get("status").equals("Выберите статус")){
                    akb.setStatus(statusRepo.findByStatus(other.get("status")));
                }
                akbRepo.save(akb);

            }
            akbMessage = "Акб: "+akbList.size()+"</br>";

        }
        message = "Нестандартная отправка</br>"+tabletMessage+akbMessage+"Город: "+other.get("city");

        response.put("message", message);
        return new Response("Done",response);

    }

}
